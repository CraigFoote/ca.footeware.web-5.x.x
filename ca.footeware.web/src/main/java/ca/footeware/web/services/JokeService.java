/**
 * 
 */
package ca.footeware.web.services;

import java.io.File;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Provides access to jokes.
 * 
 * @author Footeware.ca
 */
@Service
public class JokeService {

	public static final String JOKE_TITLE = "Nine o'clock";
	public static final String JOKE_BODY = "A newfie rolls into his factory job at 10:30. The floor manager comes up to him and says, \"You should have been here at nine o'clock,\" to which the newfie responds \"Why, what happened?\"";
	private DB db;
	private ConcurrentMap<String, String> map;

	/**
	 * Get the joke titles.
	 * 
	 * @return {@link Set} of {@link String}
	 */
	public Set<String> getTitles() {
		return map.keySet();
	}

	/**
	 * Get a joke by its title.
	 * 
	 * @param title
	 *            {@link String}
	 * @return {@link String} the joke body, may be null
	 */
	public String getJokeByTitle(String title) {
		return map.get(title);
	}

	/**
	 * Create a new joke using provided title and body.
	 * 
	 * @param title
	 *            {@link String}
	 * @param body
	 *            {@link String}
	 */
	public void createJoke(String title, String body) {
		map.put(title, body);
		db.commit();
	}

	/**
	 * Find a joke with provided title and delete it.
	 * 
	 * @param title
	 *            {@link String}
	 */
	public void deleteJoke(String title) {
		map.remove(title);
		db.commit();
	}

	/**
	 * Initializes the DB.
	 * 
	 * @author Footeware.ca
	 */
	@Bean
	CommandLineRunner init() {
		return args -> {
			db = DBMaker.fileDB(new File("file.db")).closeOnJvmShutdown().fileMmapEnable().concurrencyDisable()
					.fileLockDisable().make();
			map = db.hashMap("map", Serializer.STRING, Serializer.STRING).createOrOpen();
			map.put(JOKE_TITLE, JOKE_BODY);
			db.commit();
		};
	}

}