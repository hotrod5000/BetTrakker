package com.arliss.trakker.pojo.library;

import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/21/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameScore {

    TeamScore team1;
    TeamScore team2;
    String gameStatus;
    DateTime eventDate;


    public TeamScore getTeam1() {
        return team1;
    }

    public void setTeam1(TeamScore team1) {
        this.team1 = team1;
    }

    public TeamScore getTeam2() {
        return team2;
    }

    public void setTeam2(TeamScore team2) {
        this.team2 = team2;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public DateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(DateTime eventDate) {
        this.eventDate = eventDate;
    }
}
