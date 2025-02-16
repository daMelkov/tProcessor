package ru.sovcombank.service;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.util.Map;

public interface TemplateProcessor {
    void run();

    default VelocityContext getVelocityContext(Map<String, Object> content) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        VelocityContext context = new VelocityContext();
        content.forEach(context::put);

        return context;
    }
}