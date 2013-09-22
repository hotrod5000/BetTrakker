package com.arliss.trakker.android;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;
import com.arliss.trakker.android.library.Constants;


import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 8/30/13
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */


public class MyApplication extends Application {
        @Override
        public void onCreate()
        {
            super.onCreate();

            copyAssets();

        }


    private void copyAssets() {
        String tessDataFolder = "tessdata";
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list(tessDataFolder);
        } catch (IOException e) {
            Log.e(Constants.Tag, "Failed to get asset file list.", e);
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                String n = getExternalFilesDir(null).getPath();
                Log.d(Constants.Tag,"getExternalFilesDir = " + n);
                in = assetManager.open(tessDataFolder + "/" + filename);
                new File(getExternalFilesDir(null).getPath() +"/" + tessDataFolder).mkdirs();
                File outFile = new File(getExternalFilesDir(null).getPath() +"/" + tessDataFolder, filename);
                if(outFile.exists()){
                    Log.d(Constants.Tag, "tessdata file already exists at " + outFile.getAbsolutePath());
                }
                else
                {
                    Log.d(Constants.Tag,"copying tessdata file to " + outFile.getAbsolutePath());
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);
                    out.flush();
                    out.close();
                }
                in.close();
            } catch(Exception e) {
                Log.e(Constants.Tag, "Failed to copy asset file: " + filename, e);
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
