package entity;

import java.util.Objects;

public class Person {

    private final String first, last, city, phone;

    public Person(String first, String last, String city, String phone) {
        this.first = first;
        this.last = last;
        this.city = city;
        this.phone = phone;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(first, person.first) &&
                Objects.equals(last, person.last) &&
                Objects.equals(city, person.city) &&
                Objects.equals(phone, person.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last, city, phone);
    }

    @Override
    public String toString() {
        return "Person{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
