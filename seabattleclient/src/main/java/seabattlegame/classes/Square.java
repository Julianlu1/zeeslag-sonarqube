package seabattlegame.classes;

import seabattlegui.SquareState;

public class Square {
    private int positionY;
    private int positionX;
    private SquareState squareState;

    public Square(SquareState squareState, int positionX, int positionY){
        this.positionX = positionX;
        this.positionY = positionY;
        this.squareState = squareState;
    }

    public void setSquareState(SquareState squareState) {
        this.squareState = squareState;
    }
}
