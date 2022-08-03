package org.lunic.persistance;
import org.lunic.data.Item;
import java.util.ArrayList;

public class ItemTemplateJsonDriver extends JsonDriver {
    private static final String PATH = "data/item_templates.json";
    private static final Item type = new Item(null, null, 0, null, null, null);

    public ItemTemplateJsonDriver() {
        super(PATH);
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
