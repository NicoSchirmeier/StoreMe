package org.lunic.ui;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.repositories.ContainerRepository;

import java.util.ArrayList;

public class ItemInputHandler implements InputHandler {
    ContainerRepository repository; 
    public ItemInputHandler(ContainerRepository repository) {
        this.repository = repository; 
    }

    @Override
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
        if(option.getRootObject() instanceof Action action) {
            if(action.equals(Action.CHANGE)) {
                printItemChangeDialog(item, container);
            } else if (action.equals(Action.DELETE)) {
                printItemDeletionDialog(item, container);
            }
        }
    }

    public void printItemCreationDialog(Container container) {
        System.err.println("NOT IMPLEMENTED");
    }

    private void printItemChangeDialog(Item item, Container container) {

    }

    private void printItemDeletionDialog(Item item, Container container) {
    }

    private enum Action {
        CHANGE,
        DELETE, 
        BACK
    }
}
