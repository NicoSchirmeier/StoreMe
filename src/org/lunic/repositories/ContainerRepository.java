package org.lunic.repositories;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.persistance.*;

import java.util.ArrayList;

public class ContainerRepository extends Repository implements ContainerItemInterface {

    public ContainerRepository() {
        super(new ContainerJsonDriver());
    }

    public void Create(Container container) {
        super.Create(container);
    }

    public ArrayList<Container> Read() {
        ArrayList<Container> containers = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof Container) {
                containers.add((Container) record);
            }
        }
        return containers;
    }

    public void Update(Container containerToBeUpdated,
                       Container updatedContainer) {
        super.Update(containerToBeUpdated, updatedContainer);
    }

    public void Delete(Container container) {
        super.Delete(container);
    }

    @Override
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Container container : Read()) {
            items.addAll(container.items());
        }
        return items;
    }
}