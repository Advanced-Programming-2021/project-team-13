package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static final String ENTER_MENU = "enter menu (?<menuName>\\w+)";
    public static final String EXIT_MENU = "^menu exit$";
    public static final String SHOW_MENU = "^menu show-current$";
    public static final String CHANGE_NICKNAME = "^profile change --nickname (?<nickname>[\\w\\-]+)$";
    public static final String CHANGE_PASSWORD1 = "^profile change --password --current (?<currentPassword>\\w+) --new (?<newPassword>\\w+)$";
    public static final String CHANGE_PASSWORD2 = "^profile change --password --new (?<newPassword>\\w+) --current (?<currentPassword>\\w+)$";
    public static final String REGISTER = "^user create --(\\w+) (\\w+) --(\\w+) (\\w+) --(\\w+) (\\w+)$";
    public static final String LOGIN = "^user login --(\\w+) (\\w+) --(\\w+) (\\w+)$";
    public static final String BUY_CARD = "^buy card (?<cardName>\\w+)$";
    public static final String SHOP_SHOW_ALL = "^shop show --all$";
    public static final String CREATE_DECK = "^deck create (?<deckName>\\w+)$";
    public static final String DELETE_DECK = "^deck delete (?<deckName>\\w+)$";
    public static final String ACTIVE_DECK = "^deck set-activate (?<deckName>\\w+)$";
    public static final String ADD_CARD_TO_DECK1 = "^deck add-card (--\\w+ \\w+) (--\\w+ \\w+)( --side)?$";
    public static final String ADD_CARD_TO_DECK2 = "^deck add-card (--\\w+ \\w+)( --side)? (--\\w+ \\w+)$";
    public static final String ADD_CARD_TO_DECK3 = "^deck add-card( --side)? (--\\w+ \\w+) (--\\w+ \\w+)$";
    public static final String REMOVE_CARD_FROM_DECK1 = "^deck rm-card( --side)? (--\\w+ \\w+) (--\\w+ \\w+)$";
    public static final String REMOVE_CARD_FROM_DECK = "^deck rm-card (--\\w+ \\w+)( --side)? (--\\w+ \\w+)$";
    public static final String REMOVE_CARD_FROM_DECK2 = "^deck rm-card (--\\w+ \\w+) (--\\w+ \\w+)( --side)?$";
    public static final String SHOW_DECKS = "^deck show --all$";
    public static final String SHOW_ONE_DECK = "^deck show --([^ ]+ \\w+) (--side)?$";
    public static final String SHOW_ONE_DECK2 = "^deck show (--side)? --([^ ]+ \\w+)$";
    public static final String DECK_SHOW_CARDS = "^deck show --cards$";
    public static final String ATTACK = "attack ([1-5])";
    public static final String DUEL = "^duel [^ ]+ [^ ]+ [^ ]+ [^ ]+ [^ ]+$";
    public static final String NEW = "--new";
    public static final String SECOND_PLAYER = "--second-player (?<playerUsername>\\w+)";
    public static final String ROUNDS = "--rounds (?<rounds>\\d+)";
    public static final String AI = "--ai";
    public static final String OPPONENT = "--opponent";
    public static final String OPPONENT_WITH_FIELD = "--opponent (?<cardAddress>\\d+)";
    public static final String PLAYER_MONSTER = "--monster (?<cardAddress>\\d+)";
    public static final String PLAYER_SPELL = "--spell (?<cardAddress>\\d+)";
    public static final String PLAYER_HAND = "--hand (?<cardAddress>\\d+)";
    public static final String FIELD = "--field";
    public static final String OPPONENT_MONSTER = "--monster";
    public static final String OPPONENT_SPELL = "--spell";
    public static final String DESELECT = "select -d";
    public static final String CHANGE_SET = "set --position (?<position>attack|defense)";
    public static final String FIELD_SELECT = "^select [^ ]+$";
    public static final String PLAYER_SELECT = "^select [^ ]+ [^ ]+$";
    public static final String OPPONENT_SELECT = "^select [^ ]+ [^ ]+ [^ ]+$";


    public static String findUsername(String input) {
        Pattern pattern = Pattern.compile("--(username|u) (\\w+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    public static String findPassword(String input) {
        Pattern pattern = Pattern.compile("--(password|p) (\\w+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }


    public static String findNickname(String input) {
        Pattern pattern = Pattern.compile("--(nickname|n) (\\w+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static Matcher getInputMatcher(String input, String regex) {
        return Pattern.compile(regex).matcher(input);
    }

    public static String getCardName(String command) {
        Matcher matcher = getInputMatcher(command, "--card (\\w+)");
        if (matcher.find())
            return matcher.group(1);
        else return null;
    }

    public static String getDeckName(String command) {
        Matcher matcher = getInputMatcher(command, "--deck (\\w+)");
        if (matcher.find())
            return matcher.group(1);
        else return null;
    }

    public static String getDeckNameForSpecificShow(String command) {
        Matcher matcher = getInputMatcher(command, "--deck-name (\\w+)");
        if (matcher.find())
            return matcher.group(1);
        else return null;
    }
}
