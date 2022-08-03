package org.lunic.ui;

import org.lunic.data.ContainerType;

import java.util.ArrayList;

public enum ContainerAction {
    CREATE,
    CREATE_ITEM,
    REMOVE,
    CHANGE,
    BACK;

    public static ContainerType printTypeSelector() {
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
}
