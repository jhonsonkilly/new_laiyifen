package com.ody.p2p.productdetail.productdetail.frangment.productappraise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.productdetail.ProductDetailActivity;
import com.ody.p2p.productdetail.photoamplification.ViewPagerActivity;
import com.ody.p2p.productdetail.productdetail.adapter.CommendToAllAdapter;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.flowLayout.FlowRadioLayout;
import com.ody.p2p.views.recyclerviewlayoutmanager.OdyRecyclerView;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by ody on 2016/6/7.
 */
public class ProductCommendFragment extends BaseFragment implements ProductCommentView, View.OnClickListener, CommendToAllAdapter.AppriesAdapterCallBack {
    ProductCommendPresent apreesent;
    public OdyRecyclerView recy_goodappriaise;
    private FlowRadioLayout flowlayout_pingjia;
    ImageView img_stop;//隐藏
    int status = 0;//当前为显示状态
    private View view;
    public CommendToAllAdapter adapter_Ratedadapter;
    RelativeLayout rl_over;
    View faile_view;

    LinearLayout ll_line1;
    LinearLayout.LayoutParams ps;
    protected LinearLayout ll_havadata;
    protected LinearLayout ll_notdata;
    public ImageView img_notdata;
    public TextView tv_evaluate_notdata;

    long labelflag = 0;
    int haspic = 0;
    String mpid = "";
    //    List<ProductComment.Data.MpcList.ListObj> commendList;//评价数据的集合
    List<ProductComment.Data.MpLabelList> mpLabelLists;//评价标签
    //加载和刷新
    OdySwipeRefreshLayout swipeRefreshLayout;
    protected int pageNo = 1;
    protected int pageSize = 10;//每页要请求几条
    protected int pageCount = 1;//实际请求到多少
    protected long totalPage;
    /**
     * 评价样式
     */
    int commendThemeResource;

    public void setCommendThemeResource(int commendThemeResource) {
        this.commendThemeResource = commendThemeResource;
    }

    protected int chooseNumber = 0;//评价的item

    /**
     * 流布局评价按钮的样式
     */
    int commendBtnLayout = -1;

    public void setCommendBtnLayout(int commendBtnLayout) {
        this.commendBtnLayout = commendBtnLayout;
    }

    /**
     * 主题颜色
     */
    int theambg = R.drawable.pingjiatextview;

    public void setTheambg(int theambg) {
        this.theambg = theambg;
    }

    @Override
    public int bindLayout() {
        return R.layout.produtdetail_fragment_prodoutappraise;
    }

