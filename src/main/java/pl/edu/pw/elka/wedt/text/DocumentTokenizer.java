package pl.edu.pw.elka.wedt.text;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.CoreMap;
import morfologik.stemming.WordData;
import pl.edu.pw.elka.wedt.model.OpinionPreparer;

import java.io.StringReader;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by zbanach on 19.01.16.
 */
public class DocumentTokenizer {
    private static final StanfordCoreNLP stanfordSentenceTokenizingPipeline;
    private static final OpinionPreparer opinionPreparer;


    static {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit");
        stanfordSentenceTokenizingPipeline = new StanfordCoreNLP(props);
        opinionPreparer = new OpinionPreparer();
    }

    public List<String> tokenizeSentences(String document) {
        Annotation stanfordDoc = new Annotation(document);
        stanfordSentenceTokenizingPipeline.annotate(stanfordDoc);
        List<CoreMap> sentences = stanfordDoc.get(CoreAnnotations.SentencesAnnotation.class);

        return sentences.stream().map(CoreMap::toString).collect(toList());
    }

    public List<String> tokenizeWords(String document) {
        PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<>(new StringReader(document), new CoreLabelTokenFactory(), "");
        List<String> words = new LinkedList<>();
        while (tokenizer.hasNext())
            words.add(tokenizer.next().toString());

        return words;
    }

    public String[] sentenceToCONLL(String sentence) {
        List<String> tokens = tokenizeWords(sentence);
        List<String> conllTokens = new LinkedList<>();
        int index = 1;

        for (String token : tokenizeWords(sentence)) {
            StringBuilder out = new StringBuilder();
            if (token.equals(",") || token.equals(".") || token.equals(";")) {
                out.append(Integer.toString(index)).append("\t")
                        .append(token).append("\t")
                        .append(token).append("\t")
                        .append("interp\tinterp\t_\t_\t_\t_\t_");
                conllTokens.add(out.toString());
            }else {
                List<WordData> wordDataList = opinionPreparer.stemmer.lookup(token.toLowerCase());
                if (wordDataList.isEmpty())
                    continue;
                WordData wordData = wordDataList.get(0);
                String tag = wordData.getTag().toString();
                String pos = tag.split(":")[0];

                out.append(Integer.toString(index)).append("\t")
                        .append(token).append("\t")
                        .append(wordData.getStem()).append("\t")
                        .append(pos).append("\t")
                        .append(pos).append("\t")
                        .append(tag.substring(tag.indexOf(':') + 1).replace(':', '|')).append("\t")
                        .append("_").append("\t")
                        .append("_").append("\t")
                        .append("_").append("\t");
                conllTokens.add(out.toString());
            }
            index++;
        }

        return conllTokens.toArray(new String[conllTokens.size()]);
    }
}
