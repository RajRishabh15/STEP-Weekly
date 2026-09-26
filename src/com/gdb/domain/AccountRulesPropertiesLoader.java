package com.gdb.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AccountRulesPropertiesLoader {

    private final Properties properties = new Properties();

    public AccountRulesPropertiesLoader(String configPath) {
        loadProperties(configPath);
    }

    // TODO: Step 2.1 - Load the key=value pairs from configPath into 'properties' with properties.load(InputStream):
    //   1. Try the classpath first: getClass().getClassLoader().getResourceAsStream(configPath).
    //   2. If that returns null and new File(configPath) exists, open it with a FileInputStream instead.
    //   3. Close the stream afterwards. Catch any exception and print a warning (leave 'properties' empty).
    private void loadProperties(String configPath) {
        if (configPath == null) {
            return;
        }
        InputStream is = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            if (classLoader != null) {
                is = classLoader.getResourceAsStream(configPath);
                if (is == null && configPath.startsWith("src/main/resources/")) {
                    is = classLoader.getResourceAsStream(configPath.substring("src/main/resources/".length()));
                }
            }
            if (is == null) {
                File file = new File(configPath);
                if (file.exists()) {
                    is = new FileInputStream(file);
                }
            }
            if (is == null && !configPath.startsWith("src/main/resources/")) {
                File file = new File("src/main/resources/" + configPath);
                if (file.exists()) {
                    is = new FileInputStream(file);
                }
            }
            if (is != null) {
                properties.load(is);
            }
        } catch (IOException e) {
            System.err.println("Warning: Failed to load properties from " + configPath + ": " + e.getMessage());
            properties.clear();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignored
                }
            }
        }
    }

    // TODO: Step 2.2 - Return the value stored for key, or defaultValue if the key is missing.
    public String getProperty(String key, String defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        return properties.getProperty(key, defaultValue);
    }

    // TODO: Step 2.2 - Parse the value for key as a double (trim it first).
    //   Return defaultValue if the key is missing or the value is not a number (NumberFormatException).
    public double getDouble(String key, double defaultValue) {
        String val = getProperty(key, null);
        if (val == null || val.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(val.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // TODO: Same as getDouble, but parse the value with Integer.parseInt.
    public int getInt(String key, int defaultValue) {
        String val = getProperty(key, null);
        if (val == null || val.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
