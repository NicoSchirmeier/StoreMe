package org.lunic.persistance;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonDriverTest {

    String path = "tests/" + getClass().getName() + ".json";
    JsonDriver jsonDriverMock = new JsonDriver(path) {
        @Override
        public void save(ArrayList<Record> records) {
            super.save(records, path);
        }

        @Override
        public ArrayList<Record> read() {
            return super.read(path, new TestRecord(""));
        }
    };

    @Test
    void saveAndRead() {
        String text = "Test-.#+?ß\\";
        TestRecord testRecord = new TestRecord(text);

        ArrayList<Record> records = new ArrayList<>();
        records.add(testRecord);
        jsonDriverMock.save(records);

        File file = new File(path);
        if(file.exists()) {
            assertTrue(jsonDriverMock.read().contains(testRecord));
        } else {
            fail("File does not exist!");
        }
    }

    record TestRecord(String Test){}
}