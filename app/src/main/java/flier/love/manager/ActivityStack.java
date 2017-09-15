package flier.love.manager;

import java.util.ArrayList;
import java.util.List;

import flier.love.ui.activity.FlierActivity;

/**
 * Created by wuhenzhizao on 2014/10/9.
 */
public class ActivityStack {
    private static ActivityStack ourInstance = new ActivityStack();
    private static List<FlierActivity> activityList;

    public static ActivityStack getInstance() {
        return ourInstance;
    }

    private ActivityStack() {
        if (activityList == null){
            activityList = new ArrayList<FlierActivity>();
        }
    }

    public void add(FlierActivity activity){
        activityList.add(activity);
    }

    public void remove(FlierActivity activity){

        activityList.remove(activity);
    }

    public void clearActivities(){
        int activitySize = activityList.size();
        for (int i = activitySize - 1; i >= 0; i--){
            activityList.get(i).finish();
        }
        activityList.clear();
    }
}
