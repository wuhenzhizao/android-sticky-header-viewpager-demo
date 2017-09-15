package flier.love.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import flier.love.R;
import flier.love.utils.AppUtils;
import flier.love.utils.PicassoUtils;

public class ImageDetailActivity extends FlierActivity {
    public static final String EXTRA_IMAGE = "ImageDetailActivity.EXTRA_IMAGE";

    private Toolbar toolbar;
    private ImageView mImageView;

    @Override
    protected int setContentView() {
        return R.layout.activity_image_detail;
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
        setTitle("");

        mImageView = (ImageView) findViewById(R.id.image_detail_imageview);
    }

    @Override
    protected void initData() {
        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE);
        PicassoUtils.load(this, imageUrl, mImageView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
