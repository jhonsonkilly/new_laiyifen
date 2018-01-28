package com.ody.p2p.main.invitefriends;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.UserInfoBean;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.CurrDistributorBean;
import com.ody.p2p.main.R;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.utils.BitmapUtil;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.PermissionUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.CircleImageView;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.odysaxx.util.BitmapUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observer;
import rx.functions.Action1;

/**
 * 邀请好友
 * Created by ody on 2016/7/17.
 */
public class InviteFriendsActivity extends BaseActivity implements View.OnClickListener, InviteFriendsView {
    InviteFriendsPresenterImpl mPressenter;
    private ImageView mIvBack;
    private CircleImageView mImgUerPic;
    private ImageView mImgQR, img_own_poster;
    private LinearLayout mLinearScreenCapture;
    private TextView mTvUerName, tv_invite_you, mTvSaveQr;
    CustomDialog customDialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_invite_friends;
    }

    @Override
    public void initView(View view) {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mImgUerPic = (CircleImageView) findViewById(R.id.img_uer_pic);
        mImgQR = (ImageView) findViewById(R.id.img_QR);
        mLinearScreenCapture = (LinearLayout) findViewById(R.id.linear_screen_capture);
        mTvUerName = (TextView) findViewById(R.id.tv_uer_name);
        img_own_poster = (ImageView) findViewById(R.id.img_own_poster);
        mTvSaveQr = (TextView) findViewById(R.id.tv_save_qr);
        tv_invite_you = (TextView) findViewById(R.id.tv_invite_you);
    }

    @Override
    public void doBusiness(Context mContext) {
        mIvBack.setOnClickListener(this);
        mTvSaveQr.setOnClickListener(this);
        mPressenter.getUserInfo();
        mPressenter.getCurrDistributor();
        mPressenter.getAdData("own_poster");
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void initPresenter() {
        mPressenter = new InviteFriendsPresenterImpl(InviteFriendsActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save_qr:
                svaePic();
                break;
        }
    }

    @Override
    public void backUser(UserInfoBean.Data data) {
        mTvUerName.setText(data.getNickname());
        GlideUtil.display(getContext(), data.getHeadPicUrl()).into(mImgUerPic);
        tv_invite_you.setText(data.getNickname() + getResources().getString(R.string.inite_you_join));
    }

    @Override
    public void backCurrdistri(CurrDistributorBean.Data data) {
        if (!StringUtils.isEmpty(data.getShareCode())) {
            mImgQR.setImageBitmap(
                    BitmapUtils.createQRImage(OdyApplication.H5URL + "/applyToTuike.html" + "?shareCode=" + data.getShareCode()
                            , PxUtils.dipTopx(90)
                            , PxUtils.dipTopx(90)));
        }
    }

    @Override
    public void initAdData(AdData adData) {
        if (null != adData && null != adData.own_poster && adData.own_poster.size() > 0) {
            if (BitmapUtil.isImage(adData.own_poster.get(0).imageUrl)) {
                GlideUtil.display(getContext(), adData.own_poster.get(0).imageUrl).into(img_own_poster);
            }
        }
    }

    /**
     * 保存图片（需要注意权限处理）
     */
    public void svaePic() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                //TODO:项目运行需要的权限
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            showSavePicWindow();
                        } else {
                            ToastUtils.showShort(getResources().getString(R.string.permissiontext));
                        }
                    }
                });
    }

    public void showSavePicWindow() {
        PermissionUtils.checkPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            if (null == customDialog) {
                                customDialog = new CustomDialog(getContext(), "确定保存二维码海报？");
                                customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                                    @Override
                                    public void Confirm() {
                                        try {
                                            Bitmap mp = com.ody.p2p.utils.BitmapUtils.takeScreenShot(InviteFriendsActivity.this, mLinearScreenCapture);
                                            String path = com.ody.p2p.utils.BitmapUtils.saveBitmapFile(InviteFriendsActivity.this, mp);
                                            ToastUtils.showShort("已保存到本地相册" /*+ path*/);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        customDialog.dismiss();
                                    }
                                });
                            }
                            if (!customDialog.isShowing()) {
                                customDialog.show();
                            }
                        } else {
                            ToastUtils.showShort(getString(R.string.permissiontext));
                        }
                    }
                });
    }
}
