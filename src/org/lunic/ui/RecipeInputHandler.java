package org.lunic.ui;

import org.lunic.DataManager;
import org.lunic.data.*;
import org.lunic.data.type.RecipeType;
import org.lunic.data.Time;
import org.lunic.ui.helperclasses.*;

import java.util.ArrayList;
import java.util.HashSet;

public class RecipeInputHandler extends InputHandler implements Printable {

    public RecipeInputHandler() {
        super(DataManager.RECIPE_REPOSITORY);
    }

    @Override
    public void print() {
        System.out.println("- Recipes -");
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(Action.BACK.name(), Action.BACK));
        options.add(new Option(Action.CREATE.name(), Action.CREATE));
        for (Recipe recipe : DataManager.RECIPE_REPOSITORY.Read()) {
            options.add(new Option(recipe.name(), recipe));
        }

        Option option = ConsoleSelectionUtils.displayOptions(options);
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
        ConsoleReadingUtils.printSpacer();
        System.out.println(" Duration: " + recipe.duration().hours() + ":" + recipe.duration().minutes() + "h");
        System.out.println(" Type: " + recipe.type());
        System.out.println(" Tags: " + recipe.tags());
        System.out.println(" Needed Items: " + recipe.items());
        System.out.println(" Instruction:\n" + recipe.instruction());

        Option option = ConsoleSelectionUtils.displayActions(Action.BACK, Action.CHANGE, Action.DELETE);
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
        DataManager.RECIPE_REPOSITORY.Create(createOrChange(null));
        print();
    }

    @Override
    public void printChangeDialog(Record toChange) {
        DataManager.RECIPE_REPOSITORY.Update((Recipe) toChange, createOrChange((Recipe) toChange));
        print();
    }

    private Recipe createOrChange(Recipe recipe) {
        if(recipe == null) {
            System.out.println("- Create Recipe -");
        } else {
            System.out.println("- Edit Recipe -");
            System.out.println("Skip by typing \"!\"");
        }

        System.out.println("Enter Name:");
        String name = ConsoleReadingUtils.readString();
        System.out.println("Select Type:");
        RecipeType type = (RecipeType) ConsoleSelectionUtils.printTypeSelection(RecipeType.values());
        System.out.println("Add needed Items:");
        HashSet<Item> items = DataManager.ITEM_TEMPLATE_HANDLER.printAddTemplateItemsDialog(true); //GetItemTemplates here
        System.out.println("Enter cooking duration (in Minutes):");
        int minutes = ConsoleReadingUtils.getAmount(1, 0);
        Time duration = new Time(0, 0, minutes/(60*24), minutes/60, minutes % 60);
        System.out.println("Select Tags:");
        HashSet<Tag> tags = DataManager.TAG_INPUT_HANDLER.printSelectTagsDialog(false);
        String instruction = ConsoleReadingUtils.readText();
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
}