    @Override
    public void initView(View view) {
        apreesent = new ProductCommendImpl(this);
        adapter_Ratedadapter = new CommendToAllAdapter(getActivity(), null);
        recy_goodappriaise = (OdyRecyclerView) view.findViewById(R.id.recy_goodappriaise);
        flowlayout_pingjia = (FlowRadioLayout) view.findViewById(R.id.flowlayout_pingjia);
        img_stop = (ImageView) view.findViewById(R.id.img_stop);
        ll_havadata = (LinearLayout) view.findViewById(R.id.ll_havadata);
        faile_view = view.findViewById(R.id.faile_view);
        ll_notdata = (LinearLayout) view.findViewById(R.id.ll_notdata);
        img_notdata = (ImageView) view.findViewById(R.id.img_notdata);
        tv_evaluate_notdata = (TextView) view.findViewById(R.id.tv_evaluate_notdata);
        ll_line1 = (LinearLayout) view.findViewById(R.id.ll_line1);
        img_stop.setOnClickListener(this);
        rl_over = (RelativeLayout) view.findViewById(R.id.rl_over);
        //刷新
        swipeRefreshLayout = (OdySwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout
                .setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        pageNo = 1;
                        apreesent.productComment(mpid, haspic, labelflag, pageNo, pageSize);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        // pull distance
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                    }
                });
        swipeRefreshLayout.setHeaderViewBackgroundColor(getContext().getResources().getColor(R.color.background_color));
        swipeRefreshLayout.setTargetScrollWithLayout(true);
        swipeRefreshLayout.setOdyDefaultView(true);
        swipeRefreshLayout.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (pageNo < totalPage && pageCount != 0) {
                    pageNo++;
                    apreesent.productComment(mpid, haspic, labelflag, pageNo, pageSize);
                } else {
                    rl_over.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPushEnable(boolean enable) {
            }

            @Override
            public void onPushDistance(int distance) {
                // TODO Auto-generated method stub

            }

        });
    }

    public void setHide(boolean hidden) {
        if (hidden) {
            if (iv_top != null) {
                iv_top.setVisibility(View.GONE);
            }
        } else {
            if (iv_top != null) {
                if (showFlag) {
                    iv_top.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_stop) {
       /*     if (status == 0) {
                img_stop.setImageResource(R.drawable.icon_retract);
                status = 1;
                ps = (LinearLayout.LayoutParams) ll_line1.getLayoutParams();
                ps.height = ViewPager.LayoutParams.WRAP_CONTENT;
                ps.width = ll_line1.getWidth();
                ll_line1.setLayoutParams(ps);

            } else {
                img_stop.setImageResource(R.drawable.icon_open);
                status = 0;
                ps = (LinearLayout.LayoutParams) ll_line1.getLayoutParams();
                ps.height = PxUtils.dipTopx(100);
                ps.width = ll_line1.getWidth();
                ll_line1.setLayoutParams(ps);
            }*/
        }

    }

    @Override
    public void doBusiness(Context mContext) {
        showTop(recy_goodappriaise);
        faile_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mpid)) {
                    initAppraise(mpid);
                }
            }
        });
    }

    @Override
    public void initPresenter() {

    }

    public void initAppraise(String mpId) {
        mpid = mpId;
        if (null != ((ProductDetailActivity) getActivity()).getCommentToAllData()) {
            setData(((ProductDetailActivity) getActivity()).getCommentToAllData());//评价数据
        } else {
            apreesent.productComment(mpid, haspic, labelflag, 1, pageSize);
        }
    }


    @Override
    public void loadingError() {
        ToastUtils.failToast(getString(R.string.webErro));
    }


    @Override
    public void onclick() {

    }


    //点击评价里面的图片进入图片放大

    @Override
    public void clickPhoto(ProductComment.Data.MpcList.ListObj listObj, int postion) {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < listObj.getMpShinePicList().size(); i++) {
            urls.add(listObj.getMpShinePicList().get(i));
        }
        Intent intent = new Intent(getContext(), ViewPagerActivity.class);
        intent.putExtra("urls", (Serializable) urls);
        intent.putExtra("postion", postion);
        intent.putExtra("userImg", listObj.getUserImg());
        intent.putExtra("username", listObj.getUserUsername());
        intent.putExtra("apprisea", listObj.content);
        intent.putExtra("type", "apprieList");
        startActivity(intent);
    }

