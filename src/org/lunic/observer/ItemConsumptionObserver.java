package org.lunic.observer;

import org.lunic.DataManager;
import org.lunic.data.Item;
import org.lunic.repositories.ContainerItemInterface;

import java.time.LocalDate;
import java.util.ArrayList;

public class ItemConsumptionObserver {
    private final ContainerItemInterface itemInterface = DataManager.CONTAINER_REPOSITORY;

    public ArrayList<Item> getSoonConsumedItems() {
        ArrayList<Item> soonConsumedItems = new ArrayList<>();

        for (Item item : itemInterface.getAllItems()) {
            long daysBetween = ObserverUtils.getDaysBetween(LocalDate.now(), item.consumptionDate());
            if (daysBetween <= 10) {
                soonConsumedItems.add(item);
            }
        }

        return soonConsumedItems;
    }

}
