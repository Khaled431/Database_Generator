package relations;

import entity.Bar;
import entity.Person;

import java.util.Objects;

public class Frequents {

    private Bar bar;
    private Person person;

    public Frequents(Bar bar, Person person) {
        this.bar = bar;
        this.person = person;
    }

    public Bar getBar() {
        return bar;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frequents frequents = (Frequents) o;
        return Objects.equals(bar, frequents.bar) &&
                Objects.equals(person, frequents.person);
    }

    @Override
    public int hashCode() {

        return Objects.hash(bar, person);
    }

    @Override
    public String toString() {
        return "Frequents{" +
                "bar=" + bar.getName() +
                ", person=" + person +
                '}';
    }
}
