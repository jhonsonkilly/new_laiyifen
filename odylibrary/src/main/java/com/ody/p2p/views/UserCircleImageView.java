package com.ody.p2p.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.ody.p2p.R;

/**
 * Created by lxs on 2016/8/19.
 */
public class UserCircleImageView extends CircleImageView{

    private Paint paint;

    public UserCircleImageView(Context context) {
        super(context);
        initBackground();

    }

    public UserCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBackground();
    }

    public UserCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initBackground();
    }


    public void initBackground(){
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.user_bg_color));
        paint.setAntiAlias(true);
        setPadding(5,5,5,5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth()/2;
        canvas.drawCircle(center,center,center,paint);
        super.onDraw(canvas);
    }

}
