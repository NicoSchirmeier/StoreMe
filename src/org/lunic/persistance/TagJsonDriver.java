package org.lunic.persistance;
import org.lunic.data.Tag;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TagJsonDriver extends JsonDriver implements DataDriverInterface {

    private static final String PATH = "data/tags.json";
    private static final Tag type = new Tag(null, null, null);

    public TagJsonDriver() {
        super(PATH);
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