//    @Override
//    public void clickPhoto(List<String> list, int checkpostion, String username, String apprisea, String userImg) {
//        // TODO 单击图片处理事件
//        List<String> urls = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            urls.add(list.get(i));
//        }
//        Intent intent = new Intent(getContext(), ViewPagerActivity.class);
//        intent.putExtra("urls", (Serializable) urls);
//        intent.putExtra("postion", checkpostion);
//        intent.putExtra("userImg", userImg);
//        intent.putExtra("username", username);
//        intent.putExtra("apprisea", apprisea);
//        intent.putExtra("type", "apprieList");
//        startActivity(intent);
//    }

    @Override
    public void setCommentDate(ProductComment.Data data) {
        if (null != data) {
            //这是评价标签的放入
            setLabes(data.mpLabelList);
            //这是评价内容的放入
            try {
                setData(data.mpcList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            faile_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setErrorComment(String msg) {
        ll_havadata.setVisibility(View.GONE);
        ll_notdata.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void setLabes(final List<ProductComment.Data.MpLabelList> mpLabelList) {
        if (null == mpLabelLists) {
            mpLabelLists = mpLabelList;//储存便签数据
            if (null != mpLabelList) {
                if (mpLabelList.size() > 0) {
                    ll_line1.setVisibility(View.VISIBLE);
                    flowlayout_pingjia.setVisibility(View.VISIBLE);
                    flowlayout_pingjia.removeAllViews();
                    flowlayout_pingjia.setHorizontalSpacing(PxUtils.dipTopx(12));
                    flowlayout_pingjia.setVerticalSpacing(PxUtils.dipTopx(12));
                    for (int i = 0; i < mpLabelLists.size(); i++) {
                        final CheckBox checkButton;
                        if (commendBtnLayout != -1) {
                            checkButton = (CheckBox) LayoutInflater.from(getActivity()).inflate(commendBtnLayout, null);
                        } else {
                            checkButton = (CheckBox) LayoutInflater.from(getActivity()).inflate(R.layout.produtdetail_layout_textview, null);
                            checkButton.setBackgroundResource(theambg);
                        }
                        checkButton.setText(mpLabelLists.get(i).labelName + "(" + mpLabelList.get(i).labelNum + ")");
                        checkButton.setId(i);
                        if (i == 0) {
                            checkButton.setChecked(true);
                            checkButton.setTextColor(Color.WHITE);
                        }
                        checkButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                labelflag = Long.parseLong(mpLabelLists.get(v.getId()).labelflag);
                                reSetRadio(flowlayout_pingjia, v.getId());
                            }
                        });
                        flowlayout_pingjia.addView(checkButton);
                    }
                } else {
                    ll_line1.setVisibility(View.GONE);
                    flowlayout_pingjia.setVisibility(View.GONE);
                }
            }
        }

    }


    public void reSetRadio(FlowRadioLayout layout, int chooseNum) {
        if (null != layout && layout.getChildCount() > 0) {
            for (int i = 0; i < layout.getChildCount(); i++) {
                if (i == chooseNum) {
                    chooseNumber = chooseNum;
                    ((CheckBox) layout.getChildAt(i)).setChecked(true);
                    ((CheckBox) layout.getChildAt(i)).setTextColor(Color.WHITE);
                    pageNo = 1;

                    apreesent.productComment(mpid, haspic, labelflag, 1, pageSize);

                } else {
                    ((CheckBox) layout.getChildAt(i)).setChecked(false);
                    ((CheckBox) layout.getChildAt(i)).setTextColor(getActivity().getResources().getColor(R.color.main_title_color));
                }
            }
        }
    }


    public void setData(ProductComment.Data.MpcList response) {
        faile_view.setVisibility(View.GONE);
        ((ProductDetailActivity) getActivity()).setCommentToAllData(response);
        totalPage = ((Long.parseLong(response.total) % pageSize == 0) ? (Long.parseLong(response.total)) / pageSize : (Long.parseLong(response.total) / pageSize) + 1);
        if (null != response && null != response.listObj && response.listObj.size() > 0) {
            pageCount = response.listObj.size();
            recy_goodappriaise.setVisibility(View.VISIBLE);
            ll_havadata.setVisibility(View.VISIBLE);
            if (pageNo == 1) {
                adapter_Ratedadapter.setDatas(response.listObj);
                if (0 != commendThemeResource) {
                    adapter_Ratedadapter.setRb_style(commendThemeResource);
                }
                recy_goodappriaise.setAdapter(adapter_Ratedadapter);
                recy_goodappriaise.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter_Ratedadapter.setCallBack(this);
                recy_goodappriaise.setFocusable(false);
                recy_goodappriaise.setFocusableInTouchMode(false);
                recy_goodappriaise.setBackgroundResource(R.color.white);
            } else {
                adapter_Ratedadapter.addItemLast(response.listObj);
                ll_havadata.setVisibility(View.VISIBLE);
                ll_notdata.setVisibility(View.GONE);
            }
        } else {
            ll_havadata.setVisibility(View.GONE);
            ll_notdata.setVisibility(View.VISIBLE);
        }

    }
}
