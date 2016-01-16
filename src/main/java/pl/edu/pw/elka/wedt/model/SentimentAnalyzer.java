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

//    public final static String STH = "sth"; //do not use
    public final static String BINARY_SENTIMENT = "binarySentiment";
    public final static String SMALL_SCALE_SENTIMENT = "smallScaleSentiment";
    public final static String BIG_SCALE_SENTIMENT = "bigScaleSentiment";
    public final static String SO_PMI_SCORE = "SO-PMI_score";

    //<word, <sentimentScale{sth,binarySentiment,smallScaleSentiment,bigScaleSentiment,SO-PMI_score}, value>>
    Map<String, Map<String, Object>> sentimentDictionary;

    OpinionPreparer opinionPreparer;

    public SentimentAnalyzer() {
        String sentimentDictionaryPath = getClass().getResource("slownikWydzwieku01.csv").getPath();
        this.sentimentDictionary = new CsvReader(sentimentDictionaryPath).makeSentimentDictionary();
        this.opinionPreparer = new OpinionPreparer();
    }

    public double analyzeSentiment(final Opinion opinion, final String sentimentScale){
        Map<String, Object> sentiments;
        int counter = 0;
        for(Word word : opinion.getWordList()){
            sentiments = sentimentDictionary.get(word.getStem());
            if(sentiments != null) {
                word.setSentiment(new Double(sentiments.get(sentimentScale).toString()));
                counter++;
            }
        }
        double sentiment = counter>0?opinion.getSentiment()/counter: 0;
        switch (sentimentScale){
            case SMALL_SCALE_SENTIMENT:
                return sentiment*2 + 3;
            case BIG_SCALE_SENTIMENT:
                return sentiment + 3;
            case BINARY_SENTIMENT:
                return Math.floor(sentiment);
            case SO_PMI_SCORE:
                return sentiment/5 + 3;
            default:
                throw new IllegalArgumentException("wrong sentiment scale");
        }
    }

    public double sumUpSentiment(List<String> unprocessedOpinions, final String sentimentScale){
        double sentimentSum = 0;
        for (String unprocessedOpinion : unprocessedOpinions){
            sentimentSum += analyzeSentiment(opinionPreparer.prepareOpinion(unprocessedOpinion), sentimentScale);
        }
        return sentimentSum/unprocessedOpinions.size();
    }
}
