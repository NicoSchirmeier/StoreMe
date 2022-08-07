package org.lunic.ui;


import org.lunic.DataManager;
import org.lunic.data.Item;
import org.lunic.ui.helperclasses.ConsoleSelectionUtils;
import org.lunic.ui.helperclasses.Option;
import org.lunic.ui.helperclasses.Printable;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.lunic.ui.helperclasses.ConsoleUtilConfiguration.DATE_FORMAT;

public class UserInputManager implements Printable {

    public static boolean SYSTEM_ACTIVE = true;

    public UserInputManager() {
        while (SYSTEM_ACTIVE) print();
    }

    public void print() {
        /*
        1.  Heading
        2.  Soon expired Items
        3.  Soon to restock Items
        4.  Selectable Options
            1.  View Container
            2.  View Categories
            3.  View Shopping Lists/Categories
            4.  View Recipes
            5.  Exit
        */

        System.out.println();
        System.out.println();
        System.out.println("- StoreME -");
        System.out.println();

        System.out.println("These Items will expire soon: ");
        for (Item expiredItem : DataManager.ITEM_EXPIRATION_OBSERVER.getSoonExpiringItems()) {
            System.err.println("- " + expiredItem.name() + " " +
                    expiredItem.expirationDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        }
        System.out.println();


        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("View Container", DataManager.CONTAINER_INPUT_HANDLER));
        options.add(new Option("View Recipes", DataManager.RECIPE_INPUT_HANDLER));
        options.add(new Option("View Categories and Shopping Lists", DataManager.TAG_INPUT_HANDLER));

        Option option = ConsoleSelectionUtils.displayOptions(options);
        if(option.hasPrintable()) {
            option.getPrintable().print();
        }
    }
}
