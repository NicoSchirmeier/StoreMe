package org.lunic.ui;

import org.lunic.data.Container;
import org.lunic.data.ContainerType;
import org.lunic.data.Item;
import org.lunic.repositories.ContainerRepository;

import java.util.ArrayList;
import java.util.HashSet;

public class ContainerInputHandler implements InputHandler {
    private final ContainerRepository repository;

    public ContainerInputHandler(ContainerRepository containerRepository) {
        this.repository = containerRepository;
    }
    @Override
    public void print() {
        System.out.println();
        System.out.println("- Container -");
        printContainerOptions();
    }

    private void printContainerOptions() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("Create NEW", Actions.CREATE));
        options.add(new Option("Back", Actions.BACK));
        for (Container container : repository.Read()) {
            options.add(new Option(container.toString(), container));
        }

        Option option = ConsoleUtils.displayOptions(options);

        if(option.getRootObject() instanceof Container) {
            printContainerItems((Container) option.getRootObject());
        } else if (option.getRootObject() instanceof Actions action) {
            if(action.equals(Actions.CREATE)) {
                printContainerCreationDialog();
            }
        }

    }

    private void printContainerItems(Container container) {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("Add Item", Actions.CREATE_ITEM));
        options.add(new Option("Delete container", Actions.REMOVE));
        options.add(new Option("Change Container", Actions.CHANGE));
        options.add(new Option("Back", Actions.BACK));

        for(Item item : container.items()) {
            options.add(new Option(item.toString(), item));
        }

        Option option = ConsoleUtils.displayOptions(options);

        if(option.getRootObject() instanceof Item) {
            printItemOptions(container, (Item) option.getRootObject());
        } else if (option.getRootObject() instanceof Actions action) {
            if (action.equals(Actions.CREATE_ITEM)) {
                printItemCreationDialog(container);
            } else if (action.equals(Actions.REMOVE)) {
                printContainerDeletionDialog();
            } else if (action.equals(Actions.CHANGE)) {
                printContainerChangeDialog(container);
            }
        }
    }

    private void printContainerCreationDialog() {
        System.out.println("Enter name: ");
        String name = ConsoleUtils.readString();
        System.out.println("Enter location: ");
        String location = ConsoleUtils.readString();
        System.out.println("Select container type:");

        ContainerType type = printTypeSelector();

        Container container = new Container(name, location, new HashSet<>(), type);

        System.out.println(container);
        if(ConsoleUtils.printConfirmationDialog("Create Container")) {
            repository.Create(container);
        } else {
            printContainerCreationDialog();
        }
    }

    private void printContainerChangeDialog(Container containerToBeUpdated) {
        Container updatedContainer;

        System.out.println("Changing container: [" + containerToBeUpdated + "]");
        System.out.println("Enter name: (Enter \"!\" to skip)");
        String name = ConsoleUtils.readString();
        System.out.println("Enter location: (Enter \"!\" to skip)");
        String location = ConsoleUtils.readString();
        System.out.println("Select container type:");

        ContainerType type = printTypeSelector();

        if(name.equals("!")) name = containerToBeUpdated.name();
        if(location.equals("!")) location = containerToBeUpdated.location();

        updatedContainer = new Container(name, location, containerToBeUpdated.items(), type);

        System.out.println(containerToBeUpdated);
        System.out.println("->");
        System.out.println(updatedContainer);
        if(ConsoleUtils.printConfirmationDialog("Change Container")) {
            repository.Update(containerToBeUpdated, updatedContainer);
        } else {
            printContainerItems(containerToBeUpdated);
        }

    }

    private void printContainerDeletionDialog() {
        System.err.println("NOT IMPLEMENTED");
    }

    private void printItemCreationDialog(Container container) {
        System.err.println("NOT IMPLEMENTED");
    }

    private void printItemOptions(Container container, Item rootObject) {
        System.err.println("NOT IMPLEMENTED");
    }

    private ContainerType printTypeSelector() {
        ContainerType type = ContainerType.DEFAULT;
        ArrayList<Option> options = new ArrayList<>();
        for (ContainerType containerType : ContainerType.values()) {
            options.add(new Option(containerType.name(), containerType));
        }

        Option option = ConsoleUtils.displayOptions(options);

        if(option.getRootObject() instanceof ContainerType containerType) {
            type = containerType;
        }

        return type;
    }
    private enum Actions {
        CREATE,
        CREATE_ITEM,
        REMOVE,
        CHANGE,
        BACK
    }
}
