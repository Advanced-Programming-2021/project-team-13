package model;

import enums.*;

public class Monster extends Card {
    private MonsterType monsterType;
    private SummonType summonType;
    private MonsterAttribute monsterAttribute;
    private int attackNum;
    private int defenseNum;
    private int level;

    Monster(String name, CardType cardType, Face face, int price, int cardNum, String description,
            MonsterType monsterType, SummonType summonType, MonsterAttribute monsterAttribute,
            int attackNum, int defenseNum, int level) {
        super(name, cardType, description, face, price, cardNum);
        this.monsterAttribute = monsterAttribute;
        this.monsterType = monsterType;
        this.summonType = summonType;
        this.attackNum = attackNum;
        this.defenseNum = defenseNum;
        this.level = level;
    }


}
