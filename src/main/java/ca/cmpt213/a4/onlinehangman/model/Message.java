package ca.cmpt213.a4.onlinehangman.model;

/**
 * A message class used as the header in the welcome page and to show the status of the game
 *
 */
public class Message {
    private String message;

    public Message() {
        this.message = "";
    }

    public Message(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}// Message.java
