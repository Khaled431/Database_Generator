package relations;

import entity.Bar;
import entity.Person;

import java.time.Instant;

public class Transaction {

    private Bar bar;
    private Employee employee;
    private Person person;
    private Item item;
    private Instant instant;

    public Transaction(Bar bar, Item item, Person person, Employee employee, Instant instant) {
        this.bar = bar;
        this.item = item;
        this.person = person;
        this.employee = employee;
        this.instant = instant;
    }

    public Bar getBar() {
        return bar;
    }

    public Item getItem() {
        return item;
    }

    public Person getPerson() {
        return person;
    }

    public Instant getInstant() {
        return instant;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "bar=" + bar +
                ", employee=" + employee +
                ", person=" + person +
                ", item=" + item +
                ", instant=" + instant +
                '}';
    }
}
