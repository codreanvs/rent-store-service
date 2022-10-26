package com.rent.store.configs;

import com.rent.store.configs.properties.ServiceApiInfoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private final ServiceApiInfoProperties serviceApiInfoProperties;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(serviceApiInfoProperties.getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
            final Environment environment,
            final CorsEndpointProperties corsEndpointProperties,
            final EndpointMediaTypes endpointMediaTypes,
            final WebEndpointProperties webEndpointProperties
    ) {
        final String basePath = webEndpointProperties.getBasePath();
        final boolean shouldRegisterLinksMapping = webEndpointProperties.getDiscovery().isEnabled()
                && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));

        return new WebMvcEndpointHandlerMapping(
                new EndpointMapping(basePath),
                Collections.emptyList(),
                endpointMediaTypes,
                corsEndpointProperties.toCorsConfiguration(),
                new EndpointLinksResolver(Collections.emptyList(), basePath),
                shouldRegisterLinksMapping,
                null
        );
    }

}
