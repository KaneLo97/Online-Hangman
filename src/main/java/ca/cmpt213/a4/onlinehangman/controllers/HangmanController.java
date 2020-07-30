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

    @GetMapping("/welcome")
    private String showWelcomePage(Model model) {
        promptMessage.setMessage("Welcome to the Hangman game");
        model.addAttribute("promptMessage", promptMessage);
        // create a new game whenever player is on the welcome page
        game = new Game();
        game.setStatus("Active");
        game.setRandomWordToBeGuessed();

        // take the user to welcome.html
        return "welcome";
    }

    @GetMapping("/game")
    private String showCurrentGamePage(Model model) {
        addModelAttributes(model);
        return "game";
    }

    @PostMapping("/game")
    private String showGamePage(@ModelAttribute("game") Game currentGame, Model model) {
        wordRevealed = new ArrayList<>();
        // player is on the welcome page
        if (game.getId() == 0) {
            game.setId(nextId.incrementAndGet());
            wordRevealed = game.populateInitialRevealedList();
            addModelAttributes(model);
            gameList.add(game);
            return "game";
        } else { // player has entered a character on the game page
            return updateGamePage(currentGame, model);
        }
    }

    @GetMapping("/game/{id}")
    private String showGameIdPage(@PathVariable("id") long gameId, Model model) {
        for (Game gameSearched : gameList) {
            if (gameSearched.getId() == gameId) {
                game = (Game)gameSearched.clone();
                wordRevealed = game.getRevealedList();
                addModelAttributes(model);
                if (game.getStatus() == "Won" || game.getStatus() == "Lost") {
                    return "gameover";
                }
                    return "game";
                }
            }
        // game id is not found
        throw new GameNotFoundException("Game is not found");
    }

    private String updateGamePage(@ModelAttribute("game") Game currentGame, Model model) {
        String characterEntered = currentGame.getCharacterEntered();
        game.setCharacterEntered(characterEntered);
        game.updateGuess(characterEntered);
        wordRevealed = game.getCharacterList(characterEntered);
        addModelAttributes(model);
        game.getUpdatedGameStatus();

        if (game.getStatus() == "Lost" || game.getStatus() == "Won") {
            return "gameover";
        }

        return "game";
    }

    private void addModelAttributes(Model model) {
        model.addAttribute("game", game);
        model.addAttribute("word", wordRevealed);
    }

    // Create Exception Handle
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(GameNotFoundException.class)
    public String handleExceptionHandler(Model model) {
        promptMessage.setMessage("Game id does not exists. Game is not found.");
        model.addAttribute("promptMessage", promptMessage);
        return "gamenotfound";

    }
}