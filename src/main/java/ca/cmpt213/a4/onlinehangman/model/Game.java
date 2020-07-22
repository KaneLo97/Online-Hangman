package ca.cmpt213.a4.onlinehangman.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private long id;
    private String wordToBeGuessed;
    private String status;
    private int numberOfIncorrectGuesses;
    private int totalNumberOfGuesses;
    private String characterEntered;
    private List<String> revealedList = new ArrayList<>();
    private static final int MAX_NUMBER_INCORRECT_GUESSES = 7;

    public Game() {
        this.id = 0;
        this.wordToBeGuessed = "";
        this.numberOfIncorrectGuesses = 0;
        this.characterEntered= "";
        this.status = "";
        this.totalNumberOfGuesses = 0;
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

    public String getCharacterEntered() {
        return this.characterEntered;
    }

    public int getTotalNumberOfGuesses() {
        return this.totalNumberOfGuesses;
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

    public void setCharacterEntered(String characterEntered) {
        this.characterEntered = characterEntered;
    }

    public void setTotalNumberOfGuesses(int totalNumberOfGuesses) {
        this.totalNumberOfGuesses = totalNumberOfGuesses;
    }

    public List<String> populateInitialRevealedList() {
        for (int i = 0; i < wordToBeGuessed.length(); i++) {
            revealedList.add("_");
            revealedList.add(" ");
        }
        return revealedList;
    }

    private boolean isCorrectCharacter(String characterEntered) {
        if (wordToBeGuessed.indexOf(characterEntered.charAt(0)) != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void updateGuess(String characterEntered) {
        this.totalNumberOfGuesses++;
        if (!isCorrectCharacter(characterEntered)) {
            this.numberOfIncorrectGuesses++;
        }
    }

    public List<String> getCharacterList(String characterEntered) {
        for(int i = 0; i <  wordToBeGuessed.length(); i++) {
            if (wordToBeGuessed.charAt(i) == characterEntered.charAt(0)) {
                revealedList.remove(i * 2);
                revealedList.add(i * 2, characterEntered);
            }
        }
        return revealedList ;
    }

    public boolean isGameLost() {
        if (this.revealedList.contains("_") && this.numberOfIncorrectGuesses > MAX_NUMBER_INCORRECT_GUESSES) {
            return true;
        } else {
            return false;
        }
    }

}
