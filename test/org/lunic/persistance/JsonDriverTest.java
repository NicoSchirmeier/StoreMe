package org.lunic.persistance;

import org.junit.jupiter.api.Test;
import org.lunic.data.TestRecord;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonDriverTest {

    JsonDriverMock jsonDriverMock = new JsonDriverMock();

    @Test
    void saveAndRead() {
        String text = "Test-.#+?ß\\";
        TestRecord testRecord = new TestRecord(text);

        ArrayList<Record> records = new ArrayList<>();
        records.add(testRecord);
        jsonDriverMock.save(records);

        File file = new File(jsonDriverMock.getPath());
        if(file.exists()) {
            assertTrue(jsonDriverMock.read().contains(testRecord));
        } else {
            fail("File does not exist!");
        }
    }
}