package com.ody.p2p.main;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.ody.p2p.Constants;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.webactivity.WebActivity;
import com.odysaxx.qrcode.SweepActivity;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxs on 2017/2/8.
 */
public class LyfSweepActivity extends SweepActivity{

    private String code;

    @Override
    public void handleResult(Result rawResult) {
        code = rawResult.toString();
        BarcodeFormat barcodeFormat = rawResult.getBarcodeFormat();
        String type = barcodeFormat.toString();
        String typeCode = "1";
        if (type.equals(BarcodeFormat.QR_CODE)){
            typeCode = "2";
        }
        if (code != null && (code.startsWith("http://") || code.startsWith("https://"))) {
            JumpUtils.ToWebActivity(this, code);
        } else {
            scanCode(code, typeCode);
        }
    }

    public void scanCode(String code, String codeType) {
        Map<String,String> params = new HashMap<>();
        params.put("code",code);
        OkHttpManager.getAsyn(Constants.SCAN_CODE, params,new OkHttpManager.ResultCallback<ScanResultBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test","onError=========================");
            }
            @Override
            public void onResponse(ScanResultBean response) {
                if (response != null){
                    if (response.data != null && !StringUtils.isEmpty(response.data.mpId)){
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.SP_ID,response.data.mpId);
                        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL,bundle);
                    }
                    if (response.data != null && !StringUtils.isEmpty(response.data.visitLink)){
                        if (response.data.visitLink.contains("/detail.html")){
                            try {
                                String mpId = Uri.parse(response.data.visitLink).getQueryParameter("itemId");
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.SP_ID,mpId);
                                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL,bundle);
                            }catch (Exception e){
                            }
                        }else {
                            JumpUtils.ToWebActivity(response.data.visitLink, WebActivity.CONTENT_TITLE,-1,"");
                        }
                    }
                    if (response.data != null && response.data.productList != null && response.data.productList.size() > 0){
                        Gson gson = new Gson();
                        String productJson = gson.toJson(response,ScanResultBean.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.PRODUCT_JSON,productJson);
                        JumpUtils.ToActivity(JumpUtils.SCAN_LIST,bundle);
                    }
                }
            }
        });

    }

}
