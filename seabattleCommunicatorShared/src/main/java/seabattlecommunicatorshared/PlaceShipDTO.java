package seabattlecommunicatorshared;

import seabattlegui.ShipType;

public class PlaceShipDTO {
    private int playerNr;
    private ShipType shipType;
    private int bowX;
    private int bowY;
    private Boolean horizontal;


    public PlaceShipDTO(int playerNr, ShipType shiptype, int bowX, int bowY, Boolean horizontal) {
        this.playerNr = playerNr;
        this.shipType = shiptype;
        this.bowX = bowX;
        this.bowY = bowY;
        this.horizontal = horizontal;
    }
}
