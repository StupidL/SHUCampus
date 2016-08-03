package me.stupideme.shucampus.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

/**
 * Created by 56211 on 2016/8/1.
 */

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = ImageLoader.class.getName();

    @Override
    protected Bitmap doInBackground(String... urls) {
        // make network call to fetch image
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(new URL(urls[0]).openStream());
        } catch (Exception e) {
            e.printStackTrace();
            cancel(true);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        Log.i(TAG, "Image successfully downloaded.");
        if (result != null) {
            // do something with the bitmap
            int width = result.getWidth();
            int height = result.getHeight();
        }
    }

    @Override
    protected void onCancelled() {
        Log.i(TAG, "Image download not successful.");

    }
}
