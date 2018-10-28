package entity;

import properties.Shift;
import relations.Employee;
import relations.Item;

import java.util.Arrays;
import java.util.List;

public class Bar {

    private String name;
    private Person owner;
    private Shift[] hoursOfOperation;
    private Employee[] employees;
    private List<Item> inventory;

    public Bar(String name, Person owner, Shift[] hoursOfOperation, Employee[] employees, List<Item> inventory) {
        this.name = name;
        this.owner = owner;
        this.hoursOfOperation = hoursOfOperation;
        this.employees = employees;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
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
                ", owner=" + owner +
                ", hoursOfOperation=" + (hoursOfOperation == null ? null : Arrays.asList(hoursOfOperation)) +
                ", employees=" + (employees == null ? null : Arrays.asList(employees)) +
                ", inventory=" + inventory +
                '}';
    }
}
