package flier.love.ui.activity;

import android.annotation.TargetApi;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.Random;

import flier.love.R;
import flier.love.ui.adapter.HomePagerAdapter;
import flier.love.ui.view.PagerSlidingTabStrip;
import flier.love.utils.AppUtils;
import widget.kenburnsview.KenBurnsView;
import widget.parallaxHeaderViewPager.AlphaForegroundColorSpan;
import widget.parallaxHeaderViewPager.ScrollTabHolder;


public class LoveHomeActivity extends FlierActivity
        implements ScrollTabHolder, ViewPager.OnPageChangeListener, View.OnClickListener {
    private static AccelerateDecelerateInterpolator sSmoothInterpolator = new AccelerateDecelerateInterpolator();

    private View mHeader;
    private Toolbar mToolbar;
    private KenBurnsView mHeaderDyamicView;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private TextView mTitleNameTv;
    private ImageView mTitleImageView;
    private ImageView mLogoImageView;
    private ImageButton mAddVoiceSwitchBtn;


    private HomePagerAdapter mPagerAdapter;

    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();
    private TypedValue mTypedValue = new TypedValue();
    private SpannableString mSpannableString;
    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;

    private int mActionBarHeight;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;

    private int[] photos = {R.mipmap.liyifeng_1, R.mipmap.liyifeng_1, R.mipmap.liyifeng_3};

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        int i = 0;

        public void run() {
            // change images randomly
            Random ran = new Random();
            int i = ran.nextInt(photos.length);
            //set image resources
            mHeaderDyamicView.setImageResource(photos[i]);
            i++;
            if (i > photos.length - 1) {
                i = 0;
            }
            handler.postDelayed(this, 7000);  //for interval...
        }
    };

    @Override
    protected int setContentView() {
        return R.layout.activity_love_home;
    }

    @Override
    protected void initUI() {
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.home_header_min_height);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mMinHeaderHeight -= AppUtils.getStatusBarHeight(this);
        }
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.home_header_height);
        mMinHeaderTranslation = -mMinHeaderHeight + getActionBarHeight();

        mHeader = findViewById(R.id.home_header_layout);
        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mToolbar.getLayoutParams();
            params.topMargin = AppUtils.getStatusBarHeight(this);
        }
        setSupportActionBar(mToolbar);
        mTitleImageView = (ImageView) findViewById(R.id.home_title_imageview);
        mTitleNameTv = (TextView) findViewById(R.id.home_title_tv);

        mHeaderDyamicView = (KenBurnsView) findViewById(R.id.home_header_dynamic_imageview);
        mLogoImageView = (ImageView) findViewById(R.id.home_header_thumbnail);

        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.home_tabs);
        mViewPager = (ViewPager) findViewById(R.id.home_viewpager);
        mViewPager.setOffscreenPageLimit(4);
        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setTabHolderScrollingContent(this);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(this);
        ViewHelper.setAlpha(getActionBarIconView(), 0f);

        mSpannableString = new SpannableString(getString(R.string.app_name));
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(0xffffffff);

        getSupportActionBar().setBackgroundDrawable(null);
        setSwipeBackEnable(false);
        //handler.postDelayed(runnable, 7000); //for initial delay..

        mAddVoiceSwitchBtn = (ImageButton) findViewById(R.id.voice_text_switch_add_btn);
        mAddVoiceSwitchBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
//        ContactsPage contactsPage = new ContactsPage();
//        contactsPage.show(this);

//        RegisterPage registerPage = new RegisterPage();
//        registerPage.show(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mAddVoiceSwitchBtn)) {
            activitySwitchForResult(VoiceTextSwitchActivity.class, 0x10);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_love_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_menu_about) {
            activitySwitch(AboutLoveSpaceActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        currentHolder.adjustScroll((int) (mHeader.getHeight() + ViewHelper.getTranslationY(mHeader)), (int) (mHeaderHeight));

        if (position == 1) {
            mHeader.setTranslationY(mMinHeaderTranslation);
            float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
            interpolate(mLogoImageView, getActionBarIconView(), sSmoothInterpolator.getInterpolation(ratio));
            setTitleAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
        }

        mAddVoiceSwitchBtn.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int position) {

    }

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY) {

    }

    @Override
    public void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            mHeader.setTranslationY(Math.max(-view.getScrollY(), mMinHeaderTranslation));
            float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
            interpolate(mLogoImageView, getActionBarIconView(), sSmoothInterpolator.getInterpolation(ratio));
            setTitleAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
        }
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    private void interpolate(View view1, View view2, float interpolation) {
        getOnScreenRect(mRect1, view1);
        getOnScreenRect(mRect2, view2);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        float translationX = 0.5F * (interpolation * (mRect2.left + mRect2.right - mRect1.left - mRect1.right));
        float translationY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            translationY = 0.5F * (interpolation * (mRect2.top + mRect2.bottom - mRect1.top - mRect1.bottom + 2 * AppUtils.getStatusBarHeight(this)));
        } else {
            translationY = 0.5F * (interpolation * (mRect2.top + mRect2.bottom - mRect1.top - mRect1.bottom));
        }

        ViewHelper.setTranslationX(view1, translationX);
        ViewHelper.setTranslationY(view1, translationY - ViewHelper.getTranslationY(mHeader));
        ViewHelper.setTranslationY(view1, translationY - ViewHelper.getTranslationY(mHeader));
        ViewHelper.setScaleX(view1, scaleX);
        ViewHelper.setScaleY(view1, scaleY);
    }

    private RectF getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        return rect;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        } else {
            getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
        }
        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
        return mActionBarHeight;
    }

    private void setTitleAlpha(float alpha) {
        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTitleNameTv.setText(mSpannableString);
    }

    private ImageView getActionBarIconView() {
        return mTitleImageView;
    }
}
