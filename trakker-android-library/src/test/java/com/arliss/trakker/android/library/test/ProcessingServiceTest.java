package com.arliss.trakker.android.library.test;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import com.arliss.trakker.android.library.ProcessingService;
import com.arliss.trakker.pojo.library.Game;
import com.arliss.trakker.pojo.library.GameBetType;
import com.arliss.trakker.pojo.library.Ticket;
import com.arliss.trakker.pojo.library.TicketType;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowContentResolver;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/17/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */


@RunWith(RobolectricTestRunner.class)
public class ProcessingServiceTest {

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


        ProcessingServiceTestable sut = new ProcessingServiceTestable();
        sut.setTicket(getTestTicket());
        final ShadowContentResolver contentResolver = Robolectric.shadowOf(sut.getContentResolver());
        contentResolver.getNotifiedUris();
        MockContentProvider mockProvider = new MockContentProvider();
        mockProvider.onCreate();
        ShadowContentResolver.registerProvider("com.arliss.trakker.android.library.test.MockContentProvider", mockProvider);
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///C:/Users/rodney/Dropbox/Screenshot_2013-09-01-21-44-29.png"));

        sut.onHandleIntent(i);

    }

}
