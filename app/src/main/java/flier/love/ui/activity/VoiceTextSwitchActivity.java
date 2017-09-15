package flier.love.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import cn.sharesdk.framework.ShareSDK;
import flier.love.R;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import flier.love.service.VoiceRecognizeParser;
import flier.love.ui.adapter.TransitionAdapter;
import flier.love.ui.view.SquareImageView;
import flier.love.utils.AppUtils;
import flier.love.utils.LogUtils;
import flier.love.utils.WindowCompatUtils;
import m.framework.utils.UIHandler;
import widget.SystemBarTintManager;

/**
 * 语音转文字页面
 *
 * Created by wuhenzhizao on 2015/3/17.
 */
public class VoiceTextSwitchActivity extends FlierActivity{
    private Toolbar toolbar;
    private ScrollView mScrollView;
    private ImageButton mRecognizeBtn;
    private EditText mContentEt;

    private SpeechRecognizer mIat;
    private RecognizerDialog mIatDialog;
    private StringBuilder contentSb;

    @Override
    protected int setContentView() {
        return R.layout.activity_voice_text_switch;
    }

    @Override
    protected void initUI() {
        mScrollView = (ScrollView) findViewById(R.id.voice_switch_scrollview);
        mScrollView.setDrawingCacheEnabled(true);
        mScrollView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        toolbar = (Toolbar) findViewById(R.id.voice_switch_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = AppUtils.getStatusBarHeight(this);
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setShowHideAnimationEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.voice_switch));

