package org.lunic.repositories;

import org.lunic.persistance.JsonDriver;

import java.util.ArrayList;

public abstract class Repository {

    protected final ArrayList<Record> recordList = new ArrayList<>();
    protected final JsonDriver jsonDriver;

    protected Repository(JsonDriver jsonDriver) {
        this.jsonDriver = jsonDriver;
        Load();
    }

    private void Load() {
        recordList.clear();
        if(jsonDriver.read() != null) {
            recordList.addAll(jsonDriver.read());
        }
    }

    protected void Create(Record record) {
        recordList.add(record);
        jsonDriver.save(recordList);
    }

    protected void Update(Record recordToBeUpdated, Record updatedRecord) {
        if (recordToBeUpdated == null || updatedRecord == null) return;

        recordList.remove(Find(recordToBeUpdated));
        recordList.add(updatedRecord);

        jsonDriver.save(recordList);
    }

    protected void Delete(Record record) {
        if (Find(record) == null) return;

        recordList.remove(Find(record));
        jsonDriver.save(recordList);
    }

    private Record Find(Record recordToFind) {
        if (recordToFind == null) {
            System.err.println(new NullPointerException().getMessage());
            return null;
        } else {
            for (Record record : recordList) {
                if (record.equals(recordToFind)) return record;
            }
        }
        System.err.println(
                "ERROR: " + recordToFind.getClass() + " could not be found!");
        return null;
    }

}
