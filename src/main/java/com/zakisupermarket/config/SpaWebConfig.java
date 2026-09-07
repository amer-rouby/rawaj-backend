package com.zakisupermarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

// Lets a single deployment serve the Angular frontend from the same origin
// as the API (e.g. one jar on a customer's own offline machine, no separate
// static file server, no CORS to configure). Any request that isn't a real
// static file (JS/CSS/images already built into classpath:/static/) falls
// back to index.html so Angular's client-side router can handle it - a
// browser refresh on e.g. /dashboard would otherwise 404, since there's no
// physical file at that path. Real /api/** requests never reach this: an
// actual @RestController mapping always takes priority over this resource
// handler in Spring MVC's mapping order.
@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requested = location.createRelative(resourcePath);
                        if (requested.exists() && requested.isReadable()) {
                            return requested;
                        }
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }
}
