package org.lunic.repositories;

import org.lunic.data.TestRecord;
import org.lunic.persistance.TestJsonDriver;

import java.util.ArrayList;

public class TestRepository extends Repository {

    protected TestRepository(TestJsonDriver jsonDriver) {
        super(jsonDriver);
    }

    public ArrayList<TestRecord> Read() {
        ArrayList<TestRecord> testRecords = new ArrayList<>();
        for (Record record : recordList) {
            if(record instanceof TestRecord testRecord) {
                testRecords.add(testRecord);
            }
        }
        return testRecords;
    }

    public void Create(TestRecord record) {
        super.Create(record);
    }

    public void Delete(TestRecord record) {
        super.Delete(record);
    }

    public void Update(TestRecord toUpdate, TestRecord updated) {
        super.Update(toUpdate, updated);
    }
}
