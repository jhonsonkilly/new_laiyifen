package com.ody.p2p.views.basepopupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.ody.p2p.R;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.utils.PxUtils;

import java.util.List;


/**
 * Created by lxs on 2016/8/11.
 * 菜单。
 */
public class SelectMenu extends PopupWindow {

    private Context mContext;
    private View mContentView;
    private RecyclerView recycler_menu;
    public SelectMenu(Context context,List<MenuPopBean> list){
        super(context);
        mContext = context;
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_select_menu,null);
        recycler_menu = (RecyclerView) mContentView.findViewById(R.id.recycler_menu);

        MenuPopAdapter adapter = new MenuPopAdapter(mContext,list);
        recycler_menu.setLayoutManager(new LinearLayoutManager(mContext));
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                if (mListener != null){
                    mListener.click(position);
                    dismiss();
                }
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });
        recycler_menu.setAdapter(adapter);
        setContentView(mContentView) ;
        setWidth(PxUtils.dipTopx(80));
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_menu_select));
        setFocusable(true);
        setOutsideTouchable(true);
    }

    public interface clickSelectMenuListener{
        void click(int position);
    }

    private clickSelectMenuListener mListener;

    public void setClickSelectListener(clickSelectMenuListener listener) {
        mListener = listener;
    }


}
