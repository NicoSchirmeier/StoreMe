package org.lunic.ui;

import org.lunic.data.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class UserInputHandler implements InputHandler {

    private final ContainerInputHandler containerInputHandler;
    private final RecipeInputHandler recipeInputHandler;
    private final TagInputHandler tagInputHandler;

    public UserInputHandler() {
        containerInputHandler = new ContainerInputHandler();
        recipeInputHandler = new RecipeInputHandler();
        tagInputHandler = new TagInputHandler();

        while (true) print();
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


        System.out.println("\n\n\n\n- StoreME -");
        System.out.println("");

        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("View Container", containerInputHandler, Action.PRINT));
        options.add(new Option("View Recipes", recipeInputHandler, Action.PRINT));
        options.add(new Option("View Tags", tagInputHandler, Action.PRINT));

        Option option = printOptions(options);
        option.root().print();
    }

    public static Option printOptions(ArrayList<Option> options) {
        if(options == null || options.size() < 1) return null;

        int selection = -1;
        Scanner scanner = new Scanner(System.in);

        while (selection < 0 || selection > options.size()) {
            for(Option option : options) {
                System.out.println("[" + options.indexOf(option) + "] " + option.text());
            }
            selection = scanner.nextInt();
        }

        return options.get(selection);
    }


}
