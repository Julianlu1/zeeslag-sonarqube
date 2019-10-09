/*
 * Sea Battle Start project.
 */
package seabattlegame;

import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seabattlegame.classes.*;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.ShipType;
import seabattlegui.SquareState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The Sea Battle game. To be implemented.
 *
 * @author Nico Kuijpers
 */
public class SeaBattleGame implements ISeaBattleGame {

  private Game game = new Game();

  /*
  public SeaBattleGame(Rectangle[][] sqaurs) {


    List<Square> squares = new ArrayList<>();

    for (Rectangle[] rectangles : sqaurs) {
      for (Rectangle rectangle : rectangles) {
        squares.add(new Square(rectangle));
      }
    }
  }

   */

  public void setGameSquers() {
    Grid grid = new Grid("sd00", 0);
    grid.setSquares(new ArrayList<>());

    game.setupGame(grid);
  }

  private static final Logger log = LoggerFactory.getLogger(SeaBattleGame.class);

  @Override
  public void registerPlayer(String name, String password, ISeaBattleGUI application, boolean singlePlayerMode) {

    log.debug("Register Player {} - password {}", name, password);
    //throw new UnsupportedOperationException("Method registerPlayer() not implemented.");
    if(name==null || password ==null || application == null){
      throw new IllegalArgumentException("Name is null");
    }

    Player player = new Player(name,false);
    Player player2 = new Player("Computer",false);
    game.addPlayer(player);
      game.addPlayer(player2);

  }

  @Override
  public List<Ship> placeShipsAutomatically(int playerNr) {

      Player player = game.getCurrentPlayerByNumber(playerNr);
    Grid grid = player.getGrid();
    List<Square> squares = grid.getSquares();
    List<Ship> ships = player.getShips();

    List<Ship> lstShipsFilled = new ArrayList<>();
    for(Ship ship : ships){
      ship.getSquares().clear();
    }
    Random random = new Random();

      for(Ship ship : ships){

          int shipLength = 0;

          switch (ship.getType()){
              case AIRCRAFTCARRIER:
                  shipLength=5;
                  break;
              case BATTLESHIP:
                  shipLength = 4;
                  break;
              case CRUISER:
              case SUBMARINE:
                  shipLength =3;
                  break;
              case MINESWEEPER:
                  shipLength=2;
                  break;
          }
          boolean shipPlaced = false;
          // While de schip niet geplaatst is, blijf het proberen
          while(!shipPlaced){
              int bowX = random.nextInt(8);
              int bowY = random.nextInt(8);
              boolean horizontal = Math.random() < 0.5;

              Square chosenSquare = getChosenSquare(squares,bowX,bowY);

              if(checkSafeToAdd(shipLength,horizontal,chosenSquare,squares)){
                  for(int i = 0; i <shipLength;i++){
                      if(horizontal){
                          int x = chosenSquare.getPositionX() +i;
                          Square s = getChosenSquare(squares,x,chosenSquare.getPositionY());
                          s.setSquareState(SquareState.SHIP);
                          ship.addSquare(s);
                          shipPlaced = true;
                      }else{
                          int y = chosenSquare.getPositionY() +i;
                          Square s = getChosenSquare(squares,chosenSquare.getPositionX(),y);
                          s.setSquareState(SquareState.SHIP);
                          ship.addSquare(s);
                          shipPlaced = true;
                      }
                  }
                  lstShipsFilled.add(ship);
              }
          }
      }

      //ships.add(chosenShip);
    return lstShipsFilled;

    //throw new UnsupportedOperationException("Method placeShipsAutomatically() not implemented.");
  }

