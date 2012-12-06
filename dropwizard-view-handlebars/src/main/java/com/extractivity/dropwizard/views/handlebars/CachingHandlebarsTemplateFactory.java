package com.extractivity.dropwizard.views.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.HandlebarsException;
import com.github.jknack.handlebars.Template;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yammer.dropwizard.views.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: bartv
 * Date: 29-11-12
 * Time: 12:22
 */
public class CachingHandlebarsTemplateFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(CachingHandlebarsTemplateFactory.class);


    private final Class<? extends View> klass;
    private final LoadingCache<String, Template> templates;
    Handlebars handlebars;


    public CachingHandlebarsTemplateFactory(Class<? extends View> klass) {
        handlebars = new Handlebars(new HandlebarsTemplateLoader());
        this.klass = klass;
        this.templates = CacheBuilder.newBuilder().maximumSize(0).build(new CacheLoader<String, Template>() {
            @Override
            public Template load(String key) throws Exception {
                return compile(key);
            }
        });

    }

    public Template getTemplate(String name) {
        return templates.getUnchecked(name);
    }


    public Template compile(String name) {
        LOGGER.info("compiling: {}", name);
        try {
            return handlebars.compile(new URI(name));
        } catch (Exception e) {
            throw new HandlebarsException(e);
        }

    }

}
