package org.lunic.persistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class JsonDriver {
    JsonDriver(String path) {
        create(path);
    }

    private void saveToJson(Object item, String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File jsonFile = new File(path);
        try {
            FileWriter jsonWriter = new FileWriter(jsonFile);
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(item);
            jsonWriter.write(json);
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void create(String path) {
        try {
            File file = new File(path);
            boolean parentDirectoriesCreated = file.getParentFile().mkdirs();
            if (parentDirectoriesCreated || file.getParentFile().exists()) {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void save(ArrayList<Record> records, String path) {
        saveToJson(records, path);
    }

    protected ArrayList<Record> read(String path, Record type) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String json = Files.readString(Path.of(path));

            ArrayList<Record> records = new ArrayList<>();
            if (json.length() > 0) {
                for (Object obj : objectMapper.readValue(json, ArrayList.class)) {
                    records.add(objectMapper.convertValue(obj, type.getClass()));
                }
            }
            return records;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
