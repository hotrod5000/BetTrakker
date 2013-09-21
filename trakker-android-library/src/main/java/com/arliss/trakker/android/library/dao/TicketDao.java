package com.arliss.trakker.android.library.dao;

import com.arliss.trakker.pojo.library.Game;
import com.arliss.trakker.pojo.library.TicketType;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/20/13
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable
public class TicketDao {


    public TicketDao(){
    }
    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public float getWagerAmount() {
        return wagerAmount;
    }

    public void setWagerAmount(float wagerAmount) {
        this.wagerAmount = wagerAmount;
    }

    public String getSportsBook() {
        return sportsBook;
    }

    public void setSportsBook(String sportsBook) {
        this.sportsBook = sportsBook;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }



    @DatabaseField
    DateTime dateTime;
    @DatabaseField
    float wagerAmount;
    @DatabaseField
    String sportsBook;
    @DatabaseField
    TicketType ticketType;
    @ForeignCollectionField(eager = true)
    ForeignCollection<GameDao> games;



    public float getPayoff() {
        return payoff;
    }

    public void setPayoff(float payoff) {
        this.payoff = payoff;
    }
    @DatabaseField
    float payoff;
    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
    @DatabaseField
    int gameCount;
    @DatabaseField(id=true)
    String sourceId;
    public String getSourceId() {
        return sourceId;  //To change body of created methods use File | Settings | File Templates.
    }
    public void setSourceId(String id){
        sourceId = id;
    }

}
