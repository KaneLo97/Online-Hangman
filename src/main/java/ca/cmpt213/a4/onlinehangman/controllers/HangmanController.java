package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.Game;
import ca.cmpt213.a4.onlinehangman.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.jar.Attributes;

@Controller
public class HangmanController {
    private Message promptMessage; //a resusable String object to display a prompt message at the screen
    private AtomicLong nextId = new AtomicLong();
    private ArrayList<Game> gameList = new ArrayList<>();
    private Game game;

    //works like a constructor, but wait until dependency injection is done, so it's more like a setup
    @PostConstruct
    public void hangmanControllerInit() {
        promptMessage = new Message("Initializing...");
    }

    @GetMapping("/helloworld")
    public String showHelloworldPage(Model model) {

        promptMessage.setMessage("You are at the helloworld page!");
        model.addAttribute("promptMessage", promptMessage);

        // take the user to helloworld.html
        return "helloworld";
    }

    @GetMapping("/welcome")
    public String showWelcomePage() {

        // create a new game whenever player is on the welcome page
        game = new Game();
        game.setStatus("Active");
        game.setWordToBeGuessed("Hello");

        // take the user to welcome.html
        return "welcome";
    }

    @PostMapping("/game")
    public String showGamePage(@ModelAttribute("game") Game currentGame, Model model) {
        List<String> wordRevealed = new ArrayList<>();
        // player is on the welcome page
        if (game.getId() == 0) {
            game.setId(nextId.incrementAndGet());
            wordRevealed = game.populateInitialRevealedList();
            addModelAttributes(model, wordRevealed);
            gameList.add(game);
            return "game";
        } else { // player has entered a character on the game page
            return updateGamePage(currentGame, model, wordRevealed);
        }
    }

    public String updateGamePage(@ModelAttribute("game") Game currentGame, Model model, List<String> wordRevealed) {
        String characterEntered = currentGame.getCharacterEntered();
        game.setCharacterEntered(characterEntered);
        System.out.println("character entered " + characterEntered);
        game.updateGuess(characterEntered);
        wordRevealed = game.getCharacterList(characterEntered);
        addModelAttributes(model, wordRevealed);
        game.getUpdatedGameStatus();

        if (game.getStatus() == "Lost" || game.getStatus() == "Won") {
            return "gameover";
        }

        return "game";
    }

    public void addModelAttributes(Model model, List<String> wordRevealed) {
        model.addAttribute("game", game);
        model.addAttribute("list", wordRevealed);
    }


}