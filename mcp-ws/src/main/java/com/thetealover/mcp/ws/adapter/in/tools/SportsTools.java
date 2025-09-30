package com.thetealover.mcp.ws.adapter.in.tools;

import com.thetealover.mcp.ws.adapter.out.client.thesportsdb.TheSportsDbApiV1Client;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class SportsTools {
  @Inject @RestClient TheSportsDbApiV1Client theSportsDbClient;

  @Tool(
      name = "search_team_by_name",
      description = "Searches for a sports team by its name and returns detailed information.")
  public String searchTeamByName(
      @ToolArg(
              name = "team_name",
              description =
                  "The name of the sports team to search for, e.g., 'Arsenal', 'Los Angeles Lakers'.")
          final String teamName) {
    log.info("Tool call: Searching for team with name: {}", teamName);

    return theSportsDbClient.searchTeamByName(teamName);
  }

  @Tool(name = "search_player_by_name", description = "Searches for a sports player by their name.")
  public String searchPlayerByName(
      @ToolArg(
              name = "player_name",
              description = "The name of the player to search for, e.g., 'Lionel Messi'.")
          final String playerName) {
    log.info("Tool call: Searching for player with name: {}", playerName);

    return theSportsDbClient.searchPlayerByName(playerName);
  }

  @Tool(
      name = "search_events_by_title",
      description = "Searches for sports events by title, with an optional season filter.")
  public String searchEventsByTitle(
      @ToolArg(
              name = "event_title",
              description = "The title of the event to search for, e.g., 'Arsenal vs Chelsea'.")
          final String eventTitle,
      @ToolArg(
              name = "season",
              description =
                  "The season of the event (e.g., '2024-2025'). This parameter is optional.",
              required = false)
          final String season) {
    log.info("Tool call: Searching for event with title: {} and season: {}", eventTitle, season);

    return theSportsDbClient.searchEventsByTitle(eventTitle, season);
  }

  @Tool(
      name = "lookup_team_by_id",
      description = "Looks up a specific team by its unique TheSportsDB identifier.")
  public String lookupTeamById(
      @ToolArg(
              name = "team_id",
              description = "The unique ID of the team, e.g., '133604' for Arsenal.")
          final String teamId) {
    log.info("Tool call: Looking up team with ID: {}", teamId);

    return theSportsDbClient.lookupTeamById(teamId);
  }

  @Tool(
      name = "lookup_player_by_id",
      description = "Looks up a specific player by their unique TheSportsDB identifier.")
  public String lookupPlayerById(
      @ToolArg(
              name = "player_id",
              description = "The unique ID of the player, e.g., '34145937' for Mario Balotelli.")
          final String playerId) {
    log.info("Tool call: Looking up player with ID: {}", playerId);

    return theSportsDbClient.lookupPlayerById(playerId);
  }

  @Tool(
      name = "lookup_league_by_id",
      description = "Looks up a specific league by its unique TheSportsDB identifier.")
  public String lookupLeagueById(
      @ToolArg(
              name = "league_id",
              description =
                  "The unique ID of the league, e.g., '4328' for the English Premier League.")
          final String leagueId) {
    log.info("Tool call: Looking up league with ID: {}", leagueId);

    return theSportsDbClient.lookupLeagueById(leagueId);
  }

  @Tool(name = "get_all_sports", description = "Retrieves a list of all available sports.")
  public String getAllSports() {
    log.info("Tool call: Retrieving all sports.");

    return theSportsDbClient.getAllSports();
  }

  @Tool(name = "get_all_countries", description = "Retrieves a list of all available countries.")
  public String getAllCountries() {
    log.info("Tool call: Retrieving all countries.");

    return theSportsDbClient.getAllCountries();
  }

  @Tool(name = "get_all_leagues", description = "Retrieves a list of all available leagues.")
  public String getAllLeagues() {
    log.info("Tool call: Retrieving all leagues.");

    return theSportsDbClient.getAllLeagues();
  }

  @Tool(
      name = "lookup_league_table",
      description = "Looks up a current league points table using its ID.")
  public String lookupLeagueTable(
      @ToolArg(name = "league_id", description = "The ID of the league.") final String leagueId) {
    log.info("Tool call: Retrieving league table for league ID: {}", leagueId);

    return theSportsDbClient.lookLeagueTable(leagueId);
  }

  @Tool(
      name = "get_next_events_by_team_id",
      description = "Retrieves the next 5 upcoming events for a specific team by their ID.")
  public String getNextEventsByTeamId(
      @ToolArg(name = "team_id", description = "The unique ID of the team.") final String teamId) {
    log.info("Tool call: Retrieving next events for team ID: {}", teamId);

    return theSportsDbClient.getNextEventsByTeamId(teamId);
  }

  @Tool(
      name = "get_last_events_by_team_id",
      description = "Retrieves the last 5 completed events for a specific team by their ID.")
  public String getLastEventsByTeamId(
      @ToolArg(name = "team_id", description = "The unique ID of the team.") final String teamId) {
    log.info("Tool call: Retrieving last events for team ID: {}", teamId);

    return theSportsDbClient.getLastEventsByTeamId(teamId);
  }
}
