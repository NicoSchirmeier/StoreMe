package org.lunic.persistance;

import org.lunic.data.TestRecord;

import java.util.ArrayList;

public class TestJsonDriver extends JsonDriver implements DataDriverInterface {

    public static final String PATH = "test-data/" + TestJsonDriver.class.getName() + ".json";

    private TestJsonDriver() {
        super(PATH);
    }

    public static String getPath() {
        return PATH;
    }

    public static DataDriverInterface getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestJsonDriver();
        }
        return (DataDriverInterface) INSTANCE;
    }

    @Override
    public void save(ArrayList<Record> records) {
        super.save(records, PATH);
    }

    @Override
    public ArrayList<Record> read() {
        return super.read(PATH, new TestRecord(""));
    }
}
