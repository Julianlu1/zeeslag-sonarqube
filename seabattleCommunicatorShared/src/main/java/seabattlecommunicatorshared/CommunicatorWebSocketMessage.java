package seabattlecommunicatorshared;

public class CommunicatorWebSocketMessage {
    private CommunicatorWebSocketMessageOperation operation;
    private PlaceShipDTO placeShipDTO;

    private int playerNr;
    private int posX;
    private int posY;

    public CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation operation, PlaceShipDTO placeShipDTO){
        this.operation = operation;
        this.placeShipDTO = placeShipDTO;
    }

    public CommunicatorWebSocketMessage(int playerNr, int posX, int posY){
        this.playerNr = playerNr;
        this.posX = posX;
        this.posY = posY;
    }

    public CommunicatorWebSocketMessageOperation getOperation() {
        return operation;
    }

    public PlaceShipDTO getPlaceShipDTO() {
        return placeShipDTO;
    }

    public void setOperation(CommunicatorWebSocketMessageOperation operation) {
        this.operation = operation;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPlayerNr() {
        return playerNr;
    }
}
