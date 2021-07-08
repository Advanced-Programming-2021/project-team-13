package controll;


import model.UserDeck;
import model.cards.Card;
import model.players.User;

public class DeckController {

    public String renameDeck(User user, UserDeck userDeck, String newName) {
        if (userDeck == null){
            return "noDeckExists";
        } else if (newName == null || newName.length() == 0) {
            return "invalidDeckName";
        } else {
            if (!userDeck.getName().equals(newName)) {
                userDeck.setName(newName);
                return "renamed";
            } else return "repetitive";
        }
    }

    public String createDeck(User user, String deckName) {
        if (deckName == null || deckName.length() == 0) {
            return "invalidDeckName";
        } else {
            if (user.getDeckByName(deckName.trim()) != null)
                return "hasDeck";
            else {
                user.getAllDecks().add(new UserDeck(deckName.trim()));
                return "created";
            }
        }
    }

    public boolean deleteDeck(User user, String deckName) {
        UserDeck userDeck = user.getDeckByName(deckName);
        if (userDeck == null)
            return false;
        else {
            user.getAllDecks().remove(userDeck);
            return true;
        }
    }

    public String activeDeck(User user, UserDeck userDeck) {
        if (userDeck == null)
            return "noDeckExists";
        if (!userDeck.isValid())
            return "invalidDeck";
        else {
            for (UserDeck userDeck1 : user.getAllDecks())
                if (userDeck1 != userDeck)
                    userDeck1.setActive(false);
            userDeck.setActive(true);
            return "activated";
        }
    }

    public String addCard(User user, String cardName, UserDeck userDeck, boolean isSide) {
        if (doesHaveCard(user, cardName)) {
            if (userDeck == null)
                return "noDeckExists";
            else {
                if (isDeckFull(userDeck, isSide))
                    return "deckIsFull";
                else {
                    if (areThereThree(userDeck, cardName, isSide))
                        return "threeCardExists";
                    else {
                        userDeck.addNewCard(cardName, isSide);
                        return "added";
                    }
                }
            }
        } else return "noCardExists";
    }

    private boolean areThereThree(UserDeck userDeck, String cardName, boolean isSide) {
        if (isSide) {
            if (userDeck.getCardNameToNumberInSide().containsKey(cardName))
                return userDeck.getCardNameToNumberInSide().get(cardName) == 3;
            else
                return false;
        } else {
            if (userDeck.getCardNameToNumberInMain().containsKey(cardName))
                return userDeck.getCardNameToNumberInMain().get(cardName) == 3;
            else return false;
        }
    }

    private boolean isDeckFull(UserDeck userDeck, boolean isSide) {
        if (isSide)
            return (userDeck.getNumberOfCardsInSide() == 12);
        else
            return (userDeck.getNumberOfCardsInMain() == 60);
    }

    private boolean doesHaveCard(User user, String cardName) {
        for (Card card : user.getAllCards()) {
            if (card.getCardNameInGame().equalsIgnoreCase(cardName.replace("_", "-")))
                return true;
        }
        return false;
    }

    public String removeCard(User user, String cardName, String deckName, boolean isSide) {
        UserDeck userDeck = user.getDeckByName(deckName);
        if (userDeck == null)
            return "noDeckExists";
        else {
            boolean hasCard = false;
            if (isSide) {
                if (userDeck.getCardNameToNumberInSide().containsKey(cardName))
                    hasCard = true;
            } else {
                if (userDeck.getCardNameToNumberInMain().containsKey(cardName))
                    hasCard = true;
            }
            if (hasCard) {
                userDeck.removeCard(cardName, isSide);
                return "removed";
            } else return "noCardExists";
        }
    }
}
