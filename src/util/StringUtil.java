package util;

public class StringUtil {

    public static String capitalize(String str) {
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
