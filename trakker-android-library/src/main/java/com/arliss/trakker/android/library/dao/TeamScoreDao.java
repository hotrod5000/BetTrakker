package com.arliss.trakker.android.library.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 10/19/13
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable
public class TeamScoreDao {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    String Team;
    @DatabaseField
    int Score;

    public GameScoreDao getGame() {
        return game;
    }

    public void setGame(GameScoreDao game) {
        this.game = game;
    }

    @DatabaseField(canBeNull = false, foreign = true)
    GameScoreDao game;

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        this.Team = team;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        this.Score = score;
    }
}
