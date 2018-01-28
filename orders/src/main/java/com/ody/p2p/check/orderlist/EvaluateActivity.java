package com.ody.p2p.check.orderlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.check.R;
import com.ody.p2p.recycleviewutils.FullyLinearLayoutManager;
import com.ody.p2p.utils.BitmapUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.odysaxx.photograph.PhotoPickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvaluateActivity extends BaseActivity implements View.OnClickListener, EvaluateAdapter.OnEvaluateAdapterListener, EvaluateView {

    public EvaluateAdapter adapter;
    private AdditionalEvaluateAdapter additionalEvaluateAdapter;
    public FullyLinearLayoutManager manager;
    public RecyclerView rv;
    public ImageView rl_back;
    public TextView tv_title, tv_three_dots;
    public List<EvaluateData> mData;
    public List<ArrayList<PhotoFileBean>> mSelected;
    public final int TAKE_PHOTO = 0x101;
    public final int SHOW_IMAGE = 0x011;
    public Intent mIntent;
    public int currentPath;
    public EvaluatePresenter presenter;
    public String orderCode;//商品订单
    //    private CommitEvaluateData mCommitData;
    public CheckBox rb;
    public TextView tv_submit;
    public int isAddtional=0;

    @Override
    public void initPresenter() {
        presenter = new EvaluatePresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_evaluate;
    }

    @Override
    public void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(R.string.my_evaluation);
        tv_three_dots = (TextView) view.findViewById(R.id.tv_three_dots);
        rl_back = (ImageView) view.findViewById(R.id.iv_back);
        rb = (CheckBox) view.findViewById(R.id.rb_ishidden);
        tv_submit = (TextView) view.findViewById(R.id.tv_submit);


        rv = (RecyclerView) view.findViewById(R.id.rv_evaluate);
        manager = new FullyLinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mData = new ArrayList<>();
        mSelected = new ArrayList<ArrayList<PhotoFileBean>>();
        for (int i = 0; i < mData.size(); i++) {
            mSelected.add(new ArrayList<PhotoFileBean>());
        }
        orderCode = getIntent().getStringExtra("orderCode");
        isAddtional=getIntent().getIntExtra("isAddtional",0);
        if(isAddtional==0){
            adapter = new EvaluateAdapter(this, mData, mSelected);
            adapter.setOnEvaluateListener(this);
            rv.setAdapter(adapter);
        }else{
            additionalEvaluateAdapter=new AdditionalEvaluateAdapter(this);
            rv.setAdapter(additionalEvaluateAdapter);
        }
        rv.setLayoutManager(manager);


//        mCommitData = new CommitEvaluateData();
//        CommitEvaluateData.userMPCInputVO vo = mCommitData.new userMPCInputVO();
//        mCommitData.setUserMPCInputVO(vo);
//        mCommitData.getUserMPCInputVO().UserMPCommentVOList = new ArrayList<>();


        initEvent();
    }

    private void initEvent() {
        rl_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_three_dots.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        presenter.initEvaluate(orderCode);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();

        } else if (v.getId() == R.id.tv_submit) {
            if(isAddtional==0){
                evaluateCommit();
            }else{
                addtionalCommit();
            }
        } else if (v.getId() == R.id.tv_three_dots) {
            presenter.evaluateRule();
        }
    }

    private void addtionalCommit(){
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            jsonObject.put("userAddMPCommentVOList",jsonArray);
            for(int i=0;i<additionalEvaluateAdapter.getAll().size();i++){
                if(!TextUtils.isEmpty(additionalEvaluateAdapter.getAll().get(i).addtionalcontent)){
                    for(int j=0;j<additionalEvaluateAdapter.getAll().size();j++){
                        if(!TextUtils.isEmpty(additionalEvaluateAdapter.getAll().get(j).addtionalcontent)){
                            JSONObject jsonbean=new JSONObject();
                            jsonbean.put("addContent",additionalEvaluateAdapter.getAll().get(j).addtionalcontent);
                            jsonbean.put("mpCommentId",additionalEvaluateAdapter.getAll().get(j).id);
                            jsonArray.put(j,jsonbean);
                        }
                    }
                    String inputJson=jsonObject.toString();
                    presenter.submitAddtional(inputJson);
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void evaluateCommit(){
        List<EvaluateData> list = adapter.getEvaluateData();
        for (int i = 0; i < mSelected.size(); i++) {
            List<PhotoFileBean> list1 = mSelected.get(i);
            for (int j = 0; j < list1.size(); j++) {
                //图片还未上传，不该出现在这
                if (list1.get(j).getState() == 0) {
                    list1.remove(j);
                    presenter.upLoadPicture(list1.get(j), i);
                    ToastUtils.showStr(getString(R.string.photos_load_wait));
                    return;
                }
                //图片上传中
                if (list1.get(j).getState() == 1) {
                    ToastUtils.showStr(getString(R.string.photos_load_wait));
                    return;
                }
                //图片应该删除的，也不该出现在这
                if (list1.get(j).getState() == 3) {
                    mSelected.get(i).remove(j);
                }

            }
        }

        CommitEvaluateData data = new CommitEvaluateData();
//            CommitEvaluateData.userMPCInputVO vo = data.new userMPCInputVO();
//            data.setUserMPCInputVO(vo);
//            data.UserMPCInputVO = data.new userMPCInputVO();
        data.userMPCommentVOList = new ArrayList<EvaluateData>();
        if (rb.isChecked()) {
            data.setIsHideUserName(1);
        } else {
            data.setIsHideUserName(0);
        }
        data.setUserMPCommentVOList(list);
        for (EvaluateData da : data.getUserMPCommentVOList()) {
            if (da.getContent().length() < 1) {
                ToastUtils.failToast(getString(R.string.evaluate_isnot_nill));
                return;
            }
        }
        presenter.commiEvaluate(data);
    }

    @Override
    public void clickCamera(int position) {
        currentPath = position;
        int maxNum = 5;
        if (null != mSelected && mSelected.size() > 0) {
            maxNum = 5 - mSelected.get(position).size();
        }
        mIntent = new Intent(EvaluateActivity.this, PhotoPickerActivity.class);
        mIntent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
        mIntent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
        mIntent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, maxNum);
        startActivityForResult(mIntent, TAKE_PHOTO);
    }

    @Override
    public void showBitmap(int position, ArrayList<PhotoFileBean> list) {
        currentPath = position;
        mIntent = new Intent(EvaluateActivity.this, ShowImageViewActivity.class);
        mIntent.putExtra("data", list);
        startActivityForResult(mIntent, SHOW_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    List<String> list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                    if (mSelected != null && mSelected.size() > currentPath) {
                        for (int i = 0; i < list.size(); i++) {
                            if (isContainPhoto(mSelected.get(currentPath), list.get(i))) {
                                continue;
                            }
                            PhotoFileBean bean = new PhotoFileBean();
                            bean.setFilePath(list.get(i));
                            bean.setState(0);
                            mSelected.get(currentPath).add(bean);
                        }
                        for (int i = 0; i < mSelected.get(currentPath).size(); i++) {
                            PhotoFileBean bean = mSelected.get(currentPath).get(i);
                            if (bean.getState() == 0) {
//                                Bitmap bitmap = BitmapUtil.getSmallBitmap(bean.getFilePath(), 140, 140);
                                new AsyncTask<PhotoFileBean, Void, PhotoFileBean>() {
                                    @Override
                                    protected PhotoFileBean doInBackground(PhotoFileBean... params) {
                                        compressAndGenImage(BitmapUtil.getBitmap(params[0].getFilePath()), params[0].getFilePath(), 2 * 1024);
                                        return params[0];
                                    }

                                    @Override
                                    protected void onPostExecute(PhotoFileBean photoFileBean) {

                                        super.onPostExecute(photoFileBean);
                                        presenter.upLoadPicture(photoFileBean, currentPath);
                                    }

                                    //                                    @Override
//                                    protected String doInBackground(String... params) {
//                                        compressAndGenImage(BitmapUtil.getBitmap(params[0]), params[0], 2 * 1024);
//                                        return params[0];
//                                    }
//
//                                    @Override
//                                    protected void onPostExecute(String aVoid) {
//                                        super.onPostExecute(aVoid);
//                                        presenter.upLoadPicture(bean, currentPath);
//                                    }
                                }.execute(bean);


                                bean.setState(1);
                            }
                        }
                    }
                    adapter.notifyItemChanged(currentPath);
                }
                break;
            case SHOW_IMAGE:
                break;
        }
    }

    @Override
    public void showEvaluateInfo(EvaluateBean response) {
        mData.clear();
        if (response != null && response.code.equals("0") && response.getData() != null && response.getData().size() > 0) {
            mData.addAll(response.getData());
            mSelected.clear();

            adapter.mEvaluateData = new ArrayList<>();
            for (int i = 0; i < mData.size(); i++) {
                EvaluateData dat = new EvaluateData();
                adapter.mEvaluateData.add(dat);
            }
            for (int i = 0; i < mData.size(); i++) {
                mSelected.add(new ArrayList<PhotoFileBean>());
            }
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void commitEvaluate(BaseRequestBean response) {
        ToastUtils.sucessToast(getString(R.string.evaluate_succeed));
        finish();
    }

    @Override
    public void upLoadResult(UpLoadBean response, PhotoFileBean bean, int position) {
        //上传成功
        if (response != null && response.code.equals("0") && response.getData().getFilePath() != null) {
            bean.setState(2);
            int count = mSelected.get(position).size();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                if (mSelected.get(position).get(i).getFilePath().equals(bean.getFilePath())) {
                    mSelected.get(position).get(i).setInternetPath(response.getData().getFilePath());
                }
                list.add(mSelected.get(position).get(i).getInternetPath());
            }
            adapter.mEvaluateData.get(position).setMpcPicList(list);
        }
        //上传失败
        else {
            bean.setState(3);
            if (mSelected.get(position).contains(bean)) {
                mSelected.get(position).remove(bean);
                adapter.notifyItemChanged(position);
            }
        }


    }

    @Override
    public void remove(PhotoFileBean bean, int position) {
        mSelected.get(position).remove(bean);
        adapter.notifyItemChanged(position);
    }

    private boolean isContainPhoto(List<PhotoFileBean> data, String filePath) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getFilePath().equals(filePath)) {
                return true;
            }
        }
        return false;
    }

    public void compressAndGenImage(Bitmap bitmap, String outputPath, int maxSize) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;

        bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);

        while (os.toByteArray().length / 1024 > maxSize) {
            os.reset();
            options -= 20;
            if (options < 10) {
                options = 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        try {
            FileOutputStream fos = new FileOutputStream(outputPath);
            fos.write(os.toByteArray());
            fos.flush();
            fos.close();
            bitmap.recycle();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void skipToWeb(String url) {
        JumpUtils.ToWebActivity(JumpUtils.H5, url, 2, -0, getString(R.string.evaluate_rule));
    }
}
