package pl.edu.pw.elka.wedt;


import morfologik.stemming.WordData;
import pl.edu.pw.elka.wedt.model.Opinion;
import pl.edu.pw.elka.wedt.model.OpinionPreparer;
import pl.edu.pw.elka.wedt.model.SentimentAnalyzer;
import pl.edu.pw.elka.wedt.model.Word;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        OpinionPreparer opinionPreparer = new OpinionPreparer();
        List<String> unprocessedOpinions = new ArrayList<String>(){{
            add("Nie zabrakło oczywiście wpadek.\n"+
                    "Największym zaskoczeniem okazał się dla nas strój Katarzyny Zielińskiej, której ewidentnie o coś chodziło, ale wciąż nie wiemy o co.");
            add("cześć świecie");
            add("Lubię ten czeski samochód");
        }};

        System.out.println(sentimentAnalyzer.sumUpSentiment(unprocessedOpinions, SentimentAnalyzer.SMALL_SCALE_SENTIMENT));




        String toStemmed =  "pojechał";
        List<WordData> wordList = opinionPreparer.stemmer.lookup(toStemmed);
        WordData stemmed = wordList.isEmpty() ? null : wordList.get(0);
        System.out.println(wordList.isEmpty()+" "+stemmed);
        Word word = new Word(stemmed);
        System.out.println(word.getPOS());


        toStemmed =  "wyszłaś";
        wordList = opinionPreparer.stemmer.lookup(toStemmed);
        for (WordData wd : wordList) {
            System.out.println(wd.getWord()+" stem: "+wd.getStem()+ " isInDict " + opinionPreparer.speller.isInDictionary(wd.getStem())+
                                            " misspelled: "+ opinionPreparer.speller.isMisspelled(wd.getStem().toString()));
        }



    }
}
