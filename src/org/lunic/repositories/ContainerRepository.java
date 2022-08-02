package org.lunic.repositories;

import org.lunic.data.Container;
import org.lunic.persistance.ContainerJsonDriver;

import java.util.ArrayList;

public class ContainerRepository extends Repository {

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
}