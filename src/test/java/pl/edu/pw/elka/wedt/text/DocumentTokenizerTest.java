package pl.edu.pw.elka.wedt.text;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by zbanach on 19.01.16.
 */
public class DocumentTokenizerTest {
    private DocumentTokenizer tokenizer;

    @Before
    public void setUp() {
        tokenizer = new DocumentTokenizer();
    }

    @Test
    public void testSentenceTokenization() {
        String text = "Jakieś zdanie. I jeszcze jedno. Kolejne, ze znakami; interpunkcyjnymi.";

        List<String> sentences = tokenizer.tokenizeSentences(text);

        assertEquals("Jakieś zdanie.", sentences.get(0));
        assertEquals("I jeszcze jedno.", sentences.get(1));
        assertEquals("Kolejne, ze znakami; interpunkcyjnymi.", sentences.get(2));
    }

    @Test
    public void testWordTokenization() {
        String text = "Przykładowe zdanie, a to zdanie podrzędne.";

        List<String> words = tokenizer.tokenizeWords(text);

        assertEquals(8, words.size());
        assertEquals("Przykładowe", words.get(0));
        assertEquals("zdanie", words.get(1));
        assertEquals(",", words.get(2));

        for (String token : tokenizer.sentenceToCONLL(text))
            System.out.println(token);
    }
}
