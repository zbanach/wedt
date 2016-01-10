package pl.edu.pw.elka.wedt.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Artur Góralewski.
 */
public class SentimentAnalyzerTest {

    SentimentAnalyzer sentimentAnalyzer;
    OpinionPreparer opinionPreparer;
    @Before
    public void setUp() throws Exception {
        sentimentAnalyzer = new SentimentAnalyzer();
        opinionPreparer = new OpinionPreparer();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAnalyzeSentiment() throws Exception {
                                                        // -1     -1       -1    brak=0 brak
        Opinion opinion = opinionPreparer.prepareOpinion("Bardzo brzydki samochód. źle jeździ.");
        double sentiment = sentimentAnalyzer.analyzeSentiment(opinion, SentimentAnalyzer.SMALL_SCALE_SENTIMENT);
        assertEquals(sentiment, -3.0, 1e-15);
    }
}