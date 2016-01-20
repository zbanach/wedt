package pl.edu.pw.elka.wedt.model;

import org.maltparser.MaltParserService;
import org.maltparser.core.exception.MaltChainedException;
import org.maltparser.core.syntaxgraph.DependencyStructure;
import pl.edu.pw.elka.wedt.dataSources.CsvReader;
import pl.edu.pw.elka.wedt.text.DocumentTokenizer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zbanach on 20.01.16.
 */
public class SentimentAnalyzerWithNegation {
    private static final Map<String, Map<String, Object>> sentimentDictionary;
    private static final MaltParserService maltParser;
    private static final DocumentTokenizer tokenizer;


    static {
        String sentimentDictionaryPath = SentimentAnalyzerWithNegation.class.getResource("slownikWydzwieku01.csv").getPath();
        sentimentDictionary = new CsvReader(sentimentDictionaryPath).makeSentimentDictionary();
        tokenizer = new DocumentTokenizer();

        try {
            maltParser = new MaltParserService();
            maltParser.initializeParserModel("-c " +
                    "skladnica_liblinear_stackeager_final -m parse -w " +
                    "." +
                    " -lfi" +
                    " parser.log");
        } catch (MaltChainedException e) {
            throw new RuntimeException(e);
        }
    }

    public double analyze(String opinion) {
        double sentiment = 0.0;

        for (String sentence : tokenizer.tokenizeSentences(opinion)) {

        }

        return sentiment;
    }

    private double analyzeSentence(String sentence) {
        double sentiment = 0.0;
        String[] tokens = tokenizer.sentenceToCONLL(sentence);
        try {
            DependencyStructure graph = maltParser.parse(tokens);
            List<Integer> negationsParents = new LinkedList<>();
        } catch (MaltChainedException e) {
            throw new RuntimeException(e);
        }
        return sentiment;
    }
}
