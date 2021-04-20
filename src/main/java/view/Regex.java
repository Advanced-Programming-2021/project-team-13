package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static String registerUser;
    public static String loginUser;
    public static final String ENTER_MENU = "enter menu (?<menuName>\\w+)";
    public static final String EXIT_MENU = "menu exit";
    public static final String SHOW_MENU = "menu show-current";
    public static final String CHANGE_NICKNAME = "profile change --nickname (?<nickname>[\\w\\-]+)";
    public static final String CHANGE_PASSWORD1 = "profile change --password --current (?<currentPassword>\\w+) --new (?<newPassword>\\w+)";
    public static final String CHANGE_PASSWORD2 = "profile change --password --new (?<newPassword>\\w+) --current (?<currentPassword>\\w+)";



    public static Matcher getInputMatcher(String input, String regex) {
        return Pattern.compile(regex).matcher(input);
    }

}
