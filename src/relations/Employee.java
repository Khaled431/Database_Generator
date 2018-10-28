package relations;

import entity.Bar;
import entity.Person;
import properties.Shift;

public class Employee {

    public enum Role {
        WAITER(8.00), BARTENDER(17.00), MANAGER(25.00);

        private double hourlyPay;

        Role(double hourlyPay) {
            this.hourlyPay = hourlyPay;
        }

        public double getHourlyPay() {
            return hourlyPay;
        }
    }

    private Bar bar;
    private Person person;
    private Shift shift;
    private Role role;

    public Employee(Person person, Bar bar, Shift shift, Role role) {
        this.person = person;
        this.bar = bar;
        this.shift = shift;
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public Bar getBar() {
        return bar;
    }

    public Shift getShift() {
        return shift;
    }

    public Role getRole() {
        return role;
    }
}
