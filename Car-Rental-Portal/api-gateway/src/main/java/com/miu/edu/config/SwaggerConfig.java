package com.miu.edu.config;

import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Primary
public class SwaggerConfig implements SwaggerResourcesProvider {

    public static final String API_URI = "/v3/api-docs";

    public static final List<String> UNSUPPORT_RESOURCES = new ArrayList<>() {{
        add("API-GATEWAY");
        add("CONFIG-SERVICE");
    }};

    private final RouteDefinitionLocator routeLocator;

    public SwaggerConfig(RouteDefinitionLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    /**
     * Create Swagger resource registration details.
     * @return List
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        routeLocator.getRouteDefinitions().subscribe(
                routeDefinition -> {
                    String resourceName = routeDefinition.getUri().getAuthority();
                    String location = routeDefinition
                            .getPredicates()
                            .get(0)
                            .getArgs()
                            .get("pattern")
                            .replace("/**", API_URI);
                    resources.add(swaggerResource(resourceName, location));
                }
        );
        return resources.stream()
                .filter(resource -> !UNSUPPORT_RESOURCES.contains(resource.getName()))
                .toList();
    }

    /**
     * Swagger resource registration.
     * @param name
     * @param location
     * @return SwaggerResource
     */
    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }
}
