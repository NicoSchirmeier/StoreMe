package org.lunic.ui.helperclasses;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFileFormat;

import static org.junit.jupiter.api.Assertions.*;

class OptionTest {

    @Test
    void getRootObject() {
        Option nullOption = new Option("Test Null Root Object Option", null);
        Option option = new Option("Test Null Root Object Option", "String");

        assertNull(nullOption.getRootObject());
        assertTrue(option.getRootObject() instanceof String);
    }

    @Test
    void hasPrintable() {
        Option printableOption = new Option("Printable", (Printable) () -> System.out.println("print"));
        Option unprintableOption = new Option("Unprintable", "");

        assertTrue(printableOption.hasPrintable());
        assertFalse(unprintableOption.hasPrintable());
    }
}