package controll;

import model.*;

import java.util.ArrayList;

public class SpellEffect {
    private Player ourPlayer;/// suppose these are needed or maybe more!
    private Player rivalPlayer; // !!!!!!!!!!!!!!! where do we use this class and what attributes does it need???!!!!!!!!!!!!!!!
                                //!!!!!!!!!!!!!!!!! graveYard !!!!!!!!!!!!!!

    public void monsterReborn(Monster monster) {// revive from graveyard!!!!

    }

    public void terraforming(Spell spell) {// retrieve Field spell to hand from deck!!

    }

    public void potOfGreed(Spell firstSpell, Spell secondSpell) {// retrieve 2 spells from top deck

    }

    public void raigeki(ArrayList<Monster> rivalsMonsters) {// destroy all rival-controlled monsters

    }

    public void changeOfHeart(Monster rivalsMonster) {// control one of rivals monster till end of the round

    }

    public void harpie(ArrayList<Spell> rivalSpells, ArrayList<Trap> rivalTraps) {//destroy all traps and spells of rival

    }

    public void swordsOfRevealing(ArrayList<Monster> rivalMonsters) {// 3 round(end)-all rival monsters flip to up-enemy monsters cant attack

    }

    public void darkHole(ArrayList<Monster> allCards) {//destroy all monsters(we need an arraylist of all monster besides the one each player has)

    }

    public void supplySquad(ArrayList<Monster> destroyedMonsters) {//if min of 1 of your monsters get destroyed,retrieved one card from top deck

    }

    public void spellAbsorption() {//get 500 health instantly

    }

    public void messengerOfPeace(ArrayList<Monster> monstersWithAtkAbove1500) {//cant attack(needs 100 LP)

    }

    public void twinTwisters(Card sacrifice, ArrayList<Card> boardCards) {//sacrifice one card-destroy max(2) trap or spells

    }

    public void mysticalTyphoon(Card spellOrTrap) {// destroy a spell or a trap

    }

    public void ringOfDefense(Trap destroyerTrap) {// destroy ,destroyer trap

    }

    public void yami(ArrayList<Monster> allMonstersOnBoard) { // fiend/spell caster + 200 ATK/DEF - fairy - 200 ATK/DEF

    }

    public void forest(ArrayList<Monster> allMonstersOnBoard) { // Insect/beast/beast-warrior + 200 ATK/DEF

    }

    public void closedForest(ArrayList<Monster> ourBeastMonster) {// our controlled beast monsters receive 100 ATK per monster in graveyard

    }

    public void umiiruka(ArrayList<Monster> allAquaCardsOnBoard) {// all aquas receive +500 ATK -400 DEF

    }

    public void swordOfDarkDestruction() {

    }

    public void blackPendant() {

    }

    public void unitedWeStand() {

    }

    public void magnumShield() {

    }

    public void advancedArt() {

    }
}
