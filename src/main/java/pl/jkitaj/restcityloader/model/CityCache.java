package pl.jkitaj.restcityloader.model;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CityCache {

	private final static Logger logger = LoggerFactory.getLogger(CityCache.class);

	private SortedSet<City> cities;

	//we crate cache object at the begging of use CityCache object
	@PostConstruct
	public void init() {
		//TreeSet collection is not thread safe - we have to init SortedSet from TreeSet
		cities = Collections.synchronizedSortedSet(new TreeSet<City>());

		//We initialize cache by two value
		City firstCity = new City("Warszawa");
		firstCity.setLat(20.781008);
		firstCity.setLon(52.2330269);
		City secCity = new City("Kraków");
		secCity.setLat(19.8647882);
		secCity.setLon(50.0469018);

		logger.debug("Init cache by %s and %s values", firstCity, secCity);
		cities.add(firstCity);
		cities.add(secCity);
	}

	/**
	 * Method to get city from cache by name
	 * @param cityName - name of city to get from cache
	 * @return city object if exist in cache or null otherwise
	 */
	public synchronized City getCity(String cityName) {
		for (City c : cities) {
			if (cityName.equals(c.getCityName()))
				return c;
		}
		logger.warn("There isn't city with name %s in cache", cityName);
		return null;
	}

	/**
	 * Method to get all cities from cache
	 * @return set with cities ordered by city names
	 */
	public synchronized SortedSet<City> getAll() {
		return cities;
	}

	/**
	 * Method to add new city to cache or update it value
	 * @param c - object of city to add or update
	 */
	public synchronized void addCity(City c) {
		cities.add(c);
		logger.info("City %s added to cache", c);
	}

	/**
	 * Method to delete city from cache
	 * @param cityName - name of city to delete
	 */
	public synchronized void deleteCity(String cityName) {
		City toRemove = getCity(cityName);
		if (toRemove != null) {
			cities.remove(toRemove);
			logger.info("City %s removed from cache", cityName);
		}
	}

	/**
	 * Method to delete all cities from cache
	 */
	public synchronized void deleteAll() {
		cities.clear();
		logger.info("All cities removed from cache");
	}
}
