package com.david.dvinskykh.minesweeper.console;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PatternScanner {

    private final Scanner scanner;
    private final PrintStream printStream;

    public PatternScanner(InputStream inputStream, PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        scanner.useDelimiter("\n");
        this.printStream = printStream;
    }

    public String read(Pattern pattern, String errorMessage) {
        while (!scanner.hasNext(pattern)) {
            printStream.println(errorMessage);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }
}
