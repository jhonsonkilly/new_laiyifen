package com.ody.p2p.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;


/**
 * Created by lxs on 2016/7/6.
 */
public class UpDatePopWindow extends PopupWindow{

    private InitDataBean.UpGrade upGrade;
    private Context context;

    public UpDatePopWindow(InitDataBean.UpGrade upGrade, Context context){
        this.upGrade = upGrade;
        this.context = context;
        initPopWindow();
    }

    public void initPopWindow(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = (ViewGroup) inflater.inflate(R.layout.layout_updatewindow, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.content);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        tv_content.setText(upGrade.packageSize);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
