package flier.love.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import flier.love.R;
import flier.love.ui.activity.ImageDetailActivity;
import flier.love.ui.adapter.StartFlowAdapter;
import widget.listbuddies.lib.views.ListBuddiesLayout;
import widget.listbuddies.lib.views.containers.FrameLayoutFeedback;
import widget.parallaxHeaderViewPager.ScrollTabHolderFragment;

/**
 * 明星流
 *
 * Created by wuhenzhizao on 2015/3/16.
 */
public class StarImageFlowFragment extends ScrollTabHolderFragment {
    private ListBuddiesLayout mListBuddiesLayout;

    private StartFlowAdapter mLeftAdapter;
    private StartFlowAdapter mRightAdapter;

    private List<String> leftImageUrlList;
    private List<String> rightImageUrlList;


    public static ScrollTabHolderFragment newInstance(int position) {
        StarImageFlowFragment fragment = new StarImageFlowFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_start_image_flow;
    }

    @Override
    protected void initUI(View contentView, Bundle savedInstanceState) {
        super.initUI(contentView, savedInstanceState);

        Resources resources = getResources();
        int smallHeight = resources.getDimensionPixelSize(R.dimen.item_height_small);
        int tallHeight = resources.getDimensionPixelSize(R.dimen.item_height_tall);

        leftImageUrlList = new ArrayList<>();
        rightImageUrlList = new ArrayList<>();
        leftImageUrlList.add("http://a.hiphotos.baidu.com/image/pic/item/09fa513d269759ee65658db1b1fb43166d22df97.jpg");
        leftImageUrlList.add("http://d.hiphotos.baidu.com/image/pic/item/c2cec3fdfc039245257903758494a4c27c1e25f8.jpg");
        leftImageUrlList.add("http://g.hiphotos.baidu.com/image/pic/item/d53f8794a4c27d1e2495d7be18d5ad6eddc4381d.jpg");
        leftImageUrlList.add("http://d.hiphotos.baidu.com/image/pic/item/9a504fc2d56285354e8d3e5b93ef76c6a7ef635f.jpg");
        leftImageUrlList.add("http://a.hiphotos.baidu.com/image/pic/item/4610b912c8fcc3ce330c2b499145d688d53f20f8.jpg");
        leftImageUrlList.add("http://c.hiphotos.baidu.com/image/pic/item/03087bf40ad162d9b8d78f6612dfa9ec8a13cd8b.jpg");
        leftImageUrlList.add("http://img3.cache.netease.com/ent/2013/12/30/20131230100704e43fa_550.jpg");
        leftImageUrlList.add("http://i.zeze.com/attachment/forum/201502/09/203234kjvszeseetrwueza.jpeg");
        leftImageUrlList.add("http://img4.duitang.com/uploads/item/201408/25/20140825224532_VyRrc.jpeg");
        leftImageUrlList.add("http://img4.duitang.com/uploads/item/201407/18/20140718160913_zvasz.jpeg");
        leftImageUrlList.add("http://stimgcn4.s-msn.com/msnportal/ent/2013/12/30/a551343e-0759-4a04-813a-e7383f121837.jpg");
        leftImageUrlList.add("http://img4.duitang.com/uploads/item/201407/16/20140716092619_TKn8y.thumb.700_0.jpeg");
        leftImageUrlList.add("http://cdn.duitang.com/uploads/item/201408/08/20140808131617_iuhYW.jpeg");
        leftImageUrlList.add("http://img4.duitang.com/uploads/item/201407/20/20140720205349_YhtxZ.jpeg");
        leftImageUrlList.add("http://c.hiphotos.baidu.com/image/pic/item/622762d0f703918f724851cb523d269759eec412.jpg");
        leftImageUrlList.add("http://c.hiphotos.baidu.com/image/pic/item/e824b899a9014c08aceb44a4097b02087af4f4db.jpg");
        leftImageUrlList.add("http://h.hiphotos.baidu.com/image/pic/item/1c950a7b02087bf4cd143be8f1d3572c11dfcf33.jpg");

        rightImageUrlList.add("http://c.hiphotos.baidu.com/image/pic/item/00e93901213fb80e594f0dd635d12f2eb9389410.jpg");
        rightImageUrlList.add("http://c.hiphotos.baidu.com/image/pic/item/b8014a90f603738d03e86023b01bb051f819ec15.jpg");
        rightImageUrlList.add("http://a.hiphotos.baidu.com/image/pic/item/0b55b319ebc4b745e3afd817ccfc1e178a8215f3.jpg");
        rightImageUrlList.add("http://c.hiphotos.baidu.com/image/pic/item/730e0cf3d7ca7bcbd8729c2ebc096b63f624a8a2.jpg");
        rightImageUrlList.add("http://c.hiphotos.baidu.com/image/pic/item/fc1f4134970a304e7fb0ccfbd2c8a786c9175c24.jpg");
        rightImageUrlList.add("http://g.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a461410e4386863f6246b60af85.jpg");
        rightImageUrlList.add("http://f.hiphotos.baidu.com/image/pic/item/267f9e2f07082838a2f4b015bb99a9014c08f132.jpg");
        rightImageUrlList.add("http://h.hiphotos.baidu.com/image/pic/item/962bd40735fae6cdfc2bc7600cb30f2442a70f17.jpg");
        rightImageUrlList.add("http://c.hiphotos.baidu.com/image/pic/item/eac4b74543a982266dec44268982b9014a90eb86.jpg");
        rightImageUrlList.add("http://a.hiphotos.baidu.com/image/pic/item/30adcbef76094b36b4b774eca0cc7cd98d109d8d.jpg");
        rightImageUrlList.add("http://b.hiphotos.baidu.com/image/pic/item/f9dcd100baa1cd111b4f1e21ba12c8fcc3ce2d31.jpg");
        rightImageUrlList.add("http://d.hiphotos.baidu.com/image/pic/item/9a504fc2d5628535ce09be2093ef76c6a6ef63c1.jpg");
        rightImageUrlList.add("http://f.hiphotos.baidu.com/image/pic/item/9922720e0cf3d7ca9e635377f11fbe096b63a91e.jpg");
        rightImageUrlList.add("http://b.hiphotos.baidu.com/image/pic/item/a686c9177f3e6709a89729fe38c79f3df8dc551f.jpg");
        rightImageUrlList.add("http://h.hiphotos.baidu.com/image/pic/item/c995d143ad4bd11344a4311d59afa40f4bfb05b7.jpg");
        rightImageUrlList.add("http://e.hiphotos.baidu.com/image/pic/item/d009b3de9c82d158801635b0830a19d8bc3e421e.jpg");
        rightImageUrlList.add("http://h.hiphotos.baidu.com/image/pic/item/ae51f3deb48f8c54a31ca92e39292df5e1fe7fe7.jpg");

        mLeftAdapter = new StartFlowAdapter(mContext, smallHeight, leftImageUrlList);
        mRightAdapter = new StartFlowAdapter(mContext, tallHeight, rightImageUrlList);

        mListBuddiesLayout = (ListBuddiesLayout) contentView.findViewById(R.id.start_image_flow_listbuddies);
        mListBuddiesLayout.setAdapters(mLeftAdapter, mRightAdapter);
        mListBuddiesLayout.setOnItemClickListener(itemClickListener);
    }

    ListBuddiesLayout.OnBuddyItemClickListener itemClickListener = new ListBuddiesLayout.OnBuddyItemClickListener() {
        @Override
        public void onBuddyItemClicked(AdapterView<?> parent, View view, int buddy, int position, long id) {
            String imageUrl = (buddy == 0 ? leftImageUrlList.get(position) : rightImageUrlList.get(position));
            Activity activity = (Activity) mContext;
            Intent intent = new Intent(mContext, ImageDetailActivity.class);
            intent.putExtra(ImageDetailActivity.EXTRA_IMAGE, imageUrl);
            activity.startActivity(intent);
        }
    };

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY) {
        mScrollView.setScrollY(headerTranslationY - scrollHeight);
    }
}
