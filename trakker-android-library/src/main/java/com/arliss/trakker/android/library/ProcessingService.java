package com.arliss.trakker.android.library;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.arliss.trakker.android.library.Constants;
import com.arliss.trakker.pojo.interfaces.IRepository;
import com.arliss.trakker.pojo.library.Ticket;
import com.arliss.trakker.pojo.library.WilliamHillTicketParser;
import com.googlecode.tesseract.android.TessBaseAPI;
import roboguice.service.RoboIntentService;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 8/31/13
 * Time: 6:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessingService extends RoboIntentService {
    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";
    WilliamHillTicketParser parser = new WilliamHillTicketParser(Constants.Tag);
    @Inject
    IRepository<Ticket> ticketRepo;
    public ProcessingService() {
        super("ProcessingService");
    }
    Ticket ticket = null;
    public void setTicket(Ticket t){
        ticket = t;
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        String action = intent.getAction();


        // if this is from the share menu
        if (Intent.ACTION_SEND.equals(action)) {
            if (extras.containsKey(Intent.EXTRA_STREAM)) {
                try {
                    // Get resource path from intent callee
                    Uri uri = extras.getParcelable(Intent.EXTRA_STREAM);

                    // Query gallery for camera picture via
                    // Android ContentResolver interface
                    ContentResolver cr = getContentResolver();

                    InputStream is = cr.openInputStream(uri);
                    if(ticket == null){


                        byte[] data = toByteArrayUsingJava(is);

                        TessBaseAPI baseAPI = new TessBaseAPI();

                        baseAPI.init(getExternalFilesDir(null).getPath(), "eng");
                        Bitmap bmp;
                        bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        baseAPI.setImage(bmp);
                        String recognizedText = baseAPI.getUTF8Text();
                        baseAPI.end();
                        ticket = parser.parse(recognizedText);
                        Log.d(Constants.Tag, recognizedText);
                    }



                    ticketRepo.create(ticket);
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(Constants.ShareTicketIntent);
                    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    //String message = t.getWagerAmount() + " " + t.getTicketType() +" ticket received";
                    broadcastIntent.putExtra("Ticket",ticket);

                    sendBroadcast(broadcastIntent);

                } catch (Exception e) {
                    Log.e(Constants.Tag, e.toString());
                }
            }
        }

    }
    public static byte[] toByteArrayUsingJava(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int reads = is.read();
        while (reads != -1) {
            baos.write(reads);
            reads = is.read();
        }
        return baos.toByteArray();
    }
}
