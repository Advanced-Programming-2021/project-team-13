package model;

import Interfaces.Effects;
import enums.*;

import java.util.ArrayList;

public class Monster extends Card {
    private MonsterType monsterType;
    private MonsterType monsterTypeInGame;
    private SummonType summonType;
    private MonsterAttribute monsterAttribute;
    private MonsterAttribute monsterAttributeInGame;
    private int attackNum;
    private int defenseNum;
    private int attackPointInGame;
    private int defencePointInGame;
    private int level;
    private int levelInGame;
    private boolean isAttackable = true;
    private boolean isActivateInThisTurn = false;
    private String nameInGame;

    public int getAttackPointInGame() {
        return attackPointInGame;
    }

    public void setAttackPointInGame(int attackPointInGame) {
        this.attackPointInGame = attackPointInGame;
    }

    public int getDefencePointInGame() {
        return defencePointInGame;
    }

    public void setDefencePointInGame(int defencePointInGame) {
        this.defencePointInGame = defencePointInGame;
    }

    public Monster(String name, CardType cardType, Face face, int price, int cardNum, String description,
                   MonsterType monsterType, SummonType summonType, MonsterAttribute monsterAttribute,
                   int attackNum, int defenseNum, int level) {
        super(name, cardType, description, face, price, cardNum);
        this.monsterAttribute = monsterAttribute;
        this.monsterType = monsterType;
        this.summonType = summonType;
        this.attackNum = attackNum;
        this.attackPointInGame = attackNum;
        this.defencePointInGame = defenseNum;
        this.defenseNum = defenseNum;
        this.level = level;
        levelInGame = level;
        monsterAttributeInGame = monsterAttribute;
        monsterTypeInGame = monsterType;
        this.nameInGame = name;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    public SummonType getSummonType() {
        return summonType;
    }

    public void setSummonType(SummonType summonType) {
        this.summonType = summonType;
    }

    public MonsterAttribute getMonsterAttribute() {
        return monsterAttribute;
    }

    public void setMonsterAttribute(MonsterAttribute monsterAttribute) {
        this.monsterAttribute = monsterAttribute;
    }

    public int getAttackNum() {
        return attackNum;
    }

    public void setAttackNum(int attackNum) {
        this.attackNum = attackNum;
    }

    public int getDefenseNum() {
        return defenseNum;
    }

    public void setDefenseNum(int defenseNum) {
        this.defenseNum = defenseNum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAttackable() {
        return isAttackable;
    }

    public String getNameInGame() {
        return nameInGame;
    }

    public void setNameInGame(String nameInGame) {
        this.nameInGame = nameInGame;
    }

    public void setAttackable(boolean attackable) {
        isAttackable = attackable;
    }

    public ArrayList<Monster> getMonsterOnBoard() {
        return cardOwner.getMonsterOnBoard();
    }

    public boolean isActivateInThisTurn() {
        return isActivateInThisTurn;
    }

    public void setActivateInThisTurn(boolean activateInThisTurn) {
        isActivateInThisTurn = activateInThisTurn;
    }

    public MonsterType getMonsterTypeInGame() {
        return monsterTypeInGame;
    }

    public void setMonsterTypeInGame(MonsterType monsterTypeInGame) {
        this.monsterTypeInGame = monsterTypeInGame;
    }

    public MonsterAttribute getMonsterAttributeInGame() {
        return monsterAttributeInGame;
    }

    public void setMonsterAttributeInGame(MonsterAttribute monsterAttributeInGame) {
        this.monsterAttributeInGame = monsterAttributeInGame;
    }

    public int getLevelInGame() {
        return levelInGame;
    }

    public void setLevelInGame(int levelInGame) {
        this.levelInGame = levelInGame;
    }
}
