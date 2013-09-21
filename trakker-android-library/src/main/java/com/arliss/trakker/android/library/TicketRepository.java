package com.arliss.trakker.android.library;

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
    private final static Logger logger = Logger.getLogger("AR");
    private DatabaseHelper databaseHelper = null;
    @Inject
    android.app.Application application;
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(application.getApplicationContext(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public Ticket getAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void create(Ticket item) {
        logger.info("Write ticket to repo - implemented");

        TicketDao ticketDao = convertToTicketDao(item);

        List<GameDao> games = new ArrayList<GameDao>();
        //convert to dao
        for(Game g : item.getGames()){
            games.add(ConvertToGameDao(g,ticketDao));
        }

        write(ticketDao, games);

    }
    void write(TicketDao ticket, List<GameDao> games){
        RuntimeExceptionDao<TicketDao,Integer> ticketDao = getHelper().getTicketDao();
        RuntimeExceptionDao<GameDao,Integer> gameDao = getHelper().getGameDao();
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
        dao.setSourceId("fake source id");
        dao.setPayoff(t.getPayoff());
        dao.setTicketType(t.getTicketType());
        return dao;
    }

    static GameDao ConvertToGameDao(Game g, TicketDao ticketDao){
        GameDao dao = new GameDao();
        dao.setTeam(g.getTeam());
        dao.setValue(g.getValue());
        dao.setDateTime(g.getDateTime());
        dao.setBetType(g.getBetType());
        dao.setTicket(ticketDao);
        return dao;
    }
}
