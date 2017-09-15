package flier.love.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

/**
 * Created by wuhenzhizao on 2015/3/16.
 */
public class PicassoUtils {

    public static void load(Context context, String url, ImageView imageView){
        Picasso.with(context).load(url).into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, Callback callback){
        Picasso.with(context).load(url).into(imageView, callback);
    }

    public static void load(Context context, String url, ImageView imageView, Transformation transformation){
        Picasso.with(context).load(url).transform(transformation).into(imageView);
    }
}
