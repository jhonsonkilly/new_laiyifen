package com.ody.p2p.settings.modifyUserInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.settings.modifyUserInfo.modifyUserName.ModifyUserNameActivity;
import com.ody.p2p.settings.utils.UserPopupWindow;
import com.ody.p2p.settings.utils.pickerview.popwindow.DatePickerPopWin;
import com.ody.p2p.utils.BitmapUtil;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.CircleImageView;
import com.odysaxx.photograph.PhotoPickerActivity;
import com.odysaxx.photograph.utils.OtherUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ModifyUserInfoActivity extends BaseActivity implements View.OnClickListener,ModifyUserInfoView {

    TextView tv_name;
    RelativeLayout rl_big_back;
    RelativeLayout rl_user_name;
    RelativeLayout rl_user_sex;
    RelativeLayout rl_user_birth;
    CircleImageView civ_user_photo;
    TextView tv_user_name;
    TextView tv_user_sex;
    TextView tv_user_birth;
    RelativeLayout rl_user_photo;

    private static final int CHOOSE_PHOTO = 1;//从相册选择
    public final static int REQUEST_CAMERA = 2;//拍照
    public final static int MODIFY_NAME = 3;
    public final static String KEY_RESULT = "picker_result";
    private String name = null;
    private ModifyUserInfoPresenter modifyUserInfoPresenter;

    @Override
    public void initPresenter() {
        modifyUserInfoPresenter = new ModifyUserInfoPresenterImpl(this);
        modifyUserInfoPresenter.getUserInfo();
    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity_modify_user_info;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        rl_user_name = (RelativeLayout) view.findViewById(R.id.rl_user_name);
        rl_user_sex = (RelativeLayout) view.findViewById(R.id.rl_user_sex);
        rl_user_birth = (RelativeLayout) view.findViewById(R.id.rl_user_birth);
        civ_user_photo = (CircleImageView) view.findViewById(R.id.civ_user_photo);
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        tv_user_sex = (TextView) view.findViewById(R.id.tv_user_sex);
        tv_user_birth = (TextView) view.findViewById(R.id.tv_user_birth);
        rl_user_photo = (RelativeLayout) view.findViewById(R.id.rl_user_photo);

        tv_name.setText(getString(R.string.personal_info_title));

        rl_big_back.setOnClickListener(this);
        rl_user_photo.setOnClickListener(this);
        rl_user_name.setOnClickListener(this);
        rl_user_sex.setOnClickListener(this);
        rl_user_birth.setOnClickListener(this);


    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {
        if (!StringUtils.isEmpty(name)) {
            tv_user_name.setText(name);
        }
    }

    @Override
    public void destroy() {

    }

    private UserPopupWindow userPopupWindow;
    private int flag = 0;

    @Override
    public void onClick(View v) {
        if (v.equals(rl_big_back)){
            finish();
        }
        if (v.equals(rl_user_photo)){
            flag = 2;
            userPopupWindow = new UserPopupWindow(this, 2, new TitleListener(), new OKListener(), null, new CancelListener());
            userPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
        if (v.equals(rl_user_name)){
            Intent intent = new Intent(this, ModifyUserNameActivity.class);
            startActivityForResult(intent, MODIFY_NAME);
        }
        if (v.equals(rl_user_sex)){
            flag = 3;
            userPopupWindow = new UserPopupWindow(this, 3, new TitleListener(), new OKListener(), new OK2Listener(), new CancelListener());
            userPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
        if (v.equals(rl_user_birth)){
            chooseBirth();
        }
    }

    /**
     * 利用控件显示生日
     */
    public void chooseBirth() {
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(0,this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                tv_user_birth.setTextColor(Color.parseColor("#999999"));
                tv_user_birth.setTextSize(13);
//                String date = dateDesc.substring(0, dateDesc.lastIndexOf("-"));//2016-02  效果没有day

                //将选择的日期和当前日期比较,只能小于等于现在的时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());
                String nowDate = sdf.format(curDate);
                long result = 0;
                try {
                    Date now = sdf.parse(nowDate);
                    Date date = sdf.parse(dateDesc);
                    result = now.getTime() - date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (result < 0) {//选择的日期是未来日期
                    ToastUtils.showShort(getString(R.string.select_true_time));
//                    tv_user_birth.setText(nowDate);
                    OdyApplication.putValueByKey("isClosePop", false);//不关闭日期选择的弹窗
                } else {
                    tv_user_birth.setText(dateDesc);//不用上面的截取,显示全部生日时间2016-02-24
                    OdyApplication.putValueByKey("isClosePop", true);//关闭日期选择的弹窗
                }
            }
        })
                .colorTitleBackground(getResources().getColor(R.color.white))//第一行的背景颜色
                .textConfirm(getString(R.string.confirm)) //右侧button的text内容
                .textCancel(getString(R.string.cancel)) //左侧button的text内容
                .colorConfirm(getResources().getColor(R.color.theme_color))//button的textColor
                .colorCancel(getResources().getColor(R.color.theme_color)) //Color.parseColor("#56BA22")
                .btnTextSize(18) // 两个button的 text size
                .viewTextSize(20) //年月日三者的textSize
                .minYear(1900) //min year in loop
                .maxYear(Calendar.getInstance().get(Calendar.YEAR) + 1) // max year in loop
                .build();
        pickerPopWin.showPopWin(this);
    }

    public class TitleListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (flag == 2) {
                //拍照
                showCamera();
//            Intent intent = new Intent(ModifyUserInfoActivity.this, PhotoPickerActivity.class);
//            intent.putExtra("take_photo", 100);
//            startActivityForResult(intent, CHOOSE_PHOTO);
            } else if (flag == 3) {//性别
                tv_user_sex.setText(getString(R.string.man));
            }
            userPopupWindow.dismiss();
        }
    }

    public class OKListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (flag == 2) {
                //我的相册
                Intent intent = new Intent(ModifyUserInfoActivity.this, PhotoPickerActivity.class);
                intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, false);//点击"我的相册"后跳转的界面里不出现拍照操作
                intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, 1);
                startActivityForResult(intent, CHOOSE_PHOTO);
            } else if (flag == 3) {
                tv_user_sex.setText(getString(R.string.woman));
            }
            userPopupWindow.dismiss();
        }
    }

    public class OK2Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (flag == 3) {
                tv_user_sex.setText(getString(R.string.sex_secrecy));
            }
            userPopupWindow.dismiss();
        }
    }

    public class CancelListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //取消
            userPopupWindow.dismiss();
        }
    }

    /**
     * 拍照时存储拍照结果的临时文件
     */
    private File mTmpFile;

    /**
     * 选择相机
     */
    private void showCamera() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            mTmpFile = OtherUtils.createFile(getApplicationContext());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            Toast.makeText(getApplicationContext(),
                   "...", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<String> mSelectList = new ArrayList<String>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                if (result != null && result.size() > 0) {
//                    Log.e("test","choose path============"+result.get(0));
                    GlideUtil.display(ModifyUserInfoActivity.this, result.get(0)).into(civ_user_photo);
                    String path = BitmapUtil.saveBitmapFile(BitmapUtil.getSmallBitmap(result.get(0), BitmapUtil.UPLOAD_MIDDLE_IMAGE_MAX_720, BitmapUtil.UPLOAD_MIDDLE_IMAGE_MAX_720));
                    modifyUserInfoPresenter.uploadFile(path);
                }
            }
        } else if (requestCode == REQUEST_CAMERA) { // 相机拍照完成后，返回图片路径
            if (resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    GlideUtil.display(ModifyUserInfoActivity.this, mTmpFile.getAbsolutePath()).into(civ_user_photo);
                    String path = BitmapUtil.saveBitmapFile(BitmapUtil.getSmallBitmap(mTmpFile.getAbsolutePath(), BitmapUtil.UPLOAD_MIDDLE_IMAGE_MAX_720, BitmapUtil.UPLOAD_MIDDLE_IMAGE_MAX_720));
                    modifyUserInfoPresenter.uploadFile(path);
//                    Log.e("test","take photo path============"+mTmpFile.getAbsolutePath());
                }
            } else {
                if (mTmpFile != null && mTmpFile.exists()) {
                    mTmpFile.delete();
                }
            }
        } else if (requestCode == MODIFY_NAME) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    return;
                }
                name = data.getStringExtra("name");
            }
        }
    }
}
