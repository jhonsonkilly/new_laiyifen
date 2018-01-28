package com.odianyun.downloadview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.odianyun.uplevel.R;
/**
 * Created by ody on 2016/7/18.
 */
public class UpLevelDialog extends Dialog{
    Context mContext;
    String mUrlPath;
    public TextView tv_updata_no;
    public TextView tv_cancel;
    public TextView tv_updata;

    public UpLevelDialog(Context context, String url) {
        super(context,R.style.update_dialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        this.mContext = context;
        this.mUrlPath = url;
        View view = LayoutInflater.from(mContext).inflate(R.layout.uplevel_layout, null);
        setContentView(view);
        tv_updata_no = (TextView) view.findViewById(R.id.tv_updata_no);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_updata = (TextView) view.findViewById(R.id.tv_updata);

        tv_updata_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mUpLevelBack) {
                    mUpLevelBack.updataNo();
                }
                dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != mUpLevelBack) {
                    mUpLevelBack.activityfinish();
                }
            }
        });
        tv_updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DownloadUtils.isNetworkAvailable((Activity) mContext)) {//检测网络是否可用
                    mUpLevelBack.startDialog();
                    dismiss();
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.no_network_examine), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    UpLevelBack mUpLevelBack;

    public void setUpLevelBack(UpLevelBack mUpLevelBack) {
        this.mUpLevelBack = mUpLevelBack;
    }

    public interface UpLevelBack {
        void updataNo();
        void activityfinish();
        void startDialog();
    }
}
