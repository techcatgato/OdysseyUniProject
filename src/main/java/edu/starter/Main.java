package edu.starter;

import java.util.Scanner;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Fighter player = new Fighter(Constants.playerName,
                Constants.playerMaxHp,
                Constants.playerStartResource,
                Constants.playerMaxResource,
                Constants.playerDamage,
                Constants.playerSpecialDamage,
                Constants.playerSpecialCost,
                Constants.playerRecoverAmount);

        System.out.println("=== Encounter 1 ===");
        Fighter firstEnemy = newEnemy(Constants.ENEMY_1_NAME, Constants.ENEMY_1_HP);
        Encounter.Result first = new Encounter(player, firstEnemy, scanner).run();
        if (first != Encounter.Result.WIN) {
            printEnding(first);
            return;
        }

        System.out.println();
        System.out.println("=== Encounter 2 ===");
        Fighter secondEnemy = newEnemy(Constants.ENEMY_2_NAME, Constants.ENEMY_2_HP);
        printEnding(new Encounter(player, secondEnemy, scanner).run());
    }

    private static void printEnding(Encounter.Result result) {
        System.out.println();
        switch (result) {
            case WIN -> System.out.println("ADVENTURE WON! Both enemies defeated.");
            case LOSE -> System.out.println("ADVENTURE LOST.");
            case RUN -> System.out.println("Adventure ended early.");
        }
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
}
