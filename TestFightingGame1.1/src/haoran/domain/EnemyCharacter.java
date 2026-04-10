package haoran.domain;

public class EnemyCharacter extends Character {
    private String skill;

    public EnemyCharacter() {
    }

    public EnemyCharacter(String name, int maxHP, int maxMP, int attack, int defence, String skill) {
        super(name, maxHP, maxMP, attack, defence);
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }


}
