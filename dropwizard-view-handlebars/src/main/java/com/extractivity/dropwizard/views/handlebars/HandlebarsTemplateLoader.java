package com.extractivity.dropwizard.views.handlebars;

import com.github.jknack.handlebars.io.ClassTemplateLoader;

import java.io.IOException;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: bartv
 * Date: 29-11-12
 * Time: 21:13
 */
public class HandlebarsTemplateLoader extends ClassTemplateLoader {

    public HandlebarsTemplateLoader() {
        super("/");
    }

    @Override
    public String resolve(String uri) {
        return "/" + uri;
    }

    @Override
    public Reader read(String location) throws IOException {
        return super.read(location);
    }
}
