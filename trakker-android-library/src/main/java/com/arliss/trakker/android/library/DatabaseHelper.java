package com.arliss.trakker.android.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.arliss.trakker.android.library.dao.GameDao;
import com.arliss.trakker.android.library.dao.GameScoreDao;
import com.arliss.trakker.android.library.dao.TeamScoreDao;
import com.arliss.trakker.android.library.dao.TicketDao;
import com.arliss.trakker.pojo.library.Game;
import com.arliss.trakker.pojo.library.Ticket;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/20/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "trakker.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the Ticket table
    private RuntimeExceptionDao<TicketDao, Integer> ticketRuntimeDao = null;
    private RuntimeExceptionDao<GameDao, Integer> gameRuntimeDao = null;
    private RuntimeExceptionDao<GameScoreDao, Integer> gameScoreRuntimeDao = null;
    private RuntimeExceptionDao<TeamScoreDao, Integer> teamScoreRuntimeDao = null;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(Constants.Tag, "onCreate " + getClass().getSimpleName());
            TableUtils.createTable(connectionSource, TicketDao.class);
            TableUtils.createTable(connectionSource, GameDao.class);
            TableUtils.createTable(connectionSource, GameScoreDao.class);
            TableUtils.createTable(connectionSource, TeamScoreDao.class);
        } catch (SQLException e) {
            Log.e(Constants.Tag, "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(Constants.Tag, "onUpgrade");
            TableUtils.dropTable(connectionSource, TicketDao.class, true);
            TableUtils.dropTable(connectionSource, GameDao.class, true);
            TableUtils.dropTable(connectionSource, GameScoreDao.class, true);
            TableUtils.dropTable(connectionSource, TeamScoreDao.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(Constants.Tag, "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public RuntimeExceptionDao<TicketDao, Integer> getTicketDao() {
        if (ticketRuntimeDao == null) {
            ticketRuntimeDao = getRuntimeExceptionDao(TicketDao.class);
        }
        return ticketRuntimeDao;
    }

    public RuntimeExceptionDao<GameDao, Integer> getGameDao() {
        if (gameRuntimeDao == null) {
            gameRuntimeDao = getRuntimeExceptionDao(GameDao.class);
        }
        return gameRuntimeDao;
    }
    public RuntimeExceptionDao<GameScoreDao, Integer> getGameScoreDao() {
        if (gameScoreRuntimeDao == null) {
            gameScoreRuntimeDao = getRuntimeExceptionDao(GameScoreDao.class);
        }
        return gameScoreRuntimeDao;
    }
    public RuntimeExceptionDao<TeamScoreDao, Integer> getTeamScoreDao() {
        if (teamScoreRuntimeDao == null) {
            teamScoreRuntimeDao = getRuntimeExceptionDao(TeamScoreDao.class);
        }
        return teamScoreRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();

        ticketRuntimeDao = null;
        gameRuntimeDao = null;
    }
}
