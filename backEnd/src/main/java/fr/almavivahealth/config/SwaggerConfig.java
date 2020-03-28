package fr.almavivahealth.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import fr.almavivahealth.security.jwt.TokenProvider;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private final TokenProvider tokenProvider;

    @Autowired
    public SwaggerConfig(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    @Profile("dev")
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("fr.almavivahealth.web.rest"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("")
                .globalOperationParameters(getParameters())
                .apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo() {
    	    return new ApiInfoBuilder()
    	            .title("Daily follow up API")
    	            .description("This API allows to manage Daily follow up.")
    	            .build();
    }

    private List<Parameter> getParameters() {
        final ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name(Constants.AUTHORIZATION)
                .modelRef(new ModelRef(Constants.STRING))
                .parameterType(Constants.HEADER)
                .defaultValue(Constants.BEARER + tokenProvider.createToken())
                .required(true)
                .build();

        final List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameterBuilder.build());
        return parameters;
    }
}
