package com.arliss.trakker.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.arliss.trakker.android.library.Constants;
import com.arliss.trakker.android.library.ProcessingService;
import com.arliss.trakker.pojo.library.Ticket;


public class MyActivity extends Activity {
    private ResponseReceiver receiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        IntentFilter filter = new IntentFilter(Constants.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        String action = intent.getAction();

        Intent serviceIntent = new Intent(this,ProcessingService.class);
        serviceIntent.setAction(action);
        serviceIntent.putExtras(extras);
        Log.d(Constants.Tag,"Starting ProcessingService");
        startService(serviceIntent);
    }
    public class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.Tag, "ResponseReceiver called")     ;
            TextView result = (TextView) findViewById(R.id.textView1);
            Ticket t = (Ticket)intent.getSerializableExtra(ProcessingService.PARAM_OUT_MSG);
            String text;
            if(t == null){
                text = "Ticket is null";
            }
            else
            {
                text = t.toString();
            }
            result.setText(text);
        }
    }
    @Override
    protected void onStop()
    {
        unregisterReceiver(receiver);
        super.onStop();
    }

}

