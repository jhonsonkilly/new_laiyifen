package com.ody.p2p.views.scrollbanner;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.utils.PxUtils;

import java.util.List;

/**
 * Created by user on 2017/3/20.
 */

public class LyfScrollBanner extends LinearLayout{

    private LinearLayout ll_banner1;
    private LinearLayout ll_banner2;

    private TextView tv_text_1, tv_text_2, tv_text_3, tv_text_4;

    private ImageView iv_headlines;
    private TextView tv_isapply;
    private Handler handler;
    private boolean isShow;
    private int startY1, endY1, startY2, endY2;
    private Runnable runnable;
    private List<HeadLinesBean.CmsPageArticleVO> list;
    private int offsetY = PxUtils.dipTopx(80);

    public int totalPage = 0;       //总页数
    public int pageNo = 0;          //当前位置
    public int pageSize = 2;    //条目数量
    public boolean isDouble = false;    // 整除

    public LyfScrollBanner(Context context) {
        this(context, null);
    }

    public LyfScrollBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LyfScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.lyf_scroll_banner, this);
        ll_banner1 = (LinearLayout) view.findViewById(R.id.ll_banner1);
        ll_banner2 = (LinearLayout) view.findViewById(R.id.ll_banner2);
        tv_text_1 = (TextView) view.findViewById(R.id.tv_text_1);
        tv_text_2 = (TextView) view.findViewById(R.id.tv_text_2);
        tv_text_3 = (TextView) view.findViewById(R.id.tv_text_3);
        tv_text_4 = (TextView) view.findViewById(R.id.tv_text_4);
        iv_headlines = (ImageView) view.findViewById(R.id.iv_headlines);
        tv_isapply = (TextView) view.findViewById(R.id.tv_isapply);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                isShow = !isShow;
                if (null != list && 1 < totalPage) {
                    if (pageNo == totalPage){
                        pageNo = 0;
                    }
                    if (isShow) {
                        if (pageNo == totalPage - 1 && !isDouble) {
                            tv_text_1.setText(list.get(2 * pageNo).displayTitle);
                            tv_text_2.setText("");
                            tv_text_2.setVisibility(View.INVISIBLE);
                        } else {
                            tv_text_1.setText(list.get(2 * pageNo).displayTitle);
                            tv_text_2.setText(list.get(2 * pageNo + 1).displayTitle);
                            tv_text_2.setVisibility(View.VISIBLE);
                        }
                        pageNo++;
                    } else {
                        if (pageNo == totalPage - 1 && !isDouble) {
                            tv_text_3.setText(list.get(2 * pageNo).displayTitle);
                            tv_text_4.setText("");
                            tv_text_4.setVisibility(View.INVISIBLE);
                        } else {
                            tv_text_3.setText(list.get(2 * pageNo).displayTitle);
                            tv_text_4.setText(list.get(2 * pageNo + 1).displayTitle);
                            tv_text_4.setVisibility(View.VISIBLE);
                        }
                        pageNo++;
                    }
                    startY1 = isShow ? 0 : offsetY;
                    endY1 = isShow ? -offsetY : 0;
                    ObjectAnimator.ofFloat(ll_banner2, "translationY", startY1, endY1).setDuration(300).start();
                    startY2 = isShow ? offsetY : 0;
                    endY2 = isShow ? 0 : -offsetY;
                    ObjectAnimator.ofFloat(ll_banner1, "translationY", startY2, endY2).setDuration(300).start();
                    handler.postDelayed(runnable, 2000);
                    ll_banner1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (moreClickListener != null){
                                moreClickListener.clickMore();
                            }
                        }
                    });
                    ll_banner2.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (moreClickListener != null){
                                moreClickListener.clickMore();
                            }
                        }
                    });
                }
            }
        };
    }

    public List<HeadLinesBean.CmsPageArticleVO> getList() {
        return list;
    }

    public void setList(HeadLinesBean bean) {
        if (bean != null && bean.data != null && bean.data.pageResult != null && bean.data.pageResult.listObj != null && bean.data.pageResult.listObj.size() > 0) {
            handler.removeCallbacks(runnable);
            this.list = bean.data.pageResult.listObj ;
            totalPage = list.size() % 2 == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
            pageNo = 0;
            isDouble = list.size() % 2 == 0;
            if (list != null && list.size() > 0) {
                tv_text_1.setText(list.get(0).displayTitle);
                tv_text_2.setVisibility(View.INVISIBLE);
                tv_text_3.setVisibility(View.INVISIBLE);
                tv_text_4.setVisibility(View.INVISIBLE);
                tv_text_2.setText("");
                tv_text_3.setText("");
                tv_text_4.setText("");
                if (list.size() > 1) {
                    tv_text_2.setText(list.get(1).displayTitle);
                    tv_text_2.setVisibility(View.VISIBLE);
                }
                if (list.size() > 2) {
                    tv_text_3.setText(list.get(2).displayTitle);
                    tv_text_3.setVisibility(View.VISIBLE);
                }
                if (list.size() > 3) {
                    tv_text_4.setText(list.get(3).displayTitle);
                    tv_text_4.setVisibility(View.VISIBLE);
                }
            } else {
                return;
            }
        }

    }

    public void startScroll() {
        handler.removeCallbacks(runnable);
        handler.post(runnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(runnable);
    }

    public void stopScroll() {
        handler.removeCallbacks(runnable);
    }

    public void setImage(int srcId) {
        iv_headlines.setImageResource(srcId);
    }

    public void setColor(int colorId) {
        tv_isapply.setTextColor(getResources().getColor(colorId));
    }


    public interface MoreClickListener {
        void clickMore();
    }

    public ScrollBanner.MoreClickListener moreClickListener;

    public void setMoreClickListener(ScrollBanner.MoreClickListener listener) {
        this.moreClickListener = listener;
    }
}
