package pl.edu.pw.elka.wedt.model;



import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Artur Góralewski.
 */
public class WordTest {

    @Test
    public void testGetPOS() throws Exception {
        PolishStemmer polishStem = new PolishStemmer();
        String toStemmed =  "pojechał";
        List<WordData> wordList = polishStem.lookup(toStemmed);
        WordData stemmed = wordList.isEmpty() ? null : wordList.get(0);
        System.out.println(wordList.isEmpty()+" "+stemmed);
        Word word = new Word(stemmed);
        assertEquals("verb", word.getPOS());

    }
}