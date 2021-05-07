package model.players;

import model.Board;
import model.Deck;
import model.Graveyard;
import model.cards.Card;
import model.cards.Monster;

import java.util.ArrayList;

public class Player {
    private static final ArrayList<Player> allPlayers;

    static {
        allPlayers = new ArrayList<>();
    }

    private Graveyard playersGraveyard;
    private final String nickname;
    private int lifeInGame;
    private Player rivalPlayer;
    private Card currentCard;
    private Deck deck;
    User user;
    private Board board;
    private Card selectedCard;
    private ArrayList<Card> cardsInHand;
    private boolean isSetOrSummonInThisTurn = false;


    public Player(String nickname, Deck deck, User user) {
        this.nickname = nickname;
        this.user = user;
        this.deck = deck;
        playersGraveyard = new Graveyard(this);
        this.board = new Board(deck, playersGraveyard);
        this.lifeInGame = 8000;
//        currentCard = null;
        selectedCard = null;
        cardsInHand = new ArrayList<>();
        allPlayers.add(this);
    }

/*
    public Player findPlayerByName(String username){
<<<<<<< HEAD
        for (Player player : allPlayers){
            if (player.nickname.equals(username))
=======
        for (Player player : allPlayers) {
            if (player.username.equals(username))
>>>>>>> fb940392008a94353a4aa071577d54a4515b589f
                return player;
        }
        return null;
    }
*/

    public void play() {

    }

    public boolean isSetOrSummonInThisTurn() {
        return isSetOrSummonInThisTurn;
    }

    public void setSetOrNormalSummonInThisTurn(boolean setOrSummonInThisTurn) {
        isSetOrSummonInThisTurn = setOrSummonInThisTurn;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(Card card) {
        cardsInHand.add(card);
    }

    public Graveyard getPlayersGraveyard() {
        return playersGraveyard;
    }

    public void setPlayersGraveyard(Graveyard playersGraveyard) {
        this.playersGraveyard = playersGraveyard;
    }

    public void setLifeInGame(int lifeInGame) {
        this.lifeInGame = lifeInGame;
    }

//    public void setCurrentCard(Card currentCard) {
//        this.currentCard = currentCard;
//    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Graveyard getGraveyard() {
        return playersGraveyard;
    }

    public int getLifeInGame() {
        return lifeInGame;
    }

    public Player getRivalPlayer() {
        return rivalPlayer;
    }

    public Deck getDeck() {
        return deck;
    }

    public void increaseHealth(int amount) {
        lifeInGame += amount;
    }

    public void decreaseHealth(int amount) {       //thers an error somewhere that I didnt put a minus , dont know where but I will find out !!!!!!!!
        lifeInGame -= amount;
    }

    public void addCardInGame(Card card) {
    }
    public void setRivalPlayer(Player rivalPlayer) {
        this.rivalPlayer = rivalPlayer;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Monster> getMonsterOnBoard() {       //I dont think this works!!!!!
        ArrayList<Monster> monsters = new ArrayList<>();
        return monsters;
    }

//    public Card getCurrentCard() {
//        return currentCard;
//    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public boolean willingToSacrifice() { // /////////////////////////////////////////////////needs to be completed , player needs to give health!!!!!!
        ///prototype
        return false; // to be completed
    }
}


/*    private ArrayList<Card> selectedCardsForTribute = new ArrayList<>();
    private boolean canActivateTrap = true;
    private boolean canAttack = true;*/
/*
    public ArrayList<Card> getSelectedCardsForTribute() {
        return selectedCardsForTribute;
    }

    public void setSelectedCardsForTribute(ArrayList<Card> selectedCardsForTribute) {
        this.selectedCardsForTribute = selectedCardsForTribute;
    }
*/