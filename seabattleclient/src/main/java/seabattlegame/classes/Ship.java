package seabattlegame.classes;

import seabattlegui.ShipType;

public class Ship {
    private String name;
    private ShipType type;

    public String getName() {
        return name;
    }

    public ShipType getType() {
        return type;
    }

    public Ship(String name, ShipType type){
        this.name = name;
        this.type = type;
    }

    public boolean isDestroyed() {
        return true;
    }
}
