package org.lunic.ui;

import org.lunic.DataManager;
import org.lunic.data.Tag;
import org.lunic.data.type.TagType;
import org.lunic.ui.helperclasses.*;

import java.util.ArrayList;

public class TagInputHandler extends InputHandler implements Printable, Handler {

    public TagInputHandler() {
        super(DataManager.TAG_REPOSITORY);
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
        for (Tag tag : DataManager.TAG_REPOSITORY.read()) {
            if (!tag.type().equals(TagType.SHOPPING_LIST)) {
                options.add(new Option(tag.name(), tag));
            }
        }
        executeOption(options);
    }

    private void printTagOptions(Tag tag) {
        System.out.println("Selected Tag: " + tag);
        Option option = ConsoleSelectionUtils.displayActions(Action.BACK, Action.CHANGE, Action.DELETE);

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
        }
    }

    private void printShoppingLists() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(Action.BACK.name(), Action.BACK));
        options.add(new Option(Action.CREATE.name(), Action.CREATE));
        for (Tag tag : DataManager.TAG_REPOSITORY.read()) {
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

    @Override
    public void printCreationDialog() {
        TagInputHandlerUtils.printCreationDialog();
    }

    @Override
    public void printChangeDialog(Record toChange) {
        TagInputHandlerUtils.printChangeDialog(toChange);
    }
}
