package org.lunic.ui;

import org.lunic.data.Item;
import org.lunic.observer.ExpirationObserver;
import org.lunic.repositories.ContainerRepository;
import org.lunic.repositories.ItemTemplateRepository;
import org.lunic.repositories.RecipeRepository;
import org.lunic.repositories.TagRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserInputManager {

    public static boolean SYSTEM_ACTIVE = true;
    private final ContainerInputHandler containerInputHandler;
    private final RecipeInputHandler recipeInputHandler;
    private final TagInputHandler tagInputHandler;
    private final ExpirationObserver expirationObserver;

    public UserInputManager() {
        tagInputHandler = new TagInputHandler(new TagRepository());
        containerInputHandler = new ContainerInputHandler(new ContainerRepository(), tagInputHandler);
        expirationObserver =
                new ExpirationObserver(containerInputHandler.containerRepository);
        recipeInputHandler = new RecipeInputHandler(new RecipeRepository(), new ItemTemplateRepository(), tagInputHandler);


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

        System.out.println("This Items will expire soon: ");
        for (Item expiredItem : expirationObserver.getSoonExpiringItems()) {
            System.out.println("- " + expiredItem.name() + " " +
                    expiredItem.expirationDate().format(DateTimeFormatter.ofPattern(ConsoleUtils.dateFormat)));
        }
        System.out.println();


        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("View Container", containerInputHandler));
        options.add(new Option("View Recipes", recipeInputHandler));
        options.add(new Option("View Categories and Shopping Lists", tagInputHandler));

        Option option = ConsoleUtils.displayOptions(options);
        option.getInputHandler().print();
    }
}
