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

        new Adventure(player, scanner).run();
    }
}