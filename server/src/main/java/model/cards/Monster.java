package model.cards;

import enums.AttackOrDefense;
import enums.Face;
import enums.MonsterAttribute;
import enums.MonsterCardType;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Monster extends Card {
    private Monster attacker;
    private Monster attackedMonster;
    private MonsterCardType monsterCardType;
    private Card fieldSpell;
    private MonsterAttribute monsterAttribute;
    private AttackOrDefense attackOrDefense;
    private int attackNum;
    private int defenseNum;
    private int attackPointInGame;
    private int defencePointInGame;
    private int level;
    private String monsterType;
    private boolean isSetInThisTurn = false;
    private boolean HaveChangedPositionInThisTurn = false;
    private boolean isAttackedInThisTurn = false;
    private boolean isActiveAbility = false;
    private boolean attackable = true;
    private boolean isFieldSpellActive = false;
    private boolean hasBeenAttacked;
    private boolean isScanner = false;


    private ArrayList<Monster> monsters;
    private ArrayList<Monster> commandKnightsActive;
    private ArrayList<Card> equipSpellSword;


    public void setCommandKnightsActive(Monster activeCommandKnight) {
        if (!commandKnightsActive.contains(activeCommandKnight))
            commandKnightsActive.add(activeCommandKnight);
    }

    public void removeFromActiveCommandKnights(Monster commandKnightToBeRemoved) {
        commandKnightsActive.remove(commandKnightToBeRemoved);
    }


    public Monster(String name, Face face, int price, String description, String monsterType,
                   MonsterCardType monsterCardType, MonsterAttribute monsterAttribute,
                   int attackNum, int defenseNum, int level, Image image) {
        super(name, description, face, price, image);
        if (name.equalsIgnoreCase("scanner")) isScanner = true;
        setAttackNum(attackNum);
        setDefenseNum(defenseNum);
        setLevel(level);
        setMonsterAttribute(monsterAttribute);
        setMonsterCardType(monsterCardType);
        setMonsterType(monsterType);
        setAttackPointInGame(attackNum);
        setDefencePointInGame(defenseNum);
        commandKnightsActive = new ArrayList<>();
        equipSpellSword = new ArrayList<>();
    }

    public Monster(Monster that) {
        super(that);
        setAttackNum(that.attackNum);
        setDefenseNum(that.defenseNum);
        setLevel(that.level);
        setMonsterAttribute(that.monsterAttribute);
        setMonsterCardType(that.monsterCardType);
        setMonsterType(that.monsterType);
        setAttackPointInGame(that.attackNum);
        setDefencePointInGame(that.defenseNum);
        commandKnightsActive = new ArrayList<>();
        equipSpellSword = new ArrayList<>();
    }

    public void setCommandKnightsActive(ArrayList<Monster> commandKnightsActive) {
        this.commandKnightsActive = commandKnightsActive;
    }

    public void setEquipSpellSword(ArrayList<Card> equipSpellSword) {
        this.equipSpellSword = equipSpellSword;
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

    public boolean isScanner() {
        return isScanner;
    }

    public void setScanner(boolean scanner) {
        isScanner = scanner;
    }

    public Card getFieldSpell() {
        return fieldSpell;
    }

    public ArrayList<Card> getEquipedSpellsSword() {
        return equipSpellSword;
    }

    public void addToEquipSpellSword(Card equipSpell) {
        if (!this.equipSpellSword.contains(equipSpell))
            this.equipSpellSword.add(equipSpell);
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

    public Monster getAttackedMonster() {
        return attackedMonster;
    }

    public void setAttackedMonster(Monster attackedMonster) {
        this.attackedMonster = attackedMonster;
    }

    public void increaseAttackPoint(int amount) {
        attackPointInGame += amount;
    }

    public void decreaseAttackPoint(int amount) {
        attackPointInGame -= amount;
    }

    public void increaseDefensePoint(int amount) {
        defencePointInGame += amount;
    }

    public void decreaseDefensePoint(int amount) {
        defencePointInGame -= amount;
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

    @Override
    public String toString() {
        return "Name: "  /* + cardNameInGame*/ + "\nLevel: " + level
                + "\nType: " + monsterType + "\nATK: " + attackNum
                + "\nDEF: " + defenseNum + "\nDescription: "; /*+ cardDescription;*/
    }

    public void setHasBeenAttacked(boolean status) {
        this.hasBeenAttacked = status;
    }

    public boolean hasBeenAttacked() {
        return hasBeenAttacked;
    }
}

