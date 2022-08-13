package org.lunic.repositories;

import org.lunic.data.Recipe;
import org.lunic.persistance.*;

import java.util.ArrayList;

public class RecipeRepository extends Repository {

    public RecipeRepository() {
        super(new RecipeJsonDriver());
    }

    public void create(Recipe recipe) {
        super.create(recipe);
    }

    public ArrayList<Recipe> read() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof Recipe) {
                recipes.add((Recipe) record);
            }
        }

        return recipes;
    }

    public void update(Recipe recipeToBeUpdated, Recipe updatedRecipe) {
        super.update(recipeToBeUpdated, updatedRecipe);
    }

    public void delete(Recipe recipe) {
        super.delete(recipe);
    }
}
