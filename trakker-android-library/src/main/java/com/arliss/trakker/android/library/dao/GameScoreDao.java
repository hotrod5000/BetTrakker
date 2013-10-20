package com.arliss.trakker.android.library.dao;

import com.arliss.trakker.pojo.library.TeamScore;
import com.google.gson.Gson;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 10/19/13
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable
public class GameScoreDao {
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false, foreign = true)
    TeamScoreDao Team1;
    @DatabaseField(canBeNull = false, foreign = true)
    TeamScoreDao Team2;
    @DatabaseField
    String GameStatus;
    @DatabaseField(index=true)
    DateTime EventDate;
    @DatabaseField
    String League;

    public String getLeague() {
        return League;
    }

    public void setLeague(String league) {
        this.League = league;
    }

    public TeamScoreDao getTeam1() {
        return Team1;
    }

    public void setTeam1(TeamScoreDao team1) {
        this.Team1 = team1;
    }

    public TeamScoreDao getTeam2() {
        return Team2;
    }

    public void setTeam2(TeamScoreDao team2) {
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

    @Override
    public String toString(){
        return getTeam1().getTeam() + " " + getTeam1().getScore() + " " + getTeam2().getTeam() + " " + getTeam2().getScore();
    }
}
