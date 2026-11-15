# Dodgy Pizza

**Theme:** a very dodgy pizzeria run by an insane chef. Mario has to survive **The Busy Hour** (30 HP) and then **The Food Critic** (45 HP) with a rolling pin (Attack), a cutting board (Defend), a hot pizza to throw (Special) and slices he eats from customers (Recover). Survive both and the critic leaves five stars. Lose and the pizzeria gets shut down for safety violations.

## Requirements

- JDK 25
- Maven 3.9+ (for tests)

## Compile, run and test

```bash
mkdir -p build
javac -d build src/main/java/edu/starter/*.java
java -cp build edu.starter.Main
```

```bash
mvn test
```

The tests in `src/test/java/edu/starter/AdventureTest.java` are named after the handout IDs:

| Handout ID | Test method |
|---|---|
| Week 1: W01-ATK-01, W01-DEF-01, W01-SPC-01, W01-RES-01, W01-INV-01 | `W01_ATK_01` … `W01_INV_01` |
| Week 2 T1–T4: W02-OBJ-01, W02-ENC-01/02/03 | `W02_OBJ_01`, `W02_ENC_01`, `W02_ENC_02`, `W02_ENC_03` |
| Week 2 T5: Special at 2 resource rejected | `EDGE_SPECIAL_AT_2` |
| Week 2 T6: no counterattack after a knockout | `EDGE_ENEMY_AT_5` |
| Week 2 T7: loss ends the adventure | `T7` |
| Week 2 edge cases | `EDGE_SPECIAL_AT_3`, `W01_RES_01` (9 → 10, then rejected) |
| Week 3 T1–T9 | `T1` … `T9` |

## Reproduce an example run

```bash
printf '3\n1\n1\n7\nabc\n2\n1\n1\n1\n1\n' | java -cp build edu.starter.Main
```

---

## Traces

The battle menu is printed every turn; it's shown once and then left out.

### Trace A: POWER (Heavier Pin), with invalid reward input

Input: `3 1 1 7 abc 2 1 1 1 1`

```text
=== Encounter 1: The Busy Hour ===
The Busy Hour breaks the door!

--- Turn 1 ---
Mario [HP 100/100, Energy 5/10, Attack 10]
The Busy Hour [HP 30/30, Energy 0/10, Attack 10]
1) Rolling Pin Smack   (10 damage)
2) Cutting Board Block (block 5 damage from the next hit)
3) Pizza Throw         (15 damage, costs 3 Energy)
4) Eat a Slice         (+3 Energy, max 10)
Choose: Mario throws a flaming hot pizza! The Busy Hour HP is now 15.
The Busy Hour doesn't like Mario's service! Mario HP is now 90.

--- Turn 2 ---
Mario [HP 90/100, Energy 2/10, Attack 10]
The Busy Hour [HP 15/30, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Busy Hour HP is now 5.
The Busy Hour doesn't like Mario's service! Mario HP is now 80.

--- Turn 3 ---
Mario [HP 80/100, Energy 2/10, Attack 10]
The Busy Hour [HP 5/30, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Busy Hour HP is now 0.

W. The Busy Hour is handled.

Battle won, choose a reward:
1) Crispy Pizza: restore 25 HP (max 100)
2) Heavier Pin: +2 Rolling Pin Smack damage (Pizza Throw stays 15)
Choose one: That reward isn't on the menu brother. enter 1 or 2.
Choose one: That reward isn't on the menu brother. enter 1 or 2.
Choose one: Mario takes the Heavier Pin (POWER). Mario [HP 80/100, Energy 2/10, Attack 12]

=== Encounter 2: The Food Critic ===
The Food Critic breaks the door!

--- Turn 1 ---
Mario [HP 80/100, Energy 2/10, Attack 12]
The Food Critic [HP 45/45, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Food Critic HP is now 33.
The Food Critic doesn't like Mario's service! Mario HP is now 70.

--- Turn 2 ---
Mario [HP 70/100, Energy 2/10, Attack 12]
The Food Critic [HP 33/45, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Food Critic HP is now 21.
The Food Critic doesn't like Mario's service! Mario HP is now 60.

--- Turn 3 ---
Mario [HP 60/100, Energy 2/10, Attack 12]
The Food Critic [HP 21/45, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Food Critic HP is now 9.
The Food Critic doesn't like Mario's service! Mario HP is now 50.

--- Turn 4 ---
Mario [HP 50/100, Energy 2/10, Attack 12]
The Food Critic [HP 9/45, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Food Critic HP is now 0.

W. The Food Critic is handled.

The busy hour is handled and the critic leaves five stars.
```

### Trace B: RECOVERY (Crispy Pizza), from the same first encounter

