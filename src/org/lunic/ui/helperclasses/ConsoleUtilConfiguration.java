package org.lunic.ui.helperclasses;

import java.util.Scanner;

public class ConsoleUtilConfiguration {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    protected static final int MAX_CHARS = 40;
    protected static final int MIN_CHARS = 2;
    protected static final int SPACER_LENGTH = 50;
    protected static Scanner SCANNER = new Scanner(System.in).useDelimiter("\r?\n");
}
