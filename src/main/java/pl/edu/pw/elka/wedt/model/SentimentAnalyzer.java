package pl.edu.pw.elka.wedt.model;

import pl.edu.pw.elka.wedt.dataSources.CsvReader;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Artur Góralewski.
 */
public class SentimentAnalyzer {

    public final static String STH = "sth"; //do not use
    public final static String BINARY_SENTIMENT = "binarySentiment";
    public final static String SMALL_SCALE_SENTIMENT = "smallScaleSentiment";
    public final static String BIG_SCALE_SENTIMENT = "bigScaleSentiment";
    public final static String SO_PMI_SCORE = "SO-PMI_score";

    //<word, <sentimentScale{sth,binarySentiment,smallScaleSentiment,bigScaleSentiment,SO-PMI_score}, value>>
    Map<String, Map<String, Object>> sentimentDictionary;

    OpinionPreparer opinionPreparer;

    public SentimentAnalyzer() {
        String sentimentDictionaryPath = Paths.get(".").toAbsolutePath().normalize().toString()+"/src/main/resources/slownikWydzwieku01.csv";

        this.sentimentDictionary = new CsvReader(sentimentDictionaryPath).makeSentimentDictionary();
        this.opinionPreparer = new OpinionPreparer();
    }

    public double analyzeSentiment(final Opinion opinion, final String sentimentScale){
        Map<String, Object> sentiments;
        for(Word word : opinion.getWordList()){
            sentiments = sentimentDictionary.get(word.getStem());
            if(sentiments != null) {
                word.setSentiment(new Double(sentiments.get(sentimentScale).toString()));
            }
        }
        return opinion.getSentiment();
    }

    public double sumUpSentiment(List<String> unprocessedOpinions, final String sentimentScale){
        double sentimentSum = 0;
        for (String unprocessedOpinion : unprocessedOpinions){
            sentimentSum += analyzeSentiment(opinionPreparer.prepareOpinion(unprocessedOpinion), sentimentScale);
        }
        return sentimentSum/unprocessedOpinions.size();
    }
}
