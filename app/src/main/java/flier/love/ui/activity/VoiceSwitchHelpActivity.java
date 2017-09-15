package flier.love.ui.activity;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import flier.love.R;
import flier.love.utils.AppUtils;

public class VoiceSwitchHelpActivity extends FlierActivity {
    private Toolbar toolbar;

    @Override
    protected int setContentView() {
        return R.layout.activity_voice_switch_help;
    }

    @Override
    protected void initUI() {
        toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = AppUtils.getStatusBarHeight(this);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.voice_switch_menu_help));
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
