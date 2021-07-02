package model.players;

import controll.gameController.GameController;
import enums.AttackOrDefense;
import enums.Face;
import enums.Phase;
import enums.Zone;
import model.Board;
import model.Cell;
import model.Deck;
import model.Graveyard;
import model.cards.Monster;
import model.cards.Spell;
import model.cards.Trap;

import java.util.*;
import java.util.stream.Collectors;

public class AIPlayer extends Player {
    private final Comparator<Monster> comparator;
    private int roundsNumberThatDoNotAttack = 0;
    private String nickname;

    public AIPlayer(Deck deck) {
        super(deck);
        comparator = Comparator.comparing(Monster::getAttackPointInGame, Comparator.reverseOrder())
                .thenComparing(Monster::getDefencePointInGame, Comparator.reverseOrder());
    }

    public void renewPlayer(Deck deck) {
        this.cardsInHand = new ArrayList<>();
        this.lifePoint = 8000;
        this.isAttacking = false;
        this.board = new Board(new Deck(deck), new Graveyard(this));
        this.selectedCard = null;
        this.cardsInHand = new ArrayList<>();
        this.isAttacking = false;
        this.isSetOrSummonInThisTurn = false;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void play(Phase phase, GameController GC) {
        switch (phase) {
            case DRAW_PHASE:
            case STANDBY_PHASE:
            case END_PHASE:
                GC.nextPhase();
                break;
            case MAIN_PHASE_1:
            case MAIN_PHASE_2:
                mainPhaseMove(GC);
                GC.nextPhase();
                break;
            case BATTLE_PHASE:
                battlePhaseMove(GC);
                GC.nextPhase();
                break;
        }

    }

    private void mainPhaseMove(GameController GC) {
        activeSpell(GC);
        setTrap(GC);
        if (!isSetOrSummonInThisTurn()) {
            List<Monster> monsters = getSortedHandsMonsterBasedOnAttackAndDefence();
            if (monsters == null) return;
            for (Monster monster : monsters) {
                if (monster.getCardName().equalsIgnoreCase("Beast King Barbaros"))
                    if (summonBarbaros(monster, GC))
                        return;
                if (monster.getCardName().equalsIgnoreCase("the tricky"))
                    summonTricky(monster, monsters, GC);
                int numberOfTribute = monster.howManyTributeNeed();
                if (monster.getCardName().equalsIgnoreCase("Terratiger, the Empowered Warrior")) {
                    if (summonTerratiger(monster, monsters, GC))
                        return;
                }
                if (numberOfTribute <= getBoard().getNumberOfMonsterInBoard()) {
                    List<Monster> tributeList = Arrays.stream(getBoard().getMonsters()).map(e -> (Monster) e.getCard())
                            .filter(Objects::nonNull).sorted(comparator).collect(Collectors.toList());
                    for (int i = 1; i <= numberOfTribute; i++) {
                        getBoard().getGraveyard().addCard(tributeList.get(tributeList.size() - i));
                    }
                    GC.normalSummon(monster, AttackOrDefense.ATTACK);
                    return;
                }

            }
        }
    }

    private void setTrap(GameController GC) {
        List<Trap> traps = getCardsInHand().stream().filter(e -> e instanceof Trap)
                .map(e -> (Trap) e)
                .collect(Collectors.toList());
        if (traps.size() > 0)
            if (getBoard().getNumberOFSpellAndTrapInBoard() < 5) {
                GC.getCurrentPlayer().setSelectedCard(traps.get(0));
                GC.set();
            }
    }

    private void activeSpell(GameController GC) {
        List<Spell> spells = getCardsInHand().stream().filter(e -> e instanceof Spell)
                .map(e -> (Spell) e).filter(e -> e.getType().equalsIgnoreCase("field"))
                .collect(Collectors.toList());
        if (spells.size() > 0) {
            GC.getCurrentPlayer().setSelectedCard(spells.get(0));
            GC.activeEffect();
        }
    }

    private boolean summonBarbaros(Monster monster, GameController gc) {
        if (getBoard().getNumberOfMonsterInBoard() >= 3) {
            List<Monster> tributeList = Arrays.stream(getBoard().getMonsters()).map(e -> (Monster) e.getCard())
                    .filter(Objects::nonNull).sorted(comparator).collect(Collectors.toList());
            getBoard().getGraveyard().addCard(tributeList.get(tributeList.size() - 1));
            getBoard().getGraveyard().addCard(tributeList.get(tributeList.size() - 2));
            getBoard().getGraveyard().addCard(tributeList.get(tributeList.size() - 3));
            gc.normalSummon(monster, AttackOrDefense.ATTACK);
            for (Cell cell : gc.getCurrentPlayer().getRivalPlayer().getBoard().getMonsters())
                cell.setCard(null);
            return true;
        }
        gc.normalSummon(monster, AttackOrDefense.ATTACK);
        monster.setAttackPointInGame(1900);
        return true;
    }

    private boolean summonTerratiger(Monster monster, List<Monster> monsters, GameController gc) {
        if (monsters.size() > 1)
            for (Monster monster1 : monsters) {
                if (monster1.getLevel() <= 4) {
                    getBoard().putMonsterInBoard(monster1);
                    if (monster1.getCardName().equalsIgnoreCase("Mirage Dragon")) {
                        gc.getCurrentPlayer().getRivalPlayer().setCanActiveTrap(false);
                        monster1.setActiveAbility(true);
                    }
                    monster.setZone(Zone.MONSTER_ZONE);
                    monster.setFace(Face.UP);
                    monster.setAttackOrDefense(AttackOrDefense.DEFENSE);
                    getCardsInHand().remove(monster1);
                    gc.normalSummon(monster, AttackOrDefense.ATTACK);
                    monster.setActiveAbility(true);
                    return true;
                }
            }
        return false;
    }

    private void summonTricky(Monster monster, List<Monster> monsters, GameController GC) {
        if (monsters.size() > 1) {
            getBoard().getGraveyard().addCard(monsters.get(monsters.size() - 1));
            GC.normalSummon(monster, AttackOrDefense.ATTACK);
            if (new Random().nextInt() % 2 == 0)
                monster.setAttackOrDefense(AttackOrDefense.DEFENSE);
            this.setSetOrSummonInThisTurn(false);
            monster.setActiveAbility(true);
        }
    }

    private void battlePhaseMove(GameController GC) {
        List<Monster> monsters = Arrays.stream(getBoard().getMonsters()).map(e -> (Monster) e.getCard())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Monster> faceUpMonsters = Arrays.stream(getBoard().getMonsters()).map(e -> (Monster) e.getCard())
                .filter(Objects::nonNull)
                .filter(e -> e.getFace() == Face.UP)
                .sorted(comparator.reversed())
                .collect(Collectors.toList());
        List<Monster> faceDownMonster = Arrays.stream(getBoard().getMonsters()).map(e -> (Monster) e.getCard())
                .filter(Objects::nonNull)
                .filter(e -> e.getFace() == Face.DOWN)
                .sorted(comparator.reversed())
                .collect(Collectors.toList());
        boolean attackInThisTurn = false;
        if (monsters.size() != 0)
            for (Monster monster : monsters) {
                if (!monster.isAttackedInThisTurn()) {
                    if (GC.getCurrentPlayer().getRivalPlayer().getBoard().getNumberOfMonsterInBoard() == 0) {
                        this.setSelectedCard(monster);
                        GC.directAttack(true);
                        this.setSelectedCard(null);
                    } else {
                        Monster rivalMonster;
                        rivalMonster = faceUpAttack(faceUpMonsters, faceDownMonster, monster);
                        if (rivalMonster == null)
                            rivalMonster = faceDownAttack(faceDownMonster, monster);
                        if (rivalMonster != null) {
                            setSelectedCard(monster);
                            attackInThisTurn = true;
                            int number = GC.getCurrentPlayer().getRivalPlayer().getBoard().getMonsterCellNumber(rivalMonster);
                            if (number == 1)
                                GC.attack(3);
                            else if (number == 2)
                                GC.attack(4);
                            else if (number == 3)
                                GC.attack(2);
                            else if (number == 4)
                                GC.attack(5);
                            else
                                GC.attack(1);
                            setSelectedCard(null);
                        }
                    }
                }

            }
        if (!attackInThisTurn) roundsNumberThatDoNotAttack++;
    }

    private Monster faceDownAttack(List<Monster> faceDownMonster, Monster monster) {
        if (faceDownMonster.size() == 0) return null;
        for (Monster faceUpMonster : faceDownMonster) {
            if (!faceUpMonster.getCardName().equalsIgnoreCase("yomi ship") && !faceUpMonster.getCardName()
                    .equalsIgnoreCase("suijin")
                    && !faceUpMonster.getCardName().equalsIgnoreCase("Marshmallon"))
                if (faceUpMonster.getAttackPointInGame() < monster.getAttackPointInGame())
                    return faceUpMonster;
        }
        for (Monster faceUpMonster : faceDownMonster) {
            if (faceUpMonster.getAttackPointInGame() < monster.getAttackPointInGame())
                return faceUpMonster;
        }
        if (roundsNumberThatDoNotAttack >= 3
                && faceDownMonster.size() > 0) {
            roundsNumberThatDoNotAttack = 0;
            return faceDownMonster.get(0);
        }
        return null;
    }

    private Monster faceUpAttack(List<Monster> faceUpMonsters, List<Monster> faceDownMonsters, Monster monster) {
        if (faceUpMonsters.size() == 0) return null;
        if (faceUpMonsters.size() == 1) {
            if (faceUpMonsters.get(0).getAttackPointInGame() < monster.getAttackPointInGame())
                return faceUpMonsters.get(0);
            else
                return null;
        }
        for (Monster faceUpMonster : faceUpMonsters) {
            if (!faceUpMonster.getCardName().equalsIgnoreCase("yomi ship") && !faceUpMonster.getCardName()
                    .equalsIgnoreCase("suijin")
                    && !faceUpMonster.getCardName().equalsIgnoreCase("Marshmallon"))
                if (faceUpMonster.getAttackPointInGame() < monster.getAttackPointInGame())
                    return faceUpMonster;
        }
        if (faceDownMonsters.size() == 0)
            for (Monster faceUpMonster : faceUpMonsters) {
                if (faceUpMonster.getAttackPointInGame() < monster.getAttackPointInGame())
                    return faceUpMonster;
            }
        if (roundsNumberThatDoNotAttack >= 2
                && faceUpMonsters.size() > 0) {
            roundsNumberThatDoNotAttack = 0;
            return faceUpMonsters.get(0);
        }
        return null;
    }

    public List<Monster> getSortedHandsMonsterBasedOnAttackAndDefence() {
        if (getCardsInHand().size() == 0) return null;
        return getCardsInHand().stream().filter(e -> e instanceof Monster)
                .map(e -> (Monster) e).sorted(comparator)
                .collect(Collectors.toList());
    }

}
