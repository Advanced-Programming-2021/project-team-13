package model;

import java.util.ArrayList;

public class User implements Comparable<User> {
    private static final ArrayList<User> allUsers;

    static {
        allUsers = new ArrayList<>();
    }

    protected String username;
    protected String nickname;
    private String password;
    private long money = 100000;
    private int score = 0;
    private int winNum = 0;
    private int loseNum = 0;
    private int drawNum = 0;
    private ArrayList<Card> allCards;
    private ArrayList<Deck> allDecks;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.money = 100000;
        this.score = 0;
        this.winNum = 0;
        this.loseNum = 0;
        this.drawNum = 0;
        allCards = new ArrayList<>();
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

    public void addCard(Card card) {
        allCards.add(card);
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

    public ArrayList<Card> getAllCards() {
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

    public static ArrayList<User> getAllUsers() {
        return allUsers;
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
        return "- " + nickname + ": " + score;
    }

    //
//    public boolean equals() {
//
//    }
//
    public ArrayList<Deck> getAllDecks() {
        return allDecks;
    }
}
