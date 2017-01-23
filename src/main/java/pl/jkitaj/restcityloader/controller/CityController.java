package pl.jkitaj.restcityloader.controller;

import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.LatLng;

import pl.jkitaj.restcityloader.model.City;
import pl.jkitaj.restcityloader.model.CityCache;
import pl.jkitaj.restcityloader.service.GoogleMapService;

@RestController
public class CityController {

	private final static Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private CityCache cityCache;

	@Autowired
	private GoogleMapService googleMapService;

	@RequestMapping("/cities")
	public SortedSet<City> getAllCities() {
		logger.info("Getting all cities...");
		return cityCache.getAll();
	}

	@RequestMapping("/location")
	public LatLng getCityLocation(@RequestParam("name") String name) {
		logger.info("Getting location of city with name %s...", name);
		City c = cityCache.getCity(name);
		//if we get new city (not exist in cache) we create new object
		if (c == null)
			c = new City(name);

		if (c.getLat() == null || c.getLon() == null) {
			logger.debug("Latitude or longitude is null - getting location of %s from Google Maps API...", name);
			LatLng latLon = googleMapService.getLatLon(name);
			if (latLon != null) {
				//if Google Maps API return info about city we set them to city object
				c.setLat(latLon.lat);
				c.setLon(latLon.lng);
				//we update city value in cache
				cityCache.addCity(c);
				return latLon;
			} else {
				logger.error("Problem with getting location from Google Maps API");
				//if there is a problem with Google Maps API connection we return NaN value as location
				return new LatLng(Double.NaN, Double.NaN);
			}
		} else {
			logger.debug("Latitude or longitude is not null - getting location of %s from cache...", name);
			return new LatLng(c.getLat(), c.getLon());
		}
	}

	@RequestMapping("/delete")
	public void deleteCity(@RequestParam("name") String name) {
		logger.info("Removing city with name %s from cache...", name);
		cityCache.deleteCity(name);
	}

	@RequestMapping("/deleteAll")
	public void deleteAll() {
		logger.info("Removing all cities from cache...");
		cityCache.deleteAll();
	}
}
