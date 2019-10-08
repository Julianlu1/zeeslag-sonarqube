package seabattlegame.classes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seabattlegui.SquareState;

public class Square {
    private Rectangle reee;
    private SquareState state;
    private int positionX;
    private int positionY;

    public Square(SquareState state, int positionX, int positionY){
       this.state = state;
       this.positionX = positionX;
       this.positionY = positionY;
        //reee = rectangle;
    }

    public void setSquareState(SquareState squareState) {
        this.state = squareState;
       /*
        switch (squareState) {
            case SHIP:
                reee.setFill(Color.DARKGRAY);
                break;
            case SHOTMISSED:
                reee.setFill(Color.BLUE);
                break;
            case SHOTHIT:
                reee.setFill(Color.RED);
                break;
            case SHIPSUNK:
                reee.setFill(Color.GREEN);
                break;
            default:
                reee.setFill(Color.LIGHTBLUE);
                break;
        }

        */
    }

    public SquareState getState() {
        return state;
    }

    public int getPositionX() {
        //return (int)reee.getX();
        return positionX;
    }

    public int getPositionY() {
        //return (int)reee.getY();
        return positionY;
    }
}
