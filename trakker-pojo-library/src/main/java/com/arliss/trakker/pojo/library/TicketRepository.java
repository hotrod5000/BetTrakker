package com.arliss.trakker.pojo.library;

import com.arliss.trakker.pojo.interfaces.IRepository;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/17/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicketRepository implements IRepository<Ticket> {
    private final static Logger logger = Logger.getLogger("AR");
    @Override
    public Ticket getAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void create(Ticket item) {
        logger.info("Write ticket to repo");
    }
}
