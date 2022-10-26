package com.rent.store.configs.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "service.api.info")
public class ServiceApiInfoProperties {

    private String version;

    private String title;

    private String description;

    private String termsOfServiceUrl;

    private String license;

    private String licenseUrl;

    private ApiInfoContact contact;

    public ApiInfo getApiInfo() {
        return new ApiInfo(
                title,
                description,
                version,
                termsOfServiceUrl,
                new Contact(contact.getName(), contact.getUrl(), contact.getEmail()),
                license,
                licenseUrl,
                Collections.emptyList()
        );
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiInfoContact {

        private String name;

        private String url;

        private String email;

    }

}
