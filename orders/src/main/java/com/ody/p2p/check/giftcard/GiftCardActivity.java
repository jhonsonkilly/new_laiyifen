package com.ody.p2p.check.giftcard;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.RUtils;


/**
 * Created by tangmeijuan on 16/6/15.
 */
public class GiftCardActivity extends BaseActivity implements GiftCardView, View.OnClickListener, AdapterView.OnItemClickListener {
    private GiftCardPresenter presenter;
    private ListView lv_card_list;
    private ImageView iv_back;
    private TextView iv_more;
    private RelativeLayout rl_no_content;
    private RelativeLayout rl_add;
    private GiftCardAdapter adapter;
    private TextView tv_add;
    @Override
    public void initPresenter() {
        presenter=new GiftCardPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_gift_card;
    }

    @Override
    public void initView(View view) {
        iv_back= (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        lv_card_list= (ListView) findViewById(R.id.lv_card_list);
        rl_no_content= (RelativeLayout) findViewById(R.id.rl_no_content);
        rl_add= (RelativeLayout) findViewById(R.id.rl_add);
        tv_add= (TextView) findViewById(R.id.tv_add);
        tv_add.setTextColor(RUtils.getColorRes(this,RUtils.THEME_COLOR));
        rl_add.setOnClickListener(this);
        adapter=new GiftCardAdapter(this);
        lv_card_list.setAdapter(adapter);
        lv_card_list.setOnItemClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
    }

    @Override
    public void resume() {
        presenter.requestgiftcardlist();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void giftcardlist(GiftCardBean giftCardBean) {
        if(giftCardBean!=null&&giftCardBean.data!=null){
            if(giftCardBean.data.giftCardList!=null&&giftCardBean.data.giftCardList.size()>0){
                for(int i=0;i<giftCardBean.data.giftCardList.size();i++){
                    giftCardBean.data.giftCardList.get(i).isAvailable=1;
                    giftCardBean.data.giftCardList.get(i).cardstatus=0;
                }
                adapter.addData(giftCardBean.data.giftCardList);
            }
            if(giftCardBean.data.expiredList!=null&&giftCardBean.data.expiredList.size()>0){
                for(int i=0;i<giftCardBean.data.expiredList.size();i++){
                    giftCardBean.data.expiredList.get(i).isAvailable=0;
                    giftCardBean.data.expiredList.get(i).cardstatus=1;
                }
                adapter.addData(giftCardBean.data.giftCardList);
            }
            if(giftCardBean.data.usedList!=null&&giftCardBean.data.usedList.size()>0){
                for(int i=0;i<giftCardBean.data.usedList.size();i++){
                    giftCardBean.data.usedList.get(i).isAvailable=0;
                    giftCardBean.data.usedList.get(i).cardstatus=2;
                }
                adapter.addData(giftCardBean.data.usedList);
            }
            if(adapter.getCount()==0){
                rl_no_content.setVisibility(View.VISIBLE);
                lv_card_list.setVisibility(View.GONE);
            }
        }else{
            rl_no_content.setVisibility(View.VISIBLE);
            lv_card_list.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.rl_add){
            JumpUtils.ToActivity(JumpUtils.BIND_GIFTCARD);
        }else if(view.getId()==R.id.iv_back){
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle=new Bundle();
        bundle.putString("giftCardId",adapter.getItem(position).giftCardId);
        JumpUtils.ToActivity(JumpUtils.GIFTCARD_CONSUMER,bundle);
    }
}
