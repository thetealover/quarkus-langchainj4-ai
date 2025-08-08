package com.thetealover.mcp.ws.adapter.out.client.weather;

import com.thetealover.mcp.ws.adapter.out.client.weather.model.WeatherDto;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Default
@RegisterRestClient(baseUri = "https://api.weatherapi.com/v1")
public interface WeatherApiV1Client {

  @POST
  @Path("/current.json")
  WeatherDto getCurrentWeather(@QueryParam("q") String location, @QueryParam("key") String apiKey);
}
