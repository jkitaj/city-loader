package pl.jkitaj.restcityloader.model;

public class City implements Comparable<City> {
	private String cityName;
	private Double lat;
	private Double lon;

	public City(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "City [cityName=" + cityName + ", lat=" + lat + ", lon=" + lon + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + ((lat == null) ? 0 : lat.hashCode());
		result = prime * result + ((lon == null) ? 0 : lon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		return true;
	}

	@Override
	public int compareTo(City o) {
		if (cityName == null && o.cityName != null)
			return -1;
		if (cityName == null && o.cityName == null)
			return 0;
		if (cityName != null && o.cityName == null)
			return 1;

		return cityName.compareTo(o.cityName);
	}

}
