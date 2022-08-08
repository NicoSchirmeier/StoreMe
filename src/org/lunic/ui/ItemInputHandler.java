package org.lunic.ui;

import org.lunic.DataManager;
import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.data.ItemBuilder;
import org.lunic.data.type.ItemType;
import org.lunic.data.Tag;
import org.lunic.repositories.ItemTemplateRepository;
import org.lunic.ui.helperclasses.Action;
import org.lunic.ui.helperclasses.ConsoleReadingUtils;
import org.lunic.ui.helperclasses.ConsoleSelectionUtils;
import org.lunic.ui.helperclasses.Option;

import java.time.LocalDate;
import java.util.HashSet;

public class ItemInputHandler {

    public ItemInputHandler() {}

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
            DataManager.CONTAINER_REPOSITORY.Update(container, container);

            confirmed = ConsoleReadingUtils.printConfirmationDialog("Save as Template");
            if(confirmed) {
                DataManager.ITEM_TEMPLATE_REPOSITORY.Create(new Item(item.name(), item.type(), 0, null, null, item.tags()));
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
            DataManager.CONTAINER_REPOSITORY.Update(container, container);

            confirmed = ConsoleReadingUtils.printConfirmationDialog("Save or add as new Template");
            Item templateItem = new Item(item.name(), item.type(), 0, null, null, item.tags());
            if(confirmed && DataManager.ITEM_TEMPLATE_REPOSITORY.Contains(toChange)) {
                DataManager.ITEM_TEMPLATE_REPOSITORY.Update(toChange, templateItem);
            } else if (confirmed && !DataManager.ITEM_TEMPLATE_REPOSITORY.Contains(toChange)) {
                DataManager.ITEM_TEMPLATE_REPOSITORY.Create(templateItem);
            }
        }
    }

    private void printItemDeletionDialog(Item item, Container container) {
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Delete Item");
        if (confirmed) container.items().remove(item);
        DataManager.CONTAINER_REPOSITORY.Update(container, container);
    }

    private Item createOrChange(Item baseItem) {
        boolean isItemChange;

        ItemBuilder builder;
        if(baseItem == null) {
            isItemChange = false;
            builder = new ItemBuilder();

            System.out.println("- Create Item -");
            boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Create from Item Template");
            if(confirmed) {
                return createOrChange(DataManager.ITEM_TEMPLATE_HANDLER.printSelectTemplate());
            }
        } else {
            isItemChange = true;
            builder = new ItemBuilder(baseItem);

            System.out.println("- Change Item -");
            System.out.println(baseItem + " (Enter \"!\" to skip)");
        }

        boolean isTemplateItem = false;
        if(isItemChange) if(baseItem.expirationDate() == null || baseItem.consumptionDate() == null) isTemplateItem = true;

        System.out.println("Enter Name:");
        builder.setName(ConsoleReadingUtils.readString(isItemChange));

        System.out.println("Select Type:");
        builder.setType((ItemType) ConsoleSelectionUtils.printTypeSelection(ItemType.values(), isItemChange));

        System.out.println("Enter Amount:");
        builder.setAmount(ConsoleReadingUtils.getAmount(1, 0, isItemChange && !isTemplateItem));

        System.out.println("Enter Expiration Date: (dd/MM/YYYY)");
        builder.setExpirationDate(ConsoleReadingUtils.getDate(isItemChange && !isTemplateItem));

        System.out.println("Enter Consumption Date: (dd/MM/YYYY)");
        builder.setConsumptionDate(ConsoleReadingUtils.getDate(isItemChange && !isTemplateItem));

        System.out.println("Select Tags:");
        builder.setTags(DataManager.TAG_INPUT_HANDLER.printSelectTagsDialog(isItemChange));

        return builder.build();
    }
}
