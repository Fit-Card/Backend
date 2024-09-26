package com.fitcard.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.View;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "FitCard API",
                description = "FitCard API 목록입니다.",
                version = "v1.0"
        ),
        security = {@SecurityRequirement(name = "Authorization")}
)
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer",
        in = SecuritySchemeIn.HEADER,
        description = "access token"
)
public class SwaggerConfig {

    private final String[] noRequiredTokenApi = {"/auth/**", "/error", "login/oauth2/code/kakao", "/register/duplicate", "/sms/**"};
//    private final View error;

    private final OperationCustomizer operationCustomizer;

    public SwaggerConfig(OperationCustomizer operationCustomizer) {
//        this.error = error;
        this.operationCustomizer = operationCustomizer;
    }

    @Bean
    public GroupedOpenApi nonSecurityGroup(){ //jwt 토큰 불필요한 api
        return GroupedOpenApi.builder()
                .group("token 불필요 API")
                .pathsToMatch(noRequiredTokenApi)
                .addOperationCustomizer(operationCustomizer)
                .build();
    }

    @Bean
    public GroupedOpenApi securityGroup(){ //jwt 토큰 필요한 api
        return GroupedOpenApi.builder()
                .group("token 필요 API")
                .pathsToExclude(noRequiredTokenApi)
                .addOperationCustomizer(operationCustomizer)
                .build();
    }
}
