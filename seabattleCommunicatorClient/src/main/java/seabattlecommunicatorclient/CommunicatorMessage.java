package seabattlecommunicatorclient;

import seabattlecommunicatorshared.PlaceShipDTO;

public class CommunicatorMessage {
    private PlaceShipDTO placeShipDTO;

    private int playerNr;
    private int posX;
    private int posY;

    public CommunicatorMessage() {

    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
