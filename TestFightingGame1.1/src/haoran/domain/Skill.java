package haoran.domain;

public class Skill {
    private String name;
    private String description;

    public Skill() {
    }
    public Skill(String name,String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
