package flier.love.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import flier.love.R;
import flier.love.utils.AppUtils;

public class AboutLoveSpaceActivity extends FlierActivity {
    private Toolbar toolbar;
    private TextView mDescriptionTv;

    @Override
    protected int setContentView() {
        return R.layout.activity_about_love_space;
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
        setTitle(getString(R.string.home_menu_about));

        mDescriptionTv = (TextView) findViewById(R.id.about_description);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/xujinglei.ttf");
        mDescriptionTv.setTypeface(typeface);
    }

    @Override
    protected void initData() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_love_space, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        } else if (id == R.id.abount_contact) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.github.com/wuhenzhizao"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
