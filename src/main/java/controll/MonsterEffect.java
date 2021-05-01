package controll;

import Interfaces.Effects;
import enums.Zone;
import model.Card;
import model.Monster;
import enums.Face;

public class MonsterEffect {
    Effects commandKnightEffect = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            Monster monster = (Monster) playingCard;
            return monster.getFace() == Face.UP;
        }

        @Override
        public void useAbility(Card playingCard) {
            Monster monster = (Monster) playingCard;
            if (conditionCheck(playingCard)) {
                monster.setAttackable(false);
                for (Monster monster1 : monster.getMonsterOnBoard()) {
                    monster1.setAttackPointInGame(monster.getAttackPointInGame() + 500);
                }
            }
        }

        @Override
        public void destroyedEffect(Card playingCard) {
            Monster monster = (Monster) playingCard;
            if (conditionCheck(playingCard)) {
                monster.setAttackable(false);
                for (Monster monster1 : monster.getMonsterOnBoard()) {
                    monster1.setAttackPointInGame(monster.getAttackPointInGame() - 500);
                }
            }
        }

    };
    Effects yomiShipEffect = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return true;
        }

        @Override
        public void useAbility(Card playingCard) {
            Monster monster = (Monster) playingCard;
            monster.getPlayer().getRivalPlayer().getGraveyard().addCard(monster.getPlayer().getRivalPlayer().getCurrentCard());
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }
    };
    Effects suijinEffect = new Effects() {
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
    }
    Effects exploderDragonEffect = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return playingCard.getZone() == Zone.BETWEEN_GRAVEYARD_AND_FIELD;
        }

        @Override
        public void useAbility(Card playingCard) {
            if (conditionCheck(playingCard)) {
                Monster monster = (Monster) playingCard;
                monster.getPlayer().getRivalPlayer().getGraveyard().addCard(monster.getPlayer().getRivalPlayer().getCurrentCard());
                monster.setZone(Zone.GRAVEYARD);
            }
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }
    };
    Effects mirageDragonEffect = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return true;
        }

        @Override
        public void useAbility(Card playingCard) {
            Monster monster = (Monster) playingCard;
            monster.getPlayer().getRivalPlayer().setCanActivateTrap(false);
        }

        @Override
        public void destroyedEffect(Card playingCard) {
            Monster monster = (Monster) playingCard;
            monster.getPlayer().getRivalPlayer().setCanActivateTrap(true);
        }
    };
    Effects theCalculatorEffect = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return false;
        }

        @Override
        public void useAbility(Card playingCard) {
            Monster monster = (Monster) playingCard;

            monster.setAttackPointInGame(300 * );
        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }
    };
    Effects manEaterBugEffect = new Effects() {
        @Override
        public boolean conditionCheck(Card playingCard) {
            return playingCard.getFace() == Face.Summoning;
        }

        @Override
        public void useAbility(Card playingCard) {

        }

        @Override
        public void destroyedEffect(Card playingCard) {

        }
    };
    Effects gateGuardianEffect = new Effects() {
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
    };
    Effects scannerEffect = new Effects() {
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
    };
    Effects marshmallon = new Effects() {
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
    };
    
}
