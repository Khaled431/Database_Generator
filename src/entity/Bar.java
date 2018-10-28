package entity;

import relations.Employee;
import relations.Item;

import java.util.List;

public class Bar {

    private String name;
    private Person owner;
    private Employee[] employees;
    private List<Item> inventory;

    public Bar(String name, Person owner, Employee[] employees) {
        this.name = name;
        this.owner = owner;
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public Person getOwner() {
        return owner;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
