package org.lunic.repositories;

import org.lunic.data.Container;
import org.lunic.persistance.ContainerJsonDriver;

import java.util.ArrayList;

public class ContainerRepositoryImpl extends Repository implements ContainerRepository {

    public ContainerRepositoryImpl() {
        super(new ContainerJsonDriver());
    }

    @Override
    public void Create(Container container) {
        super.Create(container);
    }

    @Override
    public ArrayList<Container> Read() {
        ArrayList<Container> containers = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof Container) {
                containers.add((Container) record);
            }
        }
        return containers;
    }

    @Override
    public void Update(Container containerToBeUpdated,
                       Container updatedContainer) {
        super.Update(containerToBeUpdated, updatedContainer);
    }

    @Override
    public void Delete(Container container) {
        super.Delete(container);
    }
}