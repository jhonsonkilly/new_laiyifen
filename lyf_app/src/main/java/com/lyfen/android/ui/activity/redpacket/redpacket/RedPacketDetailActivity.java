package com.lyfen.android.ui.activity.redpacket.redpacket;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.lyfen.android.ui.activity.redpacket.RedPacketNetWorkApi;
import com.lyfen.android.ui.activity.redpacket.RedPacketRetrofit;
import com.lyfen.android.ui.activity.redpacket.entity.RedPacketDetailEntity;
import com.lyfen.android.ui.activity.redpacket.redpacket.redpacketlist.RedenvelopesListBean;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.statusbar.StatusBarUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public class RedPacketDetailActivity extends ActionBarActivity {

    public String REDPACKET_SENDTAIL = "redpacket.sendtail";
    public String REDPACKET_GETDETAIL = "redpacket.getdetail";
    public String method;
    private RedenvelopesListBean mBean;
    private RecyclerView mRecyclerView;
    private View mHead;
    private RedPacketDetailAdapter mRedPacketDetailAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.CFF6900));


        mRecyclerView = new RecyclerView(this);
        setContentView(mRecyclerView);
        mHead = View.inflate(this, R.layout.layout_redenvelop_detail, null);

        mRedPacketDetailAdapter = new RedPacketDetailAdapter();
        mRedPacketDetailAdapter.addHeaderView(mHead);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRedPacketDetailAdapter);
        doBusiness(this);

    }

    public void doBusiness(Context mContext) {


        mBean = (RedenvelopesListBean) getIntent().getSerializableExtra("bean");
        if (mBean != null) {
            setTitle(mBean.type == 1 ? "发送红包" : "收到红包");
            method = mBean.type == 1 ? REDPACKET_SENDTAIL : REDPACKET_GETDETAIL;
        }

        getRedenvelopesDetailBeanObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<RedPacketDetailEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RedPacketDetailEntity redPacketDetailEntity) {

                        if (redPacketDetailEntity != null && redPacketDetailEntity.data != null) {


                            TextView resultTextview = (TextView) mHead.findViewById(R.id.resultTextview);
                            TextView username = (TextView) mHead.findViewById(R.id.username);
                            TextView allMoney = (TextView) mHead.findViewById(R.id.allMoney);
                            ImageView pin = (ImageView) mHead.findViewById(R.id.pin);

                            username.setText(redPacketDetailEntity.data.name + "  的红包");
                            if (mBean.type == 1) {
                                resultTextview.setText((redPacketDetailEntity.data.number != null && !redPacketDetailEntity.data.number.equals(
                                        "") ? redPacketDetailEntity.data.number + "个红包," : "")
                                        + (redPacketDetailEntity.data.time != null && !redPacketDetailEntity.data.time.equals("") ? redPacketDetailEntity.data.time + "分被抢光" :
                                        ""));
                                pin.setVisibility(View.VISIBLE);
                            } else {
                                resultTextview.setText((redPacketDetailEntity.data.number != null && !redPacketDetailEntity.data.number.equals(
                                        "") ? redPacketDetailEntity.data.number + "个红包," : "")
                                        + (redPacketDetailEntity.data.amount != null && !redPacketDetailEntity.data.amount.equals("") ? redPacketDetailEntity.data.amount + "元" : ""));
                                pin.setVisibility(View.INVISIBLE);
                            }
                            allMoney.setText(redPacketDetailEntity.data.subamount);


                            mRedPacketDetailAdapter.setNewData(redPacketDetailEntity.data.reds);
                            mRedPacketDetailAdapter.notifyDataSetChanged();

                        } else {
                        }

                    }
                });

    }

    private Observable<RedPacketDetailEntity> getRedenvelopesDetailBeanObservable() {
        RedPacketRetrofit retrofitHelper = new RedPacketRetrofit(RedPacketActivity.BETA);
        final HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("method", method);
        stringStringHashMap.put("envelope_id", mBean.envelope_id);
        stringStringHashMap.put("type", mBean.type + "");
        stringStringHashMap.put("session", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        RedPacketNetWorkApi redPacketService = retrofitHelper.getRedPacketService();
        Observable<RedPacketDetailEntity> observable = null;
        if (mBean.type == 1) {
            observable = redPacketService.redpacketSendDetail(stringStringHashMap);
        } else {
            observable = redPacketService.redpacketReceiveDetail(stringStringHashMap);
        }
        return observable;
    }
}
