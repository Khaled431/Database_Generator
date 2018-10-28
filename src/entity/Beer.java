package entity;

public class Beer {

    private final String name, manufacturer;

    public Beer(String name, String manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}
