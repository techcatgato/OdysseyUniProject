package edu.starter;

public class Fighter {

    private final String name;
    private boolean defended;
    private int maxHp;
    private int hp;
    private int resource;
    private int maxResource;
    private int attackDamage;
    private int specialDamage;
    private int specialCost;
    private int recoverAmount;

    public Fighter(String name, int maxHp, int startResource, int maxResource, int attackDamage, int specialDamage, int specialCost, int recoverAmount) {
        
        if (maxHp <= 0) throw new IllegalArgumentException("max hp cannot be 0 or less");

        if (!(startResource <= maxResource && startResource >= 0)) throw new IllegalArgumentException("startingResource has to be between 0 and max");
        
        this.name = name;
        this.hp = maxHp;
        this.maxResource = maxResource;
        this.resource = startResource;
        this.defended = false;
        this.maxHp = maxHp;
        this.attackDamage = attackDamage;
        this.specialDamage = specialDamage;
        this.specialCost = specialCost;
        this.recoverAmount = recoverAmount;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    private void setHp(int hp) {
        this.hp = Math.clamp(hp, 0, maxHp);
    }

    public int getResource() {
        return resource;
    }

    private void setResource(int resource) {
        this.resource = Math.clamp(resource, 0, maxResource);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    private void genericAttack(Fighter enemy, int damage) {
        if (enemy.isProtected()) {
            damage = Math.max(0, damage-Constants.DEFENSE_REDUCTION);
            enemy.removeProtection();
        }
        enemy.setHp(enemy.getHp() - damage);
    }

    public boolean attack(Fighter enemy) {
        genericAttack(enemy, attackDamage);
        return true;
    }

    public void counterAttack(Fighter enemy) {
        genericAttack(enemy, attackDamage);
    }

    public boolean protect() {
        defended = true;
        return true;
    }

    public void removeProtection() {
        defended = false;
    }

    public boolean isProtected() {
        return defended;
    }

    public boolean canSpecial() {
        return resource >= specialCost;
    }

    public boolean special(Fighter enemy) {
        if (!canSpecial()) {
            return false;
        }
        setResource(resource - specialCost);
        genericAttack(enemy, specialDamage);
        return true;
    }

    public boolean canRecover() {
        return resource < maxResource;
    }

    public boolean recover() {
        if (!canRecover()) {
            return false;
        }
        setResource(resource + recoverAmount);
        return true;
    }

    @Override
    public String toString() {
        return name + " [HP " + hp + "/" + maxHp + ", Resource " + resource + "/" + maxResource
                + ", Attack " + attackDamage + (defended ? ", defending" : "") + "]";
    }

    public int getSpecialCost() {
        return specialCost;
    }

    public int getMaxResource() {
        return maxResource;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getSpecialDamage() {
        return specialDamage;
    }

    public int getRecoverAmount() {
        return recoverAmount;
    }


}