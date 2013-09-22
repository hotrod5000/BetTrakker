package com.arliss.trakker.pojo.library;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 8/31/13
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class WilliamHillTicketParser {

    static final String SPORTSBOOK = "William Hill";
    static Logger logger;
    public WilliamHillTicketParser(String loggerName){
        logger = Logger.getLogger(loggerName);
    }
    public Ticket parse(String input){
        logger.config("parsing raw input into Ticket object");
        Ticket t;
        if(input.contains("Ticket Info"))
        {
            t = handleTicketInfo(input);
        }
        else
        {
            t = handleConfirmationScreen(input);
        }
        t.setSportsBook(SPORTSBOOK);
        return t;

    }

    private Ticket handleConfirmationScreen(String input) {
        Ticket ticket = new Ticket();
        ticket.setDateTime(DateTime.now());
        String[] lines = input.split("\n");

        for(int i=0;i<lines.length;i++)
        {
            String line = lines[i];
            String trimmed = line.trim();
            if(trimmed.startsWith("€") ||
               trimmed.startsWith("£") ||
               trimmed.startsWith("$"))
            {
                ProcessTicketType(ticket, trimmed);

            }
            else if(trimmed.startsWith("Payoff:"))
            {
                String amount = line.split(":")[1].trim();
                Money payoff = Money.parse(GetCurrencyCodeFromSymbol(amount.charAt(0)) + " " + amount.substring(1));
                ticket.setPayoff(payoff.getAmount().floatValue());
            }
            else if(trimmed.contains("Cost:"))
            {
                String amount = line.split(":")[1].trim();
                Money wager = Money.parse(GetCurrencyCodeFromSymbol(amount.charAt(0)) + " " + amount.substring(1));
                ticket.setWagerAmount(wager.getAmount().floatValue());

            }
            else if(trimmed.split(" ")[0].matches("[0-9O]{1,2}-[a-zA-Z]{3}-[0-9]{4}")) //consider this a date and therefore a game
            {
                String gameLine = trimmed;
                //if the next line does not start with a date match, append it onto this line
                if(!lines[i+1].trim().startsWith("Cost") &&
                   !lines[i+1].trim().split(" ")[0].matches("[0-9]{1,2}-[a-zA-Z]{3}-[0-9]{4}"))
                {
                    gameLine = gameLine + " " + lines[i+1].trim();
                }
                Game g = ProcessGame(gameLine);
                ticket.addGame(g);
            }
            else if(trimmed.startsWith("Ticket"))
            {
                String[] split = trimmed.split(":");
                String id = split[split.length-1].trim();
                ticket.setSourceId(id);
            }

        }
        return ticket;
    }

    private Game ProcessGame(String gameLine) {
        //08-SEP-2013 BI: 454 BILLS -I10 UNDER 49.5
        //O5-SEP-2013 BI: 451 RAVENS -I10 POINT LINE 9.0
        //08-SEP-2013 BI: 455 TITANS 260 MONEY LINE
        Game g = new Game();

        String[] split = gameLine.split(" ");
        String date = split[0];
        //because OCR sometimes returns letter O for number 0, do a replace
        date = date.replace('O','0');

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-yyyy");
        DateTime dt = formatter.parseDateTime(date);
        g.setDateTime(dt);
        g.setTeam(split[3]);
        if(gameLine.contains("MONEY"))
        {
            g.setBetType(GameBetType.MoneyLine);
            String val = split[4];
            g.setValue(Float.parseFloat(val));

        }
        else if(gameLine.contains("OVER"))
        {
            g.setBetType(GameBetType.Over);
            String val = split[split.length-1];
            g.setValue(Float.parseFloat(val));
        }
        else if(gameLine.contains("UNDER"))
        {
            g.setBetType(GameBetType.Under);
            String val = split[split.length-1];
            g.setValue(Float.parseFloat(val));
        }
        else if(gameLine.contains("POINT"))
        {
            g.setBetType(GameBetType.PointsLine);
            String val = split[split.length-1];
            g.setValue(Float.parseFloat(val));
        }
        return g;

    }

    String GetCurrencyCodeFromSymbol(char symbol)
    {
        switch (symbol)
        {
//            case '€':
//                return "EUR";
//            case '£':
//                return "GBP";
            case '$':
                return "USD";
            default:
                return "???";
        }
    }
    private void ProcessTicketType(Ticket ticket, String line) {

        String[] split = line.split(" ");

        if(line.contains("traight"))
        {
            ticket.setTicketType(TicketType.StraightBet);
        }
        else if(line.contains("arlay"))
        {
            ticket.setTicketType(TicketType.Parlay);
            ticket.setGameCount(Integer.parseInt(split[1]));
        }

    }

    private Ticket handleTicketInfo(String input) {
        Ticket ticket = new Ticket();
        ticket.setDateTime(DateTime.now());
        if(input.contains("Type: PARL"))
        {
            ticket.setTicketType(TicketType.Parlay);
        }
        else
        {
            ticket.setTicketType(TicketType.StraightBet);
        }
        String[] lines = input.split("\n");


        for(int i=0;i<lines.length;i++)
        {
            String line = lines[i];
            String trimmed = line.trim();
            if(trimmed.startsWith("Date:"))
            {
                ticket.setDateTime(processDateTime(trimmed));
            }
            else if(trimmed.startsWith("Bet Cost:"))
            {
                ticket.setWagerAmount(processWagerAmount(trimmed).getAmount().floatValue());
            }
            else if(trimmed.contains("Bet Descriptions:"))
            {
                List<Game> games = ProcessGames(Arrays.copyOfRange(lines,i+1,lines.length-1));
                ticket.setGames(games);
            }


        }
        return ticket;
    }

    private List<Game> ProcessGames(String[] lines) {
        List<Game> games = new ArrayList<Game>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMMyy");
        for(int i=0; i<lines.length;i++)
        {
            String trimmed = lines[i].trim();
            String prefix = Integer.toString(i+1) + ".";
            if(trimmed.startsWith(prefix))
            {
                String game = trimmed.replaceFirst(prefix, "");
                String[] split = game.split(" ");
                //date is at index 1
                Game gameObject = new Game();
                DateTime dt = formatter.parseDateTime(split[1]);
                gameObject.setDateTime(dt);
                //team is at index 2 (there are spaces in team name, e.g. "TB RAYS", so we'll just get "TB" in that case
                gameObject.setTeam(split[2]);
                //





            }
            else
                break;
        }

        return games;
    }




    private Money processWagerAmount(String line) {
        String amount = line.split(":")[1].trim();
        String currencyCode = "???";
        if(amount.startsWith("$")) //USD
        {
            currencyCode = "USD";
        }
        else if(amount.startsWith("€"))  //EUR
        {
            currencyCode = "EUR";
        }
        else if(amount.startsWith("£")) //GBP
        {
            currencyCode = "GBP";
        }

        Money m = Money.parse(currencyCode + " " + amount.substring(1,amount.length()-1));
        return m;
    }

    private  DateTime processDateTime(String line) {
        DateTime dt = null;
        try
        {
            String date = line.split(":")[1].trim();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMMyy");
            dt = formatter.parseDateTime(date);
        }
        catch(Exception e)
        {
            logger.config("Exception parsing date from ticket");
        }
        return dt;
    }
}
