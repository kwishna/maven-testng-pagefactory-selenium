package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertyReader {
    Logger logger = LogManager.getLogger(PropertyReader.class);
    private final Path filePath;
    private Properties properties;

    public PropertyReader(Path filePath) {
        this.filePath = filePath;
    }

    public static void loadAsSystemProperties(Path path) {
        try {
            Properties properties = new Properties();
            properties.load(Files.newInputStream(path));
            properties.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getValue(String key) {
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(Files.newInputStream(filePath));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(key);
    }

}
