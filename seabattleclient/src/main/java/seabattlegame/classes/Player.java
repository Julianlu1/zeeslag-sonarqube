package seabattlegame.classes;

public class Player {
    private String name;
    private boolean playerReady;

    public Player(String name, boolean playerReady){
        this.name = name;
        this.playerReady = playerReady;
    }

    public String getName() {
        return name;
    }

    public boolean getPlayerReady(){
        return playerReady;
    }
}
