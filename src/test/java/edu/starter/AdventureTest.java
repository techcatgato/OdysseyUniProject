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

    private static Adventure wonFirstFight(Fighter player, String moreInput) {
        Adventure a = new Adventure(player, new Scanner("3\n1\n1\n" + moreInput));
        a.playFirstEncounter();
        return a;
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

    @Test
    void W02_OBJ_01() {
        Fighter a = newEnemy(30);
        Fighter b = newEnemy(45);

        newPlayer().attack(a);

        assertEquals(20, a.getHp());
        assertEquals(45, b.getHp());
    }

    @Test
    void W02_ENC_01() {
        Fighter p = newPlayer();

        Adventure a = wonFirstFight(p, "");

        assertEquals(Adventure.State.REWARD_PENDING, a.getState());
        assertEquals(80, p.getHp());
        assertEquals(2, p.getResource());
    }

    @Test
    void W02_ENC_02() {
        Fighter p = newPlayer();
        Adventure a = wonFirstFight(p, "2\n1\n1\n1\n1\n");
        a.promptForReward();

        a.playSecondEncounter();

        assertEquals(Adventure.State.WON, a.getState());
        assertEquals(50, p.getHp());
    }

    @Test
    void W02_ENC_03() {
        Fighter p = newPlayer();
        p.protect();

        new Encounter(p, newEnemy(45), new Scanner("1\n")).run();

        assertEquals(90, p.getHp());
    }

    @Test
    void EDGE_SPECIAL_AT_3() {
        Fighter p = newPlayerWithResource(3);
        Fighter enemy = newEnemy(30);

        assertTrue(p.special(enemy));

        assertEquals(0, p.getResource());
        assertEquals(15, enemy.getHp());
    }

    @Test
    void EDGE_SPECIAL_AT_2() {
        Fighter p = newPlayerWithResource(2);
        Fighter enemy = newEnemy(30);

        new Encounter(p, enemy, new Scanner("3\n")).run();

        assertEquals(100, p.getHp());
        assertEquals(2, p.getResource());
        assertEquals(30, enemy.getHp());
    }

    @Test
    void EDGE_ENEMY_AT_5() {
        Fighter p = newPlayer();
        Fighter enemy = newEnemy(5);

        Encounter.Result result = new Encounter(p, enemy, new Scanner("1\n")).run();

        assertEquals(Encounter.Result.WIN, result);
        assertEquals(0, enemy.getHp());
        assertEquals(100, p.getHp());
    }

    @Test
    void T1() {
        Fighter p = newPlayer();
        newEnemy(30).counterAttack(p);

        Reward.RECOVERY.applyTo(p);

        assertEquals(100, p.getHp());
        assertEquals(5, p.getResource());
        assertEquals(10, p.getAttackDamage());
    }

    @Test
    void T2() {
        Fighter p = newPlayer();
        Adventure a = wonFirstFight(p, "");
        p.heal(20);

        assertTrue(a.claimReward(Reward.RECOVERY));

        assertEquals(100, p.getHp());
        assertTrue(a.isRewardClaimed());
        assertFalse(a.claimReward(Reward.POWER));
        assertEquals(10, p.getAttackDamage());
    }

    @Test
    void T3() {
        Fighter p = newPlayer();
        Reward.POWER.applyTo(p);
        Fighter enemy = newEnemy(45);

        p.attack(enemy);

        assertEquals(12, p.getAttackDamage());
        assertEquals(15, p.getSpecialDamage());
        assertEquals(33, enemy.getHp());
    }

    @Test
    void T4() {
        Fighter p = newPlayer();
        Adventure a = wonFirstFight(p, "7\nabc\n2\n");
        a.promptForReward();

        assertEquals(80, p.getHp());
        assertEquals(2, p.getResource());
        assertEquals(12, p.getAttackDamage());
        assertEquals(Adventure.State.SECOND_ENCOUNTER, a.getState());
    }

    @Test
    void T5() {
        Fighter p = newPlayer();
        Adventure a = new Adventure(p, new Scanner(""));

        assertFalse(a.claimReward(Reward.POWER));

        assertFalse(a.isRewardClaimed());
        assertEquals(100, p.getHp());
        assertEquals(10, p.getAttackDamage());
    }

    @Test
    void T6() {
        Fighter p = newPlayer();
        Adventure a = wonFirstFight(p, "");

        assertTrue(a.claimReward(Reward.POWER));
        assertFalse(a.claimReward(Reward.RECOVERY));
        assertFalse(a.claimReward(Reward.POWER));

        assertEquals(12, p.getAttackDamage());
        assertEquals(80, p.getHp());
        assertEquals(2, p.getResource());
    }

    @Test
    void T7() {
        Fighter p = newPlayer();
        Adventure a = new Adventure(p, new Scanner("2\n".repeat(20)));

        assertEquals(Adventure.State.LOST, a.run());

        assertFalse(a.isRewardClaimed());
        assertEquals(0, p.getHp());
    }

    @Test
    void T8() {
        Fighter p = newPlayer();
        Adventure a = wonFirstFight(p, "1\n");
        a.promptForReward();

        assertEquals(100, p.getHp());
        assertEquals(2, p.getResource());
        assertEquals(10, p.getAttackDamage());
        assertEquals(Adventure.State.SECOND_ENCOUNTER, a.getState());
    }

    @Test
    void T9() {
        Fighter p = newPlayer();
        Adventure a = new Adventure(p, new Scanner("3\n1\n1\n2\n1\n1\n1\n1\n"));

        assertEquals(Adventure.State.WON, a.run());

        assertEquals(50, p.getHp());
    }
}
