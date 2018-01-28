package com.lyfen.android.app;

import com.laiyifen.lyfframework.utils.PreferenceUtils;
import com.laiyifen.lyfframework.utils.SystemUtils;
import com.ody.p2p.base.OdyApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiujie on 2017/7/11.
 */

public class NetWorkMap {
    public static Map<String ,String> defaultMap(){

        HashMap<String ,String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("areaCode", PreferenceUtils.getString(PrefrenceKey.AREA_CODE,"317000"));
        objectObjectHashMap.put("platformId", 0+"");
        objectObjectHashMap.put("sessionId", SystemUtils.getUUid());
        objectObjectHashMap.put("ut", OdyApplication.getLoginUt());
        objectObjectHashMap.put("platform", 3+"");
        return  objectObjectHashMap;


    }



}
