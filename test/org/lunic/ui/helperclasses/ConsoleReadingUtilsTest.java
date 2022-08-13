package org.lunic.ui.helperclasses;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleReadingUtilsTest {

    @Test
    void readString() {
        Scanner scannerBackup = ConsoleUtilConfiguration.SCANNER;
        ConsoleUtilConfiguration.SCANNER = new Scanner(new ByteArrayInputStream("!".getBytes()));

        String text = ConsoleReadingUtils.readString(true);
        assertNull(text);

        ConsoleUtilConfiguration.SCANNER = scannerBackup;
    }

    @Test
    void getAmount() {
        Scanner scannerBackup = ConsoleUtilConfiguration.SCANNER;
        ConsoleUtilConfiguration.SCANNER = new Scanner(new ByteArrayInputStream("5".getBytes()));

        int amount = ConsoleReadingUtils.getAmount(0, 100, false);
        assertEquals(5, amount);

        ConsoleUtilConfiguration.SCANNER = scannerBackup;
    }
}