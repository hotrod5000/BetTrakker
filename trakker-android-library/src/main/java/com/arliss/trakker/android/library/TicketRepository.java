package com.arliss.trakker.android.library;

import android.app.Application;
import android.util.Log;
import com.arliss.trakker.android.library.dao.GameDao;
import com.arliss.trakker.android.library.dao.TicketDao;
import com.arliss.trakker.pojo.interfaces.IRepository;
import com.arliss.trakker.pojo.library.Game;
import com.arliss.trakker.pojo.library.Ticket;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/20/13
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicketRepository implements IRepository<Ticket> {
    private final static Logger logger = Logger.getLogger(Constants.Tag);

    public TicketRepository() {
        Log.d(Constants.Tag,"TicketRepository constructor");
    }

    @Inject
    android.app.Application application;

    @Override
    public List<Ticket> getAll() {
        List<Ticket> returnList = new ArrayList<Ticket>();
        try{

            DatabaseHelper databaseHelper = OpenHelperManager.getHelper(application.getApplicationContext(), DatabaseHelper.class);

            // get our dao
            RuntimeExceptionDao<TicketDao, Integer> simpleDao = databaseHelper.getTicketDao();
            // query for all of the data objects in the database
            List<TicketDao> list = simpleDao.queryForAll();
            //convert to model objects

            for(TicketDao ticketDao : list){
                returnList.add(convertFromTicketDao(ticketDao));
            }
        }
        finally {
            OpenHelperManager.releaseHelper();
            return returnList;
        }


    }

    @Override
    public void create(Ticket item) {

        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(application.getApplicationContext(), DatabaseHelper.class);

        TicketDao ticketDao = convertToTicketDao(item);

        List<GameDao> games = new ArrayList<GameDao>();
        //convert to dao
        for(Game g : item.getGames()){
            games.add(convertToGameDao(g, ticketDao));
        }

        write(ticketDao, games,databaseHelper);
        OpenHelperManager.releaseHelper();

    }

    @Override
    public Boolean contains(Ticket item) {


        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(application.getApplicationContext(), DatabaseHelper.class);
        try
        {
            RuntimeExceptionDao<TicketDao,Integer> ticketDao = databaseHelper.getTicketDao();
            TicketDao tDao = convertToTicketDao(item);
            TicketDao existingTicket = ticketDao.queryForSameId(tDao);
            if(existingTicket != null){
                Log.d(Constants.Tag, "Ticket with id " + item.getSourceId() + " already exists");
                return true;
            }
            else{
                return false;
            }

        }
        finally {
            OpenHelperManager.releaseHelper();
        }
    }

    static void write(TicketDao ticket, List<GameDao> games, DatabaseHelper helper){
        RuntimeExceptionDao<TicketDao,Integer> ticketDao = helper.getTicketDao();
        RuntimeExceptionDao<GameDao,Integer> gameDao = helper.getGameDao();
        //check if this ticket is already stored
        //Ticket existingTicket = ticketDao.queryForSameId(item);
//        if(existingTicket != null){
//            Log.d(Constants.Tag, "Ticket with id " + item.getSourceId() + " already exists");
//            return;
//        }
        ticketDao.create(ticket);

        for (GameDao g : games)
        {
            gameDao.create(g);
        }
    }
    static TicketDao convertToTicketDao(Ticket t){
        TicketDao dao = new TicketDao();
        dao.setDateTime(t.getDateTime());
        dao.setWagerAmount(t.getWagerAmount());
        dao.setSportsBook(t.getSportsBook());
        dao.setSourceId(t.getSourceId());
        dao.setPayoff(t.getPayoff());
        dao.setTicketType(t.getTicketType());
        return dao;
    }

    static GameDao convertToGameDao(Game g, TicketDao ticketDao){
        GameDao dao = new GameDao();
        dao.setTeam(g.getTeam());
        dao.setValue(g.getValue());
        dao.setDateTime(g.getDateTime());
        dao.setBetType(g.getBetType());
        dao.setTicket(ticketDao);
        return dao;
    }
    static Ticket convertFromTicketDao(TicketDao ticketDao){
        Ticket ticket = new Ticket();
        ticket.setDateTime(ticketDao.getDateTime());
        ticket.setTicketType(ticketDao.getTicketType());
        ticket.setPayoff(ticketDao.getPayoff());
        ticket.setSportsBook(ticketDao.getSportsBook());
        ticket.setWagerAmount(ticketDao.getWagerAmount());
        ticket.setSourceId(ticketDao.getSourceId());
        for(GameDao g : ticketDao.getGames()){
            ticket.addGame(convertFromGameDao(g));
        }
        return ticket;
    }
    static Game convertFromGameDao(GameDao gameDao){
        Game game = new Game();
        game.setBetType(gameDao.getBetType());
        game.setTeam(gameDao.getTeam());
        game.setDateTime(gameDao.getDateTime());
        game.setValue(gameDao.getValue());
        return game;
    }
}
