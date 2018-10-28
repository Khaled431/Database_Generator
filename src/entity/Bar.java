package entity;

import properties.Shift;
import relations.Employee;
import relations.Item;

import java.util.Arrays;
import java.util.List;

public class Bar {

    private String name;
    private String city;
    private String number;
    private Person owner;
    private Shift[] hoursOfOperation;
    private Employee[] employees;
    private List<Item> inventory;

    public Bar(String name, String city, String number, Person owner, Shift[] hoursOfOperation, Employee[] employees, List<Item> inventory) {
        this.name = name;
        this.city = city;
        this.owner = owner;
        this.hoursOfOperation = hoursOfOperation;
        this.employees = employees;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Person getOwner() {
        return owner;
    }

    public Shift[] getHoursOfOperation() {
        return hoursOfOperation;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", owner=" + owner +
                ", hoursOfOperation=" + (hoursOfOperation == null ? null : Arrays.asList(hoursOfOperation)) +
                ", employees=" + (employees == null ? null : Arrays.asList(employees)) +
                ", inventory=" + inventory +
                '}';
    }

    public Employee getEmployee(Person person) {
        for (int index = 0; index < employees.length; index++) {
            Employee employee = employees[index];
            if (!employee.getPerson().equals(person))
                continue;
            return employee;
        }
        return null;
    }
}
