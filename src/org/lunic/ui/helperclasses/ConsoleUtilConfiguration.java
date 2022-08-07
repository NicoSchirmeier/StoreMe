package org.lunic.ui.helperclasses;

import java.util.Scanner;

public class ConsoleUtilConfiguration {
    protected static final Scanner SCANNER = new Scanner(System.in).useDelimiter("\r?\n");
    protected static final int MAX_CHARS = 40;
    protected static final int MIN_CHARS = 2;
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    protected static final int SPACER_LENGTH = 50;

    public static void configure() {
        SCANNER.nextLine();
    }
}
