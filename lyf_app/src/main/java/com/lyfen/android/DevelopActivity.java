package com.lyfen.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.lyfen.android.ui.activity.qianggou.QiangGouActivity;
import com.ody.p2p.main.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * <p> Created by qiujie on 2017/12/9/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class DevelopActivity extends ActionBarActivity {


    @Bind(R.id.common_btn_1)
    Button commonBtn1;
    @Bind(R.id.common_btn_2)
    Button commonBtn2;
    @Bind(R.id.common_btn_3)
    Button commonBtn3;
    @Bind(R.id.common_btn_4)
    Button commonBtn4;
    @Bind(R.id.common_btn_5)
    Button commonBtn5;
    @Bind(R.id.common_btn_6)
    Button commonBtn6;
    @Bind(R.id.activity_arrival_notifa)
    LinearLayout activityArrivalNotifa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyf_dev_demo);
        ButterKnife.bind(this);

        commonBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DevelopActivity.this, QiangGouActivity.class));
            }
        });
    }


}
