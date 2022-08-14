package org.lunic.ui;

import org.lunic.data.Item;
import org.lunic.data.Recipe;
import org.lunic.data.Tag;
import org.lunic.data.type.TagType;
import org.lunic.repositories.ContainerRepository;
import org.lunic.repositories.RecipeRepository;
import org.lunic.repositories.TagRepository;
import org.lunic.ui.helperclasses.*;

import java.util.ArrayList;

public class TagInputHandler extends InputHandler implements Printable, Handler {

    private static TagInputHandler INSTANCE;
    public static TagInputHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TagInputHandler();
        }
        return INSTANCE;
    }

    private TagInputHandler() {
        super(TagRepository.getInstance());
    }

    @Override
    public void print() {
        Option option = ConsoleSelectionUtils.displayActions(Action.BACK, Action.SHOPPING_LISTS, Action.CATEGORIES);
        if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.SHOPPING_LISTS)) {
                printShoppingLists();
            } else if (action.equals(Action.CATEGORIES)) {
                printCategories();
            }
        }

    }

    private void printCategories() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(Action.BACK.name(), Action.BACK));
        options.add(new Option(Action.CREATE.name(), Action.CREATE));
        for (Tag tag : TagRepository.getInstance().read()) {
            if (!tag.type().equals(TagType.SHOPPING_LIST)) {
                options.add(new Option(tag.name(), tag));
            }
        }
        executeOption(options);
    }

    private void printShoppingLists() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(Action.BACK.name(), Action.BACK));
        options.add(new Option(Action.CREATE.name(), Action.CREATE));
        for (Tag tag : TagRepository.getInstance().read()) {
            if (tag.type().equals(TagType.SHOPPING_LIST)) {
                options.add(new Option(tag.name(), tag));
            }
        }
        executeOption(options);
    }

    private void executeOption(ArrayList<Option> options) {
        Option option = ConsoleSelectionUtils.displayOptions(options);

        if (option.getRootObject() instanceof Tag tag) {
            printTagOptions(tag);
        } else if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.CREATE)) {
                printCreationDialog();
            } else {
                print();
            }
        }
    }

    private void printTagOptions(Tag tag) {
        System.out.println("Selected Tag: " + tag);
        ArrayList<Option> additionalOptions = new ArrayList<>();
        additionalOptions.add(new Option(" ----- Items -----", Action.SPACER));
        for (Item item : ContainerRepository.getInstance().getAllItems()) {
            if(item.tags().contains(tag)) additionalOptions.add(new Option(item.toString(), item));
        }
        additionalOptions.add(new Option(" ----- Recipes -----", Action.SPACER));
        for (Recipe recipe : RecipeRepository.getInstance().read()) {
            if(recipe.tags().contains(tag)) additionalOptions.add(new Option(recipe.name() + " - " + recipe.type(), recipe));
        }
        Option option = ConsoleSelectionUtils.displayActions(additionalOptions, Action.BACK, Action.CHANGE, Action.DELETE);

        if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.CHANGE)) {
                printChangeDialog(tag);
            } else if (action.equals(Action.DELETE)) {
                printDeletionDialog(tag);
            } else {
                if (tag.type().equals(TagType.SHOPPING_LIST)) {
                    printShoppingLists();
                } else {
                    printCategories();
                }
            }
        } else if (option.getRootObject() instanceof Item item) {
            System.out.println(item.toPrettyString());
        } else if (option.getRootObject() instanceof Recipe recipe) {
            System.out.println(recipe);
        }
    }

    @Override
    public void printCreationDialog() {
        TagInputHandlerUtils.printCreationDialog();
    }

    @Override
    public void printChangeDialog(Record toChange) {
        TagInputHandlerUtils.printChangeDialog(toChange);
    }
}
