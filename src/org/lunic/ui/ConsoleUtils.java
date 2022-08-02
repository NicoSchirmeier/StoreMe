package org.lunic.ui;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int MaxChars = 20;
    private static final int MinChars = 3;

    public static String readString() {
        String text = "";
        while (text.length() < MinChars || text.length() > MaxChars) {
            text = scanner.next();

            if(text.length() > 20) {
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
            selection = scanner.nextInt();

            if(selection < 0 || selection > options.size()) {
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

        return  confirmed;
    }
}
