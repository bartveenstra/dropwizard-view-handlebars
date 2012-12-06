package com.extractivity.dropwizard.views.handlebars;

import com.github.jknack.handlebars.Template;
import com.google.common.base.Charsets;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yammer.dropwizard.views.View;
import com.yammer.dropwizard.views.ViewRenderer;

import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: bartv
 * Date: 29-11-12
 * Time: 11:05
 */
public class HandlebarsViewRenderer implements ViewRenderer {
    //Cache factory per view class
    private final LoadingCache<Class<? extends View>, CachingHandlebarsTemplateFactory> factories;



    public HandlebarsViewRenderer() {
        factories = CacheBuilder.newBuilder()
                .build(new CacheLoader<Class<? extends View>, CachingHandlebarsTemplateFactory>() {
                    @Override
                    public CachingHandlebarsTemplateFactory load(Class<? extends View> key) throws Exception {
                        return new CachingHandlebarsTemplateFactory(key);
                    }
                });
    }

    @Override
    public boolean isRenderable(View view) {
        return view.getTemplateName().endsWith(".hbs") || view.getTemplateName().endsWith(".mustache");
    }

    @Override
    public void render(View view, Locale locale, OutputStream output) throws IOException, WebApplicationException {
        OutputStreamWriter writer = new OutputStreamWriter(output, Charsets.UTF_8);
        Template template = getTemplateFor(view);
        template.apply(view, writer);
        writer.close();

    }

    private Template getTemplateFor(View view) {
        return factories.getUnchecked(view.getClass()).getTemplate(view.getTemplateName());
    }
}
