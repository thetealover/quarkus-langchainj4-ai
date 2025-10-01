package com.thetealover.conversation.ws.config.mcp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ToolName {
  WEATHER_TOOLS(new String[] {"get_current_weather"}),
  SPORTS_TOOLS(
      new String[] {
        "search_team_by_name",
        "search_player_by_name",
        "search_events_by_title",
        "lookup_team_by_id",
        "lookup_player_by_id",
        "lookup_league_by_id",
        "get_all_sports",
        "get_all_countries",
        "get_all_leagues",
        "lookup_league_table",
        "get_next_events_by_team_id",
        "get_last_events_by_team_id"
      }),
  ;

  private final String[] toolNames;
}
