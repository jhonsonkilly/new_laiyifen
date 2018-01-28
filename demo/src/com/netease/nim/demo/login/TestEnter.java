package com.netease.nim.demo.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.netease.nim.demo.R;
import com.netease.nim.demo.main.activity.MainActivity;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;



/**
 * Created by jasmin on 2017/11/20.
 */

public class TestEnter extends UI implements View.OnClickListener{

    EditText etImageUrl;

    EditText etAccountID;

    EditText etMobile;

    EditText etSex;

    EditText etToken;

    EditText etNickName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_enter);

        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        setToolBar(R.id.toolbar, options);
        etImageUrl=findView(R.id.et_imageUrl);
        etAccountID=findView(R.id.et_accountID);
        etMobile=findView(R.id.et_mobile);
        etSex=findView(R.id.et_sex);
        etToken=findView(R.id.et_token);
        etNickName=findView(R.id.et_nickName);
        findView(R.id.bt_login).setOnClickListener(this);


    }



    public static void start(Context context, boolean kickOut) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_login){
            MainActivity.start(this, etAccountID.getText().toString(), etMobile.getText().toString()
                    , etImageUrl.getText().toString(), etNickName.getText().toString(), etToken.getText().toString()
                    , etSex.getText().toString());
        }

    }
}
