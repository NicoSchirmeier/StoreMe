package org.lunic.persistance;

import org.lunic.data.Item;

import java.util.ArrayList;

public class ItemTemplateJsonDriver extends JsonDriver implements DataDriverInterface {
    protected static JsonDriver INSTANCE;
    private static final String PATH = "data/item_templates.json";
    private static final Item type = new Item(null, null, 0, null, null, null);

    private ItemTemplateJsonDriver() {
        super(PATH);
    }

    public static DataDriverInterface getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ItemTemplateJsonDriver();
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
