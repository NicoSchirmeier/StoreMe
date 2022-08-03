package org.lunic.ui;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.data.ItemType;
import org.lunic.data.Tag;
import org.lunic.repositories.ContainerRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class ItemInputHandler implements InputHandler {
    ContainerRepository repository;
    ContainerInputHandler containerInputHandler;
    TagInputHandler tagInputHandler;

    public ItemInputHandler(ContainerRepository repository, ContainerInputHandler containerInputHandler, TagInputHandler tagInputHandler) {
        this.repository = repository;
        this.containerInputHandler = containerInputHandler;
        this.tagInputHandler = tagInputHandler;
    }

    @Override
    public void print() {
        System.err.println("NOT IMPLEMENTED");
    }

    public void printItemOptions(Container container, Item item) {
        ArrayList<Option> options = new ArrayList<>();
        System.out.println("Current Item: " + item);
        options.add(new Option("Change", Action.CHANGE));
        options.add(new Option("Remove", Action.DELETE));
        options.add(new Option("Back", Action.BACK));
        
        Option option = ConsoleUtils.displayOptions(options);
        if(option.getRootObject() instanceof Action action) {
            if(action.equals(Action.CHANGE)) {
                printItemChangeDialog(item, container);
            } else if (action.equals(Action.DELETE)) {
                printItemDeletionDialog(item, container);
            }
        }
    }

    public void printItemCreationDialog(Container container) {
        ArrayList<Option> options = new ArrayList<>();

        System.out.println("Enter Name:");
        String name = ConsoleUtils.readString();

        System.out.println("Select Type:");
        for (ItemType itemType : ItemType.values()) {
            options.add(new Option(itemType.name(), itemType));
        }
        ItemType itemType = (ItemType) ConsoleUtils.displayOptions(options).getRootObject();

        System.out.println("Enter Amount:");
        int amount = ConsoleUtils.getAmount(1, 0);

        System.out.println("Enter Expiration Date: (yyyy-MM-dd)");
        LocalDate expirationDate = ConsoleUtils.getDate();
        System.out.println("Enter Consumption Date: (yyyy-MM-dd)");
        LocalDate consumptionDate = ConsoleUtils.getDate();

        System.out.println("Select Tags:");
        HashSet<Tag> tags = tagInputHandler.printSelectTagsDialog();

        Item item = new Item(name, itemType, amount, expirationDate, consumptionDate, tags);
        System.out.println(item);
        boolean confirmed = ConsoleUtils.printConfirmationDialog("Create Item");

        if(confirmed) {
            container.items().add(item);
            repository.Update(container, container);
        }
    }

    private void printItemChangeDialog(Item item, Container container) {

    }

    private void printItemDeletionDialog(Item item, Container container) {
        boolean confirmed = ConsoleUtils.printConfirmationDialog("Delete Item");
        if(confirmed) container.items().remove(item);
        repository.Update(container, container);
    }

    private enum Action {
        CHANGE,
        DELETE, 
        BACK
    }
}
