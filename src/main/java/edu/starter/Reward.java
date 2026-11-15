package edu.starter;

/** The two rewards offered between Encounter 1 and Encounter 2. */
public enum Reward {
    RECOVERY(1, "Recovery: restore " + Constants.RECOVERY_REWARD_HP + " HP (max " + Constants.playerMaxHp + ")"),
    POWER(2, "Power: +" + Constants.POWER_REWARD_ATTACK + " basic Attack (Special stays " + Constants.playerSpecialDamage + ")");

    private final int menuNumber;
    private final String description;

    Reward(int menuNumber, String description) {
        this.menuNumber = menuNumber;
        this.description = description;
    }

    public int menuNumber() {
        return menuNumber;
    }

    public String description() {
        return description;
    }

    /** @return the matching reward, or null for an unknown menu number */
    public static Reward fromMenuNumber(int number) {
        for (Reward r : values()) {
            if (r.menuNumber == number) return r;
        }
        return null;
    }

    /**
     * Applies this reward's effect. Eligibility (won fight 1, not claimed yet) is checked by Adventure.
     * @post RECOVERY: hp == min(maxHp, old hp + 25), resource and attack unchanged
     * @post POWER: attack == old attack + 2, hp, resource and special unchanged
     */
    void applyTo(Fighter player) {
        switch (this) {
            case RECOVERY -> player.heal(Constants.RECOVERY_REWARD_HP);
            case POWER -> player.increaseAttackDamage(Constants.POWER_REWARD_ATTACK);
        }
    }
}
