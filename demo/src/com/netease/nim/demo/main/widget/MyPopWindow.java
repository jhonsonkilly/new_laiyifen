package com.netease.nim.demo.main.widget;

/**
 * Created by jasmin on 2017/10/26.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.demo.groupsend.activity.MessageListActivity;
import com.netease.nim.demo.main.activity.GlobalSearchActivity;
import com.netease.nim.demo.main.activity.SweepActivity;
import com.netease.nim.demo.nearpeople.NearPeopleActivity;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.dialog.EasyEditDialog;
import com.netease.nim.uikit.contact_selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.team.helper.TeamHelper;
import com.netease.nim.uikit.util.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;


/**
 * Created by 符柱成 on 2016/7/24.
 */
public class MyPopWindow extends PopupWindow implements View.OnClickListener {
    LinearLayout createRegularTeam;
    LinearLayout qunfa;
    LinearLayout createCall;
    LinearLayout createVideo;
    LinearLayout addBuddy;
    LinearLayout sao;
    LinearLayout about;
    private View     conentView;
    private Activity context;

    private static final int REQUEST_SCAN = 11;

    Fragment fragment;

    public MyPopWindow(final Activity context, Fragment fragment) {

        super(context);
        this.context = context;
        this.initPopupWindow();
        this.fragment=fragment;

    }

    private void initPopupWindow() {
        //使用view来引入布局
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popuo_dialog, null);

        //获取popupwindow的高度与宽度
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 - 20);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果，设置动画，一会会讲解
        this.setAnimationStyle(R.style.AnimationPreview);

        createRegularTeam = (LinearLayout) conentView.findViewById(R.id.create_regular_team);
        qunfa = (LinearLayout) conentView.findViewById(R.id.qunfa);
        createCall = (LinearLayout) conentView.findViewById(R.id.create_call);
        createVideo = (LinearLayout) conentView.findViewById(R.id.create_video);
        addBuddy = (LinearLayout) conentView.findViewById(R.id.add_buddy);
        sao = (LinearLayout) conentView.findViewById(R.id.sao);
//        about = (LinearLayout) conentView.findViewById(R.id.about);

        initShowItem();

        createCall.setOnClickListener(this);
        createRegularTeam.setOnClickListener(this);
        qunfa.setOnClickListener(this);
        createVideo.setOnClickListener(this);
        addBuddy.setOnClickListener(this);
        sao.setOnClickListener(this);
