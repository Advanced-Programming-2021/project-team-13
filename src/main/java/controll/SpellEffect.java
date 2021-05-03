package controll;
/////////////////////////////////////all codes in here are prototypes so dont bother if its shity , long, or empty

import Interfaces.Effects;
import enums.CardType;
import enums.Face;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SpellEffect {
    private Player ourPlayer;/// suppose these are needed or maybe more!
    private Player rivalPlayer; // !!!!!!!!!!!!!!! where do we use this class and what attributes does it need???!!!!!!!!!!!!!!!

    //!!!!!!!!!!!!!!!!! graveYard !!!!!!!!!!!!!!
    // all cards the condition of face up should be in every cards activation condition!!!!
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
            Cell[] cells = playingCard.getPlayer().getBoard().getMonsters();
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
    };

//    public void harpie(ArrayList<Spell> rivalSpells, ArrayList<Trap> rivalTraps) {//destroy all traps and spells of rival
//
//    }

    Effects harpie = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return true;
        }

        @Override
        public void useAbility(Card playingCard) {
            Cell[] spellOrTraps = playingCard.getPlayer().getBoard().getSpellOrTrap();
            for (int i = 0; i < spellOrTraps.length; i++) {
                playingCard.getPlayer().getGraveyard().addCard(spellOrTraps[i].getCard());
                spellOrTraps[i] = null;
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

//    public void swordsOfRevealing(ArrayList<Monster> rivalMonsters) {// 3 round(end)-all rival monsters flip to up-enemy monsters cant attack//challenge: we need access to all monsters in game(same as controlled monsters???)
//
//    }

    Effects swordsOfRevealing = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return false;
        }

        @Override
        public void useAbility(Card playingCard) {

        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    };

//    public void darkHole(ArrayList<Monster> allCards) {//destroy all monsters//challenge:we need an arraylist of all monster besides the one each player has
//
//    }


    Effects darkHole = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return true;
        }

        @Override
        public void useAbility(Card playingCard) {
            Cell[] ourMonsters = playingCard.getPlayer().getBoard().getMonsters();
            Cell[] rivalMonsters = playingCard.getPlayer().getRivalPlayer().getBoard().getMonsters();
            for (Cell ourMonster : ourMonsters) {
                sendToGraveyardFromBoard(ourMonster, playingCard.getPlayer().getGraveyard());
            }
            for (Cell rivalMonster : rivalMonsters) {
                sendToGraveyardFromBoard(rivalMonster, playingCard.getPlayer().getRivalPlayer().getGraveyard());
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

    //    public void supplySquad(ArrayList<Monster> destroyedMonsters) {//if min of 1 of your monsters get destroyed,retrieved one card from top deck
//
//    }
    Effects supplySquad = new Effects() {    // this is an enigma but I, could handle it just fine !!!!---- I mean if there exists in graveyard or if a monster gets destroyed afterwards
        @Override
        public boolean conditionCheck(Card playingCard) { //how to retrieve card from deck!!?>>?
            ArrayList<Card> allCardsInGraveyard = playingCard.getPlayer().getGraveyard().getAllCards();
            for (Card card : allCardsInGraveyard) {
                if (card.getCardType() == CardType.MONSTER)
                    return true;
            }
            return false;
        }

        @Override
        public void useAbility(Card playingCard) {
            if (!conditionCheck(playingCard))
                return;
            Card topCard = playingCard.getPlayer().getDeck().getAllCardsInMainDeck().get(0);
            playingCard.getPlayer().addCardToHand(topCard);
            playingCard.getPlayer().getDeck().getAllCardsInMainDeck().remove      // card gets deleted from arrayList , goes in again to shift all !!!!!!
                    (topCard);
            playingCard.getPlayer().getDeck().getAllCardsInMainDeck().add
                    (topCard);
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    };

    //    public void spellAbsorption() {//get 500 health instantly
//        ourPlayer.increaseHealth(500);
//    }
    Effects spellAbsorption = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return true;
        }

        @Override
        public void useAbility(Card playingCard) {
            if (!conditionCheck(playingCard))
                return;
            playingCard.getPlayer().increaseHealth(500);
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    };


    //    public void messengerOfPeace(ArrayList<Monster> monstersWithAtkAbove1500) {//cant attack(needs 100 LP)//challenge:the maneuver to do sth so that the enemy cant attack!!!---this is the only continues spell so we need the continues maneuver!!!
//
//    }
    Effects messengerOfPeace = new Effects() {   // the trick to check if it has ended , still remains at large
        @Override
        public boolean conditionCheck(Card playingCard) {
            return playingCard.getPlayer().willingToSacrifice();
        }

        @Override
        public void useAbility(Card playingCard) {
            if (!conditionCheck(playingCard))
                return;
            playingCard.getPlayer().decreaseHealth(100);
            Cell[] rivalMonsters = playingCard.getPlayer().getRivalPlayer().getBoard().getMonsters();
            for (Cell monster : rivalMonsters) {
                Monster rivalMonster = (Monster) monster.getCard();
                rivalMonster.setAttackable(false);
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

//    public void twinTwisters(Card sacrifice, ArrayList<Card> boardCards) {//sacrifice one card-destroy max(2) trap or spells/challenge:the destroying maneuver
//
//    }

    Effects twinTwisters = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return playingCard.getPlayer().willingToSacrifice();
        }

        @Override
        public void useAbility(Card playingCard) {
            if (!conditionCheck(playingCard))
                return;
            sendToGraveyard(playingCard.getPlayer().getSelectedCard(), playingCard.getPlayer().getGraveyard());
            //////////////////////////////////////////////////////////////////////////////////////////////////// needs a function to select cards here and max 2!!
            for (int i = 0; i < playingCard.getPlayer().getSelectedCards().size(); i++) {
                if (playingCard.getPlayer().getRivalPlayer()
                        .getSelectedCards().get(i).getCardType() != CardType.MONSTER)
                    sendToGraveyard(playingCard.getPlayer().getRivalPlayer()
                            .getSelectedCards().get(i), playingCard.getPlayer().getGraveyard());
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

//
//    public void mysticalTyphoon(Card spellOrTrap) {// destroy a spell or a trap//
//        if (spellOrTrap instanceof Spell || spellOrTrap instanceof Trap) {
//            ourPlayer.getGraveyard().addCard(spellOrTrap);
//            our
//        }
//    }

    Effects mysticalTyphoon = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return playingCard.getPlayer().getSelectedCard().getCardType() != CardType.MONSTER;
        }

        @Override
        public void useAbility(Card playingCard) {
            if (!conditionCheck(playingCard))
                return;
            sendToGraveyard(playingCard.getPlayer().getSelectedCard(), playingCard.getPlayer().getGraveyard());
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    };

//    public void ringOfDefense(Trap destroyerTrap) {// destroy ,destroyer trap
//
//    }

    Effects ringOfDefense = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return false;
        }

        @Override
        public void useAbility(Card playingCard) {

        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }

        @Override
        public boolean destroyedConditionChecker(Card playingCard) {
            return false;
        }
    };

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

    public void sendToGraveyardFromBoard(Cell cell, Graveyard graveyard) {
        graveyard.addCard(cell.getCard());
        cell = null;
    }

    private void sendToGraveyard(Card selectedCard, Graveyard graveyard) {  // if it was in game needs to empty the space so ITS NOT COMPLETED YET!!!!!
        graveyard.addCard(selectedCard);
    }

}
