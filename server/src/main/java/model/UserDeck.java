package model;

import java.util.HashMap;

public class UserDeck {

    private String name;
    private final HashMap<String, Integer> cardNameToNumberInMain;
    private final HashMap<String, Integer> cardNameToNumberInSide;
    private boolean isValid;
    private boolean isActive;
    private int numberOfCardsInMain;
    private int numberOfCardsInSide;

    public UserDeck(String name) {
        this.name = name;
        cardNameToNumberInMain = new HashMap<>();
        cardNameToNumberInSide = new HashMap<>();
        isActive = false;
        isValid = false;
        numberOfCardsInMain = 0;
        numberOfCardsInSide = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getCardNameToNumberInMain() {
        return cardNameToNumberInMain;
    }

    public HashMap<String, Integer> getCardNameToNumberInSide() {
        return cardNameToNumberInSide;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getNumberOfCardsInMain() {
        return numberOfCardsInMain;
    }

    public int getNumberOfCardsInSide() {
        return numberOfCardsInSide;
    }

    public void setNumberOfCardsInMain(int numberOfCardsInMain) {
        this.numberOfCardsInMain = numberOfCardsInMain;
    }

    public void setNumberOfCardsInSide(int numberOfCardsInSide) {
        this.numberOfCardsInSide = numberOfCardsInSide;
    }

    public void addNewCard(String cardName, boolean isSide) {
        if (isSide) {
            numberOfCardsInSide += 1;
            if (cardNameToNumberInSide.containsKey(cardName)) {
                Integer num = cardNameToNumberInSide.get(cardName);
                cardNameToNumberInSide.replace(cardName, num + 1);
            } else cardNameToNumberInSide.put(cardName, 1);
        } else {
            numberOfCardsInMain += 1;
            if (cardNameToNumberInMain.containsKey(cardName)) {
                Integer num = cardNameToNumberInMain.get(cardName);
                cardNameToNumberInMain.replace(cardName, num + 1);
            } else cardNameToNumberInMain.put(cardName, 1);
        }
        setValid(numberOfCardsInMain >= 40 && numberOfCardsInMain <= 60);
    }

    public void removeCard(String cardName, boolean isSide) {
        if (isSide) {
            if (cardNameToNumberInSide.containsKey(cardName)) {
                if (cardNameToNumberInSide.get(cardName) == 1) {
                    cardNameToNumberInSide.remove(cardName);
                } else {
                    Integer num = cardNameToNumberInSide.get(cardName);
                    cardNameToNumberInSide.replace(cardName, num - 1);
                }
                numberOfCardsInSide -= 1;
            }
        } else {
            if (cardNameToNumberInMain.containsKey(cardName)) {
                if (cardNameToNumberInMain.get(cardName) == 1) {
                    cardNameToNumberInMain.remove(cardName);
                } else {
                    int num = cardNameToNumberInMain.get(cardName);
                    cardNameToNumberInMain.replace(cardName, num - 1);
                }
                numberOfCardsInMain -= 1;
            }
        }
        setValid(numberOfCardsInMain >= 40);
    }
}
