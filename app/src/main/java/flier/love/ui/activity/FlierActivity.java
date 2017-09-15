package flier.love.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import flier.love.manager.ActivityStack;
import flier.love.utils.AppUtils;
import widget.SystemBarTintManager;
import widget.swipeback.SwipeBackActivity;

/**
 * Created by wuhenzhizao on 2014/12/11.
 */
public abstract class FlierActivity extends SwipeBackActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(setContentView(), null);
        setContentView(contentView);
        ActivityStack.getInstance().add(this);
        mContext = this;
        initUI();
        initSwipeBack();
        contentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 500);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        AppUtils.transparencyBar(getWindow());
        AppUtils.StatusBarLightMode(getWindow());
    }

    protected abstract int setContentView();

    protected abstract void initUI();

    protected abstract void initData();

    private void initSwipeBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSwipeBackEnable(false);
        }
    }

    private void initSystemTintBar() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.TRANSPARENT);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().remove(this);
    }

    protected String getTextViewValue(TextView tv) {
        return tv == null ? "" : tv.getText().toString().trim();
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int res) {
        showToast(getString(res));
    }

    // 页面切换方法，不带参数
    protected void activitySwitch(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    // 页面切换方法，带参数
    protected void activitySwitchWithBundle(Class<?> cls, Bundle options) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(options);
        startActivity(intent);
    }

    protected void activitySwitchForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(mContext, cls);
        startActivityForResult(intent, requestCode);
    }

    protected void activitySwitchForResultWithBundle(Class<?> cls, int requestCode, Bundle args) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(args);
        startActivityForResult(intent, requestCode);
    }

    // 隐藏软键盘
    protected void hideSoftInputKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View focusView = getCurrentFocus();
        if (focusView != null) {
            IBinder binder = focusView.getWindowToken();
            if (binder != null && inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(binder, 0);
            }
        }
    }

    protected FragmentTransaction getFragmentTransaction() {
        return getSupportFragmentManager().beginTransaction();
    }

}
