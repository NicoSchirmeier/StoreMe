package org.lunic.ui;


import org.lunic.DataManager;
import org.lunic.data.Item;
import org.lunic.ui.helperclasses.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.lunic.ui.helperclasses.ConsoleUtilConfiguration.DATE_FORMAT;

public class UserInputManager implements Printable {

    public static boolean SYSTEM_ACTIVE = true;
    private final String title = "- StoreME -";

    public UserInputManager() {
        while (SYSTEM_ACTIVE) print();
    }

    public void print() {
        System.out.println();
        ConsoleReadingUtils.printSpace(30 - title.length());
        System.out.print(title);
        System.out.println();
        ConsoleReadingUtils.printSpacer();
        System.out.println();

        if(DataManager.ITEM_EXPIRATION_OBSERVER.getSoonExpiringItems().size() > 0) {
            System.out.println("These Items will expire soon: ");
            for (Item expiredItem : DataManager.ITEM_EXPIRATION_OBSERVER.getSoonExpiringItems()) {
                System.err.println("- " + expiredItem.name() + " " +
                        expiredItem.expirationDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            }
            System.out.println();
        }

        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("View Container", DataManager.CONTAINER_INPUT_HANDLER));
        options.add(new Option("View Recipes", DataManager.RECIPE_INPUT_HANDLER));
        options.add(new Option("View Categories and Shopping Lists", DataManager.TAG_INPUT_HANDLER));
        options.add(new Option("View Item Templates", DataManager.ITEM_TEMPLATE_HANDLER));
        options.add(new Option("Exit", Action.EXIT));

        Option option = ConsoleSelectionUtils.displayOptions(options);
        if(option.hasPrintable()) {
            option.getPrintable().print();
        } else if (option.getRootObject() instanceof Action action) {
            if(action.equals(Action.EXIT)) SYSTEM_ACTIVE = false;
        }
    }
}
