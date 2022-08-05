package org.lunic.ui;

import org.lunic.data.Tag;
import org.lunic.data.TagType;
import org.lunic.repositories.TagRepository;

import java.util.ArrayList;
import java.util.HashSet;

public class TagInputHandler extends InputHandler {

    private final TagRepository repository;

    public TagInputHandler(TagRepository repository) {
        super(repository);
        this.repository = repository;
    }
    @Override
    public void print() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("Back", Action.BACK));
        options.add(new Option("View Shopping Lists", Action.SHOPPING_LISTS));
        options.add(new Option("View Category Lists", Action.CATEGORIES));

        Option option = ConsoleUtils.displayOptions(options);
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
        Option option = ConsoleUtils.displayOptions(options);

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

    private void printTagOptions(Tag tag) {
        System.out.println("Selected Tag: " + tag);
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("Back", Action.BACK));
        options.add(new Option("Change", Action.CHANGE));
        options.add(new Option("Delete", Action.DELETE));

        Option option = ConsoleUtils.displayOptions(options);
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
        Option option = ConsoleUtils.displayOptions(options);

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
        System.out.println("- Creating Tag -");
        System.out.println("Enter Name:");
        String name = ConsoleUtils.readString();
        System.out.println("Select Type:");
        ArrayList<Option> options = new ArrayList<>();
        for (TagType type : TagType.values()) {
            options.add(new Option(type.name(), type));
        }
        TagType type = (TagType) ConsoleUtils.displayOptions(options).getRootObject();

        System.out.println("Enter Description: (One Line)");
        String description = ConsoleUtils.readString();

        Tag tag = new Tag(name, type, description);
        System.out.println(tag);
        boolean confirmed = ConsoleUtils.printConfirmationDialog("Create Tag");
        if(confirmed) {
            repository.Create(tag);
        }
    }
    @Override
    public void printChangeDialog(Record toChange) {
        System.out.println("Enter Name: (Write \"!\" to skip)");
        String name = ConsoleUtils.readString();
        System.out.println("Select Type:");
        TagType type = (TagType) ConsoleUtils.printTypeSelection(TagType.values());
        System.out.println("Enter Description: (One Line)");
        String description = ConsoleUtils.readString();

        Tag tag = new Tag(name, type, description);

        System.out.println(toChange);
        System.out.println("->");
        System.out.println(tag);
        boolean confirmed = ConsoleUtils.printConfirmationDialog("Change Tag");
        if(confirmed) {
            repository.Update((Tag)toChange, tag);
        }
    }
    public HashSet<Tag> printSelectTagsDialog() {
        HashSet<Tag> tags = new HashSet<>();
        while (true) {
            System.out.println("Currently added Tags: " + tags);
            ArrayList<Option> options = new ArrayList<>();
            options.add(new Option("Done", Action.DONE));
            options.add(new Option("Create", Action.CREATE));
            for (Tag tag : repository.Read()) {
                options.add(new Option(tag.name(), tag));
            }
            Option option = ConsoleUtils.displayOptions(options);

            if(option.getRootObject() instanceof Action action) {
                if(action.equals(Action.DONE)) {
                    return tags;
                } else if (action.equals(Action.CREATE)) {
                    printCreationDialog();
                }
            } else if (option.getRootObject() instanceof Tag tag) {
                tags.add(tag);
            }
        }
    }
    private enum Action {
        CATEGORIES,
        SHOPPING_LISTS,
        DONE,
        CREATE,
        DELETE,
        CHANGE,
        BACK
    }
}
