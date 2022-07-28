package org.lunic.repositories;

import org.lunic.data.Container;
import org.lunic.data.ContainerType;
import org.lunic.data.Item;
import org.lunic.persistance.ContainerJsonDriver;

import java.util.ArrayList;
import java.util.HashSet;

public class ContainerRepository extends Repository {

    public ContainerRepository() {
        super(new ContainerJsonDriver());
    }

    public void Create(Container container) {
        super.Create(container);
    }

    public ArrayList<Container> Read() {
        ArrayList<Container> container = new ArrayList<>();
        for(Record record : recordList) {
            if(record instanceof Container) {
                container.add((Container) record);
            }
        }
        return container;
    }

    public void Update(Container oldContainer, Container newContainer) {
        super.Update(oldContainer, newContainer);
    }

    public void Delete(Container container) {
        super.Delete(container);
    }
}