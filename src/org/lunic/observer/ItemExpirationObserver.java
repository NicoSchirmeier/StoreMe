package org.lunic.observer;

import org.lunic.data.Item;
import org.lunic.data.Recipe;
import org.lunic.repositories.ContainerItemInterface;
import org.lunic.repositories.ContainerRepository;
import org.lunic.repositories.RecipeRepository;

import java.time.LocalDate;
import java.util.ArrayList;


public class ItemExpirationObserver {

    private static ItemExpirationObserver INSTANCE;
    public static ItemExpirationObserver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ItemExpirationObserver();
        }
        return INSTANCE;
    }
    private ItemExpirationObserver() {}

    private final ContainerItemInterface itemInterface = ContainerRepository.getInstance();

    public ArrayList<Item> getSoonExpiringItems() {
        ArrayList<Item> expiredItems = new ArrayList<>();

        for (Item item : itemInterface.getAllItems()) {
            long daysBetween = ObserverUtils.getDaysBetween(LocalDate.now(), item.expirationDate());
            if (daysBetween <= 7) {
                expiredItems.add(item);
            }
        }

        return expiredItems;
    }

    public ArrayList<Recipe> getRecommendedRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : RecipeRepository.getInstance().read()) {
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
        for (Item existingItem : ContainerRepository.getInstance().getAllItems()) {
            if (existingItem.equals(item) && ObserverUtils.getDaysBetween(LocalDate.now(), existingItem.expirationDate()) >= 0) {
                amount += existingItem.amount();
            }
        }
        return amount;
    }

}
