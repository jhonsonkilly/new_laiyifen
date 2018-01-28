package com.ody.p2p.views.scrollbanner;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.utils.PxUtils;

import java.util.List;

/**
 * Created by lxs on 2016/8/4.
 */
public class ScrollBanner extends LinearLayout {

    private TextView mBannerTV1;
    private TextView mBannerTV2;
    private TextView tv_more;
    private ImageView iv_headlines;
    private TextView tv_isapply;
    private ImageView iv_news;
    private Handler handler;
    private boolean isShow = false;
    private int startY1, endY1, startY2, endY2;
    private Runnable runnable;
    private List<HeadLinesBean.CmsPageArticleVO> list;

    private List<Ad> adList;
    private int position = 1;
    private int offsetY = PxUtils.dipTopx(60);
    public boolean runFlag;
    ImageView img_close;
    private ImageView yizai;
    private LinearLayout newBg;
    private FrameLayout bannerFl;
    View view_divider, view_divider_2;

    public ScrollBanner(Context context) {
        this(context, null);
    }

    public ScrollBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_scroll_banner, this);
        mBannerTV1 = (TextView) view.findViewById(R.id.tv_banner1);
        mBannerTV2 = (TextView) view.findViewById(R.id.tv_banner2);
        tv_more = (TextView) view.findViewById(R.id.tv_more);
        iv_headlines = (ImageView) view.findViewById(R.id.iv_headlines);
        tv_isapply = (TextView) view.findViewById(R.id.tv_isapply);
        yizai = (ImageView) view.findViewById(R.id.yizai);
        iv_news = (ImageView) view.findViewById(R.id.iv_news);
        newBg = (LinearLayout) view.findViewById(R.id.newBg);
        bannerFl = (FrameLayout) view.findViewById(R.id.bannerFl);

        view_divider = view.findViewById(R.id.view_divider);
        view_divider_2 = view.findViewById(R.id.view_divider_2);
        img_close = (ImageView) view.findViewById(R.id.img_close);

        img_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = false;
                ScrollBanner.this.setVisibility(View.GONE);
            }
        });
        tv_more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreClickListener != null) {
                    moreClickListener.clickMore();
                }
            }
        });
        mBannerTV2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position >= 1) {
                    if (listener != null) {
                        listener.click(position - 1);
                    }
                } else {
                    if (listener != null) {
                        if (null != list) {
                            listener.click(list.size() - position);
                        } else if (null != adList) {
                            listener.click(adList.size() - position);
                        }
                    }
                }
            }
        });
        mBannerTV1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position >= 1) {
                    if (listener != null) {
                        listener.click(position - 1);
                    }
                } else {
                    if (listener != null) {
                        if (null != list) {
                            listener.click(list.size() - position);
                        } else if (null != adList) {
                            listener.click(adList.size() - position);
                        }
                    }
                }
            }
        });
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                isShow = !isShow;
                if (null != list && 0 < list.size()) {
                    if (position == list.size())
                        position = 0;
                    if (isShow) {
                        mBannerTV1.setText(list.get(position).displayTitle);
                        position++;
                    } else {
                        mBannerTV2.setText(list.get(position).displayTitle);
                        position++;
                    }
                } else if (null != adList && adList.size() > 0) {
                    if (position == adList.size())
                        position = 0;
                    if (isShow) {
                        mBannerTV1.setText(/*adList.get(position).name+":"+*/adList.get(position).title);
                        position++;
                    } else {
                        mBannerTV2.setText(/*adList.get(position).name+":"+*/adList.get(position).title);
                        position++;
                    }
                }
                startY1 = isShow ? 0 : offsetY;
                endY1 = isShow ? -offsetY : 0;
                ObjectAnimator.ofFloat(mBannerTV2, "translationY", startY1, endY1).setDuration(300).start();
                startY2 = isShow ? offsetY : 0;
                endY2 = isShow ? 0 : -offsetY;
                ObjectAnimator.ofFloat(mBannerTV1, "translationY", startY2, endY2).setDuration(300).start();
                handler.postDelayed(runnable, 5000);
            }
        };
    }

    public static int FOR_SHOPCART = 1;

    public void setStyle(int type) {
        if (type == FOR_SHOPCART) {
            tv_isapply.setVisibility(View.GONE);
            view_divider.setVisibility(View.GONE);
            view_divider_2.setVisibility(View.GONE);
            iv_headlines.setImageResource(R.drawable.ic_launcher);
            tv_more.setVisibility(View.INVISIBLE);
            img_close.setVisibility(View.VISIBLE);
            iv_news.setVisibility(GONE);
            LinearLayout.LayoutParams params = (LayoutParams) bannerFl.getLayoutParams();
//            params.setMargins(PxUtils.dipTopx(15), 0, 0, 0);
//            bannerFl.setLayoutParams(params);
            newBg.setBackgroundResource(0);
        }
    }

    public List<HeadLinesBean.CmsPageArticleVO> getList() {
        return list;
    }

    public void setList(HeadLinesBean bean) {
        if (bean != null && bean.data != null && bean.data.pageResult != null && bean.data.pageResult.listObj != null && bean.data.pageResult.listObj.size() > 0) {
            this.list = bean.data.pageResult.listObj;
            if (list != null && list.size() > 0) {
                mBannerTV1.setText(list.get(0).displayTitle);
                if (list.size() > 1) {
                    mBannerTV2.setText(list.get(1).displayTitle);
                }
            } else {
                return;
            }
        }
    }

    public void setList(List<Ad> bean) {
        if (bean != null && bean.size() > 0) {
            this.adList = bean;
            mBannerTV1.setText(/*adList.get(0).name+":"+*/adList.get(0).title);
            if (adList.size() > 1) {
                mBannerTV2.setText(/*adList.get(1).name+":"+*/adList.get(1).title);
            }
        }
    }

    public void startScroll() {
        handler.post(runnable);
        runFlag = true;
    }

    public void stopScroll() {
        handler.removeCallbacks(runnable);
        runFlag = false;
    }


    public ItemClickListener listener;


    public interface ItemClickListener {
        void click(int positon);
    }

    public void setItemClick(ItemClickListener listener) {
        this.listener = listener;
    }


    public interface MoreClickListener {
        void clickMore();
    }

    public MoreClickListener moreClickListener;

    public void setMoreClickListener(MoreClickListener listener) {
        this.moreClickListener = listener;
    }

    public void setImage(int srcId) {
        iv_headlines.setImageResource(srcId);
    }

    public void setImageClose(int srcId) {
        img_close.setImageResource(srcId);
    }


    public void setColor(int colorId) {
        tv_isapply.setTextColor(getResources().getColor(colorId));
    }


    public static final int LYF = 1000;
    public static final int DES = 1001;
    public static final int JT = 1002;

    public void setType(int type) {
        if (type == LYF) {
            iv_news.setVisibility(View.VISIBLE);
            tv_isapply.setVisibility(View.GONE);
            view_divider.setVisibility(View.GONE);
            view_divider_2.setVisibility(View.VISIBLE);
            tv_more.setVisibility(VISIBLE);
            yizai.setVisibility(VISIBLE);
            mBannerTV1.setTextColor(ContextCompat.getColor(mBannerTV1.getContext(), R.color.white));
            mBannerTV2.setTextColor(ContextCompat.getColor(mBannerTV1.getContext(), R.color.white));
            tv_more.setTextColor(ContextCompat.getColor(mBannerTV1.getContext(), R.color.white));
        }
    }

}
