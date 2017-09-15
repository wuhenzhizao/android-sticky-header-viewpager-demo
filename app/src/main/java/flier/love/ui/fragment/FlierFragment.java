package flier.love.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wuhenzhizao on 2014/12/11.
 */
public abstract class FlierFragment extends Fragment {
    private View mContentView;
    protected LayoutInflater mInflater;
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = getActivity().getLayoutInflater();
        mContentView = mInflater.inflate(setContentLayout(), null);
        initUI(mContentView, savedInstanceState);
        return mContentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isAdded()) mContentView.postDelayed(
                new Runnable() { public void run() { initData(); }}, 300);
    }

    protected abstract int setContentLayout();

    protected abstract void initUI(View contentView, Bundle savedInstanceState);

    protected void initData(){}

    protected String getTextViewValue(TextView tv){
        return tv == null ? "" : tv.getText().toString().trim();
    }

    protected FragmentManager getSupportFragmentManager(){
        return ((FragmentActivity)mContext).getSupportFragmentManager();
    }

    protected void showToast(String message){
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isFinishing()){
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showToast(int res){
        if (isAdded()) showToast(getString(res));
    }

    // 隐藏软键盘
    protected void hideSoftInputKeyboard(){
        InputMethodManager inputMethodManager =
                (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusView = getActivity().getCurrentFocus();
        if (focusView != null){
            IBinder binder = focusView.getWindowToken();
            if (binder != null && inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(binder, 0);
            }
        }
    }

    // 页面切换方法，不带参数
    protected void activitySwitch(Class<?> cls){
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    // 页面切换方法，带参数
    protected void activitySwitchWithBundle(Class<?> cls, Bundle bundle){
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void activitySwitchForResult(Class<?> cls, int requestCode){
        Intent intent = new Intent(mContext, cls);
        startActivityForResult(intent, requestCode);
    }

    protected void activitySwitchForResultWithBundle(Class<?> cls, int requestCode, Bundle options){
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(options);
        ((Activity)mContext).startActivityForResult(intent, requestCode);
    }


}
