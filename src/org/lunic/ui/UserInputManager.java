package org.lunic.ui;


import org.lunic.data.Item;
import org.lunic.observer.ItemConsumptionObserver;
import org.lunic.observer.ItemExpirationObserver;
import org.lunic.ui.helperclasses.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.lunic.ui.helperclasses.ConsoleUtilConfiguration.DATE_FORMAT;

public class UserInputManager implements Printable {

    private static UserInputManager INSTANCE;

    public static UserInputManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserInputManager();
        }
        return INSTANCE;
    }
    public static boolean SYSTEM_ACTIVE = true;

    private UserInputManager() {
        while (SYSTEM_ACTIVE) print();
    }

    public void print() {
        System.out.println();
        String title = "- StoreME -";
        ConsoleReadingUtils.printSpace(30 - title.length());
        System.out.print(title);
        System.out.println();
        ConsoleReadingUtils.printSpacer();

        if (ItemExpirationObserver.getInstance().getSoonExpiringItems().size() > 0) {
            System.out.println("These Items are expired or will expire soon: ");
            for (Item expiredItem : ItemExpirationObserver.getInstance().getSoonExpiringItems()) {
                System.err.println("- " + expiredItem.name() + " " +
                        expiredItem.expirationDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            }
        }
        delayedPrint();

        if (ItemConsumptionObserver.getInstance().getSoonConsumedItems().size() > 0) {
            System.out.println("You might run out of these Items soon: ");
            for (Item soonConsumedItem : ItemConsumptionObserver.getInstance().getSoonConsumedItems()) {
                System.err.println("- " + soonConsumedItem.name() + " " +
                        soonConsumedItem.consumptionDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            }
        }
        delayedPrint();

        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("View Container", ContainerInputHandler.getInstance()));
        options.add(new Option("View Recipes", RecipeInputHandler.getInstance()));
        options.add(new Option("View Categories and Shopping Lists", TagInputHandler.getInstance()));
        options.add(new Option("View Item Templates", ItemTemplateHandler.getInstance()));
        options.add(new Option("View Recommended Recipes", Action.VIEW));
        options.add(new Option("Exit", Action.EXIT));

        Option option = ConsoleSelectionUtils.displayOptions(options);
        if (option.hasPrintable()) {
            option.getPrintable().print();
        } else if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.EXIT)) {
                SYSTEM_ACTIVE = false;
            } else if (action.equals(Action.VIEW)) {
                RecipeInputHandler.getInstance().printRecommendedRecipes();
            }
        }
    }

    private void delayedPrint() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
