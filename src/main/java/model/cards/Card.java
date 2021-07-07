package model.cards;

import controll.ImageLoader;
import enums.Face;
import enums.Zone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.csv.MonsterCSV;
import model.csv.SpellTrapCSV;
import model.players.Player;

import java.io.FileNotFoundException;

public class Card implements Comparable<Card> {
    protected String cardName;
    protected String cardDescription;
    protected Face face;
    protected Player cardOwner;
    protected Zone zone;
    protected int price;
    protected boolean activated = false;
    private Image image;
    private ImageView imageView;

    public Card(String name, String description, Face face, int price, Image image) {
        imageView = new ImageView(image);
        this.cardName = name;
        this.cardDescription = description;
        this.face = face;
        this.price = price;
        this.zone = Zone.DECK_ZONE;
        this.image = image;
    }

    public Card(Card that) {
        this.cardName = that.cardName;
        this.cardDescription = that.cardDescription;
        this.face = that.face;
        this.price = that.price;
        this.image = that.image;
        imageView=new ImageView(that.image);
        this.zone = Zone.DECK_ZONE;
        cardOwner = null;
        activated = false;
    }

    public static Card findCardFromCsv(String cardName) {
        MonsterCSV monster = null;
        SpellTrapCSV spellOrTrap = null;
        try {
            monster = MonsterCSV.findMonster(cardName.trim());
        } catch (FileNotFoundException ignored) {
        }
        try {
            spellOrTrap = SpellTrapCSV.findSpellTrap(cardName.trim());
        } catch (FileNotFoundException ignored) {
        }
        if (monster != null)
            return new Monster(monster.getName().replace("_", "-"), Face.DOWN,
                    monster.getPrice(), monster.getDescription()
                    , monster.getMonsterType(), monster.getCardType(), monster.getAttribute()
                    , monster.getAttack(), monster.getDefence(), monster.getLevel() ,
                    ImageLoader.getCardImageByName(monster.getName()));
        else {
            if (spellOrTrap.getType().equalsIgnoreCase("spell"))
                return new Spell(spellOrTrap.getName(), spellOrTrap.getDescription(),
                        Face.DOWN, spellOrTrap.getPrice(), spellOrTrap.getIcon() ,
                        ImageLoader.getCardImageByName(spellOrTrap.getName()));
            else
                return new Trap(spellOrTrap.getName(), spellOrTrap.getDescription(),
                        Face.DOWN, spellOrTrap.getPrice(), spellOrTrap.getIcon(),
                        ImageLoader.getCardImageByName(spellOrTrap.getName()));
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
    public void setImageView(ImageView imageView){
        this.imageView=imageView;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Face getFace() {
        return face;
    }

    public Player getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(Player cardOwner) {
        this.cardOwner = cardOwner;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Override
    public int compareTo(Card o) {
        if (o.getCardName().compareTo(cardName) < 0) return 1;
        return -1;
    }

    @Override
    public String toString() {
        return this.cardName + " : " + this.cardDescription;
    }

}
