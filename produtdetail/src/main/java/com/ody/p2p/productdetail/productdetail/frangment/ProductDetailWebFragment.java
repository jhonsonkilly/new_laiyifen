package com.ody.p2p.productdetail.productdetail.frangment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.productdetail.productdetail.adapter.StandardAdapter;
import com.ody.p2p.productdetail.productdetail.bean.StandardBean;
import com.ody.p2p.productdetail.productdetail.frangment.productdetail.ProductImpl;
import com.ody.p2p.productdetail.productdetail.frangment.productdetail.ProductPresent;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.scrollwebview.ScrollWebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;


/**
 * Created by ody on 2016/6/2.
 */
public class ProductDetailWebFragment extends BaseFragment implements OnClickListener, ProductWebView {
    View view;
    View textspxq;
    View textguige;
    View textshfw;
    TextView text_spxq1;
    TextView text_guige1;
    protected TextView text_shfw1;
    private ScrollWebView webView;
    ProductPresent ppreesent;
    RecyclerView guige_list; //规格参数
    boolean top = false;//判断是否在顶部
    RelativeLayout pull_up;
    protected StandardAdapter guigedeatilAdapter;
    /**
     * //置顶按钮和联系客服
     */
    ImageView img_totop_button;
    String mpid = "";
    int themeColor = R.color.theme_color;
    private String detailUrl;
    String standardUrl;
    String saleUrl;
    String showUrl = detailUrl;//要显示的URL
    int NOW_H5URL = 0;//当前所加载的 0文描 1是规格 2服务协议

    //是否有数据
    FrameLayout fl_haveH5;
    LinearLayout ll_notH5;

    public String getMpid() {
        return mpid;
    }

    public void setMpid(String mpid) {
        this.mpid = mpid;
    }


