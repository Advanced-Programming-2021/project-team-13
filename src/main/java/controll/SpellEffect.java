package controll;
/////////////////////////////////////all codes in here are prototypes so dont bother if its shity , long, or empty

import Interfaces.Effects;
import enums.Face;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SpellEffect {
    private Player ourPlayer;/// suppose these are needed or maybe more!
    private Player rivalPlayer; // !!!!!!!!!!!!!!! where do we use this class and what attributes does it need???!!!!!!!!!!!!!!!

    //!!!!!!!!!!!!!!!!! graveYard !!!!!!!!!!!!!!
    public void run() {
        HashMap<String, Effects> effects = DeckController.effects;
        effects.put("monsterReborn", monsterReborn);
        effects.put("")
    }

    Effects monsterReborn = new Effects() {                                // problem with the arguments!!!
        @Override
        public boolean conditionCheck(Card playingCard) {
            if (playingCard.getFace() == Face.DOWN)
                return false;
            int check = 0;
            if (playingCard.getPlayer().getGraveyard()
                    .getCardFromGraveyard(playingCard.getCardName()) != null)
                check++;
            if (playingCard.getPlayer().getRivalPlayer().getGraveyard()
                    .getCardFromGraveyard(playingCard.getCardName()) != null)
                check++;
            return check != 0;
        }

        @Override
        public void useAbility(Card playingCard) {
            Card cardInGraveyard = playingCard.getPlayer().getGraveyard().getCardFromGraveyard
                    (playingCard.getPlayer().getSelectedCard().getCardName());
            playingCard.getPlayer().addCardInGame(cardInGraveyard);
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    };

//    public void terraforming(Spell spell) {// retrieve Field spell to hand from deck!!
//        if (spell.getSpellEffect() == enums.SpellEffect.FEILD)
//            ourPlayer.addCardInGame(spell);
//    }

    Effects terraforming = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            if (playingCard.getFace() == Face.DOWN)
                return false;
            if (playingCard.getPlayer().getDeck().getAllCardsInMainDeck().
                    getCardFromMainDeck(playingCard.getPlayer().getSelectedCard()) == null)
                if (playingCard.getPlayer().getDeck().getAllCardsInMainDeck().
                        getCardFromSideDeck(playingCard.getPlayer().getSelectedCard()) == null)
                    return false;
            Spell spell = (Spell) playingCard.getPlayer().getSelectedCard();
            return spell.getSpellEffect() == enums.SpellEffect.FEILD;
        }

        @Override
        public void useAbility(Card playingCard) {
            if (!conditionCheck(playingCard))
                return;
            playingCard.getPlayer().addCardToHand(playingCard.getPlayer().getSelectedCard());
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    };

//    public void potOfGreed(Spell firstSpell, Spell secondSpell) {// retrieve 2 Cards from top of the deck
//        ourPlayer.addCardInGame(firstSpell);
//        ourPlayer.addCardInGame(secondSpell);
//    }

    Effects potOfGreed = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return true;
        }

        @Override
        public void useAbility(Card playingCard) {
            Card card1 = playingCard.getPlayer().getSelectedCards().get(0);
            Card card2 = playingCard.getPlayer().getSelectedCards().get(1);
            playingCard.getPlayer().addCardToHand(card1);
            playingCard.getPlayer().addCardToHand(card2);
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    };

//    public void raigeki(ArrayList<Monster> rivalsMonsters) {// destroy all rival-controlled monster///challenge:delete from board maybe??? and how does board work???
//        for (Monster rivalsMonster : rivalsMonsters) {
//            rivalPlayer.getGraveyard().addCard(rivalsMonster);
//        }
//        rivalsMonsters.clear();
//    }

    Effects raigeki = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return true;
        }

        @Override
        public void useAbility(Card playingCard) {
            Cell[] cells = playingCard.getPlayer().getBoard().getMonster();
            for (Cell cell : cells) {
                playingCard.getPlayer().getRivalPlayer().
                        getGraveyard().addCard(cell.getCard());
                cell = null;
            }
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    };

