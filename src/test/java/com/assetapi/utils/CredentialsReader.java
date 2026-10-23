
package com.assetapi.utils;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class CredentialsReader {

    private static final Properties properties =
            new Properties();

    static {
        try (InputStream input =
                     CredentialsReader.class
                             .getClassLoader()
                             .getResourceAsStream("credentials.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "credentials.properties not found in src/test/resources"
                );
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load credentials.properties", e
            );
        }
    }

    public static String getCredential(String key) {
        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new RuntimeException(
                    "Credential '" + key + "' is missing"
            );
        }

        return value.trim();
    }
}
