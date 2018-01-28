package com.netease.nim.demo.sync;


import android.content.Intent;

import com.google.gson.Gson;
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.demo.sync.util.LogUtils;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;


/**
 * @author SevenCheng
 */

public class SyncPostClient extends SyncClient {

    private String body;

    public SyncPostClient(String url, String body, SyncClientListener clientListener) {
        super(url, clientListener);
        this.body = body;

    }

    @Override
    public void run() {
        executeHttpClient(url, body);
    }

    private void executeHttpClient(String url, final String body) {

        /*StartSync();
        try {
            // Path Parameters (First Name, Middle Name and Last Name)
            URI adapterPath = new URI(Common.AdapterPath);

            WLResourceRequest request = new WLResourceRequest(adapterPath, WLResourceRequest.POST);
            // Query Parameters
            HashMap<String, String> formParams = new HashMap<>();
            formParams.put("params", "['" + url + "','" + body + "']");
            LogUtils.LogV(" url = " + url);
            LogUtils.LogV(" body = " + body);
            request.send(formParams, new WLResponseListener() {
                public void onSuccess(WLResponse response) {



                    String responseText = response.getResponseText();
                    SyncSuccess(responseText);
                }

                public void onFailure(WLFailResponse response) {
                    //String responseText = response.getResponseText();
                    String errorMsg = response.getErrorMsg();
                    SyncFail(errorMsg);
                }
            });


        } catch (URISyntaxException e) {
            e.printStackTrace();

            SyncFail(e.toString());
        }
*/

    }


}
