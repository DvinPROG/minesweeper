package com.david.dvinskykh.minesweeper.console;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PatternScannerTest {

    @Test
    void read() throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PrintStream printStream = mock(PrintStream.class);
        PatternScanner patternScanner = new PatternScanner(new PipedInputStream(outputStream), printStream);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            writer.write("wqd\n");
            writer.write("1 2\n");
        }

        String result = patternScanner.read(Pattern.compile("\\d \\d"), "ERROR_MESSAGE");

        assertThat(result)
                .isEqualTo("1 2");

        verify(printStream).println("ERROR_MESSAGE");

    }
}