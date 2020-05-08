package fr.almavivahealth.service.impl.patient;

import static java.util.concurrent.TimeUnit.HOURS;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import fr.almavivahealth.service.PatientImportationAttempts;

/**
 * Service Implementation is used to manage patient import attempts.
 *
 * @author christopher
 *
 */
@Service
public class PatientImportationAttemptsImpl implements PatientImportationAttempts {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientImportationAttemptsImpl.class);

	private final LoadingCache<String, Integer> attemptsCacheIp = CacheBuilder
			.newBuilder()
			.expireAfterWrite(1, HOURS)
			.build(new CacheLoader<String, Integer>() {
				@Override
				public Integer load(final String key) throws Exception {
					return 0;
				}
			});

	private final LoadingCache<String, Integer> attemptsCacheLogin = CacheBuilder
			.newBuilder()
			.expireAfterWrite(1, HOURS)
			.build(new CacheLoader<String, Integer>() {
				@Override
				public Integer load(final String key) throws Exception {
					return 0;
				}
			});

	/**
	 * Validate attempt.
	 *
	 * @param ip the ip
	 * @param pseudo the pseudo
	 */
	@Override
	public void validateAttempt(final String ip, final String pseudo) {
		LOGGER.debug("Validates the attempt for: {}", pseudo);
		attemptsCacheIp.invalidate(ip);
		attemptsCacheLogin.invalidate(pseudo);
	}

	/**
	 * Store failed attempts.
	 *
	 * @param ip the ip
	 * @param pseudo the pseudo
	 */
	@Override
	public void storeFailedAttempts(final String ip, final String pseudo) {
		LOGGER.debug("Failed attempts storage for: {}", pseudo);
		addInCache(attemptsCacheIp, ip);
		addInCache(attemptsCacheLogin, pseudo);
	}

	private void addInCache(final LoadingCache<String, Integer> cache, final String key) {
		int attemps;
		try {
			attemps = cache.get(key);
		} catch (final ExecutionException e) {
			attemps = 0;
		}
		attemps++;
		LOGGER.info("Attempts: {} {}", key, attemps);
		cache.put(key, attemps);
	}

	/**
	 * Checks for made an attempt.
	 *
	 * @param ip the ip
	 * @param pseudo the pseudo
	 * @return true, if successful
	 */
	@Override
	public boolean hasMadeAnAttempt(final String ip, final String pseudo) {
		try {
			return attemptsCacheIp.get(ip) > 0 && attemptsCacheLogin.get(pseudo) > 0;
		} catch (final ExecutionException e) {
			return false;
		}
	}

}
