package com.fitcard.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.View;

@Configuration
@OpenAPIDefinition(
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

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://j11a405.p.ssafy.io/api/fitcard"))  // 배포 서버
                .addServersItem(new Server().url("http://j11a405.p.ssafy.io:8081"))
                .addServersItem(new Server().url("http://localhost:8080"))  // 로컬 개발 서버
                .info(new Info().title("FitCard API").description("API Documentation").version("v1.0"));
    }
}
