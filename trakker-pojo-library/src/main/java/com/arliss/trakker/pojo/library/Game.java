package com.arliss.trakker.pojo.library;

import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/1/13
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {
    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    DateTime dateTime;
    String team;

    public GameBetType getBetType() {
        return betType;
    }

    public void setBetType(GameBetType betType) {
        this.betType = betType;
    }

    GameBetType betType;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    float value;
}
