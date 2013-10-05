package com.arliss.trakker.android;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.arliss.trakker.android.library.Constants;
import com.arliss.trakker.pojo.interfaces.IRepository;
import com.arliss.trakker.pojo.library.Game;
import com.arliss.trakker.pojo.library.GameBetType;
import com.arliss.trakker.pojo.library.Ticket;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import roboguice.activity.RoboListActivity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/18/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicketActivity extends RoboListActivity {

    @Inject
    IRepository<Ticket> ticketRepo;


    private GameAdapter m_adapter;
    ArrayList<Game> m_games;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket);

        Intent intent = getIntent();
        Ticket t = (Ticket) intent.getSerializableExtra("Ticket");
        m_games = new ArrayList<Game>(t.getGames());
        this.m_adapter = new GameAdapter(this, R.layout.row, m_games);
        setListAdapter(this.m_adapter);

        setTicketDate(t.getDateTime());
        setTicketType(t.getTicketType().toString());
        setWagerAmount(t.getWagerAmount());
        setPayoffAmount(t.getPayoff());

        if (ticketRepo == null) {
            Log.d(Constants.Tag, "ticketRepo is null, inject is crap");
        } else {
            Log.d(Constants.Tag, "ticketRepo is NOT null, inject is sweet");
            List<Ticket> tickets = ticketRepo.getAll();
            Log.d(Constants.Tag, "get all returned " + tickets.size() + " tickets");
            int i = 1;
            for (Ticket tick : tickets) {
                Log.d(Constants.Tag, "ticket " + i++ + " : gamecount=" + tick.getGames().size());
            }

        }

    }

    private void setPayoffAmount(float payoffAmount) {
        TextView tv = (TextView) findViewById(R.id.payoffAmount);
        tv.setText(payoffAmount + "");
    }
    private void setWagerAmount(float wagerAmount) {
        TextView tv = (TextView) findViewById(R.id.wagerAmount);
        tv.setText(wagerAmount + "");
    }

    private void setTicketType(String s) {
        TextView tv = (TextView) findViewById(R.id.ticketType);
        tv.setText(s);
    }

    private void setTicketDate(DateTime dateTime) {
        TextView tv = (TextView) findViewById(R.id.ticketDate);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String timeString = fmt.print(dateTime);
        tv.setText(timeString);
    }

    private class GameAdapter extends ArrayAdapter<Game> {

        private ArrayList<Game> items;

        public GameAdapter(Context context, int textViewResourceId, ArrayList<Game> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row, null);
            }
            Game o = items.get(position);
            String formatted = formatGame(o);
            if (o != null) {
                TextView tt = (TextView) v.findViewById(R.id.toptext);
                if (tt != null) {
                    tt.setText(formatted);
                }
            }
            return v;
        }

        private String formatGame(Game o) {
            GameBetType betType = o.getBetType();
            switch (betType)
            {
                case Over:
                    return o.getTeam() + " OVER " + o.getValue();
                case Under:
                    return o.getTeam() + " UNDER " + o.getValue();
                case PointsLine:
                case MoneyLine:
                    if(o.getValue() > 0){
                        return o.getTeam() + " +" + o.getValue();
                    }
                    else{
                        return o.getTeam() + o.getValue();
                    }

            }

            return null;
        }
    }
}