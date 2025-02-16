package ru.sovcombank.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

public class Utils {
    public static String getContent(String fileName) {
        File file = new File(fileName);
        StringBuilder strBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                strBuilder.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strBuilder.toString();
    }

    public static Map<String, Object> getJsonData(String fileName) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();

        return gson.fromJson(getContent(fileName), type);
    }

    public static void clearFolder(String path) {
        File directory = new File(path);
        for(File file : Objects.requireNonNull(directory.listFiles())) {
            if(file.exists()) {
                file.delete();
            }
        }
    }
}
