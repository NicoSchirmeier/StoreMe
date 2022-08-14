package org.lunic.persistance;

import org.lunic.data.Tag;

import java.util.ArrayList;

public class TagJsonDriver extends JsonDriver implements DataDriverInterface {
    private static final String PATH = "data/tags.json";
    private static final Tag type = new Tag(null, null, null);
    protected static JsonDriver INSTANCE;

    private TagJsonDriver() {
        super(PATH);
    }

    public static DataDriverInterface getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TagJsonDriver();
        }
        return (DataDriverInterface) INSTANCE;
    }

    @Override
    public void save(ArrayList<Record> tags) {
        save(tags, PATH);
    }

    @Override
    public ArrayList<Record> read() {
        return read(PATH, type);
    }
}
