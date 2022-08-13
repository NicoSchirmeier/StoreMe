package org.lunic.repositories;

import org.lunic.persistance.JsonDriver;

import java.util.ArrayList;

public abstract class Repository {

    protected final ArrayList<Record> recordList = new ArrayList<>();
    protected final JsonDriver jsonDriver;

    protected Repository(JsonDriver jsonDriver) {
        this.jsonDriver = jsonDriver;
        load();
    }

    private void load() {
        recordList.clear();
        ArrayList<Record> records = jsonDriver.read();
        if(records != null) {
            recordList.addAll(records);
        }
    }

    protected void create(Record record) {
        recordList.add(record);
        jsonDriver.save(recordList);
    }

    protected void update(Record recordToBeUpdated, Record updatedRecord) {
        if (recordToBeUpdated == null || updatedRecord == null) return;

        recordList.remove(find(recordToBeUpdated));
        recordList.add(updatedRecord);

        jsonDriver.save(recordList);
    }

    protected void delete(Record record) {
        if (find(record) == null) return;

        recordList.remove(find(record));
        jsonDriver.save(recordList);
    }

    private Record find(Record recordToFind) {
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
