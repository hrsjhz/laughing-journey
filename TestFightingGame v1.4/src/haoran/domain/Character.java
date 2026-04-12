package haoran.domain;

import java.util.ArrayList;

public class Character {
    private String name;
    private int HP;
    private int maxHP;
    private int oldMaxHP;
    private int MP;
    private int maxMP;
    private int oldMaxMP;
    private int attack;
    private int oldAttack;
    private int defence;
    private int oldDefence;
    private String attribute;
    private boolean defending;
    private int escape;
    private int lastMpHealRound;
    private int lastDefendingRound;
    private int lastAddAttackRound;
    private int lastAddDefenceRound;
    private int lastRemoveDefenceRound;

    public ArrayList<String> cSkills;

    public ArrayList<Consumables> cPackage;

    public void showPackage(){
        boolean isEmpty = checkPackage();
        if (isEmpty) {
            for(int i = 0;i < cPackage.size();i++){
                if(cPackage.get(i).getNumber() == 0){
                    if(i == cPackage.size() - 1)
                        System.out.println();
                    continue;
                }
                System.out.print(cPackage.get(i).getName() + "("+ cPackage.get(i).getAttribute() + ")" + " x" + cPackage.get(i).getNumber() );
                if(i == cPackage.size() - 1){
                    System.out.println();
                }else{
                    System.out.print("  ");
                }
            }
        }else{
            System.out.println("暂无可用道具");
        }
    }

    public boolean checkPackage(){
        for (int i = 0; i < cPackage.size(); i++) {
            if(cPackage.get(i).getNumber() > 0){
                return true;
            }
        }
        return false;
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
        if(attribute.equals("唯我独尊")){
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
        if(attackAttribute.equals("唯我独尊") ){
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
        cPackage = new ArrayList<>();
    }

    public Character(String name, int maxHP,int maxMP,int attack, int defence, String attribute) {
        this.name = name;
        this.maxHP = maxHP;
        this.oldMaxHP = maxHP;
        this.HP = maxHP;
        this.maxMP = maxMP;
        this.oldMaxMP = maxMP;
        this.MP = maxMP;
        this.attack = attack;
        this.oldAttack = attack;
        this.defence = defence;
        this.oldDefence = defence;
        this.attribute = attribute;
        cSkills = new ArrayList<>();
        cPackage = new ArrayList<>();
    }

    public int getOldAttack() {
        return oldAttack;
    }

    public void setOldAttack(int oldAttack) {
        this.oldAttack = oldAttack;
    }

    public int getOldDefence() {
        return oldDefence;
    }

    public void setOldDefence(int oldDefence) {
        this.oldDefence = oldDefence;
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

    public int getLastRemoveDefenceRound() {
        return lastRemoveDefenceRound;
    }

    public void setLastRemoveDefenceRound(int lastRemoveDefenceRound) {
        this.lastRemoveDefenceRound = lastRemoveDefenceRound;
    }

    public int getLastAddAttackRound() {
        return lastAddAttackRound;
    }

    public void setLastAddAttackRound(int lastAddAttackRound) {
        this.lastAddAttackRound = lastAddAttackRound;
    }

    public int getOldMaxHP() {
        return oldMaxHP;
    }

    public void setOldMaxHP(int oldMaxHP) {
        this.oldMaxHP = oldMaxHP;
    }

    public int getOldMaxMP() {
        return oldMaxMP;
    }

    public void setOldMaxMP(int oldMaxMP) {
        this.oldMaxMP = oldMaxMP;
    }

    public int getEscape() {
        return escape;
    }

    public void setEscape(int escape) {
        this.escape = escape;
    }

    public int getLastAddDefenceRound() {
        return lastAddDefenceRound;
    }

    public void setLastAddDefenceRound(int lastAddDefenceRound) {
        this.lastAddDefenceRound = lastAddDefenceRound;
    }
}
