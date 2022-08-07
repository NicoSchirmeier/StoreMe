package org.lunic.repositories;

import org.lunic.data.Recipe;
import org.lunic.persistance.*;

import java.util.ArrayList;

public class RecipeRepository extends Repository {

    public RecipeRepository() {
        super(new RecipeJsonDriver());
    }

    public void Create(Recipe recipe) {
        super.Create(recipe);
    }

    public ArrayList<Recipe> Read() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof Recipe) {
                recipes.add((Recipe) record);
            }
        }

        return recipes;
    }

    public void Update(Recipe recipeToBeUpdated, Recipe updatedRecipe) {
        super.Update(recipeToBeUpdated, updatedRecipe);
    }

    public void Delete(Recipe recipe) {
        super.Delete(recipe);
    }
}
