package org.lunic.ui;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.data.type.ContainerType;
import org.lunic.repositories.ContainerRepository;
import org.lunic.ui.helperclasses.*;

import java.util.ArrayList;
import java.util.HashSet;

public class ContainerInputHandler extends InputHandler implements Printable, Handler {

    private static ContainerInputHandler INSTANCE;

    public static ContainerInputHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ContainerInputHandler();
        }
        return INSTANCE;
    }

    private ContainerInputHandler() {
        super(ContainerRepository.getInstance());
    }

    @Override
    public void print() {
        System.out.println("- Container -");
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(Action.BACK.name(), Action.BACK));
        options.add(new Option(Action.CREATE.name(), Action.CREATE));
        for (Container container : ContainerRepository.getInstance().read()) {
            options.add(new Option(container.toString(), container));
        }
        Option option = ConsoleSelectionUtils.displayOptions(options);

        if (option.getRootObject() instanceof Container) {
            printContainerDetails((Container) option.getRootObject());
        } else if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.CREATE)) {
                printCreationDialog();
            }
        }
    }

    private void printContainerDetails(Container container) {
        ArrayList<Option> additionalOptions = new ArrayList<>();
        for (Item item : container.items()) additionalOptions.add(new Option(item.toString(), item));
        Option option = ConsoleSelectionUtils.displayActions(additionalOptions, Action.BACK, Action.CREATE, Action.REMOVE, Action.CHANGE);

        if (option.getRootObject() instanceof Item item) {
            ItemInputHandler.getInstance().printItemOptions(container, item);
        } else if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.CREATE)) {
                ItemInputHandler.getInstance().printItemCreationDialog(container);
            } else if (action.equals(Action.REMOVE)) {
                printDeletionDialog(container);
            } else if (action.equals(Action.CHANGE)) {
                printChangeDialog(container);
            }
        }
        print();
    }

    public void printCreationDialog() {
        Container container = createOrChange(null);

        System.out.println(container);
        if (ConsoleReadingUtils.printConfirmationDialog("Create Container")) {
            ContainerRepository.getInstance().create(container);
        } else {
            printCreationDialog();
        }
    }

    public void printChangeDialog(Record toUpdate) {
        Container containerToBeUpdated = (Container) toUpdate;
        Container updatedContainer = createOrChange(containerToBeUpdated);

        System.out.println(containerToBeUpdated);
        System.out.println("->");
        System.out.println(updatedContainer);
        if (ConsoleReadingUtils.printConfirmationDialog("Change Container")) {
            ContainerRepository.getInstance().update(containerToBeUpdated, updatedContainer);
        } else {
            printContainerDetails(containerToBeUpdated);
        }

    }

    private Container createOrChange(Container toChange) {
        boolean isContainerChange = toChange != null;

        if (!isContainerChange) {
            System.out.println("- Create Container -");
        } else {
            System.out.println("- Change Container -");
            System.out.println(toChange + " (Skip with \"!\")");
        }

        System.out.println("Enter name:");
        String name = ConsoleReadingUtils.readString(isContainerChange);
        System.out.println("Enter location:");
        String location = ConsoleReadingUtils.readString(isContainerChange);
        System.out.println("Select container type:");
        ContainerType type = (ContainerType) ConsoleSelectionUtils.printTypeSelection(ContainerType.values(), isContainerChange);

        HashSet<Item> items = new HashSet<>();
        Container container = new Container(name, location, items, type);
        if (isContainerChange) {
            return applyChanges(container, toChange);
        }
        return container;
    }

    private Container applyChanges(Container container, Container toChange) {
        String name = "", location = "";
        ContainerType type = container.type();
        if (container.name() == null) name = toChange.name();
        if (container.location() == null) location = toChange.location();
        if (container.type() == null) type = toChange.type();
        HashSet<Item> items = toChange.items();
        return new Container(name, location, items, type);
    }
}
