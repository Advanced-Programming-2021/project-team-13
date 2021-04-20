package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static String registerUser = "user create --(\\w+) (\\w+) --(\\w+) (\\w+) --(\\w+) (\\w+)";
    public static String loginUser = "user login --(\\w+) (\\w+) --(\\w+) (\\w+)";
    public static String menuEnter = "menu enter (\\w+)";
    public static String menuExit = "menu exit";


    public static Matcher getInputMatcher(String input, String regex) {
        return Pattern.compile(input).matcher(regex);
    }
}
