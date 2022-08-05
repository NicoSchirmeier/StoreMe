package org.lunic.ui;

import org.lunic.data.ItemType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\r?\n");
    private static final int MaxChars = 20;
    private static final int MinChars = 3;

    public static String readString() {
        String text = "";
        while (text.length() < MinChars || text.length() > MaxChars) {
            text = scanner.next();
            if(text.equals("!")) return text;
            if(text.length() > MaxChars) {
                System.err.println("Text too long (Maximum Length: " + MaxChars + ")");
            } else if (text.length() < MinChars) {
                System.err.println("Text too short (Minimum Length: " + MinChars + ")");
            }
        }
        return text;
    }

    public static Option displayOptions(ArrayList<Option> options) {
        if(options == null || options.size() < 1) return null;

        int selection = -1;

        while (selection < 0 || selection > options.size()) {
            for(Option option : options) {
                System.out.println("[" + options.indexOf(option) + "] " + option.getText());
            }
            try {
                selection = scanner.nextInt();
            } catch (Exception e) {
                System.err.println("No valid input detected. Try again.");
                scanner.next();
            }

            if(selection < 0 || selection >= options.size()) {
                System.err.println("Selection out of bounds!");
            }
        }

        return options.get(selection);
    }

    public static boolean printConfirmationDialog(String actionText) {
        boolean confirmed = false;
        System.out.println("Please Confirm the following action: " + actionText);
        System.out.println("(yes / no)");

        String input = "";
        while (!input.equals("yes") && !input.equals("no")) {
            input = scanner.next().toLowerCase(Locale.ROOT);
        }
        if(input.equals("yes")) {
            confirmed = true;
        }

        return confirmed;
    }

    public static int getAmount(int min, int max) {
        int amount = Integer.MIN_VALUE;
        if(max == 0) max = Integer.MAX_VALUE;
        while (min > amount || amount > max) {
            try {
                amount = scanner.nextInt();
            } catch (Exception e) {
                System.err.println("No valid input detected. Try again.");
                scanner.next();
            }
            if(min > amount && amount > max) {
                System.err.println("Input out of bounds! (Min: " + min + " Max: " + max + " )");
            }
        }
        return amount;
    }

    public static Object printTypeSelection(Object[] types) {
        ArrayList<Option> options = new ArrayList<>();
        for (Object type : types) {
            if(type instanceof Enum<?> typeEnum) {
                options.add(new Option(typeEnum.name(), typeEnum));
            }
        }
        return displayOptions(options).getRootObject();
    }

    public static LocalDate getDate() {
        LocalDate date = LocalDate.of(1,1,1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");

        while (date.isBefore(LocalDate.now())) {
            try {
                date = LocalDate.parse(readString(), formatter);
            } catch (Exception e) {
                System.err.println("Date could not be parsed. (yyyy-MM-dd)");
            }
        }

        return date;
    }

    public static String readText() {
        String text = "";
        String lineNew;

        do {
            lineNew = readString();
            text += lineNew;
            if(lineNew.equals("!")) return "!";
        } while (!lineNew.equals(""));

        return text;
    }

    public static void printLine(String text) {
        for(char c : text.toCharArray()) {
            System.out.print("_");
        }
        System.out.println();
    }
}
