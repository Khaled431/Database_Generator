package entity;

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
    public String toString() {
        return "Person{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
