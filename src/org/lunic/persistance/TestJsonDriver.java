package org.lunic.persistance;

import org.lunic.data.TestRecord;

import java.util.ArrayList;

public class TestJsonDriver extends JsonDriver implements DataDriverInterface {

    public static final String PATH = "tests/" + TestJsonDriver.class.getName() + ".json";

    public TestJsonDriver() {
        super(PATH);
    }

    @Override
    public void save(ArrayList<Record> records) {
        super.save(records, PATH);
    }

    @Override
    public ArrayList<Record> read() {
        return super.read(PATH, new TestRecord(""));
    }

    public static String getPath() {
        return PATH;
    }
}
