package pl.edu.pw.elka.wedt.model;



import java.util.ArrayList;


/**
 * Created by Artur Góralewski.
 */
public class Opinion {

    private ArrayList<Word> wordList;

    public Opinion() {
        this.wordList = new ArrayList<>();
    }

    public Opinion(ArrayList<Word> wordList) {
        this.wordList = wordList;
    }

    public void addWord(Word word){
        wordList.add(word);
    }

    public int getSentiment() {
        int sum = 0;
        for (Word word : wordList){
            sum += word.getSentiment();
        }
        return sum;
    }
}
