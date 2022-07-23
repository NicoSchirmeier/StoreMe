package org.lunic.repositories;

import org.lunic.data.Container;
import org.lunic.data.ContainerType;
import org.lunic.data.Item;

import java.util.HashSet;

public class ContainerRepository {

    private HashSet<Container> containerList;


    public void Create(String containerName, String containerLocation, HashSet<Item> containerItems, ContainerType containerType) {
        Container container = new Container(containerName, containerLocation, containerItems, containerType);
        containerList.add(container);

        //Save
    }

    public Container Read(Container container) {
        return find(container);
    }

    public void Update(Container container, String containerName, String containerLocation, HashSet<Item> containerItems, ContainerType containerType) {
        Container containerBeforeUpdate = new Container(containerName, containerLocation, containerItems, containerType);
        Container containerToUpdate = find(container);

        if(containerToUpdate == null) return;

        containerList.remove(containerToUpdate);
        containerList.add(container);

        //Save
    }

    public void Delete(Container container) {
        containerList.remove(find(container));
        //Save
    }

    private Container find(Container containerToFind) {
        for(Container container : containerList) {
            if(container.name().equals(containerToFind)) return container;
        }
        return null;
    }
}
