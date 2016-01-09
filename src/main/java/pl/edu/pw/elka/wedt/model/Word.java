package pl.edu.pw.elka.wedt.model;

import morfologik.stemming.WordData;

/**
 * Created by Artur Góralewski.
 */
public class Word {

    private String word;
    private int sentiment;
    private String stem;
    private String tag;

    public Word(WordData wordData) {
//        this.word = wordData.getWord().toString();
        this.stem = wordData.getStem().toString();
        this.tag = wordData.getTag().toString();
        this.sentiment = 0;
    }

    public Word(WordData wordData, int sentiment) {
        this.word = wordData.getWord().toString();
        this.stem = wordData.getStem().toString();
        this.tag = wordData.getTag().toString();
        this.sentiment = sentiment;
    }

    public String getWord() {
        return word;
    }

    public int getSentiment() {
        return sentiment;
    }

    public String getStem() {
        return stem;
    }

    public String getTag() {
        return tag;
    }

    public String getPOS() {
        return tag.split(":")[0];
    }


}
