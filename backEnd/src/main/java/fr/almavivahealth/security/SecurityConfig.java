package fr.almavivahealth.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fr.almavivahealth.security.filter.CorsFilter;
import fr.almavivahealth.security.jwt.JWTConfigurer;
import fr.almavivahealth.security.jwt.TokenProvider;

/**
 * SecurityConfig for managing the security of application.
 *
 * @author christopher
 * @version 17
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final UserDetailsService userDetailsService;

	private final TokenProvider tokenProvider;

	private final CorsFilter corsFilter;

	@Autowired
	public SecurityConfig(
			final AuthenticationManagerBuilder authenticationManagerBuilder,
			final UserDetailsService userDetailsService,
			final TokenProvider tokenProvider,
			final CorsFilter corsFilter) {
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.userDetailsService = userDetailsService;
		this.tokenProvider = tokenProvider;
		this.corsFilter = corsFilter;
	}

    /**
	 * Inits the.
	 */
    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        } catch (final Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    /**
	 * Password encoder.
	 *
	 * @return the password encoder
	 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
	 * Configure.
	 *
	 * @param web the web
	 */
    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/test/**");
    }

    /**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/register/**").permitAll()
                .antMatchers("/api/orders/**").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .apply(securityConfigurerAdapter())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(securityExceptionEntryPoint());

    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    /**
	 * Authentication manager bean.
	 *
	 * @return the authentication manager
	 * @throws Exception the exception
	 */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
	 * Security exception entry point.
	 *
	 * @return the rest authentication entry point
	 */
    @Bean
    public RestAuthenticationEntryPoint securityExceptionEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }

}
