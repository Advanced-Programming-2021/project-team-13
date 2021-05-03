//package controll;
//
//import bullshit.Interfaces.Effects;
//import enums.Face;
//import model.cards.Card;
//import model.cards.Monster;
//import model.cards.Spell;
//
////player.isAttacking
//public class TrapEffect {
//    private GameController gameController;
//
//    TrapEffect(GameController gameController) {
//        this.gameController = gameController;
//    }
//
//
//    Effects magicCylinder = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return playingCard.getPlayer().getRivalPlayer().isAttacking()
//                    && playingCard.getPlayer().getRivalPlayer().getCurrentCard() instanceof Monster;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//            if (conditionCheck(playingCard)) {
//
//            }
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//
//    Effects mirrorForce = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return false;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//
//    Effects mindCrush = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return false;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//
//    Effects trapHole = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return false;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//
//    Effects torrentialTribute = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return false;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//
//    Effects timeSeal = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return false;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//
//    Effects negateAttack = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return false;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//
//    Effects solemnWarning = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return false;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//
//    Effects magicJammer = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return playingCard.getPlayer().getCurrentCard() instanceof Spell
//                    || playingCard.getPlayer().getRivalPlayer().getCurrentCard() instanceof Spell;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//            if (conditionCheck(playingCard)){
//
//            }
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//
//    Effects callOfTheHaunted = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return playingCard.getPlayer().getGraveyard().getAllCards().size() != 0;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//            if (conditionCheck(playingCard)) {
//                gameController.getGameView().showGraveyard();
//                String monsterName = gameController.getGameView().inputMonsterOrSpellName();
//                Monster monster = playingCard.getPlayer().getGraveyard().getMonsterFromGraveyard(monsterName);
//                while (monster == null) {
//                    gameController.getGameView().printIvalidCardName();
//                    monsterName = gameController.getGameView().inputMonsterOrSpellName();
//                    monster = playingCard.getPlayer().getGraveyard().getMonsterFromGraveyard(monsterName);
//                }
//                playingCard.getPlayer().setSelectedCard(monster);
//                monster.setFace(Face.UP);
//                monster.setAttackable(true);
//                //place on board
//            }
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return false;
//        }
//    };
//}
