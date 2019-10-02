package seabattlegame.classes;

public class Game {
    public boolean startGame(boolean playerReady1, boolean playerReady2){
        if(playerReady1 && playerReady2){
            return true;
        }else{
            return false;
        }
    }

    public void endGame(){

    }
}
