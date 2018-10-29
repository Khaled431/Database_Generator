package relations;

import java.util.Objects;

public class Item {
    private final String bar, name;
    private final double cost;

    private int amount;

    public Item(String bar, String name, double cost, int amount) {
        this.bar = bar;
        this.name = name;
        this.cost = cost;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public void incrementAmount() {
        amount++;
    }

    public void decrementAmount() {
        amount--;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(bar, item.bar) &&
                Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bar, name);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", amount=" + amount +
                '}';
    }
}