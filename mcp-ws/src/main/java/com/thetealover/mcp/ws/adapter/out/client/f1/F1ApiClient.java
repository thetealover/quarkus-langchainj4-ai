package com.thetealover.mcp.ws.adapter.out.client.f1;

import jakarta.enterprise.inject.Default;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Default
@RegisterRestClient(baseUri = "https://f1api.dev/api")
public interface F1ApiClient {

  @GET
  @Path("/drivers/search")
  JsonObject searchForDrivers(@QueryParam("q") String query);
}
