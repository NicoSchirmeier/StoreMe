package org.lunic.ui.helperclasses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.lunic.ui.helperclasses.ConsoleUtilConfiguration.DATE_FORMAT;
import static org.lunic.ui.helperclasses.ConsoleUtilConfiguration.SCANNER;

public class ConsoleReadingUtils {

    public static String readString() {
        return readString(ConsoleUtilConfiguration.MIN_CHARS, ConsoleUtilConfiguration.MAX_CHARS, false);
    }

    public static String readString(boolean canBeSkipped) {
        return readString(ConsoleUtilConfiguration.MIN_CHARS, ConsoleUtilConfiguration.MAX_CHARS, canBeSkipped);
    }

    public static String readString(int min, int max, boolean canBeSkipped) {
        String text;
        do {
            text = SCANNER.next();
            if (text.equals("!") && canBeSkipped) return null;
            if (text.length() > max) {
                System.err.println("Text too long (Maximum Length: " + max + ")");
            } else if (text.length() < min) {
                System.err.println("Text too short (Minimum Length: " + min + ")");
            }
        } while (text.length() < min || text.length() > max);
        return text;
    }

    public static boolean printConfirmationDialog(String actionText) {
        boolean confirmed = false;
        System.out.println("Please Confirm the following action: " + actionText);
        System.out.println("(yes / no)");

        String input = "";
        while (!input.equals("yes") && !input.equals("no")) {
            input = ConsoleUtilConfiguration.SCANNER.next().toLowerCase(Locale.ROOT);
        }
        if (input.equals("yes")) {
            confirmed = true;
        }

        return confirmed;
    }

    public static int getAmount(int min, int max) {
        return getAmount(min, max, false);
    }

    public static int getAmount(int min, int max, boolean canBeSkipped) {
        int amount = Integer.MIN_VALUE;
        if (max == 0) max = Integer.MAX_VALUE;
        while (min > amount || amount > max) {
            try {
                String input = readString(1, Integer.MAX_VALUE, false);
                if (input == null) return -1;
                if (input.equals("!") && canBeSkipped) return -1;
                if(input.matches("[0-9]+")) {
                    amount = Integer.parseInt(input);
                } else {
                    System.out.println("Only numbers between " + min + " and " + max + " are allowed!");
                }
            } catch (Exception e) {
                System.err.println("No valid input detected. Try again.");
                ConsoleUtilConfiguration.SCANNER.next();
            }
            if (min > amount && amount > max) {
                System.err.println("Input out of bounds! (Min: " + min + " Max: " + max + " )");
            }
        }
        return amount;
    }

    public static LocalDate readDate(boolean canBeSkipped) {
        LocalDate date = LocalDate.of(1, 1, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        while (date.isBefore(LocalDate.now())) {
            try {
                String text = readString(1, Integer.MAX_VALUE, false);
                if (text == null) return null;
                if (text.equals("!") && canBeSkipped) return null;
                date = LocalDate.parse(text, formatter);
            } catch (Exception e) {
                System.err.println("Date could not be parsed. (" + DATE_FORMAT + ")");
            }
        }

        return date;
    }

    public static String readText(boolean canBeSkipped) {
        StringBuilder stringBuilder = new StringBuilder();
        String lineNew;

        do {
            lineNew = readString(0, Integer.MAX_VALUE, false);
            stringBuilder.append("   ").append(lineNew).append("\n");
            if (lineNew == null) return null;
            if (lineNew.equals("!") && canBeSkipped) return null;
        } while (!lineNew.equals(""));

        return stringBuilder.toString();
    }

    public static void printSpacer() {
        for (int i = 0; i < ConsoleUtilConfiguration.SPACER_LENGTH; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

    public static void printSpace(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(" ");
        }
    }
}
