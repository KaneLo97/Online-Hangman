package ca.cmpt213.a4.onlinehangman.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private long id;
    private String wordToBeGuessed;
    private String status;
    private int numberOfIncorrectGuesses;
    private int totalNumberOfGuesses;
    private String characterEntered;
    private List<String> revealedList = new ArrayList<>();
    private static final int MAX_NUMBER_INCORRECT_GUESSES = 1;

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
        if (!characterEntered.equals("") && wordToBeGuessed.indexOf(characterEntered.charAt(0)) != -1) {
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
        if (!characterEntered.equals("")) {
            for (int i = 0; i < wordToBeGuessed.length(); i++) {
                if (wordToBeGuessed.charAt(i) == characterEntered.charAt(0)) {
                    revealedList.remove(i * 2);
                    revealedList.add(i * 2, characterEntered);
                }
            }
        }
        return revealedList ;
    }

    public void getUpdatedGameStatus() {
        if (this.revealedList.contains("_") && this.numberOfIncorrectGuesses > MAX_NUMBER_INCORRECT_GUESSES) {
            this.status = "Lost";
        } else if (this.revealedList.contains("_")) {
            this.status = "Active";
        } else {
            this.status = "Won";
        }
    }

    public void setRandomWordToBeGuessed() {
        try{
            FileReader wordFile = new FileReader("./src/commonWords.txt");
            BufferedReader reader = new BufferedReader(wordFile);
            String wordLine = reader.readLine();
            List<String> wordList = new ArrayList<String>();
            while(wordLine != null) {
                wordList.add(wordLine);
                wordLine = reader.readLine();
            }

            Random rand = new Random();
            this.wordToBeGuessed = wordList.get(rand.nextInt(wordList.size()));

        } catch (Exception e) {
           System.out.println("It is not possible to read the commonWords.txt.");
        }

    }

}
