# city-loader
Simple REST API to get city names and his location from manually implemented cache.

## Installing and deployment

Build `.war` package by Maven:

```
mvn clean package
```
and then copy prepared `city-loader.war` file to server (for example to Tomcat `webapp` directory).



##REST API endpoints

- **/city-loader/cities** getting all cities and location from cache ordered by city name

- **/city-loader/location?name=city_name** get specific city location, if specific city not exist in cache or have null latitude or longitude, than application connect to Google Maps API and getting this info from there 

- **/city-loader/delete?name=city_name** delete value from cache by city name

- **/city-loader/deleteAll** clear whole cache


kacper to nie mistrz
