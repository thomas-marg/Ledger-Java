package com.example.ledger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestUtils {
    public static String loadJson(String fileName) throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("testdata/ledger/" + fileName)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + fileName);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}