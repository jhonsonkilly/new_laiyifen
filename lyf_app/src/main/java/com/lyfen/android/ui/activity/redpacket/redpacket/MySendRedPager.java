package com.lyfen.android.ui.activity.redpacket.redpacket;

import android.app.Dialog;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lyfen.android.ui.activity.redpacket.RedPacketNetWorkApi;
import com.lyfen.android.ui.activity.redpacket.RedPacketRetrofit;
import com.lyfen.android.ui.activity.redpacket.entity.RedPacketCreateEntity;
import com.lyfen.android.ui.activity.redpacket.entity.RedPacketInitEntity;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.webactivity.WebActivity;

import java.math.BigDecimal;
import java.util.HashMap;

import dsshare.ShareBean;
import dsshare.SharePopupWindow;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class MySendRedPager {
    private TextView luckRed;
    private TextView normalRed;
    private TextView allAmountTile;
    private TextView text;
    private TextView sendRed;
    private TextView allMoney;
    private TextView ycardAmount, payAmount, payRed, dialogTitle;
    private EditText numberEdit;
    private TextView amountEdit;
    private TextView sayText;
    private int number;
    private double amount, allAmount, deposit;
    private RedPacketActivity activity;
    public boolean isLuck = true;


    public MySendRedPager(final RedPacketActivity activity, View view, RedPacketInitEntity loadResult, boolean isLuckRed) {
        this.activity = activity;
        this.isLuck = isLuckRed;

        try {
            this.deposit = Double.parseDouble(loadResult.data.deposit);
        } catch (Exception e) {

        }


        luckRed = (TextView) view.findViewById(R.id.luckRed);
        normalRed = (TextView) view.findViewById(R.id.normalRed);
        allAmountTile = (TextView) view.findViewById(R.id.allAmountTile);
        text = (TextView) view.findViewById(R.id.text);
        sendRed = (TextView) view.findViewById(R.id.sendRed);
        allMoney = (TextView) view.findViewById(R.id.allMoney);
        numberEdit = (EditText) view.findViewById(R.id.number);
        amountEdit = (TextView) view.findViewById(R.id.amount);
        sayText = (TextView) view.findViewById(R.id.sayText);
        luckRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLuck) {
                    isLuck = true;
                    changeState();
                }
            }
        });
        normalRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLuck) {
                    isLuck = false;
                    changeState();
                }


            }
        });
        sendRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(activity, R.style.transparentDialog);
                dialog.setContentView(R.layout.dialog_red);
                ycardAmount = (TextView) dialog.findViewById(R.id.ycardAmount);
                dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
                payAmount = (TextView) dialog.findViewById(R.id.payAmount);
                payRed = (TextView) dialog.findViewById(R.id.payRed);
                payRed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (deposit >= allAmount) {
                            dialog.dismiss();
                            sendRed();
                        } else {


                            JumpUtils.ToWebActivity(JumpUtils.H5, "http://m.laiyifen.com/my/youdianCard.html", WebActivity.NO_TITLE, -1, "");
//                            Intent intent = new Intent(
//                                    activity,
//                                    RechargeActivity.class);
//                            String deposit = PreferenceUtils.getString(PrefrenceKey.DESPOINT, "");
//                            intent.putExtra("despoit", deposit);
//                            activity.startActivityForResult(intent, 0000);
                        }


                    }
                });
//                deposit = Double
//                        .parseDouble(PreferenceUtils.getString(PrefrenceKey.DESPOINT, "")
//                                .equals("") ? "0" : PreferenceUtils.getString(PrefrenceKey.DESPOINT, ""));
                dealDeposit();
                dialog.show();


            }
        });


        inintView();
        changeState();

    }

    private void inintView() {
        numberEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = amountEdit.getText().toString().trim();
                if (str != null && !str.equals("")) {
                    amount = Double.parseDouble(str.toString());
                    if (s.length() > 0) {
                        number = Integer.parseInt(s.toString());
                        sendRed.setEnabled(true);
                        sendRed.setBackgroundResource(R.drawable.btn_yesregister);
                        if (isLuck) {
                            allAmount = amount;
                            allMoney.setText("￥" + amount);
                        } else {
                            allAmount = amount * number;
                            allMoney.setText("￥" + (amount * number));
                        }
                    } else {
                        allMoney.setText("￥0.00");
                        sendRed.setEnabled(false);
                        sendRed.setBackgroundResource(R.drawable.btn_noregister);
                    }
                }

            }
        });
        amountEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                String str = numberEdit.getText().toString().trim();
                if (str != null && !str.equals("")) {
                    number = Integer.parseInt(str);
                    if (arg0.length() > 0) {
                        sendRed.setEnabled(true);
                        sendRed.setBackgroundResource(R.drawable.btn_yesregister);
                        try {
                            amount = Double.parseDouble(arg0.toString());
                        } catch (Exception e) {
                            Toast.makeText(activity, "金额格式不正确", Toast.LENGTH_SHORT).show();
                            amountEdit.setText("");
                        }
                        if (isLuck) {
                            allAmount = amount;
                            allMoney.setText("￥" + amount);
                        } else {
                            allAmount = amount * number;

                            BigDecimal b = new BigDecimal(amount * number);
                            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                            allMoney.setText("￥" + (f1));
                        }
                    } else {
                        allMoney.setText("￥0.00");
                        sendRed.setEnabled(false);
                        sendRed.setBackgroundResource(R.drawable.btn_noregister);
                    }
                }

            }
        });
    }

