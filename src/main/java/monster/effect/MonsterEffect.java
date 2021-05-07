package monster.effect;

import enums.Face;
import model.cards.Monster;

public class MonsterEffect {
    EffectAfterDead yomiShip = new EffectAfterDead() {
        @Override
        public boolean canActive(Monster monster) {
            return monster.getPlayer().getRivalPlayer().getSelectedCard() instanceof Monster;
        }

        @Override
        public void active(Monster monster) {
            if (canActive(monster)) {
                Monster rivalMonster = (Monster) monster.getPlayer().getRivalPlayer().getSelectedCard();
                monster.getPlayer().getRivalPlayer().getGraveyard().addCard(rivalMonster);
            }
        }

        @Override
        public boolean canDeActive(Monster monster) {
            return false;
        }

        @Override
        public void deActive(Monster monster) {

        }
    };
    EffectWhenGetAttack suijin = new EffectWhenGetAttack() {
        @Override
        public boolean canActive(Monster monster) {
            return monster.getFace() == Face.UP;
        }

        @Override
        public void active(Monster monster) {

        }

        @Override
        public boolean canDeActive(Monster monster) {
            return false;
        }

        @Override
        public void deActive(Monster monster) {

        }
    };
    EffectAfterFlipSummon manEaterBug = new EffectAfterFlipSummon() { // AND Gate Guardian Effect;
        @Override
        public boolean canActive(Monster monster) {
            return false;
        }

        @Override
        public void active(Monster monster) {
            //monster.getPlayer().
        }

        @Override
        public boolean canDeActive(Monster monster) {
            return false;
        }

        @Override
        public void deActive(Monster monster) {

        }
    };
    EffectWhenGetAttack marshmallo = new EffectWhenGetAttack() {
        @Override
        public boolean canActive(Monster monster) {
            return monster.getFace() == Face.DOWN;
        }

        @Override
        public void active(Monster monster) {
            if (canActive(monster))
                monster.getPlayer().getRivalPlayer().decreaseHealth(1000);
        }

        @Override
        public boolean canDeActive(Monster monster) {
            return false;
        }

        @Override
        public void deActive(Monster monster) {

        }
    };
    EffectAfterDead exploderDragon = new EffectAfterDead() {
        @Override
        public boolean canActive(Monster monster) {
            return false;
        }

        @Override
        public void active(Monster monster) {
            monster.getPlayer().getRivalPlayer().getGraveyard().addCard(monster
                    .getPlayer().getRivalPlayer().getSelectedCard());
        }

        @Override
        public boolean canDeActive(Monster monster) {
            return false;
        }

        @Override
        public void deActive(Monster monster) {

        }
    };
    EffectAfterNormalSummon mirageDragon = new EffectAfterNormalSummon() {
        @Override
        public boolean canActive(Monster monster) {
            return false;
        }

        @Override
        public void active(Monster monster) {
        }

        @Override
        public boolean canDeActive(Monster monster) {
            return false;
        }

        @Override
        public void deActive(Monster monster) {

        }
    };
    EffectAfterDead mirageDragonDeadEffect = new EffectAfterDead() {
        @Override
        public boolean canActive(Monster monster) {
            return false;
        }

        @Override
        public void active(Monster monster) {

        }

        @Override
        public boolean canDeActive(Monster monster) {
            return false;
        }

        @Override
        public void deActive(Monster monster) {

        }
    };
    EffectAfterNormalSummon terratiger = new EffectAfterNormalSummon() {
        @Override
        public boolean canActive(Monster monster) {
            return false;
        }

        @Override
        public void active(Monster monster) {

        }

        @Override
        public boolean canDeActive(Monster monster) {
            return false;
        }

        @Override
        public void deActive(Monster monster) {

        }
    };
}
