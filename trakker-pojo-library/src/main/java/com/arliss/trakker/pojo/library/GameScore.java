package com.arliss.trakker.pojo.library;

import com.google.gson.Gson;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/21/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameScore {

    TeamScore Team1;
    TeamScore Team2;
    String GameStatus;
    DateTime EventDate;
    String League;

    public String getLeague() {
        return League;
    }

    public void setLeague(String league) {
        this.League = league;
    }

    public TeamScore getTeam1() {
        return Team1;
    }

    public void setTeam1(TeamScore team1) {
        this.Team1 = team1;
    }

    public TeamScore getTeam2() {
        return Team2;
    }

    public void setTeam2(TeamScore team2) {
        this.Team2 = team2;
    }

    public String getGameStatus() {
        return GameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.GameStatus = gameStatus;
    }

    public DateTime getEventDate() {
        return EventDate;
    }

    public void setEventDate(DateTime eventDate) {
        this.EventDate = eventDate;
    }

    public static GameScore FromJson(String json){
        Gson gson = new Gson();
        GameScore gs = gson.fromJson(json, GameScore.class);
        return gs;
    }
}
