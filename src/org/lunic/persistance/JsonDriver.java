package org.lunic.persistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface JsonDriver {

    static void saveToJson(Object item, String path) {
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

    static void createDirectory(String path) {
        try {
            Path directory = Path.of(path);
            if(!Files.exists(directory)) Files.createDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void save(Object item);

    Object read();

}
