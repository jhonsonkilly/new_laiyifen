package com.netease.nim.uikit.contact.core.viewholder;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.contact.core.item.ContactItem;
import com.netease.nim.uikit.contact.core.model.ContactDataAdapter;
import com.netease.nim.uikit.contact.core.model.IContact;
import com.netease.nim.uikit.core.NimUIKitImpl;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

public class ContactHolder extends AbsContactViewHolder<ContactItem> {

    protected HeadImageView head;

    protected TextView name;

    protected TextView desc;

    protected RelativeLayout headLayout;

    protected LinearLayout showMore;

    protected TextView telPhone;

    protected TextView show_more;

    SharedPreferences sp;


    @Override
    public void refresh(final ContactDataAdapter adapter, int position, final ContactItem item) {
        // contact info
        final IContact contact = item.getContact();
        final NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(contact.getContactId());
        if (contact.getContactType() == IContact.Type.Friend) {
            head.loadBuddyAvatar(contact.getContactId());
            telPhone.setVisibility(View.VISIBLE);
            String mobile = userInfo.getMobile();
            if (mobile != null && mobile.length() >= 11) {
                String start = mobile.substring(0, 3);
                String end = mobile.substring(mobile.length() - 3, mobile.length());
                mobile = start + "*****" + end;
//                telPhone.setText(mobile);
            } else {
//                telPhone.setText(mobile);
            }
        } else {
            Team team = TeamDataCache.getInstance().getTeamById(contact.getContactId());
            head.loadTeamIconByTeam(team);
        }
        name.setText(contact.getDisplayName());
        //通讯录头像点击事件
        headLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact.getContactType() == IContact.Type.Friend) {
                    if (NimUIKitImpl.getContactEventListener() != null) {
                        //                        NimUIKitImpl.getContactEventListener().onAvatarClick(context, item.getContact().getContactId());
                    }
                }
            }
        });

        headLayout.setClickable(false);

//        if (item.getItemType() == 1) {
//            if (sp.getBoolean("FRIEND", true)) {
//
//                int mPostion = sp.getInt("FPOSITION", 0) + 2;
//                if (position == mPostion) {
//                    showMore.setVisibility(View.VISIBLE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putInt("FPOSITION", 0);
//                    editor.commit();
//                } else {
//                    showMore.setVisibility(View.GONE);
//                }
//
//                show_more.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //                        Toast.makeText(context, "点击更多", Toast.LENGTH_SHORT).show();
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putBoolean("FRIEND", false);
//                        editor.commit();
//                        EventBus.getDefault().post(new RefreshSearchData(adapter.getQuery().text));
//                    }
//                });
//            } else {
//                showMore.setVisibility(View.GONE);
//            }
//        }
//
//        if (item.getItemType() == 2) {
//            if (sp.getBoolean("TEAM", true)) {
//
//                int mPostion = sp.getInt("TPOSITION", 0) + 2;
//                if (position == mPostion) {
//                    showMore.setVisibility(View.VISIBLE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putInt("TPOSITION", 0);
//                    editor.commit();
//                } else {
//                    showMore.setVisibility(View.GONE);
//                }
//
//                show_more.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //                        Toast.makeText(context, "点击更多", Toast.LENGTH_SHORT).show();
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putBoolean("TEAM", false);
//                        editor.commit();
//                        EventBus.getDefault().post(new RefreshSearchData(adapter.getQuery().text));
//                    }
//                });
//            } else {
//                showMore.setVisibility(View.GONE);
//            }
//        }

        showMore.setVisibility(View.GONE);

        // query result
        desc.setVisibility(View.GONE);



        /*
        TextQuery query = adapter.getQuery();
        HitInfo hitInfo = query != null ? ContactSearch.hitInfo(contact, query) : null;
        if (hitInfo != null && !hitInfo.text.equals(contact.getDisplayName())) {
            desc.setVisibility(View.VISIBLE);
        } else {
            desc.setVisibility(View.GONE);
        }
        */
    }

    @Override
    public View inflate(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.nim_contacts_item, null);
        sp = context.getSharedPreferences("Search", Context.MODE_PRIVATE);

        headLayout = (RelativeLayout) view.findViewById(R.id.head_layout);
        head = (HeadImageView) view.findViewById(R.id.contacts_item_head);
        name = (TextView) view.findViewById(R.id.contacts_item_name);
        desc = (TextView) view.findViewById(R.id.contacts_item_desc);
        telPhone = (TextView) view.findViewById(R.id.tv_telphone);
        show_more = (TextView) view.findViewById(R.id.tv_show_more);
        showMore = (LinearLayout) view.findViewById(R.id.show_more);


        return view;
    }
}
