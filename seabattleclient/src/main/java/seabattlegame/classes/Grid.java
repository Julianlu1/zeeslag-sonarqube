package seabattlegame.classes;

import seabattlegui.SquareState;

public class Grid {
    private String name;
    private int shipCounter;

    public Grid(String name, int shipCounter){
        this.name = name;
        this.shipCounter = shipCounter;
    }

    public String getName() {
        return name;
    }

    public int getShipCounter() {
        return shipCounter;
    }

    public void placeShip(Ship ship, int positionY, int positionX){

    }
    public void removeShip(Ship ship){

    }

    public SquareState shoot(int positionY, int positionX){
        return null;
    }
}