//    public void changeOfHeart(Monster rivalsMonster) {// control one of rivals monster till end of the round//challenge:do we need controlled monsters arraylist???-how to maneuver in game??
//    }

    Effects changeOfHeart = new Effects() {// changed round needed !!! + how to use ----
        @Override
        public boolean conditionCheck(Card playingCard) {
            return playingCard.getPlayer().getSelectedCard() instanceof Monster;
        }

        @Override
        public void useAbility(Card playingCard) {
            if (!conditionCheck(playingCard))
                return;
            playingCard.getPlayer().setChangeOfHeart(playingCard.getPlayer().getSelectedCard());
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    }

    public void harpie(ArrayList<Spell> rivalSpells, ArrayList<Trap> rivalTraps) {//destroy all traps and spells of rival////how THE F do I destroy sth in general and in all cases

    }

    public void swordsOfRevealing(ArrayList<Monster> rivalMonsters) {// 3 round(end)-all rival monsters flip to up-enemy monsters cant attack//challenge: we need access to all monsters in game(same as controlled monsters???)

    }

    public void darkHole(ArrayList<Monster> allCards) {//destroy all monsters//challenge:we need an arraylist of all monster besides the one each player has

    }

    public void supplySquad(ArrayList<Monster> destroyedMonsters) {//if min of 1 of your monsters get destroyed,retrieved one card from top deck//challenge:the graveyard problem!!!!!

    }

    public void spellAbsorption() {//get 500 health instantly
        ourPlayer.increaseHealth(500);
    }

    public void messengerOfPeace(ArrayList<Monster> monstersWithAtkAbove1500) {//cant attack(needs 100 LP)//challenge:the maneuver to do sth so that the enemy cant attack!!!---this is the only continues spell so we need the continues maneuver!!!

    }

    public void twinTwisters(Card sacrifice, ArrayList<Card> boardCards) {//sacrifice one card-destroy max(2) trap or spells/challenge:the destroying maneuver

    }

    public void mysticalTyphoon(Card spellOrTrap) {// destroy a spell or a trap//
        if (spellOrTrap instanceof Spell || spellOrTrap instanceof Trap) {
            ourPlayer.getGraveyard().addCard(spellOrTrap);
            our
        }
    }

    public void ringOfDefense(Trap destroyerTrap) {// destroy ,destroyer trap

    }

    public void yami(ArrayList<Monster> allMonstersOnBoard) { // fiend/spell caster + 200 ATK/DEF - fairy - 200 ATK/DEF//challenge: get from board or each players arraylist of cards ???

    }

    public void forest(ArrayList<Monster> allMonstersOnBoard) { // Insect/beast/beast-warrior + 200 ATK/DEF

    }

    public void closedForest(ArrayList<Monster> ourBeastMonster) {// our controlled beast monsters receive 100 ATK per monster in graveyard

    }

    public void umiiruka(ArrayList<Monster> allAquaCardsOnBoard) {// all aquas receive +500 ATK -400 DEF

    }

    public void swordOfDarkDestruction() {//equipped fiend-spellcaster monsters +400 ATK -200 DEF// challenge : how to equip one monster(Card?) with sth ?? do we need attribute?? or method?? and how to make it work every round !!??!!

    }

    public void blackPendant(Monster monster) {//equipped monsters +500 ATK
        monster.setAttackNum(500);
    }

    public void unitedWeStand() {// equipped monsters receive 800 ATK/DEF per every controlled and faced up monster

    }

    public void magnumShield() {//only equips warriors - on attack:+DEF(or ATK -> DEF?);;; on def:+DEF

    }

    public void advancedArt() {//this + monsters that the sum of their levels equals ritual monster ---they go to the graveyard !!!

    }


}
