package net.rewerk.servlets.util;

import lombok.Getter;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Getter
public class ConfigLoader {
    private static ConfigLoader instance;
    private final Properties properties;

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    private ConfigLoader() {
        try {
            properties = loadProperties();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        URL resource = ConfigLoader.class.getClassLoader().getResource("config.properties");
        if (resource == null) {
            throw new RuntimeException("config.properties not found");
        }
        Path path = Paths.get(resource.getFile());
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            props.load(fis);
            return props;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Unable to load properties");
        }
    }
}
