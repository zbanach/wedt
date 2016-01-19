package pl.edu.pw.elka.wedt;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zbanach on 19.01.16.
 */
public class StanfordNlpTest {

    @Test
    public void testSentenceTokenization() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String text = "Ala ma kota, a kot ma Alę. To jest jakieś inne zdanie. I jeszcze jedno.";
        Annotation document = new Annotation(text);

        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for(CoreMap sentence: sentences)
            System.out.println(sentence);

        PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<>(new StringReader(text), new CoreLabelTokenFactory(), "");
        while (tokenizer.hasNext()) {
            CoreLabel word = tokenizer.next();
            System.out.println(word);
        }
    }
}
