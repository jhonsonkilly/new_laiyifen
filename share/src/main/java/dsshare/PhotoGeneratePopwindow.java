package dsshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.odianyun.sharesdksharedemo.R;


/**
 * Created by ody on 2016/8/15.
 * 图片生成中
 */
public class PhotoGeneratePopwindow extends PopupWindow {

    private Context context;

    public PhotoGeneratePopwindow(Context context){
        this.context = context;

        View popView = LayoutInflater.from(context).inflate(R.layout.popwindow_photo_is_generating,null);
        setContentView(popView);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
