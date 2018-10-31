package com.study.web.servlet.templater;

import com.study.ServerMain;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "/WEB-INF/product/";

    private final static PageGenerator instance = new PageGenerator();
    private Configuration cfg;

    private PageGenerator() {
        cfg = new Configuration();
        cfg.setClassForTemplateLoading(ServerMain.class,HTML_DIR);
    }

    public static PageGenerator getInstance(){
        return instance;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }
}
