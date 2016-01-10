package pl.edu.pw.elka.wedt.model;

import morfologik.stemming.WordData;

/**
 * Created by Artur Góralewski.
 */
public class Word {

    private String word;
    private double sentiment;
    private String stem;
    private String tag;

    public Word(WordData wordData) {
        if(wordData != null) {
            this.word = wordData.getWord().toString();
            this.stem = wordData.getStem().toString();
            this.tag = wordData.getTag().toString();
        }
        this.sentiment = 0;
    }

    public Word(WordData wordData, int sentiment) {
        if(wordData != null) {
            this.word = wordData.getWord().toString();
            this.stem = wordData.getStem().toString();
            this.tag = wordData.getTag().toString();
        }
        this.sentiment = sentiment;
    }

    public Word(WordData wordData, String originalWord) {
        this.word = originalWord;
        if(wordData != null) {
            this.stem = wordData.getStem().toString();
            this.tag = wordData.getTag().toString();
        }
        this.sentiment = 0;
    }

    public String getWord() {
        return word;
    }

    public double getSentiment() {
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

    public void setSentiment(double sentiment) {
        this.sentiment = sentiment;
    }
}
