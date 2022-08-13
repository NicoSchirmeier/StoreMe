package org.lunic.persistance;

import org.lunic.data.Recipe;

import java.util.ArrayList;

public class RecipeJsonDriver extends JsonDriver implements DataDriverInterface {
    protected static JsonDriver INSTANCE;
    private static final String PATH = "data/recipes.json";
    private static final Recipe type = new Recipe(null, null, null, false, null, null, null);

    private RecipeJsonDriver() {
        super(PATH);
    }

    public static DataDriverInterface getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RecipeJsonDriver();
        }
        return (DataDriverInterface) INSTANCE;
    }

    @Override
    public void save(ArrayList<Record> records) {
        save(records, PATH);
    }

    @Override
    public ArrayList<Record> read() {
        return read(PATH, type);
    }
}
