package org.lunic.ui.helperclasses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import static org.lunic.ui.helperclasses.ConsoleUtilConfiguration.DATE_FORMAT;

public class ConsoleReadingUtils {

    public static String readString() {
        String text = "";
        while (text.length() < ConsoleUtilConfiguration.MIN_CHARS || text.length() > ConsoleUtilConfiguration.MAX_CHARS) {
            text = ConsoleUtilConfiguration.SCANNER.next();
            if(text.equals("!")) return text;
            if(text.length() > ConsoleUtilConfiguration.MAX_CHARS) {
                System.err.println("Text too long (Maximum Length: " + ConsoleUtilConfiguration.MAX_CHARS + ")");
            } else if (text.length() < ConsoleUtilConfiguration.MIN_CHARS) {
                System.err.println("Text too short (Minimum Length: " + ConsoleUtilConfiguration.MIN_CHARS + ")");
            }
        }
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
        if(input.equals("yes")) {
            confirmed = true;
        }

        return confirmed;
    }

    public static int getAmount(int min, int max) {
        return getAmount(min, max, false);
    }

    public static int getAmount(int min, int max, boolean canBeSkipped) {
        int amount = Integer.MIN_VALUE;
        if(max == 0) max = Integer.MAX_VALUE;
        while (min > amount || amount > max) {
            try {
                String input = readString();
                if(input.equals("!") && canBeSkipped) return -1;
                amount = Integer.parseInt(input);

            } catch (Exception e) {
                System.err.println("No valid input detected. Try again.");
                ConsoleUtilConfiguration.SCANNER.next();
            }
            if(min > amount && amount > max) {
                System.err.println("Input out of bounds! (Min: " + min + " Max: " + max + " )");
            }
        }
        return amount;
    }

    public static LocalDate getDate() {
        return getDate(false);
    }

    public static LocalDate getDate(boolean canBeSkipped) {
        LocalDate date = LocalDate.of(1,1,1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        while (date.isBefore(LocalDate.now())) {
            try {
                String text = readString();
                if(text.equals("!") && canBeSkipped) return null;
                date = LocalDate.parse(text, formatter);
            } catch (Exception e) {
                System.err.println("Date could not be parsed. (" + DATE_FORMAT +")");
            }
        }

        return date;
    }

    public static String readText() {
        return readText(false);
    }

    public static String readText(boolean canBeSkipped) {
        String text = "";
        String lineNew;

        do {
            lineNew = readString();
            text += lineNew;
            if(lineNew.equals("!") && canBeSkipped) return null;
        } while (!lineNew.equals(""));

        return text;
    }

    public static void printSpacer() {
        for(int i = 0; i < ConsoleUtilConfiguration.SPACER_LENGTH; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

    public static void printSpace(int length) {
        for(int i = 0; i < length; i++) {
            System.out.print(" ");
        }
    }
}
