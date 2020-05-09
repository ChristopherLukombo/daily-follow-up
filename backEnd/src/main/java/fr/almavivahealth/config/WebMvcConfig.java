package fr.almavivahealth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        final LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }

//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//    	registry.addResourceHandler("swagger-ui.html")
//         .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//         .addResourceLocations("classpath:/META-INF/resources/webjars/");
//
//	    registry.addResourceHandler("/**/*")
//	        .setCachePeriod(0)
//	        .addResourceLocations("classpath:/static/")
//	        .resourceChain(true)
//	        .addResolver(new PathResourceResolver() {
//
//	            @Override
//	            protected Resource getResource(final String resourcePath,
//	                final Resource location) throws IOException {
//	                  final Resource requestedResource = location.createRelative(resourcePath);
//	                  return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
//	                : new ClassPathResource("/static/index.html");
//	            }
//
//	        });
//    }

}
