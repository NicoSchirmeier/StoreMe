package org.lunic.observer;

import org.lunic.ui.UserInputManager;

import org.lunic.repositories.*;
import org.lunic.data.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;


public class ItemExpirationObserver {

    private final GeneralItemInterface itemInterface = UserInputManager.CONTAINER_REPOSITORY;

    public ItemExpirationObserver() {
    }

    public static long getDaysBetween(LocalDate expirationDate,
                                       LocalDate currentDate) {
        return DAYS.between(currentDate, expirationDate);
    }

    public ArrayList<Item> getSoonExpiringItems() {
        LocalDate currentDate = LocalDate.now();
        ArrayList<Item> expiredItems = new ArrayList<>();

        for (Item item : itemInterface.getAllItems()) {
            long daysBetween = getDaysBetween(item.expirationDate(), currentDate);
            if (daysBetween <= 0) {
                expiredItems.add(item);
            }
        }

        return expiredItems;
    }

}