        final SquareImageView backmageImage = (SquareImageView) findViewById(R.id.voice_switch_background);
        backmageImage.setImageResource(R.mipmap.liyifeng_2);
        Bitmap bitmap = ((BitmapDrawable) backmageImage.getDrawable()).getBitmap();
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                applyPalette(palette, backmageImage);
            }
        });
        mContentEt = (EditText) findViewById(R.id.voice_switch_content_tv);
        mContentEt.addTextChangedListener(textWatcher);
        mRecognizeBtn = (ImageButton) findViewById(R.id.voice_switch_record_btn);
        mRecognizeBtn.setOnClickListener(clickListener);
    }

    @Override
    protected void onDestroy() {
        mIat.cancel();
        mIat.destroy();
        super.onDestroy();
    }

    @Override
    protected void initData() {
        UIHandler.prepare();
        ShareSDK.initSDK(this);

        contentSb = new StringBuilder();

        mIat = SpeechRecognizer.createRecognizer(this, null);
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mIat.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "string");
        // mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        mIatDialog = new RecognizerDialog(this, new InitListener() {
            @Override
            public void onInit(int i) {
                if (i != ErrorCode.SUCCESS){
                    showToast("初始化失败，错误码：" + i);
                }
            }
        });
        mIatDialog.setListener(dialogListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.equals(mRecognizeBtn)){
                mRecognizeBtn.requestFocus();
                mIatDialog.show();
            }
        }
    };

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            contentSb = new StringBuilder(getTextViewValue(mContentEt));
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    RecognizerDialogListener dialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result = recognizerResult.getResultString();
            LogUtils.d("识别结果：" + result);
            contentSb = VoiceRecognizeParser.parseRecognizedResult(contentSb, result);
            String content = contentSb.toString();
            content = content.replaceAll("下一行", "\n");
            content = content.replaceAll("下一行。", "\n");
            content = content.replaceAll("下一行？", "\n");
            content = content.replaceAll("下一行！", "\n");
            content = content.replaceAll("Enter", "\n");
            content = content.replaceAll("enter", "\n");
            content = content.replaceAll("空格", "　");
            content = content.replaceAll("空格。", "　");
            content = content.replaceAll("空格？", "　");
            content = content.replaceAll("空格！", "　");
            content = content.replaceAll("缩进", "\n　　");
            content = content.replaceAll("缩进。", "\n　　");
            content = content.replaceAll("缩进？", "\n　　");
            content = content.replaceAll("缩进！", "\n　　");
            contentSb = new StringBuilder(content);
            mContentEt.setText(content);
            mContentEt.setSelection(content.length());
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };

    private void applyPalette(Palette palette, ImageView image) {
        int primaryDark = getResources().getColor(R.color.primary_dark);
        int primary = getResources().getColor(R.color.primary);
        toolbar.setBackgroundColor(palette.getMutedColor(primary));
        WindowCompatUtils.setStatusBarcolor(getWindow(), palette.getDarkMutedColor(primaryDark));
        initScrollFade(image);
        updateBackground(findViewById(R.id.voice_switch_record_btn), palette);
        ActivityCompat.startPostponedEnterTransition(this);
    }

    private void updateBackground(View view, Palette palette) {
        int lightMutedColor = palette.getLightMutedColor(getResources().getColor(R.color.accent));
        int mutedColor = palette.getMutedColor(getResources().getColor(R.color.accent));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable ripple = (RippleDrawable) view.getBackground();
            GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
            rippleBackground.setColor(lightMutedColor);
            ripple.setColor(ColorStateList.valueOf(mutedColor));
        } else {
            StateListDrawable drawable = (StateListDrawable) view.getBackground();
            drawable.setColorFilter(mutedColor, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initScrollFade(final ImageView image) {
        final View scrollView = findViewById(R.id.voice_switch_scrollview);
        setComponentsStatus(scrollView, image);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                setComponentsStatus(scrollView, image);
            }
        });
    }

    private void setComponentsStatus(View scrollView, ImageView image) {
        int scrollY = scrollView.getScrollY();
        image.setTranslationY(-scrollY / 2);
        ColorDrawable background = (ColorDrawable) toolbar.getBackground();
        int padding = scrollView.getPaddingTop();
        double alpha = (1 - (((double) padding - (double) scrollY) / (double) padding)) * 255.0;
        alpha = alpha < 0 ? 0 : alpha;
        alpha = alpha > 255 ? 255 : alpha;

        background.setAlpha((int) alpha);

        float scrollRatio = (float) (alpha / 255f);
        int titleColor = getAlphaColor(Color.WHITE, scrollRatio);
        toolbar.setTitleTextColor(titleColor);
    }

    private int getAlphaColor(int color, float scrollRatio) {
        return Color.argb((int) (scrollRatio * 255f), Color.red(color), Color.green(color), Color.blue(color));
    }

    private void restablishActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getReturnTransition().addListener(new TransitionAdapter() {
                @Override public void onTransitionEnd(Transition transition) {
                    toolbar.setTitleTextColor(Color.WHITE);
                    toolbar.getBackground().setAlpha(255);
                }
            });
        }
    }

    @Override public void onBackPressed() {
        restablishActionBar();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voice_switch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            restablishActionBar();
            onBackPressed();
        } else if (id == R.id.voice_switch_copy){
            String content = getTextViewValue(mContentEt);
            if (TextUtils.isEmpty(content)){
                showToast("识别内容不能为空");
            } else {
                ClipboardManager cbm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cbm.setPrimaryClip(ClipData.newPlainText("FlierLoveSpace", content));
                showToast("内容已经复制到粘贴板！");
            }
        } else if (id == R.id.voice_switch_share){
            String content = getTextViewValue(mContentEt);
            String url = "http://a.hiphotos.baidu.com/image/pic/item/09fa513d269759ee65658db1b1fb43166d22df97.jpg";
            OnekeyShare onekeyShare = new OnekeyShare();
            onekeyShare.setTitle(getString(R.string.app_name));
            onekeyShare.setTitleUrl(url);
            onekeyShare.setText(content);
            onekeyShare.setUrl(url);
            onekeyShare.setImageUrl(url);
            onekeyShare.setDialogMode();
            onekeyShare.setTheme(OnekeyShareTheme.CLASSIC);
            onekeyShare.show(this);
        } else if (id == R.id.voice_switch_help){
            activitySwitch(VoiceSwitchHelpActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
}
