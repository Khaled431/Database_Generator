package relations;

import entity.Person;

import java.util.Objects;

public class Likes {

    private final Person person;
    private final String item;

    public Likes(Person person, String item) {
        this.person = person;
        this.item = item;
    }

    public Person getPerson() {
        return person;
    }

    public String getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Likes likes = (Likes) o;
        return Objects.equals(person, likes.person) &&
                Objects.equals(item, likes.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, item);
    }
}
