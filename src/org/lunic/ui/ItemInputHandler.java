package org.lunic.ui;

import org.lunic.DataManager;
import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.data.type.ItemType;
import org.lunic.data.Tag;
import org.lunic.ui.helperclasses.Action;
import org.lunic.ui.helperclasses.ConsoleReadingUtils;
import org.lunic.ui.helperclasses.ConsoleSelectionUtils;
import org.lunic.ui.helperclasses.Option;

import java.time.LocalDate;
import java.util.HashSet;

public class ItemInputHandler {

    public ItemInputHandler() {}

    public void printItemOptions(Container container, Item item) {
        System.out.println("Current Item: " + item);
        Option option = ConsoleSelectionUtils.displayActions(Action.BACK, Action.CHANGE, Action.DELETE);
        if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.CHANGE)) {
                printItemChangeDialog(item, container);
            } else if (action.equals(Action.DELETE)) {
                printItemDeletionDialog(item, container);
            }
        }
    }

    public void printItemCreationDialog(Container container) {
        Item item = createOrChange(null);
        System.out.println(item);
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Create Item");

        if (confirmed) {
            container.items().add(item);
            DataManager.CONTAINER_REPOSITORY.Update(container, container);
        }
    }

    private void printItemChangeDialog(Item toChange, Container container) {
        Item item = createOrChange(toChange);

        System.out.println(toChange);
        System.out.println("->");
        System.out.println(item);
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Change Item");

        if (confirmed) {
            container.items().remove(toChange);
            container.items().add(item);
            DataManager.CONTAINER_REPOSITORY.Update(container, container);
        }
    }

    private void printItemDeletionDialog(Item item, Container container) {
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Delete Item");
        if (confirmed) container.items().remove(item);
        DataManager.CONTAINER_REPOSITORY.Update(container, container);
    }

    private Item createOrChange(Item toChange) {
        if(toChange == null) {
            System.out.println("- Create Item -");
        } else {
            System.out.println("- Change Item -");
            System.out.println(toChange + " (Enter \"!\" to skip)");
        }

        System.out.println("Enter Name:");
        String name = ConsoleReadingUtils.readString();
        System.out.println("Select Type:");
        ItemType itemType = (ItemType) ConsoleSelectionUtils.printTypeSelection(ItemType.values());
        System.out.println("Enter Amount:");
        int amount = ConsoleReadingUtils.getAmount(1, 0);
        System.out.println("Enter Expiration Date: (dd/MM/YYYY)");
        LocalDate expirationDate = ConsoleReadingUtils.getDate();
        System.out.println("Enter Consumption Date: (dd/MM/YYYY)");
        LocalDate consumptionDate = ConsoleReadingUtils.getDate();
        System.out.println("Select Tags:");
        HashSet<Tag> tags = DataManager.TAG_INPUT_HANDLER.printSelectTagsDialog();

        if(toChange != null) {
            if(name.equals("!")) name = toChange.name();
        }

        return new Item(name, itemType, amount, expirationDate, consumptionDate, tags);
    }

    public HashSet<Item> printAddTemplateItemsDialog() {

        return new HashSet<>();
    }
}
