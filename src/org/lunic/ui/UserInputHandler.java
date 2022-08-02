package org.lunic.ui;

import org.lunic.repositories.ContainerRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInputHandler implements InputHandler {

    public static boolean SYSTEM_ACTIVE = true;
    private final ContainerInputHandler containerInputHandler;
    private final RecipeInputHandler recipeInputHandler;
    private final TagInputHandler tagInputHandler;

    public UserInputHandler() {
        containerInputHandler = new ContainerInputHandler(new ContainerRepository());
        recipeInputHandler = new RecipeInputHandler();
        tagInputHandler = new TagInputHandler();

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

        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("View Container", containerInputHandler));
        options.add(new Option("View Recipes", recipeInputHandler));
        options.add(new Option("View Tags", tagInputHandler));

        Option option = ConsoleUtils.displayOptions(options);
        option.getInputHandler().print();
    }


}
