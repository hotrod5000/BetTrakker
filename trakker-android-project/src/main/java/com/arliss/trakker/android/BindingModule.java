package com.arliss.trakker.android;

import com.arliss.trakker.pojo.interfaces.IRepository;
import com.arliss.trakker.pojo.library.Ticket;
import com.arliss.trakker.android.library.TicketRepository;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/17/13
 * Time: 7:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class BindingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<IRepository<Ticket>>(){}).to(TicketRepository.class);
    }

}
