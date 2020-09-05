# Spring Boot Maps Demo

This is a project that uses spring-boot framework to host an app that has MVC, and uses ThymeLeaf to display the data via front end.

The root of this project is mapped to templates/home.html when running from web, and this page features a text box that allows the user to input a city or location.

If the user input is lat,lon, then it will be parsed and used directly, otherwise a rest call will be made to http://api.positionstack.com/v1/forward rest API to pull the lat/lon codes.

Then the controller will route the user to the results page, which will display the found lat/lon, an iframe which embeds a map of the location via google maps, and additionally hourly info from https://api.openweathermap.org/data/2.5/onecall rest API, using the recorded lat/lon along with ThymeLeaf parameterized layout to display full info in table.

To run this app, you can run a maven build, which will trigger the build target to launch a tomcat container with the app inside.
