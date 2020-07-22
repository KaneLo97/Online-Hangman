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

        // take the user to welcome.html
        return "welcome";
    }

    @PostMapping("/game")
    public String showGamePage(Model model) {
        List<String> wordRevealed = new ArrayList<>();
        game = new Game();
        game.setId(nextId.incrementAndGet());
        game.setStatus("Active");
        game.setWordToBeGuessed("Hello");
        model.addAttribute("game", game);
        wordRevealed = game.populateInitialRevealedList();
        model.addAttribute("list", wordRevealed);
        gameList.add(game);
        return "game";
    }

    @PostMapping("/game/handle")
    public String showPage(@ModelAttribute("game") Game game1, Model model) {
        List<String> wordRevealed = new ArrayList<>();
        String characterEntered = game1.getCharacterEntered();
        game.setCharacterEntered(characterEntered);
        System.out.println("dsfkjbdfjb" + characterEntered);
        game.updateGuess(characterEntered);
        model.addAttribute("game", game);
        wordRevealed = game.getCharacterList(characterEntered);
        model.addAttribute("list", wordRevealed);
        return "game";
    }


}