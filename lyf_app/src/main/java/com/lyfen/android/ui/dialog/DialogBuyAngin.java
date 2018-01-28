package com.lyfen.android.ui.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.laiyifen.lyfframework.recyclerview.RefreshRecyclerView;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerMode;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerViewManager;
import com.lyfen.android.entity.network.dialog.BuyAgainEntity;
import com.lyfen.android.ui.BaseDialog;
import com.lyfen.android.ui.adapter.BuyAginAdapter;
import com.ody.p2p.main.R;

import java.util.List;


/**
 * <p> Created by qiujie on 2017/12/19/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class DialogBuyAngin extends BaseDialog {

    private final RefreshRecyclerView refreshRecyclerView;
    private Context context;

    public DialogBuyAngin(Context context, int layoutResID) {
        super(context, layoutResID);
        setCanceledOnTouchOutside(false);
        View viewById = findViewById(R.id.fl_content);
        this.context=context;

        findViewById(R.id.common_tv_1).setOnClickListener(view -> rotateyAnimRun(viewById));
        refreshRecyclerView = (RefreshRecyclerView) findViewById(R.id.id_recyclerview);

    }


    public void rotateyAnimRun(final View view) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.7f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.7f);

        AnimatorSet set = new AnimatorSet();
        set.play(scaleXAnimator).with(scaleYAnimator);
        set.setDuration(500);

        set.start();
        view.postDelayed(() -> dismiss(), 500);

    }

    public void setData(List<BuyAgainEntity.DataEntity.AvailableProductListEntity> availableProductList) {

        BuyAginAdapter buyAginAdapter = new BuyAginAdapter(context,availableProductList);


        RecyclerViewManager.with(buyAginAdapter,new LinearLayoutManager(context))
                .setMode(RecyclerMode.NONE)
                .into(refreshRecyclerView,context);



    }
}
