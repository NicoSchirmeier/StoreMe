package org.lunic.repositories;

import org.lunic.data.TestRecord;
import org.lunic.persistance.TestJsonDriver;

import java.util.ArrayList;

public class TestRepository extends Repository {

    private static TestRepository INSTANCE;
    private TestRepository() {
        super(TestJsonDriver.getInstance());
    }

    public static TestRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TestRepository();
        }
        return INSTANCE;
    }

    public ArrayList<TestRecord> read() {
        ArrayList<TestRecord> testRecords = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof TestRecord testRecord) {
                testRecords.add(testRecord);
            }
        }
        return testRecords;
    }

    public void create(TestRecord record) {
        super.create(record);
    }

    public void delete(TestRecord record) {
        super.delete(record);
    }

    public void update(TestRecord toUpdate, TestRecord updated) {
        super.update(toUpdate, updated);
    }

    public void clear() {
        recordList.clear();
    }
}
