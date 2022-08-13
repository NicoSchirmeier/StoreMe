package org.lunic.repositories;

import org.junit.jupiter.api.Test;
import org.lunic.data.TestRecord;
import org.lunic.persistance.TestJsonDriver;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    private final TestRepository repository = new TestRepository(new TestJsonDriver());

    @Test
    void create() {
        String text = "Test-.#+?ß\\";
        TestRecord testRecord = new TestRecord(text);
        repository.create(testRecord);
        assertTrue(repository.read().contains(testRecord));
    }

    @Test
    void update() {
        String text = "Test-.#+?ß\\";
        String updatedText = "UpdateTest-.#+?ß\\";
        TestRecord toUpdateTestRecord = new TestRecord(text);
        TestRecord updatedTestRecord = new TestRecord(updatedText);
        repository.update(toUpdateTestRecord, updatedTestRecord);
        assertFalse(repository.read().contains(toUpdateTestRecord) && repository.read().contains(updatedTestRecord));
    }

    @Test
    void delete() {
        String text = "Test-.#+?ß\\";
        TestRecord testRecord = new TestRecord(text);
        if(!repository.read().contains(testRecord)) {
            repository.create(testRecord);
        }
        repository.delete(testRecord);
        assertFalse(repository.read().contains(testRecord));
    }
}