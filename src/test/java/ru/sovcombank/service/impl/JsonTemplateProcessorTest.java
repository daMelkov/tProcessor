package ru.sovcombank.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sovcombank.config.AppProperties;
import ru.sovcombank.service.TemplateProcessor;
import ru.sovcombank.utils.Utils;

import java.io.File;
import java.util.Objects;

class JsonTemplateProcessorTest {
    private static final String contentTemplateFile = "template.vm";
    private static final String contentDataFile = "content.json";
    private static final String outputFile = "result.script";

    private static final String tableTemplateFile = "table.vm";
    private static final String tableDataFile = "table.json";
    private static final String tableOutputFile = "table.sql";

    private static final String outputPath = AppProperties.getProperty("outputPath");


    @BeforeEach
    public void clearOutput() {
        File directory = new File(outputPath);
        for(File file : Objects.requireNonNull(directory.listFiles())) {
            if(file.exists()) {
                file.delete();
            }
        }
    }

    @Test
    public void text_process_hello_world() {
        TemplateProcessor processor = new JsonTemplateProcessor(contentTemplateFile, outputFile, contentDataFile);
        processor.run();

        String expected = """
                public class VelocityExample {
                    public static void main(String[] args) {
                        System.out.println("Hello World!");
                    }
                }
                """;
        String actual = Utils.getContent(outputPath + outputFile);

        Assertions.assertEquals(expected, actual, "Ожидался иной результат формирования документа.");

    }

    @Test
    public void text_process_table() {

        TemplateProcessor processor = new JsonTemplateProcessor(tableTemplateFile, tableOutputFile, tableDataFile);
        processor.run();

        String expected = """
                DROP TABLE IF EXISTS table_schema.table_name;
                
                CREATE TABLE table_schema.table_name
                (
                    id serial,
                    name varchar(100),
                    date_create timestamptz,
                    CONSTRAINT PK_table_schema_table_name_0 PRIMARY KEY
                );
                """;
        String actual = Utils.getContent(outputPath + tableOutputFile);

        Assertions.assertEquals(expected, actual, "Ожидался иной результат формирования документа.");
    }
}