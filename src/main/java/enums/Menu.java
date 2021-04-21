package enums;

public enum Menu {
    LOGIN_MENU("Login Menu"),
    EXIT_MENU("Exit Menu"),
    MAIN_MENU("Main Menu"),
    SCOREBOARD_MENU("Scoreboard Menu"),
    PROFILE_MENU("Profile Menu"),
    SHOP_MENU("Shop Menu"),
    DECK_MENU("Deck Menu");

    private final String menuName;

    Menu(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }
}