    public void setThemeColor(int themeColor) {
        if (themeColor > 0) {
            this.themeColor = themeColor;
            if (NOW_H5URL == 0) {
                text_spxq1.setTextColor(ContextCompat.getColor(getContext(), themeColor));//文描的首页默认选中的
            } else if (NOW_H5URL == 1) {
                text_guige1.setTextColor(ContextCompat.getColor(getContext(), themeColor));//文描的首页默认选中的
            } else if (NOW_H5URL == 2) {
                text_shfw1.setTextColor(ContextCompat.getColor(getContext(), themeColor));//文描的首页默认选中的
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("ViewCreated", "ViewCreated+f2");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            ppreesent.productStandard(mpid);
        }
    }

    @Override
    public void initPresenter() {
        ppreesent = new ProductImpl(this, ProductImpl.WEB_TYPE);
        initListenter();
    }

    private void initListenter() {
        textspxq.setOnClickListener(this);
        textguige.setOnClickListener(this);
        textshfw.setOnClickListener(this);
        img_totop_button.setOnClickListener(this);
    }


    @Override
    public int bindLayout() {
        return R.layout.produtdetail_fragment_prodoutdeatiesweb;
    }

    public void initView(View view) {
        //文描部分
        guige_list = (RecyclerView) view.findViewById(R.id.guige_list);
        textspxq = view.findViewById(R.id.text_spxq);
        textguige = view.findViewById(R.id.text_guige);
        textshfw = view.findViewById(R.id.text_shfw);
        text_spxq1 = (TextView) view.findViewById(R.id.text_spxq1);
        text_guige1 = (TextView) view.findViewById(R.id.text_guige1);
        text_shfw1 = (TextView) view.findViewById(R.id.text_shfw1);
        webView = (ScrollWebView) view.findViewById(R.id.webView);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setJavaScriptEnabled(true);
        //设置WebView的一些缩放功能点
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setHorizontalScrollBarEnabled(false);
//        webView.getSettings().setSupportZoom(true);
        //设置WebView可触摸放大缩小
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.setInitialScale(70);
//        webView.setHorizontalScrollbarOverlay(true);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
//        webView.getSettings().setUseWideViewPort(true);
        //提高渲染的优先级
//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //允许JS执行
        webView.getSettings().setJavaScriptEnabled(true);
        //在同种分辨率的情况下,屏幕密度不一样的情况下,自动适配页面:
//        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 获取当前界面的高度
        //int width = dm.widthPixels;
        //int height = dm.heightPixels;
//        int scale = dm.densityDpi;
//        if (scale == 240) {
//            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (scale == 160) {
//            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        } else {
//            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        }
        fl_haveH5 = (FrameLayout) view.findViewById(R.id.fl_haveH5);
        ll_notH5 = (LinearLayout) view.findViewById(R.id.ll_notH5);
        img_totop_button = (ImageView) view.findViewById(R.id.img_totop_button);
        pull_up = (RelativeLayout) view.findViewById(R.id.pull_up);
        pull_up.setVisibility(View.GONE);

        webView.setOnScrollChangedCallback(new ScrollWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy, double alpha) {
                showBtnTop();
            }

            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                showBtnTop();
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                hideBtnTop();
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        webView.set
    }

    @Override
    public void doBusiness(Context mContext) {
        webView.setWebChromeClient(new WebChromeClient() {
        });
        guigedeatilAdapter = new StandardAdapter(null, getActivity());
        guige_list.setLayoutManager(new LinearLayoutManager(getContext()));
        guige_list.setAdapter(guigedeatilAdapter);
    }

    @Override
    public void loadingError() {
        ToastUtils.failToast(getString(R.string.webErro));
    }

    @Override
    public void onClick(View v) {
        //文描部分
        if (v.getId() == R.id.text_spxq) {
            NOW_H5URL = 0;
            text_spxq1.setTextColor(getContext().getResources().getColor(themeColor));
            text_guige1.setTextColor(getContext().getResources().getColor(R.color.main_title_color));
            text_shfw1.setTextColor(getContext().getResources().getColor(R.color.main_title_color));
            showUrl = detailUrl;
            if (!StringUtils.isEmpty(showUrl)) {
                fl_haveH5.setVisibility(View.VISIBLE);
                webView.loadUrl(showUrl);
            } else {
                fl_haveH5.setVisibility(View.GONE);
                ll_notH5.setVisibility(View.VISIBLE);
            }
        } else if (v.getId() == R.id.text_guige) {
             /*  //之前不是一个H5是走接口请求道的数据作展示
             ppreesent.productStandard(mpid);
            */
            NOW_H5URL = 1;

            text_guige1.setTextColor(ContextCompat.getColor(getContext(), themeColor));
            text_shfw1.setTextColor(ContextCompat.getColor(getContext(), R.color.main_title_color));
            text_spxq1.setTextColor(ContextCompat.getColor(getContext(), R.color.main_title_color));
            showUrl = standardUrl;
            if (!StringUtils.isEmpty(showUrl)) {
                fl_haveH5.setVisibility(View.VISIBLE);
                webView.loadUrl(showUrl);
            } else {
                fl_haveH5.setVisibility(View.GONE);
                ll_notH5.setVisibility(View.VISIBLE);
            }
        } else if (v.getId() == R.id.text_shfw) {
            NOW_H5URL = 2;
            //            webView.loadUrl(Constants.SHOUHOU_SERVICE);//本地的H5
            text_shfw1.setTextColor(getContext().getResources().getColor(themeColor));
            text_spxq1.setTextColor(getContext().getResources().getColor(R.color.main_title_color));
            text_guige1.setTextColor(getContext().getResources().getColor(R.color.main_title_color));
            showUrl = saleUrl;
            if (!StringUtils.isEmpty(showUrl)) {
                fl_haveH5.setVisibility(View.VISIBLE);
                webView.loadUrl(showUrl);
            } else {
                fl_haveH5.setVisibility(View.GONE);
                ll_notH5.setVisibility(View.VISIBLE);
            }
        } else if (v.getId() == R.id.img_totop_button) {
            if (webView.getVisibility() == View.VISIBLE) {
                webView.scrollTo(0, 0);
            } else {
                guige_list.smoothScrollToPosition(0);
            }

        }
    }
    //规格数据放入
    @Override
    public void standard(StandardBean bean) {
        guigedeatilAdapter.setDatas(bean.getData());
    }

    public void setH5Url(String detailUrl, String standardUrl, String saleUrl) {
        this.detailUrl = detailUrl;
        this.saleUrl = saleUrl;
        this.standardUrl = standardUrl;
        if (NOW_H5URL == 0) {
            showUrl = detailUrl;
        } else if (NOW_H5URL == 1) {
            showUrl = standardUrl;
        } else if (NOW_H5URL == 2) {
            showUrl = saleUrl;
        }
        if (!StringUtils.isEmpty(showUrl)) {
            fl_haveH5.setVisibility(View.VISIBLE);
            webView.loadUrl(showUrl);
        } else {
            fl_haveH5.setVisibility(View.GONE);
            ll_notH5.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 显示置顶按钮
     */
    private void showBtnTop() {
        // TODO Auto-generated method stub
        if (img_totop_button.getVisibility() == View.GONE) {
            img_totop_button.setVisibility(View.VISIBLE);
            img_totop_button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dialog_enter));
        }
    }

    /**
     * 隐藏置顶按钮
     */
    private void hideBtnTop() {
        // TODO Auto-generated method stub
        if (img_totop_button.getVisibility() == View.VISIBLE) {
            img_totop_button.setVisibility(View.GONE);
            img_totop_button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dialog_exit));
        }
    }

    @Override
    public void onDestroyView() {
        webView.setVisibility(View.GONE);
        webView.removeAllViews();
        webView.destroy();
        webView = null;
        super.onDestroyView();
    }
}
