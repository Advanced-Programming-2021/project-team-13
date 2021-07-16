package enums;

public enum Phase {
    DRAW_PHASE("Draw Phase"),
    STANDBY_PHASE("Standby Phase"),
    MAIN_PHASE_1("Main Phase 1"),
    BATTLE_PHASE("Battle Phase"),
    MAIN_PHASE_2("Main Phase 2"),
    END_PHASE("End Phase");
    private final String phaseName;

    Phase(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getPhaseName() {
        return phaseName;
    }
}
