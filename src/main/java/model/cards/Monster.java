package model.cards;

import enums.*;

import java.util.ArrayList;

public class Monster extends Card {
    public Monster attacker;
    private MonsterCardType monsterCardType;
    private SummonType summonType;
    private Card fieldSpell;
    private MonsterAttribute monsterAttribute;
    private AttackOrDefense attackOrDefense;/// this was needed very much!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private int attackNum;
    private int defenseNum;
    private int attackPointInGame;
    private int defencePointInGame;
    private int level;
    private String monsterType;///////// this need to be set , could we make enum or will it be to much???????
    private boolean isSetInThisTurn = false;
    private boolean HaveChangedPositionInThisTurn = false;
    private boolean isAttackedInThisTurn = false;
    private boolean isActiveAbility = false;
    private boolean attackable = true;
    private boolean isFieldSpellActive = false;
    private boolean usedAbilityThisTurn = false;
    private ArrayList<Monster> monsters; //This ArrayList Contains Monsters that get attackPoint From our Monster and Gets NEW in game;
    private ArrayList<Monster> commandKnightsActive;


    public void setCommandKnightsActive(Monster activeCommandKnight) {
        if (!commandKnightsActive.contains(activeCommandKnight))
            commandKnightsActive.add(activeCommandKnight);
    }

    public void removeFromActiveCommandKnights(Monster commandKnightToBeRemoved) {
        commandKnightsActive.remove(commandKnightToBeRemoved);
    }


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
        setAttackPointInGame(attackNum);
        setDefencePointInGame(defenseNum);
        commandKnightsActive = new ArrayList<>();
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public boolean isActiveAbility() {
        return isActiveAbility;
    }

    public void setActiveAbility(boolean activeAbility) {
        isActiveAbility = activeAbility;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public boolean isAttackable() {
        return attackable;
    }

    public ArrayList<Monster> getCommandKnightsActive() {
        return commandKnightsActive;
    }

    public void setAttackable(boolean attackable) {
        this.attackable = attackable;
    }

    public boolean getHaveChangePositionInThisTurn() {
        return HaveChangedPositionInThisTurn;
    }

    public void setHaveChangePositionInThisTurn(boolean doesHaveChangePositionInThisTurn) {
        this.HaveChangedPositionInThisTurn = doesHaveChangePositionInThisTurn;
    }

    public Card getFieldSpell() {
        return fieldSpell;
    }

    public void setFieldSpell(Card fieldSpell) {
        this.fieldSpell = fieldSpell;
    }

    public boolean isFieldSpellActive() {
        return isFieldSpellActive;
    }

    public void setFieldSpellActive(boolean fieldSpellActive) {
        isFieldSpellActive = fieldSpellActive;
    }

    public boolean isAttackedInThisTurn() {
        return isAttackedInThisTurn;
    }

    public void setAttackedInThisTurn(boolean attackedInThisTurn) {
        isAttackedInThisTurn = attackedInThisTurn;
    }

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

    public void setAttacker(Monster monster) {
        attacker = monster;
    }

    public Monster getAttacker() {
        return attacker;
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


  /*  public ArrayList<Monster> getMonsterOnBoard() {
        return cardOwner.getMonsterOnBoard();
    }*/

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

    public boolean hasUsedAbilityThisTurn() {
        return usedAbilityThisTurn;
    }

    public void setUsedAbilityThisTurn(boolean status) {
        usedAbilityThisTurn = status;
    }

    @Override
    public String toString() {
        return "Name: " + cardName + "\nLevel: " + level
                + "\nType: " + monsterType + "\nATK: " + attackNum
                + "\nDEF: " + defenseNum + "Description: " + cardDescription;
    }
}

