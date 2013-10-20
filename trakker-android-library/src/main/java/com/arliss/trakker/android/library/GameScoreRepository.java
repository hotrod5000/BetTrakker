package com.arliss.trakker.android.library;

import android.util.Log;
import com.arliss.trakker.android.library.dao.GameScoreDao;
import com.arliss.trakker.android.library.dao.TeamScoreDao;
import com.arliss.trakker.pojo.interfaces.IRepository;
import com.arliss.trakker.pojo.library.GameScore;
import com.arliss.trakker.pojo.library.TeamScore;
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
 * Date: 10/19/13
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameScoreRepository implements IRepository<GameScore> {

    private final static Logger logger = Logger.getLogger(Constants.Tag);

    public GameScoreRepository() {
        Log.d(Constants.Tag, "GameScoreRepository constructor");
    }
    @Inject
    android.app.Application application;

    @Override
    public List<GameScore> getAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void create(GameScore item) {
        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(application.getApplicationContext(), DatabaseHelper.class);
        GameScoreDao dao = convertToGameScoreDao(item);
        RuntimeExceptionDao<TeamScoreDao,Integer> teamScoreDao = databaseHelper.getTeamScoreDao();
        RuntimeExceptionDao<GameScoreDao,Integer> gameScoreDao = databaseHelper.getGameScoreDao();
        teamScoreDao.create(dao.getTeam1());
        teamScoreDao.create(dao.getTeam2());
        gameScoreDao.create(dao);

        OpenHelperManager.releaseHelper();
    }

    private TeamScoreDao convertToTeamScoreDao(TeamScore teamScore, GameScoreDao gameScoreDao){
        TeamScoreDao dao = new TeamScoreDao();
        dao.setScore(teamScore.getScore());
        dao.setTeam(teamScore.getTeam());
        dao.setGame(gameScoreDao);
        return  dao;
    }
    private GameScoreDao convertToGameScoreDao(GameScore item) {
        GameScoreDao dao = new GameScoreDao();
        dao.setEventDate(item.getEventDate());
        dao.setGameStatus(item.getGameStatus());
        dao.setTeam1(convertToTeamScoreDao(item.getTeam1(),dao));
        dao.setTeam2(convertToTeamScoreDao(item.getTeam2(),dao));
        dao.setLeague(item.getLeague());
        return dao;
    }

    @Override
    public Boolean contains(GameScore item) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
