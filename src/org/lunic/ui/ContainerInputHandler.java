package org.lunic.ui;

import org.lunic.DataManager;
import org.lunic.data.Container;
import org.lunic.data.type.ContainerType;
import org.lunic.data.Item;
import org.lunic.ui.helperclasses.*;

import java.util.ArrayList;
import java.util.HashSet;

public class ContainerInputHandler extends InputHandler implements Printable {

    public ContainerInputHandler() {
        super(DataManager.CONTAINER_REPOSITORY);
    }

    @Override
    public void print() {
        System.out.println("- Container -");
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(Action.BACK.name(), Action.BACK));
        options.add(new Option(Action.CREATE.name(), Action.CREATE));
        for (Container container : DataManager.CONTAINER_REPOSITORY.Read()) {
            options.add(new Option(container.toString(), container));
        }

        Option option = ConsoleSelectionUtils.displayOptions(options);

        if(option.getRootObject() instanceof Container) {
            printContainerDetails((Container) option.getRootObject());
        } else if (option.getRootObject() instanceof Action action) {
            if(action.equals(Action.CREATE)) {
                printCreationDialog();
            }
        }
    }

    private void printContainerDetails(Container container) {
        Option option = ConsoleSelectionUtils.displayActions(Action.BACK, Action.CREATE, Action.REMOVE, Action.CHANGE);

        if(option.getRootObject() instanceof Item) {
            DataManager.ITEM_INPUT_HANDLER.printItemOptions(container, (Item) option.getRootObject());
        } else if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.CREATE)) {
                DataManager.ITEM_INPUT_HANDLER.printItemCreationDialog(container);
            } else if (action.equals(Action.REMOVE)) {
                printDeletionDialog(container);
            } else if (action.equals(Action.CHANGE)) {
                printChangeDialog(container);
            }
        }
        print();
    }

    @Override
    public void printCreationDialog() {
        Container container = createOrChange(null);

        System.out.println(container);
        if(ConsoleReadingUtils.printConfirmationDialog("Create Container")) {
            DataManager.CONTAINER_REPOSITORY.Create(container);
        } else {
            printCreationDialog();
        }
    }

    @Override
    public void printChangeDialog(Record toUpdate) {
        Container containerToBeUpdated = (Container) toUpdate;
        Container updatedContainer = createOrChange(containerToBeUpdated);

        System.out.println(containerToBeUpdated);
        System.out.println("->");
        System.out.println(updatedContainer);
        if(ConsoleReadingUtils.printConfirmationDialog("Change Container")) {
            DataManager.CONTAINER_REPOSITORY.Update(containerToBeUpdated, updatedContainer);
        } else {
            printContainerDetails(containerToBeUpdated);
        }

    }

    private Container createOrChange(Container toChange) {
        if(toChange == null) {
            System.out.println("- Create Container -");
        } else {
            System.out.println("- Change Container -");
            System.out.println(toChange + " (Skip with \"!\")");
        }
        System.out.println("Enter name:");
        String name = ConsoleReadingUtils.readString();
        System.out.println("Enter location:");
        String location = ConsoleReadingUtils.readString();
        System.out.println("Select container type:");
        ContainerType type = (ContainerType) ConsoleSelectionUtils.printTypeSelection(ContainerType.values());

        if(toChange != null) {
            if(name.equals("!")) name = toChange.name();
            if(location.equals("!")) location = toChange.location();
            return new Container(name, location, toChange.items(), type);
        }

        return new Container(name, location, new HashSet<>(), type);
    }
}
