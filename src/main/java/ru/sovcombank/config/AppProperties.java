package ru.sovcombank.config;

import ru.sovcombank.service.impl.JsonProcessor;

import java.util.Properties;

public class AppProperties {
    private static Properties instance;

    private AppProperties() {
    }

    private static void createInstance() {
        if(instance == null) {
            instance = new Properties();
        }
    }

    public static String getProperty(String propertyName) {
        createInstance();

        try {
            instance.load(JsonProcessor.class.getClassLoader().getResourceAsStream("application.yml"));
            return instance.getProperty(propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}