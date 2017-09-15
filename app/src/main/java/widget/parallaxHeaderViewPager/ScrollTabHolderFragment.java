package widget.parallaxHeaderViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ScrollView;

import flier.love.R;
import flier.love.ui.fragment.FlierFragment;

public abstract class ScrollTabHolderFragment extends FlierFragment
        implements ScrollTabHolder, NotifyingScrollView.OnScrollChangedListener {
	protected ScrollTabHolder mScrollTabHolder;
    protected NotifyingScrollView mScrollView;

    protected int mPosition;
    protected static final String ARG_POSITION = "position";

	public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
		mScrollTabHolder = scrollTabHolder;
	}

    @Override
    protected void initUI(View contentView, Bundle savedInstanceState) {
        mScrollView = (NotifyingScrollView) contentView.findViewById(R.id.scrollview);
        mScrollView.setOnScrollChangedListener(this);

        mPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {}

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY) {
        mScrollView.setScrollY(headerTranslationY - scrollHeight);
    }

    @Override
    public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt) {
        if (mScrollTabHolder != null)
            mScrollTabHolder.onScroll(view, l, t, oldl, oldt, mPosition);
    }
}