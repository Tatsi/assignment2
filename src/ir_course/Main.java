package ir_course;

import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Main {
	public enum RankingMethod {
		VSM, BM25, LANGUAGE_MODEL
	}
	public static void main(String[] args) throws FileNotFoundException {
		DocumentCollectionParser parser = new DocumentCollectionParser();
		parser.parse("corpus_part2.xml");
		List<DocumentInCollection> docs = parser.getDocuments();
		
		
		// Case 1 a: VSM, porter stemmer, without stop word removal
		PrecisionRecallCurve prcurve_1a = new PrecisionRecallCurve(RankingMethod.VSM, true, false);
		List<Float> points_1a = prcurve_1a.calculateCurve(docs);
		
		// Case 1 b: VSM, porter stemmer, with stop word removal
		PrecisionRecallCurve prcurve_1b = new PrecisionRecallCurve(RankingMethod.VSM, true, true);
		List<Float> points_1b = prcurve_1b.calculateCurve(docs);
		
		// Case 1 c: VSM, without porter stemmer, without stop word removal
		PrecisionRecallCurve prcurve_1c = new PrecisionRecallCurve(RankingMethod.VSM, false, false);
		List<Float> points_1c = prcurve_1c.calculateCurve(docs);
		
		// Case 1 d: VSM, without porter stemmer, with stop word removal
		PrecisionRecallCurve prcurve_1d = new PrecisionRecallCurve(RankingMethod.VSM, false, true);
		List<Float> points_1d = prcurve_1d.calculateCurve(docs);
		
		
		// Case 2 a: BM25, porter stemmer, without stop word removal
		PrecisionRecallCurve prcurve_2a = new PrecisionRecallCurve(RankingMethod.BM25, true, false);
		List<Float> points_2a = prcurve_2a.calculateCurve(docs);
		
		// Case 2 b: BM25, porter stemmer, with stop word removal
		PrecisionRecallCurve prcurve_2b = new PrecisionRecallCurve(RankingMethod.BM25, true, true);
		List<Float> points_2b = prcurve_2b.calculateCurve(docs);
		
		// Case 2 c: BM25, without porter stemmer, without stop word removal
		PrecisionRecallCurve prcurve_2c = new PrecisionRecallCurve(RankingMethod.BM25, false, false);
		List<Float> points_2c = prcurve_2c.calculateCurve(docs);
		
		// Case 2 d: BM25, without porter stemmer, with stop word removal
		PrecisionRecallCurve prcurve_2d = new PrecisionRecallCurve(RankingMethod.BM25, false, true);
		List<Float> points_2d = prcurve_2d.calculateCurve(docs);
		
		
		// Case 3 a: Dirichlet Language Model, porter stemmer, without stop word removal
		PrecisionRecallCurve prcurve_3a = new PrecisionRecallCurve(RankingMethod.LANGUAGE_MODEL, true, false);
		List<Float> points_3a = prcurve_3a.calculateCurve(docs);
		
		// Case 3 b: Dirichlet Language Model, porter stemmer, with stop word removal
		PrecisionRecallCurve prcurve_3b = new PrecisionRecallCurve(RankingMethod.LANGUAGE_MODEL, true, true);
		List<Float> points_3b = prcurve_3b.calculateCurve(docs);
		
		// Case 3 c: Dirichlet Language Model, without porter stemmer, without stop word removal
		PrecisionRecallCurve prcurve_3c = new PrecisionRecallCurve(RankingMethod.LANGUAGE_MODEL, false, false);
		List<Float> points_3c = prcurve_3c.calculateCurve(docs);
		
		// Case 3 d: Dirichlet Language Model, without porter stemmer, with stop word removal
		PrecisionRecallCurve prcurve_3d = new PrecisionRecallCurve(RankingMethod.LANGUAGE_MODEL, false, true);
		List<Float> points_3d = prcurve_3d.calculateCurve(docs);

		// Opens a CSV file for storing the results
		PrintWriter pw = new PrintWriter(new File("results.csv"));
		StringBuilder sb = new StringBuilder();
		
		sb.append("Recall,1a,1b,1c,1d,2a,2b,2c,2d,3a,3b,3c,3d\n");
		
		for(int i=0;i<PrecisionRecallCurve.POINT_COUNT;i++){
			sb.append(i/10.0 + ",");
			sb.append(points_1a.get(i) + ",");
			sb.append(points_1b.get(i) + ",");
			sb.append(points_1c.get(i) + ",");
			sb.append(points_1d.get(i) + ",");
			
			sb.append(points_2a.get(i) + ",");
			sb.append(points_2b.get(i) + ",");
			sb.append(points_2c.get(i) + ",");
			sb.append(points_2d.get(i) + ",");
			
			sb.append(points_3a.get(i) + ",");
			sb.append(points_3b.get(i) + ",");
			sb.append(points_3c.get(i) + ",");
			sb.append(points_3d.get(i) + "\n");
		}
		System.out.println("\n\nResults:");

		System.out.print(sb.toString());

		// Calculate area under curve for all test cases
		Double[] VSM_area = {0.0, 0.0, 0.0, 0.0};
		Double[] BM25_area = {0.0, 0.0, 0.0, 0.0};
		Double[] LM_area = {0.0, 0.0, 0.0, 0.0};

		for(int i=0;i<PrecisionRecallCurve.POINT_COUNT-1;i++){
			VSM_area[0] += 0.1*0.5*(points_1a.get(i)+points_1a.get(i+1));
			VSM_area[1] += 0.1*0.5*(points_1b.get(i)+points_1b.get(i+1));
			VSM_area[2] += 0.1*0.5*(points_1c.get(i)+points_1c.get(i+1));
			VSM_area[3] += 0.1*0.5*(points_1d.get(i)+points_1d.get(i+1));

			BM25_area[0] += 0.1*0.5*(points_2a.get(i)+points_2a.get(i+1));
			BM25_area[1] += 0.1*0.5*(points_2b.get(i)+points_2b.get(i+1));
			BM25_area[2] += 0.1*0.5*(points_2c.get(i)+points_2c.get(i+1));
			BM25_area[3] += 0.1*0.5*(points_2d.get(i)+points_2d.get(i+1));
			
			LM_area[0] += 0.1*0.5*(points_3a.get(i)+points_3a.get(i+1));
			LM_area[1] += 0.1*0.5*(points_3b.get(i)+points_3b.get(i+1));
			LM_area[2] += 0.1*0.5*(points_3c.get(i)+points_3c.get(i+1));
			LM_area[3] += 0.1*0.5*(points_3d.get(i)+points_3d.get(i+1));
		}

		System.out.println("\nArea under curve");

		System.out.print("VSM: ");
		for(int i=0; i<4; i++) {
			System.out.print(VSM_area[i] + " ");
		}
		System.out.print("\nBM25: ");
		for(int i=0; i<4; i++) {
			System.out.print(BM25_area[i] + " ");
		}
		System.out.print("\nLM: ");
		for(int i=0; i<4; i++) {
			System.out.print(LM_area[i] + " ");
		}

		pw.write(sb.toString());
		pw.close();

		System.out.println("\nresults.csv saved!");
	}
}
