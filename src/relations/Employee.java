package relations;

import entity.Person;
import properties.Shift;

import java.util.Objects;

public class Employee {

    private String barName;
    private Person person;
    private Shift shift;
    private double payment;

    public Employee(Person person, String bar, Shift shift, double payment) {
        this.person = person;
        this.barName = bar;
        this.shift = shift;
        this.payment = payment;
    }

    public Person getPerson() {
        return person;
    }

    public String getBarName() {
        return barName;
    }

    public Shift getShift() {
        return shift;
    }

    public double getPayment() {
        return payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(person, employee.person);
    }

    @Override
    public int hashCode() {

        return Objects.hash(person);
    }
}
