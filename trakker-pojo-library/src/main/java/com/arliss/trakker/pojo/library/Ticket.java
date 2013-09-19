package com.arliss.trakker.pojo.library;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 8/31/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ticket {
    public Ticket(){
        games = new ArrayList<Game>();
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

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    DateTime dateTime;
    float wagerAmount;
    String sportsBook;
    TicketType ticketType;
    List<Game> games;
    public void addGame(Game game){
        games.add(game);
    }

    public float getPayoff() {
        return payoff;
    }

    public void setPayoff(float payoff) {
        this.payoff = payoff;
    }

    float payoff;
    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    int gameCount;

}
