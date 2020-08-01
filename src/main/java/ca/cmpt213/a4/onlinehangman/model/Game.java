package ca.cmpt213.a4.onlinehangman.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A model class where the system represents the game data independent of the user interface,
 * by holding information about the game, including the random word being chosen for the game and the word being revealed partly or completely
 * and checking if the guess (character entered) is correct or not
 */
public class Game {
    private long id;
    private String wordToBeGuessed;
    private String status;
    private int numberOfIncorrectGuesses;
    private int totalNumberOfGuesses;
    private String characterEntered;
    private List<String> revealedList = new ArrayList<>();
    private static final int MAX_NUMBER_INCORRECT_GUESSES = 7;

    /**
     * default constructor
     */
    public Game() {
        this.id = 0;
        this.wordToBeGuessed = "";
        this.status = "";
        this.numberOfIncorrectGuesses = 0;
        this.characterEntered = "";
        this.totalNumberOfGuesses = 0;
    }

    public long getId() {
        return this.id;
    }

    // important to add
    public String getWordToBeGuessed() {
        return this.wordToBeGuessed;
    }

    public String getStatus() {
        return this.status;
    }

    // important to add
    public int getNumberOfIncorrectGuesses() {
        return this.numberOfIncorrectGuesses;
    }

    public String getCharacterEntered() {
        return this.characterEntered;
    }

    // important to add
    public int getTotalNumberOfGuesses() {
        return this.totalNumberOfGuesses;
    }

    public List<String> getRevealedList() {
        return revealedList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCharacterEntered(String characterEntered) {
        this.characterEntered = characterEntered;
    }

    /**
     * called at the start of a new game to show the dashes and spaces based on the number of characters in the word
     */
    public List<String> populateInitialRevealedList() {
        for (int i = 0; i < wordToBeGuessed.length(); i++) {
            revealedList.add("_");
            revealedList.add(" ");
        }
        return revealedList;
    }

    /**
     * checks if the character entered by the player is a correct guess
     *
     * @return true if correct guess, false if incorrect
     */
    private boolean isCorrectCharacter() {
        // the character is present in the word to be guessed
        if (this.wordToBeGuessed.indexOf(this.characterEntered.charAt(0)) != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * updates the number of guesses made by the player
     */
    public void updateNumberGuesses() {
        if (!this.characterEntered.equals("")) {
            this.totalNumberOfGuesses++;
            if (!isCorrectCharacter()) {
                this.numberOfIncorrectGuesses++;
            }
        }
    }

    /**
     * gets the list of characters that have been revealed
     *
     * @return revealedList the list of revealed characters
     */
    public List<String> getRevealedCharactersList() {
        if (!this.characterEntered.equals("")) {
            for (int i = 0; i < this.wordToBeGuessed.length(); i++) {
                if (this.wordToBeGuessed.charAt(i) == this.characterEntered.charAt(0)) {
                    this.revealedList.remove(i * 2);
                    // the index in the revealed list is always even due to space between 2 dashes
                    this.revealedList.add(i * 2, this.characterEntered);
                }
            }
        }
        return this.revealedList;
    }

    /**
     * sets the status of the game
     */
    public void updateGameStatus() {
        if (this.revealedList.contains("_") && this.numberOfIncorrectGuesses > MAX_NUMBER_INCORRECT_GUESSES) {
            this.status = "Lost";
        } else if (this.revealedList.contains("_")) {
            this.status = "Active";
        } else {
            this.status = "Won";
        }
    }

    /**
     * sets the random word to be guessed for the game
     */
    public void setRandomWordToBeGuessed() {
        try {
            FileReader wordFile = new FileReader("./src/commonWords.txt");
            BufferedReader reader = new BufferedReader(wordFile);
            String wordLine = reader.readLine();
            List<String> wordList = new ArrayList<>();
            while (wordLine != null) {
                wordList.add(wordLine);
                wordLine = reader.readLine();
            }
            // gets a random word to be guessed
            Random rand = new Random();
            this.wordToBeGuessed = wordList.get(rand.nextInt(wordList.size()));
        } catch (Exception e) {
            System.out.println("It is not possible to read the commonWords.txt.");
        }
    }
}// Game.java


