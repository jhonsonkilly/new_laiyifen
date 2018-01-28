package com.netease.nim.demo.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.demo.R;
import com.netease.nim.demo.main.model.ContactModel;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.util.ToastUtil;

import java.util.List;

//import com.bumptech.glide.request.RequestOptions;
//import com.netease.nim.demo.collect.GlideApp;


public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<ContactModel.FriendsBean> list = null;
    private Context mContext;
    private boolean shieldingInvited;
    private boolean isShowCb;
    private int     num;

    public SortAdapter(Context mContext, List<ContactModel.FriendsBean> list, boolean isShowCb, int num) {
        super();
        this.list = list;
        this.mContext = mContext;
        this.isShowCb = isShowCb;
        this.num = num;
    }

    // when the data changed , call updateListView() to update
    public void updateListView(List<ContactModel.FriendsBean> list, boolean shieldingInvited) {
        this.list = list;
        this.shieldingInvited = shieldingInvited;
        notifyDataSetChanged();
    }


    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int pos) {
        return this.list.get(pos);
    }

    public long getItemId(int pos) {
        return pos;
    }

    public View getView(int pos, View view, ViewGroup group) {
        ViewHolder viewHolder = null;
        final ContactModel.FriendsBean mContent = list.get(pos);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.user_list_item, null);
            //			viewHolder.tvId = (TextView) view.findViewById(R.id.txt_user_id);
            viewHolder.tvName = (TextView) view.findViewById(R.id.txt_user_name);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.txt_catalog);
            viewHolder.photo = (HeadImageView) view.findViewById(R.id.user_head);
            viewHolder.tvMobile = (TextView) view.findViewById(R.id.txt_mobile);
            viewHolder.tv_invite = (TextView) view.findViewById(R.id.tv_invite);
            viewHolder.mCheckBox = (CheckBox) view.findViewById(R.id.cb);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        // get position and get the first letter
        int section = getSectionForPosition(pos);

        /*if (shieldingInvited) {

            viewHolder.tv_invite.setVisibility(View.GONE);
        } else {
            viewHolder.tv_invite.setVisibility(View.VISIBLE);
        }*/

        //        if (pos == getPositionForSection(section)) {
        viewHolder.tvLetter.setVisibility(View.VISIBLE);
        viewHolder.tv_invite.setBackgroundResource(R.drawable.border_white_hasline);
        viewHolder.tv_invite.setTextColor(mContext.getResources().getColor(R.color.gray_text));
        viewHolder.tv_invite.setVisibility(View.VISIBLE);
        viewHolder.mCheckBox.setVisibility(View.GONE);

        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num >= 200) {
                    if (finalViewHolder1.mCheckBox.isChecked()) {
                        finalViewHolder1.mCheckBox.setChecked(false);
                        ToastUtil.showLongToast(mContext, "最多邀请200个好友");
                        return;
                    }
                }


                if (finalViewHolder1.mCheckBox.isChecked()) {
                    mContent.setChecked(true);
                    if (mOnInviteClickListener != null) {
                        mOnInviteClickListener.onSelectChange("1");
                    }
                } else {
                    mContent.setChecked(false);
                    if (mOnInviteClickListener != null) {
                        mOnInviteClickListener.onSelectChange("0");
                    }

                }
            }
        });


        if (mContent.isChecked()) {
            viewHolder.mCheckBox.setChecked(true);
        } else {
            viewHolder.mCheckBox.setChecked(false);
        }

        switch (mContent.getType()) {
            case 0:  //好友
                viewHolder.tv_invite.setVisibility(View.GONE);
                viewHolder.tvLetter.setText("我的好友");
                break;
            case 1:  //未注册
                if (isShowCb) {
                    viewHolder.mCheckBox.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.mCheckBox.setVisibility(View.GONE);
                }
                viewHolder.tvLetter.setText("未注册");
                viewHolder.tv_invite.setText("邀请");
                viewHolder.tv_invite.setClickable(true);
                viewHolder.tv_invite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnInviteClickListener != null) {
                            mOnInviteClickListener.onInvite(mContent);
                        }
                    }
                });

                if (mContent.getStatus() != null) {
                    if (mContent.getStatus().equals("1")) {
                        viewHolder.mCheckBox.setEnabled(false);
                        viewHolder.tv_invite.setText("已邀请");
                        viewHolder.tv_invite.setClickable(false);
                        viewHolder.tv_invite.setBackgroundResource(R.drawable.border_white_hasline);
                        viewHolder.tv_invite.setTextColor(mContext.getResources().getColor(R.color.gray_text));
                    } else {
                        viewHolder.mCheckBox.setEnabled(true);
                        viewHolder.tv_invite.setText("邀请");
                        viewHolder.tv_invite.setClickable(true);
                        viewHolder.tv_invite.setBackgroundResource(R.drawable.border_white_hasline);
                        viewHolder.tv_invite.setTextColor(mContext.getResources().getColor(R.color.gray_text));
                    }
                }
                break;
            case 2:  //陌生人
                viewHolder.tvLetter.setText("陌生人");
                viewHolder.tv_invite.setText("添加");
                viewHolder.tv_invite.setVisibility(View.VISIBLE);
                viewHolder.tv_invite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnInviteClickListener != null) {
                            mOnInviteClickListener.onaddFriend(mContent.getAccid());
                        }
                    }
                });


                break;
            case -1:
                viewHolder.tvLetter.setVisibility(View.GONE);
                break;

        }

        if (!mContent.isHasTitle()) {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }


        //        } else
        //            viewHolder.tvLetter.setVisibility(View.GONE);

        viewHolder.tvName.setText(this.list.get(pos).getName());
        viewHolder.tvMobile.setText(this.list.get(pos).getMobile().replace(" ", ""));
        if (mContent.getIcon() != null) {
            //            RequestOptions requestOptions = new RequestOptions();
            //            RequestOptions options = requestOptions.circleCrop();
            //            GlideApp.with(mContext).load(mContent.getIcon()).placeholder(R.drawable.ic_black_list).apply(options).into(viewHolder.photo);
            Glide.with(mContext).load(mContent.getIcon()).asBitmap().placeholder(NimUIKit.getUserInfoProvider().getDefaultIconResId()).into(viewHolder.photo);

        } else {
            viewHolder.photo.setImageResource(NimUIKit.getUserInfoProvider().getDefaultIconResId());
        }

        return view;
    }

    public void setNewdata(List<ContactModel.FriendsBean> mSearchList) {
        this.list = mSearchList;
        notifyDataSetChanged();

    }

    public void showCb(boolean show) {
        isShowCb = show;
        notifyDataSetChanged();
    }

    public void setNewNum(int num) {
        this.num = num;
        notifyDataSetChanged();
    }

    final static class ViewHolder {
        TextView      tvId;
        TextView      tvLetter;
        TextView      tvName;
        //		TextView tvInfo;
        HeadImageView photo;
        TextView      tvMobile;
        TextView      tv_invite;
        CheckBox      mCheckBox;
    }

    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        if (sortStr.matches("[A-Z]"))
            return sortStr;
        else
            return "#";
    }

    private OnInviteClickListener mOnInviteClickListener;

    public void setOnInviteClickListener(OnInviteClickListener onInviteClickListener) {
        this.mOnInviteClickListener = onInviteClickListener;
    }

    public interface OnInviteClickListener {
        void onInvite(ContactModel.FriendsBean mContent);

        void onaddFriend(String num);

        void onSelectChange(String tag);
    }

}
