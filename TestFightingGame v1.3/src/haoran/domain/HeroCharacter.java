package haoran.domain;


import java.util.ArrayList;

public class HeroCharacter extends Character {

    private int playerHealMP; // 用了几次回蓝

    public HeroCharacter() {
        cSkills = new ArrayList<>();
    }

    public HeroCharacter(String name, int maxHP, int maxMP, int attack, int defence,String  attribute) {
        super(name, maxHP, maxMP, attack, defence, attribute);
        cSkills = new ArrayList<>();
    }

    @Override
    public void show() {
        System.out.println("您的角色为");
        super.show();
        System.out.print("技能：");
        System.out.println(cSkills);
    }

    public int getPlayerHealMP() {
        return playerHealMP;
    }

    public void setPlayerHealMP(int playerHealMP) {
        this.playerHealMP = playerHealMP;
    }


}
