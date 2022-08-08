package org.lunic.observer;

import org.lunic.DataManager;

import org.lunic.repositories.*;
import org.lunic.data.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;


public class ItemExpirationObserver {

    private final GeneralItemInterface itemInterface = DataManager.CONTAINER_REPOSITORY;

    public ArrayList<Item> getSoonExpiringItems() {
        LocalDate currentDate = LocalDate.now();
        ArrayList<Item> expiredItems = new ArrayList<>();

        for (Item item : itemInterface.getAllItems()) {
            long daysBetween = ObserverUtils.getDaysBetween(item.expirationDate(), currentDate);
            if (daysBetween <= 0) {
                expiredItems.add(item);
            }
        }

        return expiredItems;
    }

    public ArrayList<Recipe> getRecommendedRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for(Recipe recipe : DataManager.RECIPE_REPOSITORY.Read()) {
            boolean canBeCooked = true;
            for(Item item : recipe.items()) {
                if(item.amount() > getTotalAmount(item)) {
                    canBeCooked = false;
                }
            }
            if(canBeCooked) recipes.add(recipe);
        }

        return recipes;
    }

    public int getTotalAmount(Item item) {
        int amount = 0;
        for (Item existingItem : DataManager.CONTAINER_REPOSITORY.getAllItems()) {
            if(existingItem.equals(item)) amount += existingItem.amount();
        }
        return amount;
    }

}
