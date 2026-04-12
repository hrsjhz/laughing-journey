package haoran.domain;

public class Consumables {
    private String name;
    private int number;
    private String attribute;

    public Consumables() {
    }

    public Consumables(String name, int number, String attribute) {
        this.name = name;
        this.number = number;
        this.attribute = attribute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
