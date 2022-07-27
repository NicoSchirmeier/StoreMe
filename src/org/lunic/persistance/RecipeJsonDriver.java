package org.lunic.persistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.lunic.data.Recipe;
import org.lunic.data.Tag;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class RecipeJsonDriver implements JsonDriver {
    private static final String PATH = "data/recipes.json";
    private static final Type listType = new TypeToken<ArrayList<Recipe>>(){}.getType();

    @Override
    public void save(Object recipes) {
        if(recipes instanceof ArrayList) {
            JsonDriver.saveToJson(recipes, PATH);
        }
    }

    @Override
    public ArrayList<Recipe> read() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String json = Files.readString(Path.of(PATH));
            return gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
