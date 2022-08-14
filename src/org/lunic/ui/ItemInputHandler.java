package org.lunic.ui;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.data.ItemBuilder;
import org.lunic.data.type.ItemType;
import org.lunic.repositories.ContainerRepository;
import org.lunic.repositories.ItemTemplateRepository;
import org.lunic.ui.helperclasses.Action;
import org.lunic.ui.helperclasses.ConsoleReadingUtils;
import org.lunic.ui.helperclasses.ConsoleSelectionUtils;
import org.lunic.ui.helperclasses.Option;

public class ItemInputHandler {

    private static ItemInputHandler INSTANCE;

    public static ItemInputHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ItemInputHandler();
        }
        return INSTANCE;
    }

    private ItemInputHandler() {}

    private static void getTags(boolean isItemChange, ItemBuilder builder) {
        System.out.println("Select Tags:");
        builder.setTags(TagInputHandlerUtils.printSelectTagsDialog(isItemChange));
    }

    private static void getConsumptionDate(boolean isItemChange, ItemBuilder builder) {
        System.out.println("Enter Consumption Date: (dd/MM/YYYY)");
        builder.setConsumptionDate(ConsoleReadingUtils.readDate(isItemChange));
    }

    private static void getExpirationDate(boolean isItemChange, ItemBuilder builder) {
        System.out.println("Enter Expiration Date: (dd/MM/YYYY)");
        builder.setExpirationDate(ConsoleReadingUtils.readDate(isItemChange));
    }

    private static void getAmount(boolean isItemChange, ItemBuilder builder) {
        System.out.println("Enter Amount:");
        builder.setAmount(ConsoleReadingUtils.getAmount(1, 0, isItemChange));
    }

    private static void getType(boolean isItemChange, ItemBuilder builder) {
        System.out.println("Select Type:");
        builder.setType((ItemType) ConsoleSelectionUtils.printTypeSelection(ItemType.values(), isItemChange));
    }

    private static void getName(boolean isItemChange, ItemBuilder builder) {
        System.out.println("Enter Name:");
        builder.setName(ConsoleReadingUtils.readString(isItemChange));
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
            addItem(container, item);
        }
    }

    private void addItem(Container container, Item item) {
        container.items().add(item);
        ContainerRepository.getInstance().update(container, container);

        printSaveAsTemplateDialog(item);
    }

    private static void printSaveAsTemplateDialog(Item item) {
        boolean confirmed;
        confirmed = ConsoleReadingUtils.printConfirmationDialog("Save as Template");
        if (confirmed) {
            ItemTemplateRepository.getInstance().create(new ItemBuilder().setName(item.name()
            ).setType(item.type()).setAmount(0).setTags(item.tags()).build());
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
            addItem(container, item);
        }
    }

    private void printItemDeletionDialog(Item item, Container container) {
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Delete Item");
        if (confirmed) container.items().remove(item);
        ContainerRepository.getInstance().update(container, container);
    }

    private Item createOrChange(Item baseItem) {
        boolean isItemChange = baseItem != null;
        printCreateOrChangeWelcomeText(isItemChange, baseItem);

        if(!isItemChange) baseItem = ItemTemplateHandler.getInstance().printSelectTemplate();
        isItemChange = baseItem != null;

        ItemBuilder builder = createItemBuilder(isItemChange, baseItem);

        getName(isItemChange, builder);
        getType(isItemChange, builder);
        getAmount(isItemChange, builder);
        getExpirationDate(isItemChange, builder);
        getConsumptionDate(isItemChange, builder);
        getTags(isItemChange, builder);

        return builder.build();
    }

    private void printCreateOrChangeWelcomeText(boolean isItemChange, Item item) {
        if (isItemChange) {
            System.out.println("- Change Item -");
            System.out.println(item + " (Enter \"!\" to skip)");
        } else {
            System.out.println("- Create Item -");
        }
    }

    private ItemBuilder createItemBuilder(boolean isItemChange, Item baseItem) {
        if (isItemChange) return new ItemBuilder(baseItem);
        return new ItemBuilder();
    }

}
