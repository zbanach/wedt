package pl.edu.pw.elka.wedt.model;



import java.util.ArrayList;
import java.util.List;


/**
 * Created by Artur Góralewski.
 */
public class Opinion {

    private List<Word> wordList;

    public Opinion() {
        this.wordList = new ArrayList<>();
    }

    public Opinion(List<Word> wordList) {
        this.wordList = wordList;
    }

    public void addWord(Word word){
        wordList.add(word);
    }

    public double getSentiment() {
        double sum = 0;
        for (Word word : wordList){
            sum += word.getSentiment();
        }
        return sum;
    }

    public List<Word> getWordList() {
        return wordList;
    }
}
