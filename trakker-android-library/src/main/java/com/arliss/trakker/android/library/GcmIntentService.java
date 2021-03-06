package com.arliss.trakker.android.library;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.arliss.trakker.pojo.interfaces.IRepository;
import com.arliss.trakker.pojo.library.GameScore;
import com.arliss.trakker.pojo.library.JodaDateTimeDeserializer;
import com.arliss.trakker.pojo.library.TeamScore;
import com.arliss.trakker.pojo.library.Ticket;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;
import roboguice.service.RoboIntentService;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 10/1/13
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends RoboIntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Inject
    IRepository<GameScore> scoreRepo;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Post notification of received message.
                processMessage(extras);
                Log.i(Constants.Tag, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
    private void sendNotification(String msg){

    }
    private void processMessage(Bundle msg){
        String json = msg.getString("Scores");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        gsonBuilder.registerTypeAdapter(DateTime.class, new JodaDateTimeDeserializer());
        Gson gson = gsonBuilder.create();
        GameScore[] ss = gson.fromJson(json, GameScore[].class);

        writeScoresToDatabase(ss);
        LogMessage(ss);

    }

    private void writeScoresToDatabase(GameScore[] scores) {

        for(GameScore score : scores){
            scoreRepo.create(score);
        }
    }

    private void LogMessage(GameScore[] scores) {
        StringBuilder builder = new StringBuilder();
        builder.append("Received score update from app server\n");
        for(GameScore score : scores){
            builder.append(score.toString() + "\n");
        }
        Log.d(Constants.Tag, builder.toString());
    }
    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
//    private void sendNotification(String msg) {
//        mNotificationManager = (NotificationManager)
//                this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, DemoActivity.class), 0);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_stat_gcm)
//                        .setContentTitle("GCM Notification")
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText(msg))
//                        .setContentText(msg);
//
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//    }
}
