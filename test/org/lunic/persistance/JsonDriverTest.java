package org.lunic.persistance;

import org.junit.jupiter.api.Test;
import org.lunic.data.TestRecord;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonDriverTest {

    DataDriverInterface dataDriver = TestJsonDriver.getInstance();

    @Test
    void saveAndRead() {
        String text = "Test-.#+?ß\\";
        TestRecord testRecord = new TestRecord(text);

        ArrayList<Record> records = new ArrayList<>();
        records.add(testRecord);
        dataDriver.save(records);

        File file = new File(TestJsonDriver.getPath());
        if(file.exists()) {
            assertTrue(dataDriver.read().contains(testRecord));
        } else {
            fail("File does not exist!");
        }
    }
}