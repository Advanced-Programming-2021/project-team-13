//
//package controll.gameController;
//
//import javafx.animation.FadeTransition;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.util.Duration;
//import model.cards.Card;
//import model.cards.Trap;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public abstract class TrapAction implements Effect {
//    protected static GameController gameController;
//    protected static HashMap<String, TrapAction> allTrapEffects;
//
//    static {
//        setAllTrapEffects();
//    }
//
//    protected Trap trap;
//    protected EffectHandler startActionCheck;
//    protected ArrayList<CardCommand> cardCommands;
//
//    public static void setGameController(GameController gameController) {
//        TrapAction.gameController = gameController;
//    }
//
//    private static void setAllTrapEffects() {
//        allTrapEffects = new HashMap<>();
//        allTrapEffects.put("Call Of The Haunted", new CallOfTheHaunted());
//        allTrapEffects.put("Time Seal", new TimeSeal());
//        allTrapEffects.put("Magic Jammer", new MagicJammer());
//        allTrapEffects.put("Mind Crush", new MindCrush());
//        allTrapEffects.put("Magic Cylinder", new MagicCylinder());
//        allTrapEffects.put("Negate Attack", new NegateAttack());
//        allTrapEffects.put("Torrential Tribute", new TorrentialTribute());
//        allTrapEffects.put("Mirror Force", new MirrorForce());
//        allTrapEffects.put("Trap Hole", new TrapHole());
//    }
//
//    public Trap getTrap() {
//        return trap;
//    }
//
//    public void setTrapCommands(ArrayList<CardCommand> cardCommands) {
//        this.cardCommands = cardCommands;
//    }
//
//    public void setStartActionCheck(EffectHandler startActionCheck) {
//        this.startActionCheck = startActionCheck;
//    }
//
//    public void run() {
//        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), trap.getImageView());
//        fadeTransition.setFromValue(1);
//        fadeTransition.setToValue(0.5);
//        fadeTransition.setAutoReverse(true);
//        fadeTransition.setCycleCount(1);
//        fadeTransition.play();
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2) , event -> {
//            for (CardCommand cardCommand : cardCommands)
//                cardCommand.execute();
//        }));
//        timeline.setDelay(Duration.millis(3000));
//        timeline.play();
//    }
//}
//
//class MagicCylinder extends TrapAction {
//
//    @Override
//    public void setCard(Card card) {
//        this.trap = (Trap) card;
//        EffectHandler effectHandler = new IsInAttack(card);
//        EffectHandler effectHandler1 = new PlayerCanActivateTrap(card);
//        effectHandler1.setNextHandler(effectHandler);
//        setStartActionCheck(effectHandler1);
//        CardCommand cardCommand = new SetCannotContinueAttack(card);
//        CardCommand cardCommand1 = new ReverseAttack(card);
//        CardCommand cardCommand2 = new SendCardToGraveyard(card);
//        cardCommands = new ArrayList<>();
//        cardCommands.add(cardCommand);
//        cardCommands.add(cardCommand1);
//        cardCommands.add(cardCommand2);
//    }
//}
//
//class CallOfTheHaunted extends TrapAction {
//
//    private EffectHandler endActionCheck1;
//    private EffectHandler endActionCheck2;
//    private ArrayList<CardCommand> endCommands;
//
//    public EffectHandler getEndActionCheck1() {
//        return endActionCheck1;
//    }
//
//    public void setEndActionCheck1(EffectHandler endActionCheck1) {
//        this.endActionCheck1 = endActionCheck1;
//    }
//
//    public EffectHandler getEndActionCheck2() {
//        return endActionCheck2;
//    }
//
//    public void setEndActionCheck2(EffectHandler endActionCheck2) {
//        this.endActionCheck2 = endActionCheck2;
//    }
//
//    @Override
//    public void setCard(Card card) {
//        this.trap = (Trap) card;
//        EffectHandler effectHandler = new PlayerCanActivateTrap(card);
//        EffectHandler effectHandler1 = new IsCardInGraveyard(card);
//        EffectHandler effectHandler2 = new IsEffectedCardInGraveyard(card);
//        EffectHandler effectHandler3 = new IsPlayerMonsterZoneHasEmptyPlace(card);
//        EffectHandler effectHandler4 = new GraveYardHasMonsterIn(card);
//        effectHandler3.setNextHandler(effectHandler4);
//        effectHandler.setNextHandler(effectHandler3);
//        setStartActionCheck(effectHandler);
//        setEndActionCheck1(effectHandler1);
//        setEndActionCheck2(effectHandler2);
//        cardCommands = new ArrayList<>();
//        CardCommand cardCommand = new BringMonsterBackFromGraveYardToBoard(card, this);
//        cardCommands.add(cardCommand);
//        setTrapCommands(cardCommands);
//        endCommands = new ArrayList<>();
//        CardCommand cardCommand2 = new SendCardToGraveyard(card);
//        CardCommand cardCommand3 = new SendEffectedCardToGraveyard(card);
//        endCommands.add(cardCommand3);
//        endCommands.add(cardCommand2);
//    }
//
//    public void runEnd() {
//        for (CardCommand cardCommand : endCommands)
//            cardCommand.execute();
//    }
//}
//
//class MagicJammer extends TrapAction {
//
//    @Override
//    public void setCard(Card card) {
//        this.trap = (Trap) card;
//        EffectHandler effectHandler = new IsAnySpellActive(card);
//        EffectHandler effectHandler1 = new PlayerCanActivateTrap(card);
//        effectHandler1.setNextHandler(effectHandler);
//        setStartActionCheck(effectHandler1);
//        CardCommand cardCommand = new ChooseCardFromHandToSacrifice(card);
//        CardCommand cardCommand1 = new FindActiveSpell(card);
//        CardCommand cardCommand2 = new SendCardToGraveyard(card);
//        CardCommand cardCommand3 = new SendEffectedCardToGraveyard(card);
//        cardCommands = new ArrayList<>();
//        cardCommands.add(cardCommand);
//        cardCommands.add(cardCommand1);
//        cardCommands.add(cardCommand2);
//        cardCommands.add(cardCommand3);
//    }
//}
//
//class TimeSeal extends TrapAction {
//
//    @Override
//    public void setCard(Card card) {
//        this.trap = (Trap) card;
//        EffectHandler effectHandler = new PlayerCanActivateTrap(card);
//        setStartActionCheck(effectHandler);
//        CardCommand cardCommand = new SetRivalPlayerCannotDrawCardNextTurn(card);
//        CardCommand cardCommand1 = new SendCardToGraveyard(card);
//        cardCommands = new ArrayList<>();
//        cardCommands.add(cardCommand);
//        cardCommands.add(cardCommand1);
//    }
//
//}
//
//class MindCrush extends TrapAction {
//
//    @Override
//    public void setCard(Card card) {
//        this.trap = (Trap) card;
//        EffectHandler effectHandler = new PlayerCanActivateTrap(card);
//        EffectHandler effectHandler1 = new EnemyHasAnyCardInHand(card);
//        effectHandler.setNextHandler(effectHandler1);
//        setStartActionCheck(effectHandler);
//        CardCommand cardCommand = new AnnounceCardNameToRemove(card);
//        CardCommand cardCommand1 = new SendCardToGraveyard(card);
//        cardCommands = new ArrayList<>();
//        cardCommands.add(cardCommand);
//        cardCommands.add(cardCommand1);
//    }
//}
//
//class NegateAttack extends TrapAction {
//
//    @Override
//    public void setCard(Card card) {
//        this.trap = (Trap) card;
//        EffectHandler effectHandler = new PlayerCanActivateTrap(card);
//        EffectHandler effectHandler1 = new IsInAttack(card);
//        effectHandler.setNextHandler(effectHandler1);
//        setStartActionCheck(effectHandler);
//        CardCommand cardCommand = new SetCannotContinueAttack(card);
//        CardCommand cardCommand1 = new SendCardToGraveyard(card);
//        CardCommand cardCommand2 = new GoNextPhase(card);
//        cardCommands = new ArrayList<>();
//        cardCommands.add(cardCommand);
//        cardCommands.add(cardCommand1);
//        cardCommands.add(cardCommand2);
//    }
//}
//
//class TorrentialTribute extends TrapAction {
//
//    @Override
//    public void setCard(Card card) {
//        this.trap = (Trap) card;
//        EffectHandler effectHandler = new PlayerCanActivateTrap(card);
//        EffectHandler effectHandler1 = new HasAnySummonHappenedThisTurn(card);
//        effectHandler.setNextHandler(effectHandler1);
//        setStartActionCheck(effectHandler);
//        CardCommand cardCommand = new DestroyAllMonsters(card);
//        CardCommand cardCommand1 = new SendCardToGraveyard(card);
//        cardCommands = new ArrayList<>();
//        cardCommands.add(cardCommand);
//        cardCommands.add(cardCommand1);
//    }
//}
//
//class MirrorForce extends TrapAction {
//
//    @Override
//    public void setCard(Card card) {
//        this.trap = (Trap) card;
//        EffectHandler effectHandler = new PlayerCanActivateTrap(card);
//        EffectHandler effectHandler1 = new IsInAttack(card);
//        effectHandler.setNextHandler(effectHandler1);
//        setStartActionCheck(effectHandler);
//        CardCommand cardCommand = new DestroyEnemyAttackingMonsters(card);
//        CardCommand cardCommand1 = new SendCardToGraveyard(card);
//        cardCommands = new ArrayList<>();
//        cardCommands.add(cardCommand);
//        cardCommands.add(cardCommand1);
//    }
//}
//
//class TrapHole extends TrapAction {
//
//    @Override
//    public void setCard(Card card) {
//        this.trap = (Trap) card;
//        EffectHandler effectHandler = new PlayerCanActivateTrap(card);
//        EffectHandler effectHandler1 = new HasNormalOrFlipSummonHappened(card);
//        EffectHandler effectHandler2 = new HasSummonedMonsterPowerMoreThan1000(card);
//        effectHandler1.setNextHandler(effectHandler2);
//        effectHandler.setNextHandler(effectHandler1);
//        setStartActionCheck(effectHandler);
//        CardCommand cardCommand = new DestroySummonedCard(card);
//        CardCommand cardCommand1 = new SendCardToGraveyard(card);
//        cardCommands = new ArrayList<>();
//        cardCommands.add(cardCommand);
//        cardCommands.add(cardCommand1);
//    }
//}
