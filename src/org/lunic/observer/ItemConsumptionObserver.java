package org.lunic.observer;

import org.lunic.DataManager;
import org.lunic.data.Item;
import org.lunic.repositories.GeneralItemInterface;

import java.time.LocalDate;
import java.util.ArrayList;

public class ItemConsumptionObserver {
    private final GeneralItemInterface itemInterface = DataManager.CONTAINER_REPOSITORY;

    public ArrayList<Item> getSoonConsumedItems() {
        ArrayList<Item> soonConsumedItems = new ArrayList<>();

        for (Item item : itemInterface.getAllItems()) {
            long daysBetween = ObserverUtils.getDaysBetween(item.consumptionDate(), LocalDate.now());
            if (daysBetween <= 10) {
                soonConsumedItems.add(item);
            }
        }

        return soonConsumedItems;
    }

}
