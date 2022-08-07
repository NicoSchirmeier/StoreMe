package org.lunic.ui.helperclasses;

import java.util.Scanner;

public class ConsoleUtilConfiguration {
    public static final Scanner SCANNER = new Scanner(System.in).useDelimiter("\r?\n");
    public static final int MAX_CHARS = 40;
    public static final int MIN_CHARS = 2;
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final int SPACER_LENGTH = 50;
}
