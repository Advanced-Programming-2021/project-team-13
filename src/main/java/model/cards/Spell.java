package model.cards;

import enums.Face;
import javafx.scene.image.Image;

public class Spell extends Card {
    private Monster equippedMonster;
    private String type;
    private boolean isSetINThisTurn = false;

    public Spell(String name, String description, Face face, int price, String type , Image image) {
        super(name, description, face, price , image);
        this.type = type;
    }

    public Spell(Spell that) {
        super(that);
        this.type = that.type;
        equippedMonster = null;
        isSetINThisTurn = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSetINThisTurn() {
        return isSetINThisTurn;
    }

    public void setSetINThisTurn(boolean setINThisTurn) {
        isSetINThisTurn = setINThisTurn;
    }

    @Override
    public String toString() {
        return "Name: " + cardName + "Spell"
                + "\nType: " + type + "Description: " + cardDescription;
    }

    public Monster getEquippedMonster() {
        return equippedMonster;
    }

    public String getSpellEffect() {
        return type;
    }

    public void setSpellEffect(String type) {
        this.type = type;
    }

    public void setEquippedMonster(Monster equippedMonster) {
        this.equippedMonster = equippedMonster;
    }

}
