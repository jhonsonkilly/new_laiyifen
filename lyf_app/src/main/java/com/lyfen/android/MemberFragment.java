package com.lyfen.android;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.laiyifen.lyfframework.base.BaseFragment;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.laiyifen.lyfframework.recyclerview.manager.FullyGridLayoutManager;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.laiyifen.lyfframework.utils.LogUtils;
import com.lyfen.android.app.MineApi;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.entity.local.MineMenuItemEntity;
import com.lyfen.android.entity.network.mine.MyOrderEntity;
import com.lyfen.android.entity.network.mine.MyWalletEntity;
import com.lyfen.android.entity.network.mine.PersonalEntity;
import com.lyfen.android.entity.network.mine.PrecommissonEntity;
import com.lyfen.android.entity.network.mine.UserInfoEntity;
import com.lyfen.android.ui.activity.login.LoginHelper;
import com.lyfen.android.ui.adapter.MineMenuAdapter;
import com.lyfen.android.ui.dialog.DialogZhuanshu;
import com.lyfen.android.ui.viewholder.mine.MyOrderHolder;
import com.lyfen.android.ui.viewholder.mine.MyPacketHolder;
import com.lyfen.android.ui.viewholder.mine.YiQiZhuanHolder;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.main.BuildConfig;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.views.recyclerviewlayoutmanager.AtMostRecyclerView;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscriber;

