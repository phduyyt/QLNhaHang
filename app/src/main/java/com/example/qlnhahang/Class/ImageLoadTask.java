package com.example.qlnhahang.Class;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.qlnhahang.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<Uri, Void, Bitmap> {
    private ImageView imageView;

    public ImageLoadTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {
        Uri uri = uris[0];
        Bitmap bitmap = null;
        try {
            InputStream input = imageView.getContext().getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            imageView.setImageBitmap(result);
        } else {
            imageView.setImageResource(R.drawable.img); // placeholder là ảnh mặc định
        }
    }
}