//        about.setOnClickListener(this);
        //        布局控件初始化与监听设置
        //        LinearLayout llayout_remind = (LinearLayout) conentView
        //                .findViewById(R.id.llayout_remind);
        //        LinearLayout llayout_history = (LinearLayout) conentView
        //                .findViewById(R.id.llayout_history);
        //        llayout_remind.setOnClickListener(new View.OnClickListener() {
        //
        //            @Override
        //            public void onClick(View arg0) {
        //
        //            }
        //        });
        //
        //        llayout_history.setOnClickListener(new View.OnClickListener() {
        //
        //            @Override
        //            public void onClick(View v) {
        //
        //            }
        //        });
    }

    private String getStatus(String key){
        SharedPreferences sp = context.getSharedPreferences("JurisdictionButton", Context.MODE_PRIVATE);
        return sp.getString(key,"0");
    }

    private void initShowItem() {

        if (getStatus("QZ").equals("2")) {
            createRegularTeam.setVisibility(View.GONE);
            conentView.findViewById(R.id.view1).setVisibility(View.GONE);
        }

        if (getStatus("QF").equals("2")) {
            qunfa.setVisibility(View.GONE);
            conentView.findViewById(R.id.view2).setVisibility(View.GONE);
        }

        if (getStatus("DDH").equals("2")) {
            createCall.setVisibility(View.GONE);
            conentView.findViewById(R.id.view3).setVisibility(View.GONE);
        }


        if (getStatus("DSP").equals("2")) {
            createVideo.setVisibility(View.GONE);
            conentView.findViewById(R.id.view4).setVisibility(View.GONE);
        }

        if (getStatus("CZHY").equals("2")) {
            addBuddy.setVisibility(View.GONE);
            conentView.findViewById(R.id.view5).setVisibility(View.GONE);
        }

        if (getStatus("QR").equals("2")) {
            sao.setVisibility(View.GONE);
        }

    }

    /**
     * 显示popupWindow的方式设置，当然可以有别的方式。
     * 一会会列出其他方法
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }

    /**
     * 第一种
     * 显示在控件的下右方
     *
     * @param parent parent
     */
    public void showAtDropDownRight(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0] + parent.getWidth() - parent.getLayoutParams().width, location[1] + parent.getHeight());
        }
    }


    /**
     * 第二种
     * 显示在控件的下中方
     *
     * @param parent parent
     */
    public void showAtDropDownCenter(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0] / 2 + parent.getWidth() / 2 - parent.getLayoutParams().width / 6, location[1] + parent.getHeight());
        }
    }


    /**
     * 第三种
     * 显示在控件的下左方
     *
     * @param parent parent
     */
    public void showAtDropDownLeft(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0], location[1] - 10 + parent.getHeight() - 10);
        }
    }

    private static final int REQUEST_CODE_ADVANCED = 2;
    private static final int REQUEST_CODE_NORMAL   = 1;

    /*@OnClick({R.id.create_regular_team, R.id.qunfa, R.id.create_call, R.id.create_video, R.id.add_buddy, R.id.sao, R.id.about})
    public void onViewClicked(View view) {
        switch (view.getId()) {


        }
    }*/

    @Override
    public void onClick(View v) {
        this.dismiss();
        int id = v.getId();
//        if (id == R.id.about) {
//            context.startActivity(new Intent(context, SettingsActivity.class));
//        } else
        if (id == R.id.qunfa) {
            MessageListActivity.start(context);
        } else if (id == R.id.create_regular_team) {
            final EasyEditDialog dialog = new EasyEditDialog(context);
            dialog.setEditTextMaxLength(100);
            dialog.setTitle(context.getString(R.string.create_group_title));
            //                dialog.setMessageShow(false);
            //                dialog.setMessage(getString(R.string.create_group_title));
            dialog.setInputType(InputType.TYPE_CLASS_TEXT);
            dialog.addNegativeButtonListener(R.string.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
            });
            dialog.addPositiveButtonListener(R.string.creat, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String content = dialog.getEditMessage();
                    if (!TextUtils.isEmpty(content)) {
                        SharedPreferences.Editor editor = context.getSharedPreferences("sp_demo", Context.MODE_PRIVATE).edit();
                        editor.putString("GroupName", content);
                        editor.commit();
                        ContactSelectActivity.Option advancedOption = TeamHelper.getCreateContactSelectOption(null, 50);
                        NimUIKit.startContactSelector(fragment,context, advancedOption, REQUEST_CODE_ADVANCED);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "请填写群名称", Toast.LENGTH_LONG).show();
                    }
                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
            });
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });
            dialog.show();
        } else if (id == R.id.add_buddy) {
            //查找好友入口(已关闭)
            //            AddFriendActivity.start(context);
            //附近的人入口
            NearPeopleActivity.start(context);
        } else if (id == R.id.search_btn) {
            GlobalSearchActivity.start(context);
        } else if (id == R.id.sao) {
            RxPermissions rxPermissions = new RxPermissions(context);
            rxPermissions.request(
                    //mTODO:meiyizhi 定位需要的权限
                    android.Manifest.permission.CAMERA)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                fragment.startActivityForResult(new Intent(context, SweepActivity.class), REQUEST_SCAN);
                            } else {

                                Toast.makeText(context, "为了更好的使用体验，请开启相机使用权限!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else if (id == R.id.create_video) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onVideoCall();
            }
        } else if (id == R.id.create_call) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onAudioCall();
            }
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onVideoCall();

        void onAudioCall();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
