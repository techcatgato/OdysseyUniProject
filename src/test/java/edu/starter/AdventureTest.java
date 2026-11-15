package edu.starter;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AdventureTest {

    private static Fighter newPlayer() {
        return new Fighter(Constants.playerName, Constants.playerMaxHp,
                Constants.playerStartResource, Constants.playerMaxResource,
                Constants.playerDamage, Constants.playerSpecialDamage,
                Constants.playerSpecialCost, Constants.playerRecoverAmount);
    }

    private static Fighter newPlayerWithResource(int resource) {
        return new Fighter(Constants.playerName, Constants.playerMaxHp,
                resource, Constants.playerMaxResource,
                Constants.playerDamage, Constants.playerSpecialDamage,
                Constants.playerSpecialCost, Constants.playerRecoverAmount);
    }

    private static Fighter newEnemy(int hp) {
        return new Fighter("enemy", hp, 0, Constants.playerMaxResource,
                Constants.playerDamage, Constants.playerSpecialDamage,
                Constants.playerSpecialCost, Constants.playerRecoverAmount);
    }

    @Test
    void W01_ATK_01() {
        Fighter enemy = newEnemy(30);

        newPlayer().attack(enemy);

        assertEquals(20, enemy.getHp());
    }

    @Test
    void W01_DEF_01() {
        Fighter p = newPlayer();
        p.protect();

        newEnemy(30).counterAttack(p);

        assertEquals(95, p.getHp());
        assertFalse(p.isProtected());
    }

    @Test
    void W01_SPC_01() {
        Fighter p = newPlayer();
        Fighter enemy = newEnemy(30);

        assertTrue(p.special(enemy));

        assertEquals(2, p.getResource());
        assertEquals(15, enemy.getHp());
    }

    @Test
    void W01_RES_01() {
        Fighter p = newPlayerWithResource(9);

        assertTrue(p.recover());
        assertEquals(10, p.getResource());

        assertFalse(p.recover());
        assertEquals(10, p.getResource());
    }

    @Test
    void W01_INV_01() {
        Fighter p = newPlayer();
        Fighter enemy = newEnemy(30);

        Encounter.Result result = new Encounter(p, enemy, new Scanner("abc\n9\n")).run();

        assertEquals(Encounter.Result.RUN, result);
        assertEquals(100, p.getHp());
        assertEquals(30, enemy.getHp());
    }
}
