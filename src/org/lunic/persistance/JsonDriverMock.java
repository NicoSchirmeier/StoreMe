package org.lunic.persistance;

import org.lunic.data.TestRecord;

import java.util.ArrayList;

public class JsonDriverMock extends JsonDriver {

    public static final String PATH = "tests/" + JsonDriverMock.class.getName() + ".json";

    public JsonDriverMock() {
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
