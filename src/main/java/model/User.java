package model;

import java.util.ArrayList;

public class User implements Comparable<User> {
    private static ArrayList<User> allUsers;
    protected String username;
    protected String nickname;
    private String password;
    private int score = 0;
    private int winNum = 0;
    private int loseNum = 0;
    private int drawNum = 0;
    private ArrayList<Card> allCards;
    private ArrayList<Deck> userDecks;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
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

    public void addDeck(Deck deck) {
        userDecks.add(deck);
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


    //    public String toString() {
//
//   }
//
//    public boolean equals() {
//
//    }
//
    public ArrayList<Deck> getUserDecks() {
        return userDecks;
    }
}
