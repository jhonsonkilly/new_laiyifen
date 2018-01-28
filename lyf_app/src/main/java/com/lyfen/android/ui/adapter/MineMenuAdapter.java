package com.lyfen.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.lyfen.android.entity.network.mine.PersonalEntity;
import com.ody.p2p.AliServicePolicy;
import com.ody.p2p.Constants;
import com.ody.p2p.QiYuServicePolicy;
import com.ody.p2p.ServiceManager;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.entity.QIYuEntity;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

;import static com.alibaba.mobileim.extra.xblink.config.GlobalConfig.context;

/**
 * 作者：qiujie on 16/4/25
 * 邮箱：qiujie@laiyifen.com
 */
public class MineMenuAdapter extends RecyclerView.Adapter<MineMenuAdapter.ListHolder> {


    private Context mContext;
    List<PersonalEntity.DataEntity.ModuleListEntity> entities;

    public MineMenuAdapter(Context activity, List<PersonalEntity.DataEntity.ModuleListEntity> menuList) {
        this.mContext = activity;
        this.entities = menuList;
    }

    public List<PersonalEntity.DataEntity.ModuleListEntity> getMenuList() {
        return entities;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(mContext, R.layout.mine_menu_item, null);

        return new ListHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.setData(entities.get(position));

    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.common_tv_horizontal_number_1)
        SimpleDraweeView mCommonTvHorizontalNumber1;
        @Bind(R.id.common_img_vertical_number_1)
        TextView mCommonImgVerticalNumber1;
        @Bind(R.id.rl_root)
        RelativeLayout rlRoot;

        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setData(PersonalEntity.DataEntity.ModuleListEntity mineMenuItemEntity) {

            if (!TextUtils.isEmpty(mineMenuItemEntity.name)) {
                mCommonImgVerticalNumber1.setText(mineMenuItemEntity.name);
            }
            if (!TextUtils.isEmpty(mineMenuItemEntity.logoUrl)) {
                FrescoUtils.displayUrl(mCommonTvHorizontalNumber1, mineMenuItemEntity.logoUrl);

            }
            rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mineMenuItemEntity.linkUrlAndroid.contains("callcustomservice")) {

                        if (OdyApplication.getString(Constants.SERVICE_TOGGLE, "0").equals("0")) {

                            ServiceManager.getInstance().doPolicy(new AliServicePolicy(), mContext);
                        } else {
                            ServiceManager.getInstance().doPolicy(new QiYuServicePolicy(), QIYuEntity.MY, mContext);
                        }


                    }
                    if (mineMenuItemEntity.linkUrlAndroid.contains("callalicustomservice")) {

                        ServiceManager.getInstance().doPolicy(new AliServicePolicy(), mContext);
                    } else {

                        JumpUtils.ToActivity(mineMenuItemEntity.linkUrlAndroid);
                    }
                }
            });
        }
    }
}