//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId ()) {
//            case R.id.luckRed:
//                break;
////            case R.id.luckRed:
////                if (!isLuckRed) {
////                    isLuckRed = true;
////                    changeState ();
////                }
////                break;
//////            case R.id.normalRed:
//////                if (isLuckRed) {
//////                    isLuckRed = false;
//////                    changeState ();
//////                }
//////                break;
//////            case R.id.payRed:
//////                if (deposit >= allAmount) {
//////                    sendRed ();
//////                } else {
//////                    Intent intent = new Intent (
//////                            activity,
//////                            RechargeActivity.class);
//////                    String deposit = PreferenceUtils.getString (PrefrenceKey.DESPOINT, "");
//////                    intent.putExtra ("despoit", deposit);
//////                    activity.startActivityForResult (intent, 0000);
//////                }
//////                break;
//////            case R.id.sendRed:
//////                dialog = new Dialog (activity, R.style.transparentDialog);
//////                dialog.setContentView (R.layout.dialog_red);
//////                ycardAmount = (TextView) dialog.findViewById (R.id.ycardAmount);
//////                dialogTitle = (TextView) dialog.findViewById (R.id.dialogTitle);
//////                payAmount = (TextView) dialog.findViewById (R.id.payAmount);
//////                payRed = (TextView) dialog.findViewById (R.id.payRed);
//////                payRed.setOnClickListener (this);
//////                deposit = Double
//////                        .parseDouble (PreferenceUtils.getString (PrefrenceKey.DESPOINT, "")
//////                                .equals ("") ? "0" : PreferenceUtils.getString (PrefrenceKey.DESPOINT, ""));
//////                dealDeposit ();
//////                dialog.show ();
//////                break;
////            default:
////                break;
//        }
//    }

    private void dealDeposit() {
        if (deposit >= allAmount) {
            dialogTitle.setText("悠点卡支付");
            payRed.setText("支付");
        } else {
            dialogTitle.setText("悠点卡支付");
            payRed.setText("去充值");
        }
        ycardAmount.setText("￥" + deposit);
        payAmount.setText("￥" + allAmount);

    }

    private void sendRed() {
        getRedenvelopesCreateBeanObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<RedPacketCreateEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onNext(RedPacketCreateEntity redPacketCreateEntity) {

                        ShareBean.Data data = new ShareBean.Data();
                        data.title = redPacketCreateEntity.data.sharetitle;
                        data.content = redPacketCreateEntity.data.sharecontent;
                        data.sharePicUrl = redPacketCreateEntity.data.shareimg;
                        data.linkUrl = redPacketCreateEntity.data.shareurl;
                        String log = new Gson().toJson(data);
                        Log.i("mySend", log);
                        SharePopupWindow shareindow = new SharePopupWindow(activity, SharePopupWindow.SHARE_REDPACKET, log);
                        shareindow.showAtLocation(luckRed, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    }
                });

    }

    private Observable<RedPacketCreateEntity> getRedenvelopesCreateBeanObservable() {
        try {
            RedPacketRetrofit retrofitHelper = new RedPacketRetrofit(RedPacketActivity.BETA);
            final HashMap<String, String> stringStringHashMap = new HashMap<>();

            stringStringHashMap.put("method", "envelop.create");
            stringStringHashMap.put("number", number + "");
            stringStringHashMap.put("amount", amount + "");
            stringStringHashMap.put("type", isLuck ? "1" : "2");

            stringStringHashMap.put("title", sayText.getText().toString());

            stringStringHashMap.put("session", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
            RedPacketNetWorkApi redPacketService = retrofitHelper.getRedPacketService();
            Observable<RedPacketCreateEntity> observable = null;

            observable = redPacketService.redpacketCreate(stringStringHashMap);
            return observable;
        } catch (Exception e) {

        }
        return null;


    }

    private void changeState() {
        if (isLuck) {
            allAmountTile.setText("总金额");
            text.setVisibility(View.VISIBLE);
            luckRed.setBackgroundResource(R.drawable.red_select1);
            luckRed.setTextColor(ContextCompat.getColor(activity, R.color.white));
            normalRed.setBackgroundResource(R.drawable.red_select4);
            normalRed.setTextColor(ContextCompat.getColor(activity, R.color.cfff58c5b));
        } else {
            allAmountTile.setText("单个金额");
            text.setVisibility(View.INVISIBLE);
            luckRed.setBackgroundResource(R.drawable.red_select2);
            luckRed.setTextColor(ContextCompat.getColor(activity, R.color.cfff58c5b));
            normalRed.setBackgroundResource(R.drawable.red_select3);
            normalRed.setTextColor(ContextCompat.getColor(activity, R.color.white));
        }
        allMoney.setText("￥0.00");
        numberEdit.setText("");
        amountEdit.setText("");
        sendRed.setEnabled(false);
        sendRed.setBackgroundResource(R.drawable.btn_noregister);

    }


}
