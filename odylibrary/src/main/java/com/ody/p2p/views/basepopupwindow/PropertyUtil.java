package com.ody.p2p.views.basepopupwindow;

import java.util.List;

import static android.R.attr.key;

/**
 * Created by ody on 2016/8/30.
 */
public class PropertyUtil {
    /**
     * 获取当前选中的商品属性的ID
     */
    public static ProductBean getThisProduct(PropertyBean Product) {
        if (null == Product || null == Product.getData() || null == Product.getData().getAttributes()) {
            return null;
        }
        String key = getKey(Product.getData().getAttributes());
        if (null != key && key.length() > 0) {
            for (PropertyBean.SerialProducts serialProducts : Product.getData().getSerialProducts()) {
                if (key.contains(serialProducts.getKey())) {
                    return serialProducts.getProduct();
                }
            }
        }
        return null;
    }

    /**
     * 获取当前属性的key来匹配商品队列
     *
     * @param attrs
     * @return
     */
    private static String getKey(List<PropertyBean.Attributes> attrs) {
        String key = "";
        if (null != attrs && attrs.size() > 0) {
            for (PropertyBean.Attributes attr : attrs) {
                if (null != attr && attr.getValues().size() > 0) {
                    for (PropertyBean.Values values : attr.getValues()) {
                        if (values.getChecked()) {
                            key += "_" + values.getId();
                        }
                    }
                }
            }
        }
        if (key.length() > 0) {
            key += "_";
        }
        return key;
    }

    /**
     * 获取当前的属性
     *
     * @param attrs
     * @return
     */
    public static String getKeyValue(List<PropertyBean.Attributes> attrs) {
        String KeyValue = "";
        if (null != attrs && attrs.size() > 0) {
            for (PropertyBean.Attributes attr : attrs) {
                if (null != attr && attr.getValues().size() > 0) {
                    for (PropertyBean.Values values : attr.getValues()) {
                        if (values.getChecked()) {
                            KeyValue += values.getValue() + "，";
                        }
                    }
                }
            }
        }
        return KeyValue.length() > 0 ? KeyValue : "";
    }

}
