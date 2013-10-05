package com.arliss.trakker.pojo.library;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/21/13
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class TeamScore {

    String Team;
    int Score;

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

    public static TeamScore fromJson(String json){
        Gson gson = new Gson();
        TeamScore s = gson.fromJson(json, TeamScore.class);
        return s;
    }
}
