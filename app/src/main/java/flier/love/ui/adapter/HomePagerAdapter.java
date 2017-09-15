package flier.love.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import flier.love.ui.fragment.StarImageFlowFragment;
import flier.love.ui.fragment.VoiceSwitchListFragment;
import widget.parallaxHeaderViewPager.ScrollTabHolder;
import widget.parallaxHeaderViewPager.ScrollTabHolderFragment;

/**
 * Created by wuhenzhizao on 2015/3/16.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    private ScrollTabHolder mListener;
    private String[] TITLES;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        mScrollTabHolders = new SparseArrayCompat<>();
        TITLES = new String[]{"语音录入", "我的男神"};
    }

    public void setTabHolderScrollingContent(ScrollTabHolder listener) {
        mListener = listener;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        ScrollTabHolderFragment fragment = null;
        switch (position){
            case 0:
                fragment = VoiceSwitchListFragment.newInstance(position);
                break;
            case 1:
                fragment = StarImageFlowFragment.newInstance(position);
                break;
        }
        mScrollTabHolders.put(position, fragment);
        if (mListener != null) {
            fragment.setScrollTabHolder(mListener);
        }
        return fragment;
    }

    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }

}
