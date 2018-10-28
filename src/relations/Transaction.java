package relations;

import entity.Bar;
import entity.Person;

import java.time.Instant;

public class Transaction {

    private Bar bar;
    private Item item;
    private Person person;
    private Instant instant;

    public Transaction(Bar bar, Item item, Person person, Instant instant) {
        this.bar = bar;
        this.item = item;
        this.person = person;
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
}
