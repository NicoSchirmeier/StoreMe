package org.lunic.observer;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.repositories.ContainerRepository;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;


public class ExpirationObserver {

    public ContainerRepository currentContainers;
    LocalDate currentDate;

    public ExpirationObserver(ContainerRepository currentContainers) {
        this.currentContainers = currentContainers;
        this.currentDate = LocalDate.now();
    }

    public static long getDaysBetween(LocalDate expirationDate,
                                       LocalDate currentDate) {
        return DAYS.between(currentDate, expirationDate);
    }

    public ArrayList<Item> getSoonExpiringItems() {

        ArrayList<Item> expiredItems = new ArrayList<>();
        for (Container container : currentContainers.Read()) {
            for (Item item : container.items()) {
                long daysBetween = getDaysBetween(item.expirationDate(), currentDate);
                if (daysBetween <= 0) {
                    expiredItems.add(item);
                }
            }
        }
        return expiredItems;
    }

}
