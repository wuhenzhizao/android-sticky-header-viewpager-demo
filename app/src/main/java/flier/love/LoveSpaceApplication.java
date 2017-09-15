package flier.love;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.smssdk.SMSSDK;

/**
 * Created by wuhenzhizao on 2015/3/17.
 */
public class LoveSpaceApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化讯飞语音
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=55063f38");
        // 初始化验证码发送SDK
        SMSSDK.initSDK(this, "64f4442a36a0", "d7fb1b16f0072831e9b50c2e5e0657fb");
    }
}
