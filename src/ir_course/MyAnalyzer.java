package ir_course;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

public class MyAnalyzer extends Analyzer {
	private boolean usePorter;
	private boolean removeStopWords;

	public MyAnalyzer(boolean usePorter, boolean removeStopWords) {
		super();
		this.usePorter = usePorter;
		this.removeStopWords = removeStopWords;
	}

	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		Tokenizer tokenizer = new StandardTokenizer();
		TokenStream result = new StandardFilter(tokenizer);
		result = new LowerCaseFilter(result);
		if(removeStopWords){
			result = new StopFilter(result, EnglishAnalyzer.getDefaultStopSet());
		}
		if(usePorter){
			result = new PorterStemFilter(result);
		}
		
		return new TokenStreamComponents(tokenizer, result);
	}
}
