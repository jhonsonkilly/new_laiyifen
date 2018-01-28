package com.netease.nim.demo.session.viewholder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.session.extension.DistinguishAttachment;
import com.netease.nim.demo.session.model.DistinguishModel;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.ody.p2p.Constants;
import com.ody.p2p.utils.JumpUtils;

import java.util.List;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderDistinguish extends MsgViewHolderBase {

    private DistinguishAttachment attachment;
    private GridView gridView;
    LayoutInflater inflater;
    List<DistinguishModel> listData;

    // 图片封装为一个数组
    private int[] icon = { R.drawable.admin_icon, R.drawable.admin_icon,
            R.drawable.admin_icon, };
    private String[] iconName = { "通讯录", "日历", "照相机" };

    public MsgViewHolderDistinguish(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.custom_distinguish;
    }


    @Override
    protected boolean isSystemMessage() {
        return false;
    }


    @Override
    protected void inflateContentView() {
//        imageView = (ImageView) view.findViewById(R.id.image_view);
        gridView = (GridView) view.findViewById(R.id.gridview);
        inflater= LayoutInflater.from(context);

    }

    @Override
    protected void bindContentView() {
        String contentStr = "";
        try {
            attachment = (DistinguishAttachment) message.getAttachment();
            contentStr = attachment.getValue();
        }catch (Exception e){
            com.netease.nim.uikit.session.extension.DistinguishAttachment attachment = (com.netease.nim.uikit.session.extension.DistinguishAttachment) message.getAttachment();
            contentStr = attachment.getValue();
        }


        listData = new Gson().fromJson(contentStr, new TypeToken<List<DistinguishModel>>() {
        }.getType());

        GridViewSim myGridView=new GridViewSim(context,listData);
        //配置适配器
        gridView.setAdapter(myGridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bd = new Bundle();
                bd.putString(Constants.SP_ID, listData.get(position).getMpsId() + "");
                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
            }
        });
    }

    @Override
    protected boolean isShowMessageBackground() {
        return false;
    }

    @Override
    protected void onItemClick() {

        super.onItemClick();


    }

    @Override
    protected boolean isShowHeadImage() {
        return false;
    }

    @Override
    protected boolean isMiddleItem() {
        return  true;
    }

    class GridViewSim extends BaseAdapter {
        private Context context=null;
        private List<DistinguishModel> list;


        private class Holder{

            ImageView item_img;
            TextView item_tex;

            public ImageView getItem_img() {
                return item_img;
            }

            public void setItem_img(ImageView item_img) {
                this.item_img = item_img;
            }

            public TextView getItem_tex() {
                return item_tex;
            }

            public void setItem_tex(TextView item_tex) {
                this.item_tex = item_tex;
            }




        }
        //构造方法
        public GridViewSim(Context context, List<DistinguishModel> list ) {
            this.context = context;
            this.list = list;
        }


        @Override
        public int getCount() {


            return list.size();

        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Holder holder;
            if(view==null){
                view=inflater.inflate(R.layout.item_distinguish,null);
                holder=new Holder();
                holder.item_img=(ImageView)view.findViewById(R.id.image);
                holder.item_tex=(TextView)view.findViewById(R.id.text);
                view.setTag(holder);
            }else{
                holder=(Holder) view.getTag();
            }
            holder.item_tex.setText(list.get(position).getName());
//            holder.item_img.setImageResource(imgId[position]);
            Glide.with(context).load(list.get(position).getPicUrl()).asBitmap().into(holder.item_img);
            return view;
        }
    }


}



