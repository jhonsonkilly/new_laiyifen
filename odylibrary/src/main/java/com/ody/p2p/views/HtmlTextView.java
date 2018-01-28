package com.ody.p2p.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ody.p2p.utils.StringUtils;

import java.net.URL;

/**
 * Created by pzy on 2017/2/14.
 */
public class HtmlTextView extends TextView {
    String url;
    Spanned sp;
    Handler hd=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!StringUtils.isEmpty(sp+"")){
                HtmlTextView.this.setText(sp);
            }
            return false;
        }
    });

    public HtmlTextView(Context context) {
        super(context);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHtmlText(String url){
        this.url=url;
        new Thread(networkTask).start();
    }
    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            sp=Html.fromHtml(url,imgGetter,null);
            Message msg = new Message();
            hd.sendMessage(msg);
        }
    };

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            //需要设置绘制的宽高，不然展示不出来
            if (drawable != null){
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 2, drawable.getIntrinsicHeight() * 2);
            }
            return drawable;
        }
    };

}
