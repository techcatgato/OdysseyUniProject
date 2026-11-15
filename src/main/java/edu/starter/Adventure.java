package edu.starter;

import java.util.Scanner;

/**
 * Coordinates the two-encounter adventure and the single reward between them.
 *
 * FIRST_ENCOUNTER --win--> REWARD_PENDING --valid choice--> SECOND_ENCOUNTER --win--> WON
 * any encounter --loss--> LOST;  input runs out --> ABANDONED
 */
public final class Adventure {

    public enum State { FIRST_ENCOUNTER, REWARD_PENDING, SECOND_ENCOUNTER, WON, LOST, ABANDONED }

    private final Fighter player;
    private final Scanner in;
    private State state = State.FIRST_ENCOUNTER;
    private boolean rewardClaimed = false;

    public Adventure(Fighter player, Scanner in) {
        this.player = player;
        this.in = in;
    }

    public State getState() {
        return state;
    }

    public boolean isRewardClaimed() {
        return rewardClaimed;
    }

    /** Plays the whole adventure and returns the final state. */
    public State run() {
        playFirstEncounter();
        if (state == State.REWARD_PENDING) {
            promptForReward();
        }
        if (state == State.SECOND_ENCOUNTER) {
            playSecondEncounter();
        }

        System.out.println();
        switch (state) {
            case WON -> System.out.println("FIVE-STAR REVIEWS! The busy hour is handled and the critic leaves five stars.");
            case LOST -> System.out.println("CLOSED DOWN! The pizzeria is shut for a safety inspection.");
            default -> System.out.println("The shift ended early.");
        }
        return state;
    }

    /** @pre state == FIRST_ENCOUNTER @post state is REWARD_PENDING, LOST or ABANDONED */
    void playFirstEncounter() {
        requireState(State.FIRST_ENCOUNTER);
        System.out.println("=== Encounter 1: " + Constants.ENEMY_1_NAME + " ===");
        Fighter enemy = newEnemy(Constants.ENEMY_1_NAME, Constants.ENEMY_1_HP);
        state = switch (new Encounter(player, enemy, in).run()) {
            case WIN -> State.REWARD_PENDING;
            case LOSE -> State.LOST;
            case RUN -> State.ABANDONED;
        };
    }

    /**
     * @pre state == REWARD_PENDING
     * @post exactly one reward applied and state == SECOND_ENCOUNTER,
     *       or input ran out and state == ABANDONED. Invalid input changes nothing and asks again.
     */
    void promptForReward() {
        requireState(State.REWARD_PENDING);
        System.out.println();
        System.out.println("Service survived! Mario treats himself to one reward:");
        for (Reward r : Reward.values()) {
            System.out.println(r.menuNumber() + ") " + r.displayName() + ": " + r.description());
        }

        while (!rewardClaimed) {
            System.out.print("Choose reward: ");
            if (!in.hasNextLine()) {
                System.out.println();
                System.out.println("No more orders. Shift abandoned.");
                state = State.ABANDONED;
                return;
            }
            Reward choice = Reward.fromMenuNumber(ConsoleChoice.read(in, 1, Reward.values().length));
            if (choice == null) {
                System.out.println("That reward isn't on the menu. Nothing changed; enter 1 or 2.");
                continue;
            }
            claimReward(choice);
            System.out.println("You chose " + choice.displayName() + " (" + choice + "). " + player);
        }
        state = State.SECOND_ENCOUNTER;
    }

    /**
     * The only way to apply a reward.
     * @pre state == REWARD_PENDING (fight 1 won, player alive) and no reward claimed yet
     * @post on success: reward applied once and rewardClaimed == true; on rejection: nothing changes
     * @return true if applied, false if rejected
     */
    boolean claimReward(Reward reward) {
        if (state != State.REWARD_PENDING || rewardClaimed || reward == null) {
            return false;
        }
        reward.applyTo(player);
        rewardClaimed = true;
        return true;
    }

    /** @pre state == SECOND_ENCOUNTER @post state is WON, LOST or ABANDONED */
    void playSecondEncounter() {
        requireState(State.SECOND_ENCOUNTER);
        System.out.println();
        System.out.println("=== Encounter 2: " + Constants.ENEMY_2_NAME + " ===");
        Fighter enemy = newEnemy(Constants.ENEMY_2_NAME, Constants.ENEMY_2_HP); // fresh, independent object
        state = switch (new Encounter(player, enemy, in).run()) {
            case WIN -> State.WON;
            case LOSE -> State.LOST;
            case RUN -> State.ABANDONED;
        };
    }

    private static Fighter newEnemy(String name, int hp) {
        return new Fighter(name,
                hp,
                0,
                Constants.playerMaxResource,
                Constants.playerDamage,
                Constants.playerSpecialDamage,
                Constants.playerSpecialCost,
                Constants.playerRecoverAmount);
    }

    private void requireState(State expected) {
        if (state != expected) {
            throw new IllegalStateException("Expected state " + expected + " but was " + state);
        }
    }
}