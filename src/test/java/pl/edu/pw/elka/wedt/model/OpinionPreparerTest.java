package pl.edu.pw.elka.wedt.model;

import morfologik.stemming.WordData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Artur Góralewski.
 */
public class OpinionPreparerTest {

    OpinionPreparer opinionPreparer;
    @Before
    public void setUp() throws Exception {
        opinionPreparer = new OpinionPreparer();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateWord() throws Exception {
        Word word = opinionPreparer.createWord("traktorr");
        assertEquals("traktor", word.getStem());
        assertEquals("traktorr", word.getWord()); // ?????? czy chcemy oryginał
        assertEquals("subst", word.getPOS());
    }

    @Test
    public void testPrepareOpinion() throws Exception {
        Opinion opinion = opinionPreparer.prepareOpinion("Ala ma kota.");
        assertTrue(opinion.getWordList().size() == 3);

//        assertEquals("kot", opinion.getWordList().get(2).getStem());
//        getStemnie przechodzi poniewaz: kota, kot, kot, kot. a my bierzemy pierwszy ze znalezionych rdzeni
//                for (WordData wordData : opinionPreparer.stemmer.lookup("kota")) {
//                    System.out.println(wordData);
//                }
    }



    @Test
    public void testLemmatization() throws Exception {
        Word word = opinionPreparer.createWord("traktorr"); //błąd wstawienia
        assertEquals("traktor", word.getStem());

        word = opinionPreparer.createWord("trakstor"); //błąd wstawienia
        assertEquals("traktor", word.getStem());

        word = opinionPreparer.createWord("trktor"); //błąd skasowania
        assertEquals("traktor", word.getStem());

        word = opinionPreparer.createWord("trkstor"); //błąd wstawienia i skasowania
        assertEquals("traktor", word.getStem());

        word = opinionPreparer.createWord("traktro"); //błąd transpozycji
        assertEquals("traktor", word.getStem());

        word = opinionPreparer.createWord("tragtor"); //błąd substytucji
        assertEquals("traktor", word.getStem());

        word = opinionPreparer.createWord("żółc"); //błąd substytucji
        assertEquals("żółć", word.getStem());

        word = opinionPreparer.createWord("rzuc");
        assertEquals("rzucić", word.getStem());

    }



}