Input: `3 1 1 1 1 1 1 1 1` (Encounter 1 is identical to Trace A)

```text
Battle won, choose a reward:
1) Crispy Pizza: restore 25 HP (max 100)
2) Heavier Pin: +2 Rolling Pin Smack damage (Pizza Throw stays 15)
Choose one: Mario takes the Crispy Pizza (RECOVERY). Mario [HP 100/100, Energy 2/10, Attack 10]

=== Encounter 2: The Food Critic ===
The Food Critic breaks the door!

--- Turn 1 ---
Mario [HP 100/100, Energy 2/10, Attack 10]
The Food Critic [HP 45/45, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Food Critic HP is now 35.
The Food Critic doesn't like Mario's service! Mario HP is now 90.

--- Turn 2 ---
Mario [HP 90/100, Energy 2/10, Attack 10]
The Food Critic [HP 35/45, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Food Critic HP is now 25.
The Food Critic doesn't like Mario's service! Mario HP is now 80.

--- Turn 3 ---
Mario [HP 80/100, Energy 2/10, Attack 10]
The Food Critic [HP 25/45, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Food Critic HP is now 15.
The Food Critic doesn't like Mario's service! Mario HP is now 70.

--- Turn 4 ---
Mario [HP 70/100, Energy 2/10, Attack 10]
The Food Critic [HP 15/45, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Food Critic HP is now 5.
The Food Critic doesn't like Mario's service! Mario HP is now 60.

--- Turn 5 ---
Mario [HP 60/100, Energy 2/10, Attack 10]
The Food Critic [HP 5/45, Energy 0/10, Attack 10]
Choose: Mario beats them with the rolling pin! The Food Critic HP is now 0.

W. The Food Critic is handled.

The busy hour is handled and the critic leaves five stars.
```

### Trace C: loss in Encounter 1

Input: `2` twenty times (block every turn, 5 damage per turn)

```text
--- Turn 20 ---
Mario [HP 5/100, Energy 5/10, Attack 10]
The Busy Hour [HP 30/30, Energy 0/10, Attack 10]
Choose: Mario uses the cutting board as a shield.
The Busy Hour doesn't like Mario's service! Mario HP is now 0.

Mario experienced cardiac arrest.

The pizzeria is shut down due to safety violations.
```

No reward is offered and Encounter 2 never starts. The loss is also covered by test `T7`.

---

## Design notes

### Week 1: one architectural decision

The main decision in Week 1 was to send every point of damage through one private method, `Fighter.genericAttack(target, damage)`. The rolling pin (`attack`), the flaming pizza (`special`) and the enemy's hit (`counterAttack`) all end up there. Only the amount of damage differs.

This method is the only place where the cutting-board shield is checked. If the target is protected, the damage is reduced by `DEFENSE_REDUCTION` (5), but never below 0, and the protection is removed straight away, so a block can't stack or last for more than one hit. After that, HP is lowered through the private `setHp`, which clamps the value between 0 and `maxHp`.

Doing it this way means each rule exists exactly once. If Defend had been handled separately inside `attack` and `counterAttack`, the two copies could easily drift apart, for example one forgetting to clear the shield. It also makes "HP never goes below zero" impossible to break, because no other code is able to write to `hp`.

The same idea applies to the actions themselves. `special()` and `recover()` check whether they're allowed before changing anything, and return `false` if not. `Encounter` uses that return value to skip the enemy's turn, so a rejected action never costs Mario HP.

### Week 2: ownership, encapsulation and object identity

Each class owns one clear piece of the game. `Fighter` owns a combatant's state: HP, Energy, Attack and whether the cutting board is raised. `Encounter` owns a single battle: the turn counter, the menu, reading input and deciding whose turn it is. `Adventure` owns the order of the whole shift, and `Main` only creates Mario and starts it.

Encapsulation is what keeps that ownership real. Every field in `Fighter` is `private`, and `setHp` and `setResource` are private too, so code outside `Fighter` can't set HP or Energy to whatever it wants. It can only ask for an action (`attack`, `special`, `recover`, `heal`), and `Fighter` decides whether that's allowed and keeps the values inside their caps. `Encounter` can read state through getters but never edits it directly.

Object identity is how progress carries over. Mario is created once in `Main`, and the same reference is passed into both encounters. When The Busy Hour knocks him to 80 HP, Encounter 2 sees 80 HP because it's the same object. The enemies are the opposite: each is a brand-new `Fighter`, created right before its encounter, so damaging The Busy Hour can never affect The Food Critic.

The only thing reset between encounters is temporary state. `Encounter.run()` calls `removeProtection()` first, so a shield can't carry into the next fight. One thing I deliberately kept out of `Fighter` is the turn logic. A fighter doesn't know whose turn it is or when the enemy hits back, because that belongs to the battle, not the combatant.

