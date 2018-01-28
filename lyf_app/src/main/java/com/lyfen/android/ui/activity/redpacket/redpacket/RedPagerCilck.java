package com.lyfen.android.ui.activity.redpacket.redpacket;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyfen.android.ui.activity.redpacket.redpacket.redpacketlist.RedPacketListActivity;
import com.ody.p2p.main.R;


/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public class RedPagerCilck {
    private ImageView readeSelect1;
    private ImageView readeSelect2;
    private TextView myRedText;
    private TextView cancle, getRed, sendRed;
    private RedPacketActivity activity;

    private Dialog dialog;

    public RedPagerCilck(RedPacketActivity activity, View view) {
        this.activity = activity;
        readeSelect1 = (ImageView) view.findViewById(R.id.readeSelect1);
        readeSelect2 = (ImageView) view.findViewById(R.id.readeSelect2);
        myRedText = (TextView) view.findViewById(R.id.myRedText);

        initView();
    }

    private void initView() {
        readeSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setDisplay(0);
            }
        });
        readeSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setDisplay(1);
            }
        });
        myRedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog == null) {
                    dialog = new Dialog(activity, R.style.transparentDialog);
                    dialog.setContentView(R.layout.dialog_envelo);
                    cancle = (TextView) dialog.findViewById(R.id.cancle);
                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    });
                    getRed = (TextView) dialog.findViewById(R.id.getRed);
                    getRed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(activity, RedPacketListActivity.class);
                            intent.putExtra(RedPacketListActivity.METHOD, RedPacketListActivity.REDPACKET_GETRED);
                            activity.startActivity(intent);

                        }
                    });
                    sendRed = (TextView) dialog.findViewById(R.id.sendRed);
                    sendRed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(activity, RedPacketListActivity.class);
                            intent.putExtra(RedPacketListActivity.METHOD, RedPacketListActivity.REDPACKET_SENDRED);
                            activity.startActivity(intent);

                        }
                    });
                    dialog.show();
                } else {
                    dialog.show();
                }

            }
        });
    }


}
