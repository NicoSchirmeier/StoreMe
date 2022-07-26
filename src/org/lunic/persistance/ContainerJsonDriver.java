package org.lunic.persistance;

import org.lunic.data.Container;

import java.util.ArrayList;

public class ContainerJsonDriver extends JsonDriver implements DataDriverInterface {
    private static final String PATH = "data/container.json";
    private static final Container type = new Container(null, null, null, null);
    protected static JsonDriver INSTANCE;

    private ContainerJsonDriver() {
        super(PATH);
    }

    public static DataDriverInterface getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContainerJsonDriver();
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
