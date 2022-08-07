package org.lunic.ui;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.data.ItemType;
import org.lunic.data.Tag;
import org.lunic.repositories.ContainerRepository;
import org.lunic.repositories.ItemTemplateRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class ItemInputHandler {
    ContainerRepository repository;
    ItemTemplateRepository templateRepository;
    ContainerInputHandler containerInputHandler;
    TagInputHandler tagInputHandler;

    public ItemInputHandler(ContainerRepository repository, ContainerInputHandler containerInputHandler, TagInputHandler tagInputHandler) {
        this.repository = repository;
        this.containerInputHandler = containerInputHandler;
        this.tagInputHandler = tagInputHandler;
    }

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
        if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.CHANGE)) {
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
        ItemType itemType = (ItemType) ConsoleUtils.printTypeSelection(ItemType.values());
        // TODO 2 as DRY Example
        System.out.println("Enter Amount:");
        int amount = ConsoleUtils.getAmount(1, 0);
        System.out.println("Enter Expiration Date: (" + ConsoleUtils.dateFormat + ")");
        LocalDate expirationDate = ConsoleUtils.getDate();
        System.out.println("Enter Consumption Date: (" + ConsoleUtils.dateFormat + ")");
        LocalDate consumptionDate = ConsoleUtils.getDate();
        System.out.println("Select Tags:");
        HashSet<Tag> tags = tagInputHandler.printSelectTagsDialog();

        Item item = new Item(name, itemType, amount, expirationDate, consumptionDate, tags);
        System.out.println(item);
        boolean confirmed = ConsoleUtils.printConfirmationDialog("Create Item");

        if (confirmed) {
            container.items().add(item);
            repository.Update(container, container);
        }
    }

    private void printItemChangeDialog(Item item, Container container) {
        ArrayList<Option> options = new ArrayList<>();

        System.out.println("Enter Name: (Write \"!\" to skip)");
        String name = ConsoleUtils.readString();
        if (name.equals("!")) name = item.name();
        System.out.println("Select Type:");
        ItemType itemType = (ItemType) ConsoleUtils.printTypeSelection(ItemType.values());
        System.out.println("Enter Amount:");
        int amount = ConsoleUtils.getAmount(1, 0);

        System.out.println("Enter Expiration Date: (" + ConsoleUtils.dateFormat + ")");
        LocalDate expirationDate = ConsoleUtils.getDate();
        System.out.println("Enter Consumption Date: (" + ConsoleUtils.dateFormat + ")");
        LocalDate consumptionDate = ConsoleUtils.getDate();


        System.out.println("Select Tags:");
        HashSet<Tag> tags = tagInputHandler.printSelectTagsDialog();

        Item changedItem = new Item(name, itemType, amount, expirationDate, consumptionDate, tags);

        System.out.println(item);
        System.out.println("->");
        System.out.println(changedItem);
        boolean confirmed = ConsoleUtils.printConfirmationDialog("Change Item");

        if (confirmed) {
            container.items().remove(item);
            container.items().add(changedItem);
            repository.Update(container, container);
        }
    }

    private void printItemDeletionDialog(Item item, Container container) {
        boolean confirmed = ConsoleUtils.printConfirmationDialog("Delete Item");
        if (confirmed) container.items().remove(item);
        repository.Update(container, container);
    }

    public HashSet<Item> printAddTemplateItemsDialog() {

        return new HashSet<>();
    }

    private enum Action {
        CHANGE,
        DELETE,
        BACK
    }
}
