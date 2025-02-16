package ru.sovcombank.service.impl;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import ru.sovcombank.config.AppProperties;
import ru.sovcombank.service.TemplateProcessor;
import ru.sovcombank.utils.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class JsonProcessor implements TemplateProcessor {
    private final String absoluteTemplatePath;
    private final String absoluteOutputPath;
    private final VelocityContext context;


    public JsonProcessor(String templatePath, String outputPath, String dataPath) {
        absoluteTemplatePath = AppProperties.getProperty("templatePath") + templatePath;
        absoluteOutputPath = AppProperties.getProperty("outputPath") + outputPath;
        String absoluteDataPath = AppProperties.getProperty("dataPath") + dataPath;

        context = getVelocityContext(Utils.getJsonData(absoluteDataPath));
    }

    @Override
    public void run() {
        try {
            Writer writer = new FileWriter(absoluteOutputPath, true);
            Velocity.mergeTemplate(absoluteTemplatePath, "UTF-8", context, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
