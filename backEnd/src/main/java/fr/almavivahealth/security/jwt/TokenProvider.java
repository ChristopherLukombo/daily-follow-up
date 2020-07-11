package fr.almavivahealth.security.jwt;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import fr.almavivahealth.config.TokenPropeties;
import fr.almavivahealth.dao.UserRepository;
import fr.almavivahealth.domain.enums.RoleName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * TokenProvider for managing configuration validity of token.
 *
 * @author christopher
 * @version 16
 */
@Component
public class TokenProvider {

	private static final String USER_ID = "user_id";

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

	private static final String USER = "USER";

    private static final String AUTHORITIES_KEY = "auth";

	private final TokenPropeties tokenPropeties;

	private final UserRepository userRepository;

    @Autowired
	public TokenProvider(final TokenPropeties tokenPropeties, final UserRepository userRepository) {
		this.tokenPropeties = tokenPropeties;
		this.userRepository = userRepository;
	}

    public String createToken(final Authentication authentication) {
    	final String authorities = authentication.getAuthorities().stream()
    			.map(GrantedAuthority::getAuthority)
    			.collect(Collectors.joining(","));

    	final long now = (new Date()).getTime();
    	final Date validity = new Date(now + this.tokenPropeties.getTokenValidityInMilliseconds());

    	final Long userId = userRepository.findOneByPseudoIgnoreCase(authentication.getName()).map(fr.almavivahealth.domain.entity.User::getId).orElse(null);

    	final Map<String, Object> claims = new HashedMap<>();
    	claims.put(USER_ID, userId);

    	return Jwts.builder()
    			.setSubject(authentication.getName())
    			.claim(AUTHORITIES_KEY, authorities)
    			.addClaims(claims)
    			.signWith(SignatureAlgorithm.HS512, this.tokenPropeties.getSecretKey())
    			.setExpiration(validity)
    			.compact();
    }

    public String createToken() {
        final long now = (new Date()).getTime();
        Date validity;
        validity = new Date(now + this.tokenPropeties.getTokenValidityInMilliseconds());

        return Jwts.builder()
                .setSubject(USER)
                .claim(AUTHORITIES_KEY, RoleName.ROLE_ADMIN.name())
                .signWith(SignatureAlgorithm.HS512, this.tokenPropeties.getSecretKey())
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(final String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(this.tokenPropeties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        final User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(final String authToken) {
        try {
            Jwts.parser().setSigningKey(this.tokenPropeties.getSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (final SignatureException e) {
            LOGGER.info("Invalid JWT signature.");
            LOGGER.trace("Invalid JWT signature trace: {}", e);
        } catch (final MalformedJwtException e) {
            LOGGER.info("Invalid JWT token.");
            LOGGER.trace("Invalid JWT token trace: {}", e);
        } catch (final ExpiredJwtException e) {
            LOGGER.info("Expired JWT token.");
            LOGGER.trace("Expired JWT token trace: {}", e);
        } catch (final UnsupportedJwtException e) {
            LOGGER.info("Unsupported JWT token.");
            LOGGER.trace("Unsupported JWT token trace: {}", e);
        } catch (final IllegalArgumentException e) {
            LOGGER.info("JWT token compact of handler are invalid.");
            LOGGER.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
