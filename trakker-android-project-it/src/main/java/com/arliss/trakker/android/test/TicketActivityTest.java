package com.arliss.trakker.android.test;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import com.arliss.trakker.android.HelloAndroidActivity;
import com.arliss.trakker.android.TicketActivity;
import com.arliss.trakker.pojo.library.Game;
import com.arliss.trakker.pojo.library.Ticket;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/20/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicketActivityTest extends ActivityInstrumentationTestCase2<TicketActivity> {
    private Instrumentation mInstrumentation;
    public TicketActivityTest() {
        super(TicketActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.mInstrumentation = getInstrumentation();
        Intent i = new Intent(mInstrumentation.getContext(), TicketActivity.class);
        Ticket t = new Ticket();
        Game g = new Game();
        g.setTeam("Reds");
        g.setValue(4.5f);

        t.addGame(g);
        t.setWagerAmount(666);
        i.putExtra("Ticket", t);
        setActivityIntent(i);
        //mActivity = this.getActivity();
    }
    public void testActivity() {
        TicketActivity activity = getActivity();
        assertNotNull(activity);
    }
    @Override
    protected void tearDown() throws Exception{
        //Thread.sleep(10000);
    }

}
