package abartech.mobile.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import abartech.mobile.callcenter118.G;
import android.util.Log;


public class FileDownloader {

    public static void download(final String downloadPath, final String filepath) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Log.i("LPP", "hh");
                    URL url = new URL(downloadPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);
                    connection.connect();

                    File file = new File(filepath);
                    Log.i("LPP", "hhh4");
                    if (file.exists()) {
                        Log.i("LPP", "hhh6");
                        file.delete();
                    }
                    Log.i("LPP", "" + filepath);
                    FileOutputStream outputStream = new FileOutputStream(filepath);

                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[G.DOWNLOAD_BUFFER_SIZE];
                    int len = 0;
                    int y = 0;
                    while ((len = inputStream.read(buffer)) > 0) {
                        Log.i("LPP", "" + y);
                        outputStream.write(buffer, 0, len);
                        y = y + len;

                    }

                    outputStream.close();
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


    private boolean checkDataBase() {
        File dbFile = new File(G.DB_PATH + G.DB_NAME);
        return dbFile.exists();
    }
}
