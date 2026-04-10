package haoran.domain;

public class Character {
    private String name;
    private int HP;
    private int maxHP;
    private int MP;
    private int maxMP;
    private int attack;
    private int defence;
    private boolean defending;
    private boolean  MPHealing;

    public boolean isMPHealing() {
        return MPHealing;
    }

    public void setMPHealing(boolean healMP) {
        MPHealing = healMP;
    }

    public boolean isSurvive() {
        return HP > 0;
    }

    public void heal(int addHP) {
        HP += addHP;
        if (HP > maxHP) {
            HP = maxHP;
        }
    }

    public void healMP(int addMP) {
        MP += addMP;
        if (MP > maxMP) {
            MP = maxMP;
        }
    }

    public int takeDamage(int damage) {
        if (defending) {
            damage = damage / 2 > 1 ? damage / 2 : 1;
            defending = false;
        }
        HP -= damage;
        if (HP < 0) {
            HP = 0;
        }
        return damage;
    }

    public void show() {
        System.out.println(name + " 生命值"+"[" + HP + "/" + maxHP + "]" + " 法力值"+"[" + MP + "/" + maxMP + "]"+"  攻击力:" + attack + "  防御力:" + defence);
    }

    public Character() {
    }

    public Character(String name, int maxHP,int maxMP,int attack, int defence) {
        this.name = name;
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.maxMP = maxMP;
        this.MP = maxMP;
        this.attack = attack;
        this.defence = defence;
    }

    public boolean isDefending() {
        return defending;
    }

    public void setDefending(boolean defending) {
        this.defending = defending;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public void setMaxMP(int maxMP) {
        this.maxMP = maxMP;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }


}
