package c.y.s.pangu.app.utils;

/**
 * Created by Administrator on 2016/9/10.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;



import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/6/9.
 */
public class PanguImageViewLoader {
    private static PanguImageViewLoader ourInstance = null;


    public static synchronized PanguImageViewLoader getInstance() {
        if (ourInstance == null) {
            ourInstance = new PanguImageViewLoader();
        }
        return ourInstance;
    }

    private PanguImageViewLoader() {
    }


    public void display(Context context, ImageView view, String url, int size, int loadId, int errorId) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

//        display(context,view,url);
        Picasso.with(context).load(url).config(Bitmap.Config.RGB_565).placeholder(loadId).error(errorId).into(view);
    }

    public void display(Context context, ImageView view, String url, int width,int heigth, int loadId, int errorId) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Picasso.with(context).load(url).resize(width, heigth).config(Bitmap.Config.RGB_565).placeholder(loadId).error(errorId).into(view);
    }

    public void display(Context context, ImageView view, String url, int loadId, int errorId) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Picasso.with(context).load(url).config(Bitmap.Config.RGB_565).placeholder(loadId).error(errorId).into(view);
    }

    public void display(Context context, ImageView view, String url, int loadId) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Picasso.with(context).load(url).config(Bitmap.Config.RGB_565).placeholder(loadId).error(loadId).into(view);
    }

    public void display(Context context, ImageView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Picasso.with(context).load(url).config(Bitmap.Config.RGB_565).into(view);
    }
}
