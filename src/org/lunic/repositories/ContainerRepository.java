package org.lunic.repositories;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.persistance.*;

import java.util.ArrayList;

public class ContainerRepository extends Repository implements ContainerItemInterface {

    public ContainerRepository() {
        super(new ContainerJsonDriver());
    }

    public void create(Container container) {
        super.create(container);
    }

    public ArrayList<Container> read() {
        ArrayList<Container> containers = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof Container) {
                containers.add((Container) record);
            }
        }
        return containers;
    }

    public void update(Container containerToBeUpdated,
                       Container updatedContainer) {
        super.update(containerToBeUpdated, updatedContainer);
    }

    public void Delete(Container container) {
        super.delete(container);
    }

    @Override
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Container container : read()) {
            items.addAll(container.items());
        }
        return items;
    }
}