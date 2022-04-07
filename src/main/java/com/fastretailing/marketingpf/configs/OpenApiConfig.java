package com.fastretailing.marketingpf.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(new Info().title("FR Marketing Platform API").version("v0.0.1"));
    }

    @Bean
    public OpenApiCustomiser customGlobalErrorResponseOpenApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

            }));
        };
    }

    @Bean
    public OperationCustomizer addCustomGlobalHeader() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiResponses apiResponses = operation.getResponses();
            apiResponses.addApiResponse("400", new ApiResponse().description("Bad request"));
            apiResponses.addApiResponse("401", new ApiResponse().description("Unauthorized"));
            apiResponses.addApiResponse("403", new ApiResponse().description("Forbidden"));
            apiResponses.addApiResponse("404", new ApiResponse().description("Not found"));
            apiResponses.addApiResponse("500", new ApiResponse().description("Internal server error"));
            operation.setResponses(apiResponses);
            return operation;
        };
    }
}
