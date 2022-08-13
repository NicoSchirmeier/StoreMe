package org.lunic.repositories;

import org.junit.jupiter.api.Test;
import org.lunic.data.TestRecord;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    private final TestRepository repository = new TestRepository();

    @Test
    void create() {
        repository.clear();
        String text = "Test-.#+?ß\\";
        TestRecord testRecord = new TestRecord(text);
        repository.create(testRecord);
        assertTrue(repository.read().contains(testRecord));
    }

    @Test
    void update() {
        repository.clear();
        String text = "Test-.#+?ß\\";
        String updatedText = "UpdateTest-.#+?ß\\";
        TestRecord toUpdateTestRecord = new TestRecord(text);
        TestRecord updatedTestRecord = new TestRecord(updatedText);

        repository.create(toUpdateTestRecord);
        repository.update(toUpdateTestRecord, updatedTestRecord);

        assertFalse(repository.read().contains(toUpdateTestRecord));
        assertTrue(repository.read().contains(updatedTestRecord));
    }

    @Test
    void delete() {
        repository.clear();
        String text = "Test-.#+?ß\\";
        TestRecord testRecord = new TestRecord(text);
        repository.create(testRecord);

        repository.delete(testRecord);
        assertFalse(repository.read().contains(testRecord));
    }
}