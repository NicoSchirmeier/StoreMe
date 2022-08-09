package org.lunic.repositories;

import org.lunic.data.TestRecord;
import org.lunic.persistance.JsonDriver;
import org.lunic.persistance.JsonDriverMock;

import java.util.ArrayList;

public class TestingRepository extends Repository {

    protected TestingRepository(JsonDriverMock jsonDriver) {
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
