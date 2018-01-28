package com.ody.p2p.data;

import android.util.Log;

import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.retrofit.adviertisement.AdData;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 数据传输对象
 *
 * @author lxs
 * @version 1.0
 */
public class OdyDto<K, V> extends HashMap<K, V> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6377960213315892547L;

    /**
     * 赋值
     *
     * @param objKey   键值
     * @param objValue 对应值
     */
    public V put(K objKey, V objValue) {
        if (readonly) {
            throw new RuntimeException("属性只读");
        } else {
            return super.put(objKey, objValue);
        }

    }

    /**
     * 只读开关
     */
    private boolean readonly = false;

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * 移除空值的Item
     */
    public void removeEmptyValueItem() {
        for (Iterator<K> iterator = keySet().iterator(); iterator.hasNext(); ) {
            Object key = iterator.next();
            if (null == get(key) || "".equals(String.valueOf(get(key)))) {
                remove(key);
            }
        }
    }


    /**
     * 获取Object对象，所有成员变量属性值
     */
    public static List<Ad> getObjAttr(String key, AdData data) {
        // 获取对象obj的所有属性域
        List<Ad> list = new ArrayList<>();
        try {
            Field o = data.getClass().getField(key);
            list = (ArrayList<Ad>)o.get(new ArrayList<Ad>());
        } catch (Exception e) {
            Log.e("tag",e.toString());
        }
        return list;
    }

}
