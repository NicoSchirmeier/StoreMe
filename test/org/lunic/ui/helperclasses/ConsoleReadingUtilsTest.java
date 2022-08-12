package org.lunic.ui.helperclasses;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleReadingUtilsTest {

    @Test
    void readString() {
        InputStream sysInBackup = System.in;

        ByteArrayInputStream in = new ByteArrayInputStream("!".getBytes());
        System.setIn(in);
        String text = ConsoleReadingUtils.readString(true);
        assertNull(text);

        System.setIn(sysInBackup);
    }

    @Test
    void getAmount() {
        InputStream sysInBackup = System.in;

        ByteArrayInputStream in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);
        int amount = ConsoleReadingUtils.getAmount(0, 100, false);
        assertEquals(5, amount);

        System.setIn(sysInBackup);
    }
}