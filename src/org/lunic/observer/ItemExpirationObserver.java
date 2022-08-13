package org.lunic.observer;

import org.lunic.DataManager;
import org.lunic.data.Item;
import org.lunic.data.Recipe;
import org.lunic.repositories.ContainerItemInterface;

import java.time.LocalDate;
import java.util.ArrayList;


public class ItemExpirationObserver {

    private final ContainerItemInterface itemInterface = DataManager.CONTAINER_REPOSITORY;

    public ArrayList<Item> getSoonExpiringItems() {
        ArrayList<Item> expiredItems = new ArrayList<>();

        for (Item item : itemInterface.getAllItems()) {
            long daysBetween = ObserverUtils.getDaysBetween(LocalDate.now(), item.expirationDate());
            if (daysBetween <= 0) {
                expiredItems.add(item);
            }
        }

        return expiredItems;
    }

    public ArrayList<Recipe> getRecommendedRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : DataManager.RECIPE_REPOSITORY.read()) {
            boolean canBeCooked = true;
            for (Item item : recipe.items()) {
                if (item.amount() > getTotalAmount(item)) {
                    canBeCooked = false;
                }
            }
            if (canBeCooked) recipes.add(recipe);
        }

        return recipes;
    }

    public int getTotalAmount(Item item) {
        int amount = 0;
        for (Item existingItem : DataManager.CONTAINER_REPOSITORY.getAllItems()) {
            if (existingItem.equals(item) && ObserverUtils.getDaysBetween(LocalDate.now(), existingItem.expirationDate()) >= 0) {
                amount += existingItem.amount();
            }
        }
        return amount;
    }

}
