package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.Game;
import ca.cmpt213.a4.onlinehangman.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A class for handling all the post and get requests, converting them into method calls to the model,
 * communicating the results back to the view and redirecting the player to the appropriate pages
 */
@Controller
public class HangmanController {
    private Message promptMessage; //a resusable String object to display a prompt message at the screen
    private AtomicLong nextId = new AtomicLong();
    private ArrayList<Game> gameList = new ArrayList<>();
    private List<String> wordRevealed = new ArrayList<>();
    private Game game;

    //works like a constructor, but wait until dependency injection is done, so it's more like a setup
    @PostConstruct
    private void hangmanControllerInit() {
        promptMessage = new Message("Initializing...");
    }

    /**
     * handles the get request from localhost:8080/welcome where the player can read the rules and start a game
     */
    @GetMapping("/welcome")
    private String showWelcomePage(Model model) {
        promptMessage.setMessage("WELCOME TO THE HANGMAN GAME");
        model.addAttribute("promptMessage", promptMessage);
        // create a new game whenever player is on the welcome page
        // don't set the game id till the player clicks on start
        game = new Game();
        game.setStatus("Active");
        game.setRandomWordToBeGuessed();

        // take the user to welcome.html
        return "welcome";
    }

    /**
     * Player can enter the same game url
     */
    @GetMapping("/game")
    private String showCurrentGamePage(Model model) {
        addModelAttributes(model);
        return getAppropriatePage();
    }

    /**
     * handles the post request from localhost:8080/game
     */
    @PostMapping("/game")
    private String showGamePage(@ModelAttribute("game") Game currentGame, Model model) {
        // the characters that have been revealed in the word
        wordRevealed = new ArrayList<>();
        // player is on the welcome page, and has started a game
        if (game.getId() == 0) {
            game.setId(nextId.incrementAndGet());
            //  no characters revealed yet
            wordRevealed = game.populateInitialRevealedList();
            promptMessage.setMessage("Active");
            addModelAttributes(model);
            // add this new game to the gameList
            gameList.add(game);
            return "game";
        } else { // player is on the current game page and has entered a character on the game page
            return updateGamePage(currentGame, model);
        }
    }

    /**
     * handles the get request from localhost:8080/game/id to show the game based on the id
     */
    @GetMapping("/game/{id}")
    private String showGameIdPage(@PathVariable("id") long gameId, Model model) {
        for (Game gameSearched : gameList) {
            if (gameSearched.getId() == gameId) {
                game = gameSearched;
                wordRevealed = game.getRevealedList();
                promptMessage.setMessage(game.getStatus());
                addModelAttributes(model);
                return getAppropriatePage();
            }
        }
        // game id is not found
        promptMessage.setMessage("Game " + gameId + " does not exists. Game is not found.");
        throw new GameNotFoundException();
    }

    /**
     * helper method to update the game page based on the character entered by the player
     */
    private String updateGamePage(Game currentGame, Model model) {
        String characterEntered = currentGame.getCharacterEntered();
        game.setCharacterEntered(characterEntered);
        game.updateNumberGuesses();
        wordRevealed = game.getRevealedCharactersList();
        game.updateGameStatus();
        promptMessage.setMessage(game.getStatus());
        addModelAttributes(model);
        return getAppropriatePage();
    }

    /**
     * adds the main model attributes to be used in the html templates
     */
    private void addModelAttributes(Model model) {
        model.addAttribute("game", game);
        model.addAttribute("word", wordRevealed);
        addMessageModelAttribute(model);
    }

    /**
     * adds the message model attribute to be used in the html templates
     */
    private void addMessageModelAttribute(Model model) {
        model.addAttribute("promptMessage", promptMessage);
    }

    /**
     * gets the appropriate page based on the game status
     */
    private String getAppropriatePage() {
        if (game.getStatus() == "Lost" || game.getStatus() == "Won") {
            return "gameover";
        }

        return "game";
    }

    /**
     * handles the "GameNotFoundException" by throwing a 404 http status
     * and redirects the player to a gamenotfound page
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(GameNotFoundException.class)
    private String handleExceptionHandler(Model model) {
        addMessageModelAttribute(model);
        return "gamenotfound";
    }
}// HangmanController.java