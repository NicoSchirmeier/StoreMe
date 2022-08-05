package org.lunic.ui;

import org.lunic.data.Container;
import org.lunic.data.ContainerType;
import org.lunic.data.Item;
import org.lunic.repositories.ContainerRepository;

import java.util.ArrayList;
import java.util.HashSet;

public class ContainerInputHandler extends InputHandler {
    private final ContainerRepository repository;
    private final ItemInputHandler itemInputHandler;

    public ContainerInputHandler(ContainerRepository containerRepository, TagInputHandler tagInputHandler) {
        super(containerRepository);
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
                printCreationDialog();
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
                printDeletionDialog(container);
            } else if (action.equals(ContainerAction.CHANGE)) {
                printChangeDialog(container);
            }
        }
        printContainerOptions();
    }
    @Override
    public void printCreationDialog() {
        System.out.println("Enter name: ");
        String name = ConsoleUtils.readString();
        System.out.println("Enter location: ");
        String location = ConsoleUtils.readString();
        System.out.println("Select container type:");
        ContainerType type = (ContainerType) ConsoleUtils.printTypeSelection(ContainerType.values());

        Container container = new Container(name, location, new HashSet<>(), type);

        System.out.println(container);
        if(ConsoleUtils.printConfirmationDialog("Create Container")) {
            repository.Create(container);
        } else {
            printCreationDialog();
        }
    }
    @Override
    public void printChangeDialog(Record toUpdate) {
        Container containerToBeUpdated = (Container) toUpdate;
        Container updatedContainer;

        System.out.println("Changing container: [" + containerToBeUpdated + "]");
        System.out.println("Enter name: (Enter \"!\" to skip)");
        String name = ConsoleUtils.readString();
        System.out.println("Enter location: (Enter \"!\" to skip)");
        String location = ConsoleUtils.readString();
        System.out.println("Select container type:");
        ContainerType type = (ContainerType) ConsoleUtils.printTypeSelection(ContainerType.values());

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
}
