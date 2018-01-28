package com.lyfen.android.hybird;

import android.text.TextUtils;
import android.util.Log;

import com.tencent.smtt.sdk.WebView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import static android.text.TextUtils.isEmpty;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>文件名:lyfen</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie@laiyifen.com">vernal(邱洁)</a>
 */
public class HybridEvent {

    private WebView eventSource;
    private Object[] eventExtras;
    private HybridEntity hybridModel;
    private int mtag;

    public HybridEvent(int tag) {
        this.mtag = tag;
    }

    /**
     * 存放WebView或者其他第一主体
     *
     * @return WebView
     */
    public WebView getEventSource() {
        return eventSource;
    }

    public void setEventSource(WebView eventSource) {
        this.eventSource = eventSource;
    }

    /**
     * 存放相关其他参数
     *
     * @return Object[]
     */
    public Object[] getEventExtras() {
        return eventExtras;
    }

    public void setEventExtras(Object[] eventExtras) {
        this.eventExtras = eventExtras;
    }

    /**
     * 从WebView获得的其他参数
     *
     * @return HybridModel
     */
    public HybridEntity getHybridModel() {
        return hybridModel;
    }

    public void setHybridModel(HybridEntity hybridModel) {
        this.hybridModel = hybridModel;
    }


    /**
     * 原声方法注入， 供js调用
     *
     * @param params
     */
    @android.webkit.JavascriptInterface
    public void postMessage(String params) {
//        if (!TextUtils.isEmpty(params)) {
//            try {
//                LogUtils.i("web---postMessage", params);
//                UIUtils.showToastSafe("js"+params);
//                HybridEntity hybridEntity = GsonUtils.fromJson(params, HybridEntity.class);
//                hybridEntity.tag = this.mtag;
//                EventBus.getDefault().post(hybridEntity);
//            } catch (Exception e) {
//                LogUtils.e("rxbus", e.getMessage());
//
//            }
//
//
//        }

        Log.i("web---postMessage",params);
        try {
            JSONObject json = new JSONObject(params);
            String function = json.optString("function");
            String callback = json.optString("callback");
            String param = json.optString("param");
            HybridEntity msg = new HybridEntity();
            msg.function = function;
            msg.params = param;
            msg.callback = callback;
            msg.tag = this.mtag;    //增加hashCode作为发起消息的标识符 在处理一些特定事件的时候用作过滤器
            EventBus.getDefault().post(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void execJs(HybridEntity cmd) {
        if (cmd != null && !TextUtils.isEmpty(cmd.callback)) {
            if (cmd.params.contains("[") && cmd.params.contains("]")) {
                String js = "javascript:" + cmd.callback + "(" + cmd.params + ")"; //当返回载体是数组的时候
                Log.i("web---execjs1",js);
                getEventSource(). loadUrl(js);
            } else {
                String js = "javascript:" + cmd.callback + "('" + cmd.params + "')";//当返回载体是字符串的时候
                Log.i("web---execjs2",js);
                getEventSource().   loadUrl(js);
            }
        }
    }
    public void execJs(String function, String event, String params) {
        String js;
        if (isEmpty(event)) {
            js = "javascript:" + function + "()"; //当返回载体是数组的时候
        } else if (isEmpty(params)) {
            js = "javascript:" + function + "('" + event + "')"; //当返回载体是数组的时候
        } else {
            js = "javascript:" + function + "('" + event + "', '" + params + "')"; //当返回载体是数组的时候
        }
        Log.i("web---execjs3",js);
        getEventSource().loadUrl(js);
    }
}
