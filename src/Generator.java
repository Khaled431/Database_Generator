import entity.Beer;
import entity.Person;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

public class Generator {

    public static final SecureRandom SECURE_RANDOM;

    public static final int NUMBER_OF_PEOPLE_TO_GEN = 100, NUM_BARS_TO_GEN = 15;

    public static final String FIRST_NAMES_PATH;
    public static final List<String> FIRST_NAMES;

    public static final String LAST_NAMES_PATH;
    public static final List<String> LAST_NAMES;

    public static final Set<String> FULL_NAMES;

    public static final String NJ_PHONE_EXTENSIONS;
    public static final Map<String, String> CITY_PHONE_EXTENSIONS;

    public static final HashMap<String, String> PHONE_NUMBERS;

    public static final String BEER_MANU_NAMES;
    public static final String BAR_NAMES_FILE;


    // Entities
    public static final Person[] PEOPLE;
    public static final List<Beer> BEERS;
    private static final List<String> BAR_NAMES;


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

        BAR_NAMES_FILE = "./data/bar_names.txt";
        BAR_NAMES = new ArrayList<>();

        PEOPLE = new Person[NUMBER_OF_PEOPLE_TO_GEN];
        BEERS = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        loadData();

        for (int index = 0; index < PEOPLE.length; index++) {
            String name = getRandElement(FULL_NAMES);
            Map.Entry<String, String> phoneCityElement = getRandElement(PHONE_NUMBERS.entrySet());

            PHONE_NUMBERS.remove(phoneCityElement.getKey());

            String[] nameSplit = name.split(" ");
            PEOPLE[index] = new Person(nameSplit[0], nameSplit[1], phoneCityElement.getKey(), phoneCityElement.getValue());
        }




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
            FULL_NAMES.add(capitalize(getRandElement(FIRST_NAMES) + " " + getRandElement(LAST_NAMES)));
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
    }


    private static <T> T getRandElement(Collection<T> collection) {
        return collection.stream().skip(SECURE_RANDOM.nextInt(collection.size())).findFirst().orElse(null);
    }

    private static String capitalize(String str) {
        str = str.toLowerCase();

        StringBuilder buffer = new StringBuilder();

        char ch = ' ';
        for (int i = 0; i < str.length(); i++) {
            if (ch == ' ' && str.charAt(i) != ' ')
                buffer.append(Character.toUpperCase(str.charAt(i)));
            else
                buffer.append(str.charAt(i));
            ch = str.charAt(i);
        }

        return buffer.toString().trim();
    }

}
