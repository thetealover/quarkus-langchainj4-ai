package com.thetealover.mcp.ws.adapter.out.client.thesportsdb;

import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * A JAX-RS client interface for TheSportsDB API v1. This client makes HTTP calls and returns the
 * responses as String types.
 */
@Default
@RegisterRestClient(baseUri = "https://www.thesportsdb.com/api/v1/json/123")
public interface TheSportsDbApiV1Client {

  /**
   * Searches for a sports team by its name.
   *
   * @param teamName The name of the team to search for.
   * @return The API response as a String.
   */
  @GET
  @Path("/searchteams.php")
  String searchTeamByName(@QueryParam("t") String teamName);

  /**
   * Searches for a player by their name.
   *
   * @param playerName The name of the player to search for.
   * @return The API response as a String.
   */
  @GET
  @Path("/searchplayers.php")
  String searchPlayerByName(@QueryParam("p") String playerName);

  /**
   * Looks up a league by its unique ID.
   *
   * @param leagueId The unique identifier of the league.
   * @return The API response as a String.
   */
  @GET
  @Path("/lookupleague.php")
  String lookupLeagueById(@QueryParam("id") String leagueId);

  /**
   * Looks up a team by its unique ID.
   *
   * @param teamId The unique identifier of the team.
   * @return The API response as a String.
   */
  @GET
  @Path("/lookupteam.php")
  String lookupTeamById(@QueryParam("id") String teamId);

  /**
   * Looks up a player by their unique ID.
   *
   * @param playerId The unique identifier of the player.
   * @return The API response as a String.
   */
  @GET
  @Path("/lookupplayer.php")
  String lookupPlayerById(@QueryParam("id") String playerId);

  /**
   * Retrieves a list of all sports.
   *
   * @return The API response as a String.
   */
  @GET
  @Path("/all_sports.php")
  String getAllSports();

  /**
   * Retrieves a list of all countries.
   *
   * @return The API response as a String.
   */
  @GET
  @Path("/all_countries.php")
  String getAllCountries();

  /**
   * Retrieves a list of all leagues.
   *
   * @return The API response as a String.
   */
  @GET
  @Path("/all_leagues.php")
  String getAllLeagues();

  /**
   * Lookup a current league points table.
   *
   * @return The API response as a String.
   */
  @GET
  @Path("/lookuptable.php")
  String lookLeagueTable(@QueryParam("l") String leagueId);

  /**
   * Retrieves the next 5 upcoming events for a specific team.
   *
   * @param teamId The unique identifier of the team.
   * @return The API response as a String.
   */
  @GET
  @Path("/eventsnext.php")
  String getNextEventsByTeamId(@QueryParam("id") String teamId);

  /**
   * Retrieves the last 5 completed events for a specific team.
   *
   * @param teamId The unique identifier of the team.
   * @return The API response as a String.
   */
  @GET
  @Path("/eventslast.php")
  String getLastEventsByTeamId(@QueryParam("id") String teamId);

  /**
   * Searches for events by title.
   *
   * @param eventTitle The title of the event.
   * @param season The season of the event (optional).
   * @return The API response as a String.
   */
  @GET
  @Path("/searchevents.php")
  String searchEventsByTitle(@QueryParam("e") String eventTitle, @QueryParam("s") String season);
}
