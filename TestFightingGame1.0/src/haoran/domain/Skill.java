package haoran.domain;

public class Skill {
    private String name;
    private int cost;
    private int heal;
    private double multiple;

    public Skill() {
    }
    public Skill(String name, int cost, int heal, double multiple) {
        this.name = name;
        this.cost = cost;
        this.heal = heal;
        this.multiple = multiple;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHeal() {
        return heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public double getMultiple() {
        return multiple;
    }

    public void setMultiple(double multiple) {
        this.multiple = multiple;
    }
}
