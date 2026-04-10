package haoran.domain;


public class HeroCharacter extends Character {

    public Skill[] skills;

    public HeroCharacter() {
        skills = new Skill[4];
    }

    public HeroCharacter(String name, int maxHP, int maxMP, int attack, int defence) {
        super(name, maxHP, maxMP, attack, defence);
        skills = new Skill[4];
    }

    @Override
    public void show() {
        System.out.println("您的角色为");
        super.show();
        System.out.print("技能：[");
        for (int i = 0; i < skills.length; i++) {
            if(skills[i] ==  null){
                System.out.println("]");
                break;
            }
            System.out.print(skills[i].getName());

            if(i != skills.length - 1){
                System.out.print(", ");
            }else {
                System.out.println("]");
            }
        }
    }
}
