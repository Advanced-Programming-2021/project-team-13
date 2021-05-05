package model.cards;

import enums.*;

import java.util.ArrayList;

public class Monster extends Card {
    private MonsterCardType monsterCardType;
    private SummonType summonType;
    private MonsterAttribute monsterAttribute;
    private AttackOrDefense attackOrDefense;    /// this was needed very much!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private int attackNum;
    private int defenseNum;
    private int attackPointInGame;
    private int defencePointInGame;
    private int level;
    private String monsterType;/////////////////////////////////////// this need to be set , could we make enum or will it be to much???????
    private boolean isSetInThisTurn = false;

    public Monster(String name, CardType cardType, Face face, int price, String description, String monsterType,
                   MonsterCardType monsterCardType, MonsterAttribute monsterAttribute,
                   int attackNum, int defenseNum, int level) {
        super(name, cardType, description, face, price);
        setAttackNum(attackNum);
        setDefenseNum(defenseNum);
        setLevel(level);
        setMonsterAttribute(monsterAttribute);
        setMonsterCardType(monsterCardType);
        setMonsterType(monsterType);
    }

    /*
        private MonsterType monsterTypeInGame;
        private MonsterAttribute monsterAttributeInGame;
        private int levelInGame;
    private String nameInGame;
       private boolean isAttackable = true;
    private boolean isActivateInThisTurn = false;


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
    }*/

    public boolean isSetInThisTurn() {
        return isSetInThisTurn;
    }

    public void setSetInThisTurn(boolean setInThisTurn) {
        isSetInThisTurn = setInThisTurn;
    }

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

    public MonsterCardType getMonsterCardType() {
        return monsterCardType;
    }

    public void setMonsterCardType(MonsterCardType monsterCardType) {
        this.monsterCardType = monsterCardType;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public AttackOrDefense getAttackOrDefense() {
        return attackOrDefense;
    }

    public void setAttackOrDefense(AttackOrDefense attackOrDefense) {
        this.attackOrDefense = attackOrDefense;
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


    public void increaseAttackPoint(int amount) {
        attackPointInGame += amount;
    }

    public void decreaseAttackPoint(int amount) {
        attackPointInGame += amount;
    }

    public void increaseDefensePoint(int amount) {
        attackPointInGame += amount;
    }

    public void decreaseDefensePoint(int amount) {
        attackPointInGame += amount;
    }


    public ArrayList<Monster> getMonsterOnBoard() {
        return cardOwner.getMonsterOnBoard();
    }

    public int howManyTributeNeed() {
        switch (this.level) {
            case 1:
            case 2:
            case 3:
            case 4:
                return 0;
            case 5:
            case 6:
                return 1;
            default:
                return 2;
        }
    }
}

