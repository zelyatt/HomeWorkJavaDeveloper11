package org.example;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TemplateConfig {
    private final TemplateEngine templateEngine;

    public TemplateConfig() {
        this.templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(resolver);
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public void process(String template, IContext context, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            templateEngine.process(template, context, writer);
        }
    }
}