### Week 3: preconditions, postconditions, invariants and state ownership

The reward is the one place where an action is allowed only at a certain point in the game, so I made those conditions explicit.

`Adventure.claimReward(reward)` is the only way to apply a reward. Its precondition is that the adventure is in the `REWARD_PENDING` state, which means Encounter 1 was won, and that `rewardClaimed` is still `false`. If that holds, the postcondition is that exactly one reward was applied and `rewardClaimed` is now `true`. If it doesn't hold, the method returns `false` and changes nothing. This is what makes "no reward before a win" and "only one reward" true no matter who calls it, and tests `T5` and `T6` check both cases.

The reward effects reuse methods that protect Mario's state. `heal(amount)` requires `amount > 0` and guarantees `hp == min(maxHp, old hp + amount)`, so Crispy Pizza at full HP can't go past 100. `increaseAttackDamage(increase)` requires `increase > 0`, so Attack can only go up, and it changes only the rolling pin's damage. The pizza throw uses a separate `specialDamage` field, so it stays at 15.

The invariants are that HP stays in `0..maxHp`, Energy stays in `0..maxResource`, Attack never decreases, and at most one reward is claimed per adventure. Ownership follows from this. `Fighter` enforces the caps, `Adventure` owns `state` and `rewardClaimed`, and `Encounter` knows nothing about rewards. The adventure states (`FIRST_ENCOUNTER`, `REWARD_PENDING`, `SECOND_ENCOUNTER`, `WON`, `LOST`, `ABANDONED`) plus `requireState()` make it an error to run the steps in the wrong order.

---

## AI-use reflection

I wrote the Week 1–2 core myself: `Fighter`, the battle loop in `Encounter`, `Main` and `Constants`. I used an AI chatbot as a reviewer and assistant, not as the designer. Specifically, I used it to:

- **Review my code against the handouts.** It found bugs I then fixed myself, such as Mario starting with 10 Energy instead of 5, and an abandoned fight being counted as a win.
- **Draft the Week 3 structure.** It suggested the `Adventure` coordinator and the `Reward` enum, and wrote the first versions of `Adventure.java` and `Reward.java`, which I reviewed and kept.
- **Write most of the tests** (I wrote `T1`–`T4` myself), the theming and in-game text based on my direction, and first drafts of the design notes, which I edited.
- **Turn my notes into a clean README.** I gave it my ideas (the game name, the theme, the in-game lines and what to say in each section), and it did the tedious formatting work: organising the sections, recording the traces from real runs, and writing everything up in a more formal style.

| | |
|---|---|
| **Useful suggestion** | Moving the reward out of `Encounter` into a separate `Adventure` class, where `claimReward()` refuses a reward before the first win or a second reward. This made the "only one reward, only after a win" rule enforceable and testable (`T5`, `T6`). |
| **Rejected / corrected suggestion** | The AI first gave me one big patch file. I rejected it and asked where each fix belonged instead, so I could make the Week 1–2 fixes myself. Later, its reward text said "The owner offers you one reward", which made no sense because Mario *is* the owner. I caught it and had it changed. I also rewrote the in-game lines in my own words. |
| **Test I verified myself** | I wrote `T1`–`T4` myself. For example, `T3` checks that the Heavier Pin raises rolling-pin damage to 12 while the pizza throw stays at 15, and that the enemy actually loses 12 HP. I checked those numbers against the game's own trace output. |

---

## Python vs Java

| Topic | Python | Java |
|---|---|---|
| Typing | Dynamic: types checked at runtime | Static: types checked at compile time |
| Compilation | Interpreted: `python game.py` | Compiled with `javac`, then run with `java` |
| Entry point | Top-level script / `if __name__ == "__main__":` | `public static void main(String[] args)` |

Java has more rules than Python and needs a lot more boilerplate. Every value has a declared type, every file is a class, and even printing one line needs `public static void main`. In exchange, Java is built for larger, more complex structures, and this project relies on a few features that Python either lacks or leaves to convention:

- **`private` fields** in `Fighter`, so HP and Energy can only change through methods that enforce the caps.
- **`static final` constants** in `Constants` that can't be reassigned.
- **`switch` expressions over enums**, where the compiler refuses to build if a case is missing.

The compiler also catches a whole class of hidden errors before the game ever runs. When `Constants.java` was missing the reward and enemy constants, the project simply didn't compile, while in Python the same mistake would only crash once that line ran mid-game. Overall, Java's strictness forced me to think about the architecture up front: who owns which state, what each method is allowed to change, and how the classes talk to each other. That led to a cleaner, more correct design than I'd probably have written in Python.
