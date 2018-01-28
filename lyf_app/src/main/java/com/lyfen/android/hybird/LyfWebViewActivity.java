package com.lyfen.android.hybird;

import android.os.Bundle;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.laiyifen.lyfframework.views.action.Action;
import com.laiyifen.lyfframework.views.action.TextAction;
import com.ody.p2p.main.R;

import butterknife.ButterKnife;


/**
 * 作者：qiujie on 16/5/24
 * 邮箱：qiujie@laiyifen.com
 */
public class LyfWebViewActivity extends ActionBarActivity {


    private LyfWebViewFragment mWebFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        hideActionbar();

        HybridEntity model = getIntent().getParcelableExtra("mode");
        Bundle bundle = new Bundle();
        bundle.putParcelable("mode", model);


        mWebFragment = new LyfWebViewFragment();
        mWebFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.common_frameLayout_1, mWebFragment).commitAllowingStateLoss();
        addDefaultReturnAction();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    private boolean canGoBack() {
        return mWebFragment != null && mWebFragment.canGoBack();
    }

    private void webViewGoBack() {
        if (mWebFragment != null) {
            mWebFragment.webViewGoBack();
        }
    }

    /**
     * 添加返回Action
     */
    public void addDefaultReturnAction() {

        getActionTitleBar().removeAllLeftAction();
        TextAction mReturnAction = new TextAction(this, "", R.drawable.icon_back_white, Action.LEFT_OF);
        mReturnAction.setHorizontalRule(Action.LEFT_OF);
        mReturnAction.getView().setOnClickListener(v -> {
            if (canGoBack()) {
                webViewGoBack();
            } else {
                finish();
            }
        });
        getActionTitleBar().addAction(mReturnAction);
    }
//
//    // 主线程的监听（另外四种方式如下）
//    public void onEventMainThread(ShopCarNumEvent event) {
//        if (event.getNum() == 1) {
//            if (isShowCar) {
//                shopcarNum();
//            }
//        }
//
//    }
//
//    private void shopcarNum() {
//        int badgeCnt = PreferenceUtils.getInt(PrefrenceKey.CART_NUM, 0);
//
//        mCommonImgHorizontalNumber1.setVisibility(badgeCnt > 0 ? View.VISIBLE : View.INVISIBLE);
//
//
//        if (badge == null && badgeCnt > 0) {
//            //imgMessagesIcon is the imageview in your custom view, apply the badge to this view.
//            badge = new BadgeView(this, mCommonImgHorizontalNumber1);
//            badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//            badge.setBadgeMargin(1);
//            badge.setTextSize(10);
//            badge.setText(String.valueOf(badgeCnt));
//            badge.show();
//        } else if (badge != null && badgeCnt > 0) {
//            badge.setText(String.valueOf(badgeCnt));
//            badge.show();
//        } else if (badge != null && badgeCnt == 0) {
//            badge.hide();
//        }
//
//    }


}