import static android.view.View.GONE;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>文件名:Lyfen</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public class MemberFragment extends BaseFragment {


    @Bind(R.id.img_bg)
    SimpleDraweeView img_bg;
    @Bind(R.id.common_loading_progressbar)
    SimpleDraweeView simpleDraweeView;
    @Bind(R.id.ll_center)
    LinearLayout llCenter;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.fl_yiqizhuan)
    FrameLayout flYiqizhuan;
    @Bind(R.id.fl_myorder)
    FrameLayout flMyorder;
    @Bind(R.id.fl_packet)
    FrameLayout fl_packet;
    @Bind(R.id.fl_recylist)
    FrameLayout flRecylist;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.common_tv_1)
    TextView commonTv1;

    @Bind(R.id.common_tv_2)
    TextView commonTv2;
    @Bind(R.id.common_img_1)
    SimpleDraweeView commonImg1;
    @Bind(R.id.common_tv_3)
    TextView commonTv3;
    private MineApi beanOfClass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beanOfClass = RestRetrofit.getBeanOfClass(MineApi.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_member, container, false);

        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initAnimat();

        initData();


    }


    private void initData() {

        initHeadImgAndNameData();
        initWalletData();
        initOrderData();
        initYIqizhuanData();//一起赚金额
        initListData();


    }
    private void initListData() {

        beanOfClass.personalCenter(NetWorkMap.defaultMap()).compose(bindToLifecycle()).subscribe(new Observer<PersonalEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PersonalEntity personalEntity) {
                initLIstView(personalEntity);
            }
        });

    }

    private void initYIqizhuanData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        Map<String, String> stringStringMap = NetWorkMap.defaultMap();
        stringStringMap.put("startDate", yesterday);
        stringStringMap.put("endDate", yesterday);
        beanOfClass.getCommison(stringStringMap).subscribe(new Subscriber<PrecommissonEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PrecommissonEntity precommissonEntity) {

                initYiQiZhuanView(precommissonEntity);
            }
        });
    }

    private void initOrderData() {
        Map<String, String> stringStringMap = NetWorkMap.defaultMap();
        stringStringMap.put("sysSource", "ody");
        beanOfClass.order(stringStringMap).subscribe(new Subscriber<MyOrderEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MyOrderEntity myOrderEntity) {
                initMyOrderView(myOrderEntity);

            }
        });
    }

    private void initWalletData() {
//
//        ut:e0d3c964e6008854e909ee05775e67ed71
//        platformId:3
//        isECard:1
//        isYCard:1
//        isBean:1
//        isCoupon:1
//        isPoint:1
//        cashe:1513217276000

        Map<String, String> stringStringMap = NetWorkMap.defaultMap();
        stringStringMap.put("isECard", "1");
        stringStringMap.put("isYCard", "1");
        stringStringMap.put("isBean", "1");
        stringStringMap.put("isCoupon", "1");
        stringStringMap.put("isPoint", "1");
        stringStringMap.put("cashe", System.currentTimeMillis() + "");


        beanOfClass.wallet(stringStringMap).subscribe(new Observer<MyWalletEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MyWalletEntity myWalletEntity) {
                initPackView(myWalletEntity);

            }
        });


    }

    @Override
    protected void onFragmentShowed() {
        super.onFragmentShowed();
        initData();
    }

    private void initHeadImgAndNameData() {


        beanOfClass.getHeadImg(NetWorkMap.defaultMap()).subscribe(new Observer<UserInfoEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoEntity orderListEntity) {
                if (orderListEntity.code != 0) {
                    commonTv1.setText("- -");
                    commonTv2.setText("- -");
                    commonTv3.setText("登录/注册");

                    FrescoUtils.displayUrl(commonImg1, "res://" + getActivity().getPackageName() + "/" + R.mipmap.img_member_head);
                }

                if (!TextUtils.isEmpty(orderListEntity.data.nickname)) {
                    commonTv3.setText(orderListEntity.data.nickname);
                } else {
                    commonTv3.setText("- -");

                }
                if (!TextUtils.isEmpty(orderListEntity.data.headPicUrl)) {
                    FrescoUtils.displayUrl(commonImg1, orderListEntity.data.headPicUrl);
                }

                if (!TextUtils.isEmpty(orderListEntity.data.userLevlName)) {
                    commonTv1.setText(orderListEntity.data.userLevlName);

                } else {
                    commonTv1.setText("- -");
                }

            }
        });
    }

    private void initView() {


        initYiQiZhuanView(null);
        initMyOrderView(null);
        initPackView(null);
        initLIstView(null);

    }

    private void initLIstView(PersonalEntity entity) {
        flRecylist.removeAllViews();

        View inflate = View.inflate(getActivity(), R.layout.layout_mine_lisg, null);
        AtMostRecyclerView recyclerView = (AtMostRecyclerView) inflate.findViewById(R.id.recycler);
        if (null != entity && null != entity.data && entity.data.size() > 0 && null != entity.data.get(0)) {


            final MineMenuAdapter mineMenuAdapter = new MineMenuAdapter(getActivity(), entity.data.get(0).moduleList);
            recyclerView.setLayoutManager(new FullyGridLayoutManager(getActivity(), 4));
            recyclerView.setAdapter(mineMenuAdapter);

        }

        flRecylist.addView(inflate);


    }

    private void initPackView(MyWalletEntity myWalletEntity) {


        fl_packet.removeAllViews();
        MyPacketHolder myPacketHolder = new MyPacketHolder();
        if (null != myWalletEntity && null != myWalletEntity.data) {
            myPacketHolder.setData(myWalletEntity);

            commonTv2.setText(myWalletEntity.data.yBean);

        }
        myPacketHolder.refreshView();
        fl_packet.addView(myPacketHolder.getRootView());
    }

    private void initMyOrderView(MyOrderEntity myOrderEntity) {

        flMyorder.removeAllViews();
        MyOrderHolder yiQiZhuanHolder = new MyOrderHolder(getActivity());
        if (null != myOrderEntity && null != myOrderEntity.data) {
            yiQiZhuanHolder.setData(myOrderEntity);

        }
        yiQiZhuanHolder.refreshView();
        flMyorder.addView(yiQiZhuanHolder.getRootView());
    }

    private void initYiQiZhuanView(PrecommissonEntity precommissonEntity) {
        flYiqizhuan.removeAllViews();
        YiQiZhuanHolder yiQiZhuanHolder = new YiQiZhuanHolder(getActivity());
        if (null != precommissonEntity && null != precommissonEntity.data) {
            yiQiZhuanHolder.setData(precommissonEntity);


        }
        yiQiZhuanHolder.refreshView();
        flYiqizhuan.addView(yiQiZhuanHolder.getRootView());

    }

    private void initAnimat() {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFailure(String id, Throwable throwable) {

                super.onFailure(id, throwable);

            }

            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {

                super.onFinalImageSet(id, imageInfo, animatable);
                img_bg.setVisibility(GONE);
                LogUtils.i("aaa", "success");

            }
        };


        DraweeController controller
                =
                Fresco.newDraweeControllerBuilder()
                        .setControllerListener(controllerListener)
                        .setUri(Uri.parse("res:///" + R.mipmap.img_sales))
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        simpleDraweeView.setController(controller);


        Animatable animatable = simpleDraweeView.getController().getAnimatable();
        if (animatable != null && !animatable.isRunning()) {
            animatable.start();
        }
        simpleDraweeView.setOnClickListener(view
                -> JumpUtils.ToWebActivity(getActivity(),
                BuildConfig.H5URL + "/earnTogether/index.html"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private static List<MineMenuItemEntity> getMenuList(Context context) {
        try {
            return new Gson().fromJson(
                    new InputStreamReader(context.getAssets().open("mine_gridview_content.json"))
                    , new TypeToken<List<MineMenuItemEntity>>() {
                    }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @OnClick(R.id.imageView)
    public void sign() {
        if (LoginHelper.needLogin(getActivity())) {
            JumpUtils.ToWebActivity(UIUtils.getContext(), BuildConfig.H5URL + "/community/my-signIn.html");

        }
    }

    @OnClick(R.id.common_img_2)
    public void message() {
        if (LoginHelper.needLogin(getActivity())) {

            JumpUtils.ToWebActivity(UIUtils.getContext(), BuildConfig.H5URL + "/message/message-center.html");
        }
    }

    @OnClick(R.id.common_img_3)
    public void setting() {
        if (LoginHelper.needLogin(getActivity())) {
            JumpUtils.ToWebActivity(UIUtils.getContext(), BuildConfig.H5URL + "/setting/setting.html");

        }
    }

    @OnClick(R.id.img_zhuangshu)
    public void zhuangshu() {
        if (LoginHelper.needLogin(getActivity())) {

            DialogZhuanshu dialogZhuanshu = new DialogZhuanshu(getActivity(), R.layout.dialog_zhuan_shu);
            dialogZhuanshu.show();


        }
    }

    @OnClick(R.id.common_tv_3)

    public void login() {
        if (LoginHelper.needLogin(getActivity())) {

        }

    }

    @OnClick(R.id.ll_yidou)
    public void yidou() {
        if (LoginHelper.needLogin(getActivity())) {
            JumpUtils.ToWebActivity(UIUtils.getContext(), BuildConfig.H5URL + "/my/yidou.html");

        }
    }
}
