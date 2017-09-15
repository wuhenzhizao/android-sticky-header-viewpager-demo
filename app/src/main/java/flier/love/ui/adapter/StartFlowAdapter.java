package flier.love.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import flier.love.R;
import flier.love.ui.activity.ImageDetailActivity;
import flier.love.ui.transform.ScaleToFitWidhtHeigthTransform;
import flier.love.utils.PicassoUtils;
import widget.listbuddies.lib.adapters.CircularLoopAdapter;

public class StartFlowAdapter extends CircularLoopAdapter {
    private List<String> imageUrlList = new ArrayList<>();
    private Context context;
    private int rowHeight;

    public StartFlowAdapter(Context context, int rowHeight, List<String> imageUrlList) {
        this.context = context;
        this.rowHeight = rowHeight;
        this.imageUrlList = imageUrlList;
    }

    @Override
    public String getItem(int position) {
        return imageUrlList.get(getCircularPosition(position));
    }

    @Override
    protected int getCircularCount() {
        return imageUrlList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_start_image_flow, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String imageUrl = getItem(position);
        holder.image.setTag(imageUrl);
        holder.image.setMinimumHeight(rowHeight);

        Transformation transformation = new ScaleToFitWidhtHeigthTransform(rowHeight, true);
        PicassoUtils.load(context, imageUrl, holder.image, transformation);

        return convertView;
    }

    class ViewHolder {
        ImageView image;

        public ViewHolder(View convertView) {
            image = (ImageView) convertView.findViewById(R.id.start_image_flow_imageview);
        }
    }
}
