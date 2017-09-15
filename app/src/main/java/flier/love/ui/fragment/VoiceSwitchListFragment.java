package flier.love.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;

import flier.love.R;
import flier.love.ui.activity.VoiceTextSwitchActivity;
import widget.floatingactionbutton.fab.FloatingActionButton;
import widget.parallaxHeaderViewPager.ScrollTabHolderFragment;

/**
 * 语音转文字
 *
 * Created by wuhenzhizao on 2015/3/16.
 */
public class VoiceSwitchListFragment extends ScrollTabHolderFragment {


    public static ScrollTabHolderFragment newInstance(int position) {
        VoiceSwitchListFragment fragment = new VoiceSwitchListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_voice_text_switch;
    }

    @Override
    protected void initUI(View contentView, Bundle savedInstanceState) {
        super.initUI(contentView, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){

        }
    }
}
