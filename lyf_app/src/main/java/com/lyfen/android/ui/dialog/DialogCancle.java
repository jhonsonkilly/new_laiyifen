package com.lyfen.android.ui.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import com.lyfen.android.ui.BaseDialog;
import com.ody.p2p.main.R;


/**
 * <p> Created by qiujie on 2017/12/19/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class DialogCancle extends BaseDialog {
    public DialogCancle(Context context, int layoutResID) {
        super(context, layoutResID);
        setCanceledOnTouchOutside(false);
        View viewById = findViewById(R.id.fl_content);

        findViewById(R.id.common_tv_1).setOnClickListener(view -> rotateyAnimRun(viewById));

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
}
