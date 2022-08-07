package org.lunic.ui;


import org.lunic.data.Item;
import org.lunic.ui.helperclasses.ConsoleSelectionUtils;
import org.lunic.ui.helperclasses.Option;
import org.lunic.ui.helperclasses.Printable;

import org.lunic.repositories.*;

import org.lunic.observer.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.lunic.ui.helperclasses.ConsoleUtilConfiguration.DATE_FORMAT;

public class UserInputManager implements Printable {

    public static boolean SYSTEM_ACTIVE = true;

    //Repositories
    public static final ContainerRepository CONTAINER_REPOSITORY = new ContainerRepository();
    public static final ItemTemplateRepository ITEM_TEMPLATE_REPOSITORY = new ItemTemplateRepository();
    public static final RecipeRepository RECIPE_REPOSITORY = new RecipeRepository();
    public static final TagRepository TAG_REPOSITORY = new TagRepository();

    //InputHandler
    public static final ContainerInputHandler CONTAINER_INPUT_HANDLER = new ContainerInputHandler();
    public static final RecipeInputHandler RECIPE_INPUT_HANDLER = new RecipeInputHandler();
    public static final TagInputHandler TAG_INPUT_HANDLER = new TagInputHandler();
    public static final ItemInputHandler ITEM_INPUT_HANDLER = new ItemInputHandler();

    //Observer
    private static final ItemExpirationObserver itemExpirationObserver = new ItemExpirationObserver();

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
            3.  View Shopping Lists
            4.  View Recipes
            5.  Exit
        */


        System.out.println();
        System.out.println();
        System.out.println("- StoreME -");
        System.out.println();

        System.out.println("These Items will expire soon: ");
        for (Item expiredItem : itemExpirationObserver.getSoonExpiringItems()) {
            System.err.println("- " + expiredItem.name() + " " +
                    expiredItem.expirationDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        }
        System.out.println();


        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("View Container", CONTAINER_INPUT_HANDLER));
        options.add(new Option("View Recipes", RECIPE_INPUT_HANDLER));
        options.add(new Option("View Categories and Shopping Lists", TAG_INPUT_HANDLER));

        Option option = ConsoleSelectionUtils.displayOptions(options);
        if(option.hasPrintable()) {
            option.getPrintable().print();
        }
    }
}
