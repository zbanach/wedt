package pl.edu.pw.elka.wedt.model;

import morfologik.speller.Speller;
import morfologik.stemming.Dictionary;
import morfologik.stemming.DictionaryLookup;
import morfologik.stemming.WordData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Artur Góralewski.
 */
public class OpinionPreparer {

    //public to debug
    public Speller speller;
    public DictionaryLookup stemmer; //zamiennie to samo - PolishStemmer polishStem = new PolishStemmer();

    LinkedHashMap<String, String> conversion = new LinkedHashMap<String, String>(){{
        put("lubie", "lubię");

    }};


    public OpinionPreparer() {
        try {
            Dictionary polishDictionary = Dictionary.read(getClass().getResource("pl.dict"));
            this.speller = new Speller(polishDictionary);
            this.stemmer = new DictionaryLookup(polishDictionary);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Opinion prepareOpinion(String opinion){
        ArrayList<Word> wordList = new ArrayList<>();
        for (String word : opinion.split("[\\s\\.\\,\\~\\!\\#\\$\\%\\^\\&\\*\\(\\)\\-\\_\\+\\=\\'\\|\\{\\}\\:\\;]+")) {//split to atomic words
            for (String part : word.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])"))//split CamelCase
                wordList.add(createWord(part));
        }
        return new Opinion(wordList);
    }

    public Word createWord(final String word){
        String temp = word.toLowerCase(new Locale("pl"));

        if(conversion.containsKey(temp))
            temp = conversion.get(temp);

        if(speller.isMisspelled(temp)) {
            List<String> replacements = speller.findReplacements(temp);
            if(!replacements.isEmpty())
                temp = replacements.get(0);
        }
        WordData wd = stemmer.lookup(temp).get(0);

        return new Word(wd, word); // hmmmm czy zapisaywac blednie napisane slowa?? czy nowe
    }
}
