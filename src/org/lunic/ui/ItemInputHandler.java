package org.lunic.ui;

import org.lunic.DataManager;
import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.data.ItemBuilder;
import org.lunic.data.type.ItemType;
import org.lunic.ui.helperclasses.Action;
import org.lunic.ui.helperclasses.ConsoleReadingUtils;
import org.lunic.ui.helperclasses.ConsoleSelectionUtils;
import org.lunic.ui.helperclasses.Option;

public class ItemInputHandler {

    public ItemInputHandler() {
    }

    public void printItemOptions(Container container, Item item) {
        System.out.println("Current Item: " + item.toPrettyString());
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
            DataManager.CONTAINER_REPOSITORY.update(container, container);

            confirmed = ConsoleReadingUtils.printConfirmationDialog("Save as Template");
            if (confirmed) {
                DataManager.ITEM_TEMPLATE_REPOSITORY.create(new Item(item.name(), item.type(), 0, null, null, item.tags()));
            }
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
            DataManager.CONTAINER_REPOSITORY.update(container, container);
        }
    }

    private void printItemDeletionDialog(Item item, Container container) {
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Delete Item");
        if (confirmed) container.items().remove(item);
        DataManager.CONTAINER_REPOSITORY.update(container, container);
    }

    private Item createOrChange(Item baseItem) {
        boolean isItemChange;

        ItemBuilder builder;
        if (baseItem == null) {
            isItemChange = false;
            builder = new ItemBuilder();

            System.out.println("- Create Item -");
        } else {
            isItemChange = true;
            builder = new ItemBuilder(baseItem);

            System.out.println("- Change Item -");
            System.out.println(baseItem + " (Enter \"!\" to skip)");
        }

        System.out.println("Enter Name:");
        builder.setName(ConsoleReadingUtils.readString(isItemChange));

        System.out.println("Select Type:");
        Object itemType = ConsoleSelectionUtils.printTypeSelection(ItemType.values(), isItemChange);
        if (itemType == null) {
            builder.setType(null);
        } else {
            builder.setType((ItemType) itemType);
        }

        System.out.println("Enter Amount:");
        builder.setAmount(ConsoleReadingUtils.getAmount(1, 0, isItemChange));

        System.out.println("Enter Expiration Date: (dd/MM/YYYY)");
        builder.setExpirationDate(ConsoleReadingUtils.getDate(isItemChange));

        System.out.println("Enter Consumption Date: (dd/MM/YYYY)");
        builder.setConsumptionDate(ConsoleReadingUtils.getDate(isItemChange));

        System.out.println("Select Tags:");
        builder.setTags(TagInputHandlerUtils.printSelectTagsDialog(isItemChange));

        return builder.build();
    }

}
