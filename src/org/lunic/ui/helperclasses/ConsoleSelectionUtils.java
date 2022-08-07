package org.lunic.ui.helperclasses;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleSelectionUtils {

    public static Option displayOptions(ArrayList<Option> options) {
        return displayOptions(options, false);
    }

    public static Option displayOptions(ArrayList<Option> options, boolean canBeSkipped) {
        if(options == null || options.size() < 1) return null;

        int selection = -1;

        while (selection < 0 || selection > options.size()) {
            for(Option option : options) {
                System.out.println("[" + options.indexOf(option) + "] " + option.getText());
            }
            try {
                String input = ConsoleReadingUtils.readString(1, Integer.MAX_VALUE, false);
                System.out.println(input);
                if(input != null && input.equals("!") && canBeSkipped) {
                    return new Option(null, null);
                } else if (!input.equals("!")) {
                    selection = Integer.parseInt(input);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("No valid input detected. Try again.");
                ConsoleUtilConfiguration.SCANNER.next();
            }

            if(selection < 0 || selection >= options.size()) {
                System.err.println("Selection out of bounds!");
            }
        }

        return options.get(selection);
    }

    public static Option displayActions(Action... actions) {
        return displayActions(null, actions);
    }

    public static Option displayActions(ArrayList<Option> additionalOptions, Action... actions) {
        ArrayList<Option> options = new ArrayList<>();
        for (Action action : actions) {
            options.add(new Option(action.name(), action));
        }
        if(additionalOptions != null) {
            options.addAll(additionalOptions);
        }

        return  displayOptions(options);
    }

    public static Object printTypeSelection(Object[] types) {
        return printTypeSelection(types, false);
    }

    public static Object printTypeSelection(Object[] types, boolean canBeSkipped) {
        ArrayList<Option> options = new ArrayList<>();
        for (Object type : types) {
            if(type instanceof Enum<?> typeEnum) {
                options.add(new Option(typeEnum.name(), typeEnum));
            }
        }
        return displayOptions(options, canBeSkipped).getRootObject();
    }

}
