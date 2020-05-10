package fr.almavivahealth.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import fr.almavivahealth.config.ConfigurationProperties;

/**
 * Cors filter allowing cross-domain requests
 * 
 * @author christopher
 *
 */
@Component
public class CorsFilter implements Filter {

	private final ConfigurationProperties configurationProperties;

	@Autowired
	public CorsFilter(final ConfigurationProperties configurationProperties) {
		this.configurationProperties = configurationProperties;
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse response = (HttpServletResponse) res;
		final HttpServletRequest request = (HttpServletRequest) req;

		final String origin = request.getHeader(HttpHeaders.ORIGIN);

		if (configurationProperties.getCorsAllowedOrigins().contains(origin)) {
			response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS));
			response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, PATCH, GET, OPTIONS, DELETE");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization,Cache-Control,Content-Language,Content-Type,Expires,Last-Modified,Pragma,Location");
		}

		if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		chain.doFilter(req, res);
	}
}