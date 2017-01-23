package pl.jkitaj.restcityloader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

@Service
public class GoogleMapService {

	private final static Logger logger = LoggerFactory.getLogger(GoogleMapService.class);

	private static final String API_KEY = "AIzaSyBmjHNExdcmRZ-ol5TJiPh0h7j2Uo5SwIQ";

	private GeoApiContext context;

	/**
	 * Method to initialize Google Maps API Context, used to connect with API
	 */
	public GoogleMapService() {
		context = new GeoApiContext().setApiKey(API_KEY);
	}

	/**
	 * Method to get latitude and longitude object for city from Google Maps API
	 * 
	 * @param cityName
	 *            - name of city to getting latitude and longitude info
	 * @return LatLng API object with latitude and longitude information of city
	 *         or null value if there is some error with connection
	 */
	public LatLng getLatLon(String cityName) {
		try {
			logger.info("Getting latitude and longitude for city %s from Google Maps...", cityName);
			// connecting with Google Maps API and waiting for result
			GeocodingResult[] results = GeocodingApi.geocode(context, cityName).await();
			if (results.length > 0) {
				logger.debug("Google Maps API result: %s", results[0]);
				return results[0].geometry.location;
			}
			return null;
		} catch (Exception e) {
			logger.error("Error while connection to Google Maps API", e);
			return null;
		}
	}
}
