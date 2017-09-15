package flier.love.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 优化后的适配器基类
 * <p/>
 * Created by wuhenzhizao on 2014/10/11.
 */
public abstract class FlierAdapter<T> extends BaseAdapter {
    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> modelList;
    protected int resource;

    public FlierAdapter(Context context, List<T> modelList) {
        this.context = context;
        this.modelList = new ArrayList<T>();
        if (modelList != null) {
            this.modelList.addAll(modelList);
        }
        inflater = LayoutInflater.from(context);
        resource = setViewResource();
    }

    public abstract int setViewResource();

    public void refreshListView(List<T> modelList) {
        this.modelList.clear();
        this.modelList.addAll(modelList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return modelList == null ? 0 : modelList.size();
    }

    @Override
    public T getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        resource = setViewResource();
        View view = null;
        if (convertView == null) {
            view = inflater.inflate(resource, null);
        } else {
            view = convertView;
        }
        T item = getItem(position);
        return bindConvertView(position, view, item);
    }

    public abstract View bindConvertView(int position, View convertView, T t);

    @SuppressWarnings("unchecked")
    public static class ViewHolder {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;

        public static <T extends View> T get(View view, int id) {
            SparseArray<View> holder = (SparseArray<View>) view.getTag();
            if (holder == null) {
                holder = new SparseArray<View>();
                view.setTag(holder);
            }
            View childView = holder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                holder.put(id, childView);
            }
            return (T) childView;
        }
    }
}
