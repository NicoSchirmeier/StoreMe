package org.lunic.ui;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.repositories.ContainerRepository;

import java.util.ArrayList;

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

        Option option = UserInputHandler.displayOptions(options);

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

        Option option = UserInputHandler.displayOptions(options);

        if(option.getRootObject() instanceof Item) {
            printItemOptions(container, (Item) option.getRootObject());
        } else if (option.getRootObject() instanceof Actions action) {
            if (action.equals(Actions.CREATE_ITEM)) {
                printItemCreationDialog(container);
            } else if (action.equals(Actions.REMOVE)) {
                printContainerDeletionDialog();
            } else if (action.equals(Actions.CHANGE)) {
                printContainerChangeDialog();
            }
        }
    }

    private void printContainerCreationDialog() {
        System.err.println("NOT IMPLEMENTED");
    }

    private void printContainerChangeDialog() {
        System.err.println("NOT IMPLEMENTED");
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

    private enum Actions {
        CREATE,
        CREATE_ITEM,
        REMOVE,
        CHANGE,
        BACK
    }
}
