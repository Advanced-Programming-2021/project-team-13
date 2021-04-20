package model;

import java.util.ArrayList;

public class User implements Comparable<User> {
    private static ArrayList<User> allUsers;
    protected String username;
    protected String nickname;
    private String password;
    private int score;
    private int winNum;
    private int loseNum;
    private int drawNum;
    private ArrayList<Card> allCards;
    private ArrayList<Deck> userDecks;


//
//    public User findUserByUsername(String username) {
//
//    }

    public void addDeck(Deck deck) {

    }

    public void addScore(int scoreToAdd) {

    }

    //
//    public String getUsername() {
//
//    }
//
    public String getNickname() {
        return this.nickname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {

    }

    public void setNickname(String nickname) {
        this.nickname = nickname;

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

    public int getScore() {
        return score;
    }
    //
//    public int getScore() {
//
//    }
//
//    public int getLoseNum() {
//
//    }
//
//    public int getWinNum() {
//
//    }
//
//    public int getDrawNum() {
//
//    }
//
//    public String toString() {
//
//    }
//
//    public boolean equals() {
//
//    }
//
//    public ArrayList<Deck> getUserDecks() {
//
//    }
//

}
