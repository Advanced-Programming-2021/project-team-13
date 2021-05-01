package enums;

public enum Phase {
    DRAW_PHASE("Draw"),
    STANDBY_PHASE("Standby"),
    MAIN_PHASE_1("Main1"),
    BATTLE_PHASE("Battle"),
    MAIN_PHASE_2("Main2"),
    END_PHASE("End");
    private final String phaseName;

    Phase(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getPhaseName() {
        return phaseName;
    }
}
