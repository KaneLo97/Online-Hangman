package ca.cmpt213.a4.onlinehangman.model;

public class Game {
    private long id;
    private String status;

    public Game() {
        this.id = 0;
        this.status= "";
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
