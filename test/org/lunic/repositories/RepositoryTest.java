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
        repository.Create(testRecord);
        assertTrue(repository.Read().contains(testRecord));
    }

    @Test
    void update() {
        String text = "Test-.#+?ß\\";
        String updatedText = "UpdateTest-.#+?ß\\";
        TestRecord toUpdateTestRecord = new TestRecord(text);
        TestRecord updatedTestRecord = new TestRecord(updatedText);
        repository.Update(toUpdateTestRecord, updatedTestRecord);
        assertFalse(repository.Read().contains(toUpdateTestRecord) && repository.Read().contains(updatedTestRecord));
    }

    @Test
    void delete() {
        String text = "Test-.#+?ß\\";
        TestRecord testRecord = new TestRecord(text);
        if(!repository.Read().contains(testRecord)) {
            repository.Create(testRecord);
        }
        repository.Delete(testRecord);
        assertFalse(repository.Read().contains(testRecord));
    }
}