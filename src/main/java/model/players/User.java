package model.players;

import model.Deck;
import model.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class User implements Comparable<User> {
    private static ArrayList<User> allUsers;

    static {
        allUsers = new ArrayList<>();
    }

    protected String username;
    protected String nickname;
    private String password;
    private long money;
    private int score;
    private int winNum;
    private int loseNum;
    private int drawNum;
    private HashMap<String, Integer> allCards;
    private ArrayList<Deck> allDecks;
    private ArrayList<Card> cards;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.money = 100000;
        this.score = 0;
        this.winNum = 0;
        this.loseNum = 0;
        this.drawNum = 0;
        allCards = new HashMap<>();
        allDecks = new ArrayList<>();
        allUsers.add(this);
    }

    public static User getUserByUsername(String username) {
        for (User allUser : allUsers) {
            if (allUser.username.equals(username))
                return allUser;
        }
        return null;
    }

    public static User getUserByNickname(String nickname) {
        for (User allUser : allUsers) {
            if (allUser.getNickname().equals(nickname))
                return allUser;
        }
        return null;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(String cardName) {
        cardName = cardName.replace("_", "-");
        if (allCards.containsKey(cardName))
            allCards.put(cardName, allCards.get(cardName) + 1);
        else
            allCards.put(cardName, 1);
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void addDeck(Deck deck) {
        allDecks.add(deck);
    }

    public void addScore(int scoreToAdd) {
        score += scoreToAdd;
    }

    public void addWins(int winToAdd) {
        winNum += winToAdd;
    }

    public void addDraws(int drawToAdd) {
        drawNum += drawToAdd;
    }

    public void addLosts(int lostToAdd) {
        loseNum += lostToAdd;
    }

    public void addMoney(int moneyToAdd) {
        money += moneyToAdd;
    }

    public HashMap<String, Integer> getAllCards() {
        return allCards;
    }

    public long getMoney() {
        return money;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public int getLoseNum() {
        return loseNum;
    }

    public int getWinNum() {
        return winNum;
    }

    public int getDrawNum() {
        return drawNum;
    }

    public Deck getActiveDeck() {
        for (Deck allDeck : allDecks) {
            if (allDeck.isActive())
                return allDeck;
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> users) {
        allUsers = users;
    }

    @Override
    public int compareTo(User user) {
        if (user.score > score) return 1;
        if (user.score == score
                && user.getNickname().compareTo(nickname) < 0) return 1;
        return -1;
    }


    @Override
    public String toString() {
        return "- " + this.nickname + ": " + this.score;
    }

    public ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : allDecks) {
            if (deck.getName().equals(deckName))
                return deck;
        }
        return null;
    }
}
