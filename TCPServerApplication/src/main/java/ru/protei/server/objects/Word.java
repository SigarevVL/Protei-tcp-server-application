package ru.protei.server.objects;

public class Word {

    private String word;
    private String wordExplanation;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordExplanation() {
        return wordExplanation;
    }

    public void setWordExplanation(String wordExplanation) {
        this.wordExplanation = wordExplanation;
    }

    @Override
    public String toString() {
        return this.getWord() + " - " + this.getWordExplanation();
    }
}
