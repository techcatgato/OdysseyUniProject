package edu.starter;

import java.util.Scanner;

public class Encounter {
    private static final int ATTACK = 1;
    private static final int DEFEND = 2;
    private static final int SPECIAL = 3;
    private static final int RECOVER = 4;

    private final Fighter player;
    private final Fighter enemy;
    private final Scanner in;
    private int turn;

    public enum Result {WIN, LOSE, RUN}

    public Encounter(Fighter player, Fighter enemy, Scanner in) {
        this.player = player;
        this.enemy = enemy;
        this.in = in;
        this.turn = 1;
    }

    public boolean isOver() {
        return !player.isAlive() || !enemy.isAlive();
    }

    /** @post returns WIN only if the enemy is at 0 HP and the player is alive; RUN if input ran out */
    public Result run() {
        player.removeProtection();
        System.out.println("A wild " + enemy.getName() + " appears!");

        while (!isOver()) {
            System.out.println();
            System.out.println("--- Turn " + turn + " ---");
            System.out.println(player);
            System.out.println(enemy);
            printMenu();

            if (!in.hasNextLine()) {
                System.out.println("No more input. Fight abandoned.");
                return Result.RUN;
            }
            int choice = ConsoleChoice.read(in, ATTACK, RECOVER);
            if (choice == -1) {
                System.out.println("Invalid choice. Enter a number from " + ATTACK + " to " + RECOVER + ".");
                continue;
            }

            if (!playerTurn(choice)) {
                continue;
            }

            if (!enemy.isAlive()) {
                break;
            }

            enemyTurn();
            turn++;
        }

        return printResult();
    }

    private void printMenu() {
        System.out.println(ATTACK + ") Attack  (" + player.getAttackDamage() + " damage)");
        System.out.println(DEFEND + ") Defend  (prevent 5 damage from next hit)");
        System.out.println(SPECIAL + ") Special (" + player.getSpecialDamage() + " damage, costs " + player.getSpecialCost() + " resource)");
        System.out.println(RECOVER + ") Recover (+" + player.getRecoverAmount() + " resource, max " + player.getMaxResource() + ")");
        System.out.print("Choose: ");
    }

    private boolean playerTurn(int choice) {
        switch (choice) {
            case ATTACK -> {
                player.attack(enemy);
                System.out.println("You attack! " + enemy.getName() + " HP is now " + enemy.getHp() + ".");
                return true;
            }
            case DEFEND -> {
                player.protect();
                System.out.println("You raise your guard.");
                return true;
            }
            case SPECIAL -> {
                if (!player.special(enemy)) {
                    System.out.println("Not enough resource for Special (need " + player.getSpecialCost() + ", have " + player.getResource() + ").");
                    return false;
                }
                System.out.println("Special attack! " + enemy.getName() + " HP is now " + enemy.getHp() + ".");
                return true;
            }
            case RECOVER -> {
                if (!player.recover()) {
                    System.out.println("Resource is already at max (" + player.getMaxResource() + ").");
                    return false;
                }
                System.out.println("You recover. Resource is now " + player.getResource() + ".");
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private void enemyTurn() {
        enemy.counterAttack(player);
        System.out.println(enemy.getName() + " counterattacks! " + player.getName() + " HP is now " + player.getHp() + ".");
    }

    private Result printResult() {
        System.out.println();
        if (player.isAlive()) {
            System.out.println("You win! " + enemy.getName() + " is defeated.");
            return Result.WIN;
        }
        System.out.println("You lose! " + player.getName() + " has fallen.");
        return Result.LOSE;
    }

}
