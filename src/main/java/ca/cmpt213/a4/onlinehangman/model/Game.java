package ca.cmpt213.a4.onlinehangman.model;

public class Game {
    private long id;
    private String wordToBeGuessed;
    private String status;
    private int numberOfIncorrectGuesses;
    private static final int MAX_NUMBER_INCORRECT_GUESSES = 7;

    public Game() {
        this.id = 0;
        this.status= "";
    }

    public long getId() {
        return this.id;
    }

    public String getWordToBeGuessed() {
        return this.wordToBeGuessed;
    }

    public String getStatus() {
        return this.status;
    }

    public int getNumberOfIncorrectGuesses() {
        return this.numberOfIncorrectGuesses;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWordToBeGuessed(String word) {
        this.wordToBeGuessed = word;
    }

    public void setNumberOfIncorrectGuesses(int numberOfIncorrectGuesses) {
        this.numberOfIncorrectGuesses = numberOfIncorrectGuesses;
    }

    public int getLengthOfWordToBeGuessed(String word) {
        return word.length();
    }

}
