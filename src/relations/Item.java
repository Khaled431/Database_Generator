package relations;

public class Item {
    private final String name;
    private final double cost;

    private int amount;

    public Item(String name, double cost) {
        this.name = name;
        this.cost = cost;
        this.amount = 0;
    }
}