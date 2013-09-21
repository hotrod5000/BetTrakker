package com.arliss.trakker.android.library.dao;

import com.arliss.trakker.pojo.library.GameBetType;
import com.arliss.trakker.pojo.library.Ticket;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/20/13
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable
public class GameDao {
    @DatabaseField(generatedId = true)
    public int id;

    public TicketDao getTicket() {
        return ticket;
    }

    public void setTicket(TicketDao ticket) {
        this.ticket = ticket;
    }

    @DatabaseField(canBeNull = false, foreign = true)
    TicketDao ticket;

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
    @DatabaseField(index=true)
    DateTime dateTime;
    @DatabaseField
    String team;

    public GameBetType getBetType() {
        return betType;
    }

    public void setBetType(GameBetType betType) {
        this.betType = betType;
    }
    @DatabaseField
    GameBetType betType;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    @DatabaseField
    float value;

    @Override
    public String toString(){
        return id + ": " + dateTime + " "        + team + " " + value;
    }
}
