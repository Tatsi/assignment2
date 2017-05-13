package ir_course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ir_course.Main.RankingMethod;

public class PrecisionRecallCurve {

	public static final int POINT_COUNT = 11;
	private static final int LIMIT = 1000;

	private RankingMethod rankingMethod;
	private boolean usePorter;
	private boolean removeStopWords;

	public PrecisionRecallCurve(RankingMethod rm, boolean usePorter, boolean removeStopWords) {
		this.rankingMethod = rm;
		this.usePorter = usePorter;
		this.removeStopWords = removeStopWords;
	}

	// what is relevant and what is not?
	private boolean isRelevant(DocumentInCollection doc, String query) {
		return doc.isRelevant();
	}

	// find 11 point precisions for multiple queries as average
	public List<Float> calculateCurve(List<DocumentInCollection> docs) {
		List<Float> points = new ArrayList<Float>(11);
		for (int i = 0; i < POINT_COUNT; i++) {
			points.add(0.0f); // initialize with 0s
		}

		List<String> queries = new ArrayList<String>();

		// add some queries
		queries.add("Automatic or semiautomatic video tagging");
		queries.add("Content based video annotation");
		queries.add("feature based Multimedia annotation");
		queries.add("Models and techniques used for video tagging or annotation");

		for (String query : queries) {
			List<Float> queryPoints = getPrecisions(docs, query);
			// store the sum for all queries in points
			for (int i = 0; i < POINT_COUNT; i++) {
				points.set(i, points.get(i) + queryPoints.get(i));
			}

		}

		for (int i = 0; i < POINT_COUNT; i++) {
			float total = points.get(i);
			points.set(i, total / queries.size()); // get the average precision
		}

		return points; // our points for the curve
	}

	// find 11 point precisions for a query
	private List<Float> getPrecisions(List<DocumentInCollection> docs, String query) {
		List<Float> precisions = new ArrayList<Float>(); //interpolated precisions for 11 points
		LuceneSearch searchApp = new LuceneSearch();
		try {
			searchApp.index(docs);
			//do the search and get all results
			List<DocumentInCollection> results = searchApp.search(query, LIMIT, this.rankingMethod, this.usePorter, this.removeStopWords); 
			//find total number of relevant items
			int totalRelevant = 0;
			for (DocumentInCollection doc : results) {
				if (isRelevant(doc, query)) {
					totalRelevant++;
				}
			}
			
			//find precision-recall pairings
			List<PRPoint> prpoints = calculatePrPoints(results,query,totalRelevant);
			

			for (int i = 0; i < POINT_COUNT; i++) {
				float recall = i * 0.1f;
				float precision = findPrecision(prpoints, recall);
				precisions.add(precision);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return precisions;
	}

	//find all precision-recall pairings
	private List<PRPoint> calculatePrPoints(List<DocumentInCollection> results, String query, int totalRelevant) {
		List<PRPoint> prpoints = new ArrayList<PRPoint>();
		
		float tp = 0; // true positives
		float fp = 0; // false positives

		for (DocumentInCollection doc : results) {
			if (isRelevant(doc, query)) {
				tp++;
			} else {
				fp++;
			}
			PRPoint point = new PRPoint();
			point.precision = tp/(tp+fp);
			point.recall = tp/totalRelevant;
			prpoints.add(point);
		}
		
		
		return prpoints;
	}

	// find a precision at a recall level by interpolation
	private float findPrecision(List<PRPoint> points, float recall) {
		if(recall==0){
			return 1;
		}
		float max = 0;
		for(PRPoint p:points){
			if(p.recall>=recall && p.precision>max){
				max = p.precision;
			}
		}
		return max;
	}
}
