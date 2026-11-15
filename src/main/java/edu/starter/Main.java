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

        Fighter enemy = newEnemy(Constants.ENEMY_1_NAME, Constants.ENEMY_1_HP);
        new Encounter(player, enemy, scanner).run();
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
