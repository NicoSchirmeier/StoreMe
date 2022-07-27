package org.lunic.persistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.lunic.data.Item;
import org.lunic.data.Tag;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ItemTemplateJsonDriver implements JsonDriver {
    private static final String PATH = "data/item_templates.json";
    private static final Type listType = new TypeToken<ArrayList<Item>>(){}.getType();


    @Override
    public void save(Object item_templates) {
        if(item_templates instanceof ArrayList) {
            JsonDriver.saveToJson(item_templates, PATH);
        }
    }

    @Override
    public ArrayList<Item> read() {
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
