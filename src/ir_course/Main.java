package ir_course;

import java.util.List;



public class Main {
	public enum RankingMethod {
	    VSM, BM25, LANGUAGE_MODEL
	}
	public static void main(String[] args) {
		DocumentCollectionParser parser = new DocumentCollectionParser();
		parser.parse("corpus_part2.xml");
		List<DocumentInCollection> docs = parser.getDocuments();
		
		
		// Case 1 a: VSM, porter stemmer, with stop words
		PrecisionRecallCurve prcurve_1a = new PrecisionRecallCurve(RankingMethod.VSM, true, false);
		List<Float> points_1a = prcurve_1a.calculateCurve(docs);
		
		// Case 1 b: VSM, porter stemmer, without stop words = stop words are removed
		PrecisionRecallCurve prcurve_1b = new PrecisionRecallCurve(RankingMethod.VSM, true, true);
		List<Float> points_1b = prcurve_1b.calculateCurve(docs);
		
		// Case 1 c: VSM, without porter stemmer, with stop words
		PrecisionRecallCurve prcurve_1c = new PrecisionRecallCurve(RankingMethod.VSM, false, false);
		List<Float> points_1c = prcurve_1c.calculateCurve(docs);
		
		// Case 1 d: VSM, without porter stemmer, without stop words = stop words are removed
		PrecisionRecallCurve prcurve_1d = new PrecisionRecallCurve(RankingMethod.VSM, false, true);
		List<Float> points_1d = prcurve_1d.calculateCurve(docs);
		
		
		// Case 2 a: BM25, porter stemmer, with stop words
		PrecisionRecallCurve prcurve_2a = new PrecisionRecallCurve(RankingMethod.BM25, true, false);
		List<Float> points_2a = prcurve_2a.calculateCurve(docs);
		
		// Case 2 b: BM25, porter stemmer, without stop words = stop words are removed
		PrecisionRecallCurve prcurve_2b = new PrecisionRecallCurve(RankingMethod.BM25, true, true);
		List<Float> points_2b = prcurve_2b.calculateCurve(docs);
		
		// Case 2 c: BM25, without porter stemmer, with stop words
		PrecisionRecallCurve prcurve_2c = new PrecisionRecallCurve(RankingMethod.BM25, false, false);
		List<Float> points_2c = prcurve_2c.calculateCurve(docs);
		
		// Case 2 d: BM25, without porter stemmer, without stop words = stop words are removed
		PrecisionRecallCurve prcurve_2d = new PrecisionRecallCurve(RankingMethod.BM25, false, true);
		List<Float> points_2d = prcurve_2d.calculateCurve(docs);
		
		
		// Case 3 a: Dirichlet Language Model, porter stemmer, with stop words
		PrecisionRecallCurve prcurve_3a = new PrecisionRecallCurve(RankingMethod.LANGUAGE_MODEL, true, false);
		List<Float> points_3a = prcurve_3a.calculateCurve(docs);
		
		// Case 3 b: Dirichlet Language Model, porter stemmer, without stop words = stop words are removed
		PrecisionRecallCurve prcurve_3b = new PrecisionRecallCurve(RankingMethod.LANGUAGE_MODEL, true, true);
		List<Float> points_3b = prcurve_3b.calculateCurve(docs);
		
		// Case 3 c: Dirichlet Language Model, without porter stemmer, with stop words
		PrecisionRecallCurve prcurve_3c = new PrecisionRecallCurve(RankingMethod.LANGUAGE_MODEL, false, false);
		List<Float> points_3c = prcurve_3c.calculateCurve(docs);
		
		// Case 3 d: Dirichlet Language Model, without porter stemmer, without stop words = stop words are removed
		PrecisionRecallCurve prcurve_3d = new PrecisionRecallCurve(RankingMethod.LANGUAGE_MODEL, false, true);
		List<Float> points_3d = prcurve_3d.calculateCurve(docs);
		
		System.out.println(""); System.out.println("");
		System.out.println("Recall,1a,1b,1c,1d,2a,2b,2c,2d,3a,3b,3c,3d");
		
		for(int i=0;i<PrecisionRecallCurve.POINT_COUNT;i++){
			System.out.print(i/10.0 + ",");
			
			System.out.print(points_1a.get(i) + ",");
			System.out.print(points_1b.get(i) + ",");
			System.out.print(points_1c.get(i) + ",");
			System.out.print(points_1d.get(i) + ",");
			
			System.out.print(points_2a.get(i) + ",");
			System.out.print(points_2b.get(i) + ",");
			System.out.print(points_2c.get(i) + ",");
			System.out.print(points_2d.get(i) + ",");
			
			System.out.print(points_3a.get(i) + ",");
			System.out.print(points_3b.get(i) + ",");
			System.out.print(points_3c.get(i) + ",");
			System.out.println(points_3d.get(i));
		}

	}

}
