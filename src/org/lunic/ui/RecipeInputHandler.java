package org.lunic.ui;

import org.lunic.data.*;
import org.lunic.repositories.ItemTemplateRepository;
import org.lunic.repositories.RecipeRepository;
import org.lunic.repositories.Repository;
import org.lunic.repositories.TagRepository;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.HashSet;

public class RecipeInputHandler extends InputHandler {
    private final RecipeRepository repository;
    private final TagInputHandler tagInputHandler;

    public RecipeInputHandler(RecipeRepository repository, ItemTemplateRepository templateRepository, TagInputHandler tagInputHandler) {
        super(repository);
        this.repository = repository;
        this.tagInputHandler = tagInputHandler;
    }

    @Override
    public void print() {
        System.out.println("- Recipes -");
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("Back", Action.BACK));
        options.add(new Option("Create", Action.CREATE));
        for (Recipe recipe : repository.Read()) {
            options.add(new Option(recipe.name(), recipe));
        }

        Option option = ConsoleUtils.displayOptions(options);
        if (option.getRootObject() instanceof Action action) {
            if(action.equals(Action.CREATE)) {
                printCreationDialog();
            }
        } else if (option.getRootObject() instanceof Recipe recipe) {
            printRecipeDetails(recipe);
        }
    }

    private void printRecipeDetails(Recipe recipe) {
        System.out.println("  - " + recipe.name() + " -   ");
        ConsoleUtils.printLine("  - " + recipe.name() + " -  ");
        System.out.println(" Duration: " + recipe.duration().hours() + ":" + recipe.duration().minutes() + "h");
        System.out.println(" Type: " + recipe.type());
        System.out.println(" Tags: " + recipe.tags());
        System.out.println(" Needed Items: " + recipe.items());
        System.out.println(" Instruction:\n" + recipe.instruction());
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("Back", Action.BACK));
        options.add(new Option("Change", Action.CHANGE));
        options.add(new Option("Delete", Action.DELETE));

        Option option = ConsoleUtils.displayOptions(options);
        if(option.getRootObject() instanceof Action action) {
            if(action.equals(Action.CHANGE)) {
                printChangeDialog(recipe);
            } else if (action.equals(Action.DELETE)) {
                printDeletionDialog(recipe);
            } else {
                print();
            }
        }
    }

    @Override
    public void printCreationDialog() {
        repository.Create(createRecipe(null));
        print();
    }

    @Override
    public void printChangeDialog(Record toChange) {
        repository.Update((Recipe) toChange, createRecipe((Recipe) toChange));
        print();
    }

    private Recipe createRecipe(Recipe recipe) {
        if(recipe == null) {
            System.out.println("- Create Recipe -");
        } else {
            System.out.println("- Edit Recipe -");
            System.out.println("Skip by typing \"!\"");
        }

        System.out.println("Enter Name:");
        String name = ConsoleUtils.readString();
        System.out.println("Select Type:");
        RecipeType type = (RecipeType) ConsoleUtils.printTypeSelection(RecipeType.values());
        System.out.println("Add needed Items:");
        HashSet<Item> items = new HashSet<>(); //GetItemTemplates here
        System.out.println("Enter cooking duration (in Minutes):");
        int minutes = ConsoleUtils.getAmount(1, 0);
        Time duration = new Time(0, 0, minutes/(60*24), minutes/60, minutes % 60);
        System.out.println("Select Tags:");
        HashSet<Tag> tags = tagInputHandler.printSelectTagsDialog();
        String instruction = ConsoleUtils.readText();
        if(recipe != null) {
            if(name.equals("!")) name = recipe.name();
            if(type == null) type = recipe.type();
            if(items == null) items = recipe.items();
            if(minutes < 1) duration = recipe.duration();
            if(tags == null) tags = recipe.tags();
            if(instruction.equals("!")) instruction = recipe.instruction();
        }

        return new Recipe(name, type, items, checkItemsExist(items), duration, tags, instruction);
    }

    private boolean checkItemsExist(HashSet<Item> items) {
        System.err.println("Implement Recipe Observer for handling needed Items exist requests.");
        return false;
    }

    private enum Action {
        BACK,
        CREATE,
        CHANGE,
        DELETE
    }
}
