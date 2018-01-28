package com.netease.nim.demo.redpacket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.redpacket.model.RedPacketModel;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.util.IOSDialog;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import static com.netease.nim.demo.R.id.input_money;
import static java.lang.Double.parseDouble;

/**
 * 红包发送UI
 */
public class RedpacketActivity extends UI implements View.OnClickListener {

    private TextView amount;
    private EditText mInputMoney;
    private Button redpacket;
    private boolean canSend = false;
    private TextView notice;
    private EditText mContemt;
    private TextView notice_top;
    private SessionTypeEnum sessionType;
    private String mType;
    private View mTeam_redpacket;
    private View mTeam_redpacket_count;
    private       int redPacketType        = 0;
    private final int RANDOMPACKET         = 0;  //随机红包
    private final int IMMOBILIZATIONPACKET = 1; //固定红包
    private EditText mInput_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redpacket);
        //区分红包类型
        mType = getIntent().getStringExtra("TYPE");

        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "发红包";
        setToolBar(R.id.toolbar, options);
        initView();
        initListener();
        changeView();
        initData();
    }

    /**
     * 转换群红包或者个人红包
     */
    private void changeView() {
        switch (mType) {
            case "P2P": //个人红包


                break;

            case "TEAM"://群红包
                mTeam_redpacket.setVisibility(View.VISIBLE);
                mTeam_redpacket_count.setVisibility(View.VISIBLE);

                break;
        }

    }


    public static void startActivityForResult(Activity activity, String type, int reqCode) {
        Intent intent = new Intent();
        intent.setClass(activity, RedpacketActivity.class);
        intent.putExtra("TYPE", type);
        activity.startActivityForResult(intent, reqCode);
    }

    private void initData() {

    }

    private void initView() {
        mInputMoney = (EditText) findViewById(input_money);
        amount = (TextView) findViewById(R.id.amount);
        redpacket = (Button) findViewById(R.id.redpacket);
        notice = (TextView) findViewById(R.id.notice);
        mContemt = (EditText) findViewById(R.id.content);
        notice_top = (TextView) findViewById(R.id.notice_top);

        mTeam_redpacket = findViewById(R.id.team_redpacket);
        initTeamRedPacket();

        mTeam_redpacket_count = findViewById(R.id.team_redpacket_count);
        initTeamRedPacketCount();


    }


    private void initTeamRedPacket() {
        View random = mTeam_redpacket.findViewById(R.id.random);
        final TextView tv_random = (TextView) mTeam_redpacket.findViewById(R.id.tv_random);
        final View line_random = mTeam_redpacket.findViewById(R.id.line_random);

        View immobilization = mTeam_redpacket.findViewById(R.id.immobilization);
        final TextView tv_immobilization = (TextView) mTeam_redpacket.findViewById(R.id.tv_immobilization);
        final View line_immobilization = mTeam_redpacket.findViewById(R.id.line_immobilization);


        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_random.setTextColor(getResources().getColor(R.color.jrmf_rp_red));
                tv_immobilization.setTextColor(getResources().getColor(R.color.black));
                line_random.setBackgroundColor(getResources().getColor(R.color.jrmf_rp_red));
                line_immobilization.setBackgroundColor(getResources().getColor(R.color.white));

                redPacketType = RANDOMPACKET;

                String money = mInputMoney.getText().toString();
                if (money.equals("")) {
                    amount.setText("0.00");
                } else {
                    amount.setText(String.format("%.2f", parseDouble(money)));
                }

                checkRedPacketCount();
            }
        });

        immobilization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_immobilization.setTextColor(getResources().getColor(R.color.jrmf_rp_red));
                tv_random.setTextColor(getResources().getColor(R.color.black));
                line_immobilization.setBackgroundColor(getResources().getColor(R.color.jrmf_rp_red));
                line_random.setBackgroundColor(getResources().getColor(R.color.white));

                redPacketType = IMMOBILIZATIONPACKET;

                String money = mInputMoney.getText().toString();
                if (mInput_count.getText().toString().equals("")) {
                    return;
                }
                if (money.equals("")) {

                    amount.setText("0.00");
                } else {
                    double amountMoney = parseDouble(money) * Integer.parseInt(mInput_count.getText().toString());
                    if (amountMoney == 0) {
                        amount.setText("0.00");
                    } else {
                        amount.setText(String.format("%.2f", amountMoney));
                    }
                }


                checkRedPacketCount();
            }
        });

    }


    private void checkRedPacketCount() {
        try {
            String count = mInput_count.getText().toString();
            String money = mInputMoney.getText().toString();

            if (count.equals("")) {
                count = "0";
            }

            if (money.equals("")) {
                money = "0";
            }

            if (!mInput_count.equals("")) {
                if (Integer.parseInt(count) > 200 || parseDouble(amount.getText().toString()) > 10000 || parseDouble(money) > 10000) {
                    notice_top.setVisibility(View.VISIBLE);
                    if (parseDouble(count) > 200) {
                        notice_top.setText("一次最多发200个红包");
                    } else if (parseDouble(amount.getText().toString()) > 10000 || parseDouble(money) > 10000) {
                        notice_top.setText("红包金额不可超过10000元");
                    }


                } else {
                    notice_top.setVisibility(View.GONE);
                }
            }

            if (count.equals("") || money.equals("") || Integer.parseInt(count) == 0 || Integer.parseInt(count) > 200 || (Double.parseDouble(money) * parseDouble(count) > 10000) && redPacketType == IMMOBILIZATIONPACKET || Double.parseDouble(amount.getText().toString()) > 10000) {
                redpacket.setBackgroundResource(R.drawable.border_ora);
                canSend = false;
            } else {
                redpacket.setBackgroundResource(R.drawable.border_red);
                canSend = true;
            }
        } catch (Exception e) {

        }
    }


    private void initTeamRedPacketCount() {
        mInput_count = (EditText) mTeam_redpacket_count.findViewById(R.id.input_count);
        mInput_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    String count = s.toString();


                    if (redPacketType == IMMOBILIZATIONPACKET) {
                        if (count.equals("")) {
                            amount.setText("0.00");
                            return;
                        }


                        String money = mInputMoney.getText().toString();
                        if (money.equals("")) {

                            amount.setText("0.00");
                        } else {
                            double amountMoney = parseDouble(money) * Integer.parseInt(count);
                            if (amountMoney == 0) {
                                amount.setText("0.00");
                            } else {
                                amount.setText(String.format("%.2f", amountMoney));
                            }
                        }


                    }
                    checkRedPacketCount();


                } catch (Exception e) {

                }


            }
        });
    }


    private void initListener() {
        mInputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mType.equals("P2P")) {

                    String money = s.toString();
                    if (money.equals("")) {

                        amount.setText("0.00");
                    } else {
                        amount.setText(String.format("%.2f", parseDouble(money)));
                    }

                    if (parseDouble(amount.getText().toString()) != 0 && parseDouble(amount.getText().toString()) <= 500) {
                        redpacket.setBackgroundResource(R.drawable.border_red);
                        canSend = true;
                    } else {
                        redpacket.setBackgroundResource(R.drawable.border_ora);
                        canSend = false;
                    }

                    if (parseDouble(amount.getText().toString()) > 500) {
                        notice_top.setVisibility(View.VISIBLE);
                        notice_top.setText("单个红包金额不可超过500元");
                    } else {
                        notice_top.setVisibility(View.GONE);
                    }


                } else if (mType.equals("TEAM")) {


                    String money = s.toString();
                    if (redPacketType == IMMOBILIZATIONPACKET) {


                        if (money.equals("")) {

                            amount.setText("0.00");
                        } else {
                            String count = mInput_count.getText().toString();
                            if (!count.equals("")) {
                                double amountMoney = parseDouble(money) * Integer.parseInt(count);
                                if (amountMoney == 0) {
                                    amount.setText("0.00");
                                } else {
                                    amount.setText(String.format("%.2f", amountMoney));
                                }
                            }
                        }

                    } else {

                        if (money.equals("")) {

                            amount.setText("0.00");
                        } else {
                            amount.setText(String.format("%.2f", parseDouble(money)));
                        }

                    }


                    String count = mInput_count.getText().toString();
                    if (count.equals("") || Integer.parseInt(count) == 0 || Integer.parseInt(count) > 200 || parseDouble(amount.getText().toString()) > 10000 || parseDouble(mInputMoney.getText().toString()) > 10000) {
                        redpacket.setBackgroundResource(R.drawable.border_ora);
                        canSend = false;
                    } else {
                        redpacket.setBackgroundResource(R.drawable.border_red);
                        canSend = true;
                    }


                    checkRedPacketCount();


                }
            }
        });


        redpacket.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.redpacket) {  //红包发送
            if (canSend) {
                sendRedpacket();
            }
        }
    }

    private void sendRedpacket() {
        final IOSDialog dialog = new IOSDialog(this, R.style.customDialog, R.layout.dialog_sendredpacket);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView content = (TextView) dialog.findViewById(R.id.content);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);

        content.setText("红包" + amount.getText().toString() + "元");

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送
                RedPacketModel redPacketModel = new RedPacketModel();
                redPacketModel.setAmount(mInputMoney.getText().toString());
                redPacketModel.setCount(mInput_count.getText().toString().equals("") ? 1 : Integer.parseInt(mInput_count.getText().toString()));
                String msg = mContemt.getText().toString().trim();
                redPacketModel.setMsg(msg.equals("") ? mContemt.getHint().toString() : msg);
                if (mType.equals("TEAM") && redPacketType == RANDOMPACKET) {
                    redPacketModel.setType(2);
                } else {
                    redPacketModel.setType(1);
                }
                Intent intent = new Intent();
                intent.putExtra("data", redPacketModel);
                setResult(RESULT_OK, intent);
                finish();
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
}
