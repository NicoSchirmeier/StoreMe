package org.lunic.ui;

import org.lunic.data.Container;
import org.lunic.data.ContainerType;
import org.lunic.data.Item;
import org.lunic.repositories.ContainerRepository;

import java.util.ArrayList;
import java.util.HashSet;

public class ContainerInputHandler implements InputHandler {
    private final ContainerRepository repository;
    private final ItemInputHandler itemInputHandler;

    public ContainerInputHandler(ContainerRepository containerRepository, TagInputHandler tagInputHandler) {
        this.repository = containerRepository;
        this.itemInputHandler = new ItemInputHandler(repository, this, tagInputHandler);
    }
    @Override
    public void print() {
        System.out.println();
        System.out.println("- Container -");
        printContainerOptions();
    }

    public void printContainerOptions() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("Create NEW", ContainerAction.CREATE));
        options.add(new Option("Back", ContainerAction.BACK));
        for (Container container : repository.Read()) {
            options.add(new Option(container.toString(), container));
        }

        Option option = ConsoleUtils.displayOptions(options);

        if(option.getRootObject() instanceof Container) {
            printContainerDetails((Container) option.getRootObject());
        } else if (option.getRootObject() instanceof ContainerAction action) {
            if(action.equals(ContainerAction.CREATE)) {
                printContainerCreationDialog();
            }
        }

    }

    private void printContainerDetails(Container container) {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("Add Item", ContainerAction.CREATE_ITEM));
        options.add(new Option("Delete container", ContainerAction.REMOVE));
        options.add(new Option("Change Container", ContainerAction.CHANGE));
        options.add(new Option("Back", ContainerAction.BACK));

        for(Item item : container.items()) {
            options.add(new Option(item.toString(), item));
        }

        Option option = ConsoleUtils.displayOptions(options);

        if(option.getRootObject() instanceof Item) {
            itemInputHandler.printItemOptions(container, (Item) option.getRootObject());
        } else if (option.getRootObject() instanceof ContainerAction action) {
            if (action.equals(ContainerAction.CREATE_ITEM)) {
                itemInputHandler.printItemCreationDialog(container);
            } else if (action.equals(ContainerAction.REMOVE)) {
                printContainerDeletionDialog(container);
            } else if (action.equals(ContainerAction.CHANGE)) {
                printContainerChangeDialog(container);
            }
        }
        printContainerOptions();
    }

    private void printContainerCreationDialog() {
        System.out.println("Enter name: ");
        String name = ConsoleUtils.readString();
        System.out.println("Enter location: ");
        String location = ConsoleUtils.readString();
        System.out.println("Select container type:");

        ContainerType type = ContainerAction.printTypeSelector();

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

        ContainerType type = ContainerAction.printTypeSelector();

        if(name.equals("!")) name = containerToBeUpdated.name();
        if(location.equals("!")) location = containerToBeUpdated.location();

        updatedContainer = new Container(name, location, containerToBeUpdated.items(), type);

        System.out.println(containerToBeUpdated);
        System.out.println("->");
        System.out.println(updatedContainer);
        if(ConsoleUtils.printConfirmationDialog("Change Container")) {
            repository.Update(containerToBeUpdated, updatedContainer);
        } else {
            printContainerDetails(containerToBeUpdated);
        }

    }

    private void printContainerDeletionDialog(Container container) {
        boolean confirmed = ConsoleUtils.printConfirmationDialog("Delete Container");
        if(confirmed) repository.Delete(container);
    }
}
