package org.lunic.ui;

import org.lunic.data.Tag;
import org.lunic.data.type.TagType;
import org.lunic.repositories.TagRepository;
import org.lunic.ui.helperclasses.*;

import java.util.ArrayList;
import java.util.HashSet;

public class TagInputHandler extends InputHandler implements Printable {

    private final TagRepository repository = UserInputManager.TAG_REPOSITORY;

    public TagInputHandler() {
        super(UserInputManager.TAG_REPOSITORY);
    }

    @Override
    public void print() {
        Option option = ConsoleSelectionUtils.displayActions(Action.BACK, Action.SHOPPING_LISTS, Action.CATEGORIES);
        if(option.getRootObject() instanceof Action action) {
            if(action.equals(Action.SHOPPING_LISTS)) {
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
        for (Tag tag : repository.Read()) {
            if(!tag.type().equals(TagType.SHOPPING_LIST)) {
                options.add(new Option(tag.name(), tag));
            }
        }
        executeOption(options);
    }

    private void printTagOptions(Tag tag) {
        System.out.println("Selected Tag: " + tag);
        Option option = ConsoleSelectionUtils.displayActions(Action.BACK, Action.CHANGE,  Action.DELETE);

        if(option.getRootObject() instanceof Action action) {
            if(action.equals(Action.CHANGE)) {
                printChangeDialog(tag);
            } else if (action.equals(Action.DELETE)) {
                printDeletionDialog(tag);
            } else {
                if(tag.type().equals(TagType.SHOPPING_LIST)) {
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
        for (Tag tag : repository.Read()) {
            if(tag.type().equals(TagType.SHOPPING_LIST)) {
                options.add(new Option(tag.name(), tag));
            }
        }
        executeOption(options);
    }

    private void executeOption(ArrayList<Option> options) {
        Option option = ConsoleSelectionUtils.displayOptions(options);

        if(option.getRootObject() instanceof Tag tag) {
            printTagOptions(tag);
        } else if (option.getRootObject() instanceof Action action){
            if(action.equals(Action.CREATE)) {
                printCreationDialog();
            } else {
                print();
            }
        }
    }

    @Override
    public void printCreationDialog() {
        Tag tag = createTag(null);
        System.out.println(tag);
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Create Tag");
        if(confirmed) {
            repository.Create(tag);
        }
    }

    @Override
    public void printChangeDialog(Record toChange) {
        Tag tag = createTag((Tag) toChange);

        System.out.println(toChange);
        System.out.println("->");
        System.out.println(tag);
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Change Tag");
        if(confirmed) {
            repository.Update((Tag)toChange, tag);
        }
    }

    public Tag createTag(Tag tagToEdit) {
        if(tagToEdit == null) {
            System.out.println("- Create Tag -");
        } else {
            System.out.println("- Edit Tag -");
            System.out.println("Skip by typing \"!\"");
        }

        System.out.println("Enter Name: (Write \"!\" to skip)");
        String name = ConsoleReadingUtils.readString();
        System.out.println("Select Type:");
        TagType type = (TagType) ConsoleSelectionUtils.printTypeSelection(TagType.values());
        System.out.println("Enter Description: (One Line)");
        String description = ConsoleReadingUtils.readString();

        if(tagToEdit != null) {
            if(name.equals("!")) name = tagToEdit.name();
            if(description.equals("!")) description = tagToEdit.description();
        }

        return new Tag(name, type, description);
    }

    public HashSet<Tag> printSelectTagsDialog() {
        HashSet<Tag> tags = new HashSet<>();
        while (true) {
            System.out.println("Currently added Tags: " + tags);
            ArrayList<Option> options = new ArrayList<>();
            options.add(new Option(Action.DONE.name(), Action.DONE));
            options.add(new Option(Action.CREATE.name(), Action.CREATE));
            for (Tag tag : repository.Read()) {
                options.add(new Option(tag.name(), tag));
            }
            Option option = ConsoleSelectionUtils.displayOptions(options);

            if(option.getRootObject() instanceof Action action) {
                if(action.equals(Action.DONE)) {
                    break;
                } else if (action.equals(Action.CREATE)) {
                    printCreationDialog();
                }
            } else if (option.getRootObject() instanceof Tag tag) {
                tags.add(tag);
            }
        }
        return tags;
    }

}
