package ir_course;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		DocumentCollectionParser parser = new DocumentCollectionParser();
		parser.parse("corpus_part2.xml");
		List<DocumentInCollection> docs = parser.getDocuments();
		PrecisionRecallCurve prcurve = new PrecisionRecallCurve(false);
		List<Float> points = prcurve.calculateCurve(docs);
		
		for(int i=0;i<PrecisionRecallCurve.POINT_COUNT;i++){
			System.out.println(points.get(i));
		}

	}

}
