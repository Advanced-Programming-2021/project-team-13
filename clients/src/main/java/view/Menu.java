package view;

public enum Menu {
    LOGIN_MENU("Login Menu"),
    EXIT_MENU("Exit Menu"),
    MAIN_MENU("Main Menu"),
    SCOREBOARD_MENU("Scoreboard Menu"),
    PROFILE_MENU("Profile Menu"),
    SHOP_MENU("Shop Menu"),
    DECK_MENU("Deck Menu"),
    GAME_MENU("Game Menu"),
    DUEL_MENU("Duel Menu"),
    SHOW_GRAVEYARD("show graveyard"),
    IMPORT_EXPORT_MENU("Import/Export Menu");

    private final String menuName;

    Menu(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        if (this == SHOW_GRAVEYARD)
            return GAME_MENU.menuName;
        return menuName;
    }
}
