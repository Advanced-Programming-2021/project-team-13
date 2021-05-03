//package controll;
//
//import bullshit.Interfaces.Effects;
//import bullshit.Interfaces.Summon;
//import enums.Phase;
//import enums.Zone;
//import model.Card;
//import model.Cell;
//import model.Monster;
//import enums.Face;
//
//public class MonsterEffect {
//    GameController gameController = new GameController();
//    Effects commandKnightEffect = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            Monster monster = (Monster) playingCard;
//            return monster.getFace() == Face.UP;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//            Monster monster = (Monster) playingCard;
//            if (conditionCheck(playingCard)) {
//                monster.setAttackable(false);
//                for (Cell cell : monster.getPlayer().getBoard().getMonsters()) {
//                    Monster friendMonster = (Monster) cell.getCard();
//                    friendMonster.setAttackPointInGame(500 + friendMonster.getAttackNum());
//                }
//            }
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//            Monster monster = (Monster) playingCard;
//            if (conditionCheck(playingCard)) {
//                monster.setAttackable(true);
//                if (playingCard.getFace() == Face.DOWN)
//                    monster.setZone(Zone.MONSTER_ZONE);
//                else
//                    monster.setZone(Zone.GRAVEYARD);
//                for (Cell cell : monster.getPlayer().getBoard().getMonsters()) {
//                    Monster friendMonster = (Monster) cell.getCard();
//                    friendMonster.setAttackPointInGame(-500 + friendMonster.getAttackNum());
//                }
//            }
//        }
//
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return ((playingCard.getFace() == Face.DOWN)
//                    || (playingCard.getZone() == Zone.BETWEEN_GRAVEYARD_AND_FIELD));
//        }
//    };
//    Effects yomiShipEffect = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            Monster yomiShip = (Monster) playingCard;
//            Monster rivalMonster = (Monster) yomiShip.getPlayer().getRivalPlayer().getCurrentCard();
//            return (((yomiShip.getFace() == Face.UP) && (yomiShip.getAttackPointInGame() <= rivalMonster.getAttackPointInGame()))
//                    || ((yomiShip.getFace() == Face.DOWN) && yomiShip.getDefencePointInGame() <= rivalMonster.getAttackPointInGame()));
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//            if (conditionCheck(playingCard)) {
//                Monster yomiShip = (Monster) playingCard;
//                yomiShip.getPlayer().getRivalPlayer().getGraveyard().addCard(
//                        yomiShip.getPlayer().getRivalPlayer().getCurrentCard()
//                );
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
//            return true;
//        }
//    };
//    Effects suijinEffect = new Effects() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return playingCard.getFace() == Face.UP;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//            if (conditionCheck(playingCard)) {
//                Monster suijin = (Monster) playingCard;
//                Monster rivalMonster = (Monster) suijin.getPlayer().getRivalPlayer().getCurrentCard();
//                rivalMonster.setAttackPointInGame(0);
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
//    Summon manEaterBugSummon = new Summon() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return playingCard.getFace() == Face.DOWN;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//            if (conditionCheck(playingCard)) {
//                Monster manEaterBug = (Monster) playingCard;
//                manEaterBug.getPlayer().getRivalPlayer().getGraveyard().addCard(
//                        manEaterBug
//                                .getPlayer()
//                                .getSelectedCard()
//                );
//            }
//        }
//    };
//    Summon gateGuardianSummon = new Summon() {
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            return playingCard.getZone() == Zone.IN_HAND;
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//            Monster gateGuardian = (Monster) playingCard;
//            for (Card card : gateGuardian.getPlayer().getSelectedCards()) {
//                gateGuardian
//                        .getPlayer()
//                        .getGraveyard()
//                        .addCard(card);
//            }
//        }
//    };
//    Effects scannerEffect = new Effects() {
//
//        @Override
//        public boolean conditionCheck(Card playingCard) {
//            Monster scanner = (Monster) playingCard;
//            return !scanner.isActivateInThisTurn();
//        }
//
//        @Override
//        public void useAbility(Card playingCard) {
//            if (conditionCheck(playingCard)) {
//                Monster scanner = (Monster) playingCard;
//                Monster selectedMonster = (Monster) scanner.getPlayer().getSelectedCard();
//                scanner.setNameInGame(selectedMonster.getNameInGame());
//                scanner.setAttackPointInGame(selectedMonster.getAttackNum());
//                scanner.setDefencePointInGame(selectedMonster.getDefenseNum());
//                scanner.setMonsterAttributeInGame(selectedMonster.getMonsterAttribute());
//                scanner.setMonsterTypeInGame(selectedMonster.getMonsterType());
//                scanner.setLevelInGame(selectedMonster.getLevel());
//            }
//        }
//
//        @Override
//        public void destroyedEffect(Card playingCard) {
//            if (destroyedConditionChecker(playingCard)) {
//                Monster scanner = (Monster) playingCard;
//                Monster selectedMonster = (Monster) scanner.getPlayer().getSelectedCard();
//                scanner.setNameInGame(scanner.getCardName());
//                scanner.setAttackPointInGame(scanner.getAttackNum());
//                scanner.setDefencePointInGame(scanner.getDefenseNum());
//                scanner.setMonsterAttributeInGame(scanner.getMonsterAttribute());
//                scanner.setMonsterTypeInGame(scanner.getMonsterType());
//                scanner.setLevelInGame(scanner.getLevel());
//            }
//
//        }
//        @Override
//        public boolean destroyedConditionChecker(Card playingCard) {
//            return gameController.getCurrentPhase() == Phase.END_PHASE;
//        }
//    };
//}
