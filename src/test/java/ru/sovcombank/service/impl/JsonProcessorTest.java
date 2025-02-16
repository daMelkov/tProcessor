package ru.sovcombank.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sovcombank.config.AppProperties;
import ru.sovcombank.service.TemplateProcessor;
import ru.sovcombank.utils.Utils;

class JsonProcessorTest {
    private static final String contentTemplateFile = "template.vm";
    private static final String contentDataFile = "content.json";
    private static final String outputFile = "result.script";

    private static final String tableTemplateFile = "table.vm";
    private static final String tableDataFile = "table.json";
    private static final String tableOutputFile = "table.sql";

    private static final String outputPath = AppProperties.getProperty("outputPath");


    @BeforeEach
    public void clearOutput() {
        Utils.clearFolder(outputPath);
    }

    @Test
    public void test_process_hello_world() {
        TemplateProcessor processor = new JsonProcessor(contentTemplateFile, outputFile, contentDataFile);
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
    public void test_process_table() {

        TemplateProcessor processor = new JsonProcessor(tableTemplateFile, tableOutputFile, tableDataFile);
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

    @Test
    public void test_process_create_procedure() {
        TemplateProcessor processor = new JsonProcessor("procedure_r.vm", "read_procedure.sql", tableDataFile);
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
        String actual = Utils.getContent(outputPath + "read_procedure.sql");

        Assertions.assertEquals(expected, actual, "Ожидался иной результат для скрипта процедуры чтения.");
    }
}