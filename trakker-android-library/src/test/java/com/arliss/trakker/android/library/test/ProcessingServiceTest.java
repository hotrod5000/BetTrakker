package com.arliss.trakker.android.library.test;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import com.arliss.trakker.android.library.ProcessingService;
import com.arliss.trakker.pojo.interfaces.IRepository;
import com.arliss.trakker.pojo.library.Game;
import com.arliss.trakker.pojo.library.GameBetType;
import com.arliss.trakker.pojo.library.Ticket;
import com.arliss.trakker.pojo.library.TicketType;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;
import static org.mockito.Mockito.*;


/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/17/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */


@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ProcessingServiceTest {

    @Mock
    IRepository<Ticket> mockTicketRepo;

    Ticket getTestTicket(){
        Ticket t = new Ticket();
        t.setWagerAmount(110);
        t.setPayoff(100);
        t.setTicketType(TicketType.StraightBet);
        t.setDateTime(DateTime.now());
        Game g = new Game();
        g.setDateTime(DateTime.now());
        g.setTeam("Bengals");
        g.setValue(-110);
        g.setBetType(GameBetType.MoneyLine);

        return t;
    }

    @Test
    public void CallsCreateOnTicketRepository() throws Exception{

        Ticket testTicket = getTestTicket();
        MockitoAnnotations.initMocks(this);     //this should go somewhere else
        ProcessingServiceTestable sut = new ProcessingServiceTestable();
        sut.setTicket(testTicket);
        sut.setTicketRepo(mockTicketRepo);
        final ShadowContentResolver contentResolver = Robolectric.shadowOf(sut.getContentResolver());
        contentResolver.getNotifiedUris();
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///C:/Users/rodney/Dropbox/Screenshot_2013-09-01-21-44-29.png"));

        sut.onHandleIntent(i);
        verify(mockTicketRepo).create(testTicket);


    }

}
