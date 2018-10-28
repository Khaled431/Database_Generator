package relations;

import java.util.Objects;

public class Item {
    private final String name;
    private final double cost;

    private int amount;

    public Item(String name, double cost, int amount) {
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
        return Double.compare(item.cost, cost) == 0 &&
                Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, cost);
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