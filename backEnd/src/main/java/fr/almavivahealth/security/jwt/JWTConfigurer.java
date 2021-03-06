package fr.almavivahealth.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JWTConfigurer to configure JWT
 *
 * @author christopher
 * @version 17
 */
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JWTConfigurer(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
	 * Configure.
	 *
	 * @param http the http
	 */
    @Override
    public void configure(final HttpSecurity http) {
        final JWTFilter customFilter = new JWTFilter(tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}