package com.arliss.trakker.pojo.library;

import org.joda.money.Money;
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

    public Money getWagerAmount() {
        return wagerAmount;
    }

    public void setWagerAmount(Money wagerAmount) {
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
    Money wagerAmount;
    String sportsBook;
    TicketType ticketType;
    List<Game> games;
    public void addGame(Game game){
        games.add(game);
    }

    public Money getPayoff() {
        return payoff;
    }

    public void setPayoff(Money payoff) {
        this.payoff = payoff;
    }

    Money payoff;
    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    int gameCount;

}