 @Override
 public Ship placeShip(int playerNr, ShipType shipType, int bowX, int bowY, boolean horizontal) {
   boolean safeToAdd = false;
    // Get all squares
    Player player = game.getCurrentPlayerByNumber(playerNr);

    Grid grid = player.getGrid();
    List<Square> squares = grid.getSquares();

    int shipLength = 0;

    switch(shipType){
      case AIRCRAFTCARRIER:
        shipLength = 5;
        break;
      case BATTLESHIP:
        shipLength = 4;
        break;
      case CRUISER:
      case SUBMARINE:
        shipLength = 3;
        break;
      case MINESWEEPER:
        shipLength = 2;
        break;
    }

    Square chosenSquare = getChosenSquare(squares,bowX,bowY);
    Ship chosenShip = player.getShipByType(shipType);

     List<Ship> playerShips = player.getShips();
     // Als het veilig is om een schip te plaatsen en er geen duplicaten zijn van schepen
    if(checkSafeToAdd(shipLength,horizontal,chosenSquare,squares) && checkForMultipleShips(playerShips,shipType)){
        for(int i = 0; i < shipLength ; i++){
            assert chosenSquare != null;
            if(horizontal){
                int x = chosenSquare.getPositionX() + i; // Steeds 1 erbij optellen
                Square s = getChosenSquare(squares,x,chosenSquare.getPositionY()); // De volgende square pakken || getPositionY is steeds dezelfde locatie
                // Als de Square al een ship heeft, is er overlapping
                playerShips.removeIf(ship-> ship.getType().equals(shipType));
                s.setSquareState(SquareState.SHIP); // Set state naar SHIP
                chosenShip.addSquare(s);
            }else{
                int y = chosenSquare.getPositionY() + i; // Steeds 1 erbij optellen
                Square s = getChosenSquare(squares,chosenSquare.getPositionX(),y); // De volgende square pakken
                s.setSquareState(SquareState.SHIP); // Set state naar SHIP
                playerShips.removeIf(ship-> ship.getType().equals(shipType));
                chosenShip.addSquare(s);
            }
        }
    }else{
        return null;
    }

  return chosenShip;
 }

 private boolean checkSafeToAdd(int shiplength,boolean horizontal,Square chosensquare,List<Square> squares){
    for(int i = 0; i < shiplength; i++){
        if(horizontal){
            int x = chosensquare.getPositionX() +i; // De volgende X positie
            Square s = getChosenSquare(squares,x,chosensquare.getPositionY());
            if(s.getState().equals(SquareState.SHIP) || x < 0 || x > 9){
                return false;
            }
        }else{
            int y = chosensquare.getPositionY() +i; // De volgende Y positie
            Square s = getChosenSquare(squares,chosensquare.getPositionX(),y);
            if(s.getState().equals(SquareState.SHIP) || y < 0 || y > 9){
                return false;
            }
        }
    }
     return true;
 }

 private boolean checkForMultipleShips(List<Ship> ships, ShipType shiptype){
      for(Ship s : ships){
          if(s.getType().equals(shiptype)){
              return true;
          }
      }
      return false;
 }


  private Square getChosenSquare(List<Square> squares, int x, int y) {
    // Loop door alle squares
    for (Square s : squares) {
      if (x == s.getPositionX() && y == s.getPositionY()) {
        return s;
      }
    }
    return null;
  }

  @Override
  public void removeShip(int playerNr, int posX, int posY) {
    throw new UnsupportedOperationException("Method removeShip() not implemented.");
  }

  @Override
  public List<Ship> removeAllShips(int playerNr) {
    Player player = game.getCurrentPlayerByNumber(playerNr);
    Grid grid = player.getGrid();
    List<Square> squares = grid.getSquares();
    for(Square s : squares){
        s.setSquareState(SquareState.WATER);
    }
    for(Ship ship: player.getShips()){
        for(Square square : ship.getSquares()){
            square.setSquareState(SquareState.WATER);
        }
    }
    return player.getShips();
    //throw new UnsupportedOperationException("Method removeAllShips() not implemented.");
  }

  @Override
  public void notifyWhenReady(int playerNr) {

    throw new UnsupportedOperationException("Method notifyWhenReady() not implemented.");
  }

  @Override
  public void fireShot(int playerNr, int posX, int posY) {

      //throw new UnsupportedOperationException("Method fireShot() not implemented.");
  }

  @Override
  public void startNewGame(int playerNr) {
    throw new UnsupportedOperationException("Method startNewGame() not implemented.");
  }
}

