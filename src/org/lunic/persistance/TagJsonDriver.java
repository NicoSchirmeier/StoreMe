package org.lunic.persistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.lunic.data.Tag;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class TagJsonDriver extends JsonDriver {

    private static final String PATH = "data/tags.json";
    private static final Type listType = new TypeToken<ArrayList<Tag>>(){}.getType();

    public TagJsonDriver() {
        super(PATH);
    }

    @Override
    public void save(ArrayList<Record> tags) {
        save(tags, PATH);
    }

    @Override
    public ArrayList<Record> read() {
        return read(PATH, listType);
    }
}
