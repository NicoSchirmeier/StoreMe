package org.lunic.ui;

import org.lunic.DataManager;
import org.lunic.data.*;
import org.lunic.data.type.RecipeType;
import org.lunic.data.Time;
import org.lunic.ui.helperclasses.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashSet;

public class RecipeInputHandler extends InputHandler implements Printable, Handler {

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

    public void printCreationDialog() {
        Recipe recipe = createOrChange(null);

        System.out.println(recipe);
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Create Recipe");
        if(confirmed) {
            DataManager.RECIPE_REPOSITORY.Create(recipe);
            print();
        }
    }

    public void printChangeDialog(Record toChange) {
        Recipe changedRecipe = createOrChange((Recipe) toChange);
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Edit Recipe");
        System.out.println(toChange);
        System.out.println("->");
        System.out.println(changedRecipe);
        if(confirmed) {
            DataManager.RECIPE_REPOSITORY.Update((Recipe) toChange, changedRecipe);
            print();
        }
    }

    private Recipe createOrChange(Recipe recipe) {
        boolean isChange = false;
        if(recipe == null) {
            System.out.println("- Create Recipe -");
        } else {
            isChange = true;
            System.out.println("- Edit Recipe -");
            System.out.println("Skip by typing \"!\"");
        }

        System.out.println("Enter Name:");
        String name = ConsoleReadingUtils.readString(isChange);
        System.out.println("Select Type:");
        RecipeType type = (RecipeType) ConsoleSelectionUtils.printTypeSelection(RecipeType.values(), isChange);
        System.out.println("Add needed Items:");
        HashSet<Item> items = DataManager.ITEM_TEMPLATE_HANDLER.printAddTemplateItemsDialog(true, isChange);
        System.out.println("Enter cooking duration (in Minutes):");
        int minutes = ConsoleReadingUtils.getAmount(1, 0, isChange);
        Time duration = new Time(0, 0, minutes/(60*24), minutes/60, minutes % 60);
        System.out.println("Select Tags:");
        HashSet<Tag> tags = DataManager.TAG_INPUT_HANDLER.printSelectTagsDialog(isChange);
        System.out.println("Write Instruction (End with blank line)");
        String instruction = ConsoleReadingUtils.readText(isChange);
        if(recipe != null) {
            if(name == null) name = recipe.name();
            if(type == null) type = recipe.type();
            if(items == null) items = recipe.items();
            if(minutes < 1) duration = recipe.duration();
            if(tags == null) tags = recipe.tags();
            if(instruction == null) instruction = recipe.instruction();
        }

        return new Recipe(name, type, items, checkItemsExist(items), duration, tags, instruction);
    }

    private boolean checkItemsExist(HashSet<Item> items) {
        boolean canBeCooked = true;
        for(Item item : items) {
            if(item.amount() > DataManager.ITEM_EXPIRATION_OBSERVER.getTotalAmount(item)) {
                canBeCooked = false;
            }
        }
        return canBeCooked;
    }

    public void printRecommendedRecipes() {
        System.out.println("- Recommended Recipes -");
        System.out.println("(based on stored items)");
        ArrayList<Option> additionalOptions = new ArrayList<>();
        for(Recipe recipe : DataManager.ITEM_EXPIRATION_OBSERVER.getRecommendedRecipes()) {
            additionalOptions.add(new Option(recipe.toString(), recipe));
        }
        Option option = ConsoleSelectionUtils.displayActions(additionalOptions, Action.BACK);
        if(option.getRootObject() instanceof Recipe recipe) {
            printRecipeDetails(recipe);
        }
    }
}
