import entity.Bar;
import entity.Beer;
import entity.Person;
import properties.Shift;
import relations.Employee;
import relations.Frequents;
import relations.Item;
import relations.Transaction;
import util.StringUtil;
import util.TimeUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generator {

    public static final SecureRandom SECURE_RANDOM;

    public static final int NUMBER_OF_PEOPLE_TO_GEN = 1000, NUM_BARS_TO_GEN = 15, NUMBER_EMPLOYEES_PER_BAR = 10, NUM_MINIMUM_ITEM_AMOUNT = 1000,
            NUM_MINIMUM_INVENTORY_SIZE = 50;

    public static final String FIRST_NAMES_PATH;
    public static final List<String> FIRST_NAMES;

    public static final String LAST_NAMES_PATH;
    public static final List<String> LAST_NAMES;

    public static final Set<String> FULL_NAMES;

    public static final String NJ_PHONE_EXTENSIONS;
    public static final Map<String, String> CITY_PHONE_EXTENSIONS;

    public static final HashMap<String, String> PHONE_NUMBERS;

    public static final String BEER_MANU_NAMES;
    public static final String FOOD_NAMES_FILE;

    public static final String BAR_NAMES_FILE;
    public static final Shift[] SHIFTS;


    // Entities
    public static final Person[] PEOPLE;
    public static final Bar[] BARS;
    public static final List<Beer> BEERS;
    public static final List<String> FOOD;

    // Relations
    private static final Set<Person> EMPLOYEES;
    private static final List<String> BAR_NAMES;

    private static final List<Transaction> TRANSACTIONS;
    private static final List<Frequents> FREQUENTS;

    static {
        SECURE_RANDOM = new SecureRandom();

        FIRST_NAMES_PATH = "./data/first_names.txt";
        FIRST_NAMES = new ArrayList<>();

        LAST_NAMES_PATH = "./data/last_names.txt";
        LAST_NAMES = new ArrayList<>();

        FULL_NAMES = new TreeSet<>();

        NJ_PHONE_EXTENSIONS = "./data/nj_phone_extensions.txt";
        CITY_PHONE_EXTENSIONS = new TreeMap<>();
        PHONE_NUMBERS = new HashMap<>();

        BEER_MANU_NAMES = "./data/beer_manu_names.txt";
        FOOD_NAMES_FILE = "./data/bar_food.txt";

        BAR_NAMES_FILE = "./data/bar_names.txt";
        BAR_NAMES = new ArrayList<>();

        PEOPLE = new Person[NUMBER_OF_PEOPLE_TO_GEN];
        BARS = new Bar[NUM_BARS_TO_GEN];

        BEERS = new ArrayList<>();
        FOOD = new ArrayList<>();
        EMPLOYEES = new HashSet<>();

        DayOfWeek[] dayOfWeeks = DayOfWeek.values();
        SHIFTS = new Shift[dayOfWeeks.length];
        for (int j = 0; j < SHIFTS.length; j++) {
            DayOfWeek day = dayOfWeeks[j];

            int startTime, endTime;
            if (TimeUtil.isPrime(day)) { // we need two bartenders this day for 3 days
                startTime = 9;
                endTime = 3 + 24;
            } else {
                startTime = 16;
                endTime = 24;
            }

            SHIFTS[j] = new Shift(startTime, endTime, day);
        }

        TRANSACTIONS = new ArrayList<>();
        FREQUENTS = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        loadData();

        for (int index = 0; index < PEOPLE.length; index++) {
            String name = getRandElement(FULL_NAMES);
            Map.Entry<String, String> phoneCityElement = getRandElement(PHONE_NUMBERS.entrySet());

            PHONE_NUMBERS.remove(phoneCityElement.getKey());

            String[] nameSplit = name.split(" ");
            PEOPLE[index] = new Person(nameSplit[0], nameSplit[1], phoneCityElement.getValue(), phoneCityElement.getKey());
        }

        for (int index = 0; index < BARS.length; index++) {
            String name = BAR_NAMES.remove(SECURE_RANDOM.nextInt(BAR_NAMES.size()));
            Person owner = PEOPLE[SECURE_RANDOM.nextInt(PEOPLE.length)];

            EMPLOYEES.add(owner);

            int scheduleCount = 0;
            List<Employee> employees = new ArrayList<>();
            do { // Need ten bartenders daily, they cannot already work for another bar.
                Person person = PEOPLE[SECURE_RANDOM.nextInt(PEOPLE.length)];
                if (EMPLOYEES.contains(person)) {
                    continue;
                }

                Shift barShift = SHIFTS[scheduleCount];

                int startHour, endHour;
                if (TimeUtil.isPrime(barShift.getDayOfWeek())) {
                    int shift = employees.size() % 2;
                    if (shift == 0) { // 1st person
                        startHour = barShift.getStartHour();
                        endHour = startHour + 8;
                    } else { // 2nd person
                        startHour = barShift.getStartHour() + 8;
                        endHour = barShift.getEndHour();

                        scheduleCount++;
                    }
                } else {
                    startHour = barShift.getStartHour();
                    endHour = barShift.getEndHour();

                    scheduleCount++;
                }


                Employee employee = new Employee(person, name, new Shift(startHour, endHour, barShift.getDayOfWeek()), (SECURE_RANDOM
                        .nextDouble() * 10.00) + 14.00);

                EMPLOYEES.add(employee.getPerson());

                employees.add(employee);
            } while (employees.size() != NUMBER_EMPLOYEES_PER_BAR);

            List<Item> inventory = new ArrayList<>();
            do {
                String itemName;
                if (SECURE_RANDOM.nextInt(3) == 1) {
                    itemName = getRandElement(FOOD);
                } else {
                    itemName = getRandElement(BEERS).getName();
                }

                double cost = (SECURE_RANDOM.nextDouble() * 7.00) + 5.00;
                int amount = SECURE_RANDOM.nextInt(9000) + NUM_MINIMUM_ITEM_AMOUNT;

                Item item = new Item(itemName, cost, amount);
                if (inventory.contains(item)) {
                    continue;
                }

                inventory.add(item);
            } while (inventory.size() < NUM_MINIMUM_INVENTORY_SIZE);

            Map.Entry<String, String> phoneCityElement = getRandElement(PHONE_NUMBERS.entrySet());
            PHONE_NUMBERS.remove(phoneCityElement.getKey());

            BARS[index] = new Bar(name, phoneCityElement.getValue(), phoneCityElement.getKey(), owner, SHIFTS,
                    employees.stream().toArray(Employee[]::new), inventory);
        }


        int targetTransSize = SECURE_RANDOM.nextInt(40000) + 1000;
        while (TRANSACTIONS.size() < targetTransSize) {
            Person person = getRandElement(List.of(PEOPLE));
            boolean randomBar = SECURE_RANDOM.nextInt(10) == 1;
            Bar bar;

            if (randomBar) {
                bar = getRandElement(List.of(BARS));
            } else {
                List<Bar> inCityBar = Stream.of(BARS).filter(b -> b.getCity().equals(person.getCity())).collect(Collectors.toList());
                if (inCityBar.isEmpty()) {
                    continue;
                }
                bar = getRandElement(inCityBar);
            }

            if (bar.getOwner().equals(person))
                continue;

            Employee employee = getRandElement(List.of(bar.getEmployees()));
            if (employee.equals(bar.getEmployee(person))) {
                continue;
            }

            int minutes = SECURE_RANDOM.nextInt(60);
            int seconds = SECURE_RANDOM.nextInt(60);
            int hour = SECURE_RANDOM.nextInt(employee.getShift().getEndHour()) + employee.getShift().getStartHour();
            int day = employee.getShift().getDayOfWeek().getValue();
            int month = SECURE_RANDOM.nextInt(12) + 1;
            LocalDateTime localDateTime = LocalDate.parse("2018-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day)).atTime
                    (0, minutes, seconds);

            if (hour > 23) {
                localDateTime = localDateTime.plusHours(hour - 23).plusDays(1);
            } else {
                localDateTime = localDateTime.plusHours(hour);
            }

            int numPurchases = SECURE_RANDOM.nextInt(3) + 1;
            for (int index = 0; index < numPurchases; index++) {
                TRANSACTIONS.add(new Transaction(bar, getRandElement(bar.getInventory()), person, employee, localDateTime.toInstant(ZoneOffset.UTC)));
            }
        }

        Map<Frequents, Integer> FREQUENCY_MAP = new HashMap<>();
        for (Transaction transaction : TRANSACTIONS) {
            Frequents frequents = new Frequents(transaction.getBar(), transaction.getPerson());
            FREQUENCY_MAP.put(frequents, 1 + FREQUENCY_MAP.getOrDefault(frequents, 0));
        }

        FREQUENCY_MAP.forEach((frequents, integer) -> {
            if (integer < 5)
                return;
            FREQUENTS.add(frequents);
        });

       // FREQUENTS.forEach(System.out::println);
    }

    private static void loadData() throws IOException {
        File firstNameFile = new File(FIRST_NAMES_PATH);
        if (!firstNameFile.exists())
            throw new IllegalStateException("Could not find the first name text file.");

        FIRST_NAMES.addAll(Files.lines(firstNameFile.toPath()).collect(Collectors.toCollection(ArrayList::new)));

        File lastNameFile = new File(FIRST_NAMES_PATH);
        if (!lastNameFile.exists())
            throw new IllegalStateException("Could not find the last name text file.");

        LAST_NAMES.addAll(Files.lines(lastNameFile.toPath()).collect(Collectors.toCollection(ArrayList::new)));

        while (FULL_NAMES.size() < NUMBER_OF_PEOPLE_TO_GEN) {
            FULL_NAMES.add(StringUtil.capitalize(getRandElement(FIRST_NAMES) + " " + getRandElement(LAST_NAMES)));
        }

        File njPhoneExtensions = new File(NJ_PHONE_EXTENSIONS);
        if (!lastNameFile.exists())
            throw new IllegalStateException("Could not find the nj phone extension text file.");

        CITY_PHONE_EXTENSIONS.putAll(Files.lines(njPhoneExtensions.toPath()).collect(Collectors.toMap(string -> string.split("\t")[0], string ->
                string.split("\t")[1])));

        //  CITY_PHONE_EXTENSIONS.forEach((key, entry) -> System.out.println(key + " " + entry));

        Set<Map.Entry<String, String>> cityPhoneSet = CITY_PHONE_EXTENSIONS.entrySet();
        while (PHONE_NUMBERS.size() < NUMBER_OF_PEOPLE_TO_GEN + NUM_BARS_TO_GEN + 1) {
            Map.Entry<String, String> entry = getRandElement(cityPhoneSet);

            StringBuilder builder = new StringBuilder(entry.getValue());
            builder.append("-");
            for (int index = 0; index < 7; index++) {
                builder.append(SECURE_RANDOM.nextInt(10));
                if (index == 2)
                    builder.append("-");
            }

            PHONE_NUMBERS.put(builder.toString(), entry.getKey());
        }

        File beerManuNames = new File(BEER_MANU_NAMES);
        if (!beerManuNames.exists())
            throw new IllegalStateException("Could not find the beer/manu names");

        BEERS.addAll(Files.lines(beerManuNames.toPath()).map(line -> {
            String[] split = line.split("-");
            return new Beer(split[0], split[1]);
        }).collect(Collectors.toList()));

        File barNames = new File(BAR_NAMES_FILE);
        if (!barNames.exists())
            throw new IllegalStateException("Could not find file bar_names.txt");

        BAR_NAMES.addAll(Files.lines(barNames.toPath()).collect(Collectors.toList()));

        File foodNames = new File(FOOD_NAMES_FILE);
        if (!foodNames.exists())
            throw new IllegalStateException("Food names could not be found.");

        FOOD.addAll(Files.lines(foodNames.toPath()).collect(Collectors.toList()));
    }


    private static <T> T getRandElement(Collection<T> collection) {
        return collection.stream().skip(SECURE_RANDOM.nextInt(collection.size())).findFirst().orElse(null);
    }


}
