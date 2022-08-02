package org.lunic.persistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class JsonDriver {

    JsonDriver(String path) {
        create(path);
    }

    private void saveToJson(Object item, String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File jsonFile = new File(path);
        try {
            FileWriter jsonWriter = new FileWriter(jsonFile);
            jsonWriter.write(gson.toJson(item));
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void create(String path) {
        try {
            File file = new File(path);
            boolean parentDirectoriesCreated = file.getParentFile().mkdirs();
            if(!file.exists() && parentDirectoriesCreated) {
                var _created = file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void save(ArrayList<Record> records, String path) {
        saveToJson(records, path);
    }

    protected ArrayList<Record> read(String path, Type listType) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = Files.readString(Path.of(path));
            return gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    abstract public void save(ArrayList<Record> records);
    abstract public ArrayList<Record> read();


}
