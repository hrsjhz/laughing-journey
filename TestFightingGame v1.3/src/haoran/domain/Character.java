package haoran.domain;

import java.util.ArrayList;

public class Character {
    private String name;
    private int HP;
    private int maxHP;
    private int MP;
    private int maxMP;
    private int attack;
    private int defence;
    private String attribute;
    private boolean defending;
    private boolean MPHealing;
    private int lastMpHealRound;
    private int lastDefendingRound;
    public ArrayList<String> cSkills;

    public boolean isMPHealing() {
        return MPHealing;
    }

    public void setMPHealing(boolean MPHealing) {
        this.MPHealing = MPHealing;
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

    public int takeDamage(int damage,String attackAttribute) {

        if(attackAttribute.equals("喜") && attribute.equals("怒")){
            System.out.println("属性克制！");
            damage = (int) (1.2 * damage);
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
        if(attackAttribute.equals("怒") && attribute.equals("哀")){
            System.out.println("属性克制！");
            damage = (int) (1.2 * damage);
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
        if(attackAttribute.equals("哀") && attribute.equals("乐")){
            System.out.println("属性克制！");
            damage = (int) (1.2 * damage);
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
        if(attackAttribute.equals("乐") && attribute.equals("喜")){
            System.out.println("属性克制！");
            damage = (int) (1.2 * damage);
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
        if(attackAttribute.equals("喜") && attribute.equals("乐")){
            System.out.println("属性被克制！");
            damage = (int) (0.8 * damage);
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
        if(attackAttribute.equals("怒") && attribute.equals("喜")){
            System.out.println("属性被克制！");
            damage = (int) (0.8 * damage);
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
        if(attackAttribute.equals("哀") && attribute.equals("怒")){
            System.out.println("属性被克制！");
            damage = (int) (0.8 * damage);
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
        if(attackAttribute.equals("乐") && attribute.equals("哀")){
            System.out.println("属性被克制！");
            damage = (int) (0.8 * damage);
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
        System.out.println(name + " 生命值"+"[" + HP + "/" + maxHP + "]" + " 法力值"+"[" + MP + "/" + maxMP + "]"+"  攻击力:" + attack + "  防御力:" + defence + " 初始属性:" +  attribute);
    }

    public Character() {
        cSkills = new ArrayList<>();
    }

    public Character(String name, int maxHP,int maxMP,int attack, int defence, String attribute) {
        this.name = name;
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.maxMP = maxMP;
        this.MP = maxMP;
        this.attack = attack;
        this.defence = defence;
        this.attribute = attribute;
        cSkills = new ArrayList<>();
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

    public int getLastMpHealRound() {
        return lastMpHealRound;
    }

    public void setLastMpHealRound(int lastMpHealRound) {
        this.lastMpHealRound = lastMpHealRound;
    }

    public int getLastDefendingRound() {
        return lastDefendingRound;
    }

    public void setLastDefendingRound(int lastDefendingRound) {
        this.lastDefendingRound = lastDefendingRound;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
