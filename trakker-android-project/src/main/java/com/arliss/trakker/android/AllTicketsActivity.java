package com.arliss.trakker.android;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import com.arliss.trakker.pojo.interfaces.IRepository;
import com.arliss.trakker.pojo.library.Ticket;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/21/13
 * Time: 8:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class AllTicketsActivity extends ListActivity {

    @Inject
    IRepository<Ticket> ticketRepo;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Ticket> tickets = ticketRepo.getAll();



    }
}