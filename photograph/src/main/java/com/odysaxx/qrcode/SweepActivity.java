package com.odysaxx.qrcode;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.lling.photopicker.R;
import com.odysaxx.util.BitmapUtils;
import com.odysaxx.zxingview.ScanView.ScannerView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxs on 2016/6/3.
 */
public class SweepActivity extends Activity implements ScannerView.ResultHandler, ScannerView.QrSize, View.OnClickListener {

    private ScannerView scanView;

    private ImageView iv_back;
    private ImageView iv_led;
    private TextView tv_photo;
    private ImageView iv_photo;
    private LinearLayout ll_sweep;

    private boolean led_flag = false;
    public static final int RESULT_IMG_OK = 1;
    private boolean is_photo = false;

    public static final int CAMERA_PER = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        doBusiness();
    }

    public void initView() {
        scanView = new ScannerView(this);
        scanView.setContentView(R.layout.layout_sweep);
        scanView.setQrSize(this);
        setContentView(scanView);
        setupFormats();
        iv_back = (ImageView) scanView.findViewById(R.id.iv_back);
        iv_led = (ImageView) scanView.findViewById(R.id.iv_led);
        //tv_photo = (TextView) scanView.findViewById(R.id.tv_photo);
        iv_photo = (ImageView) scanView.findViewById(R.id.iv_photo);
        ll_sweep = (LinearLayout) scanView.findViewById(R.id.ll_sweep);
    }

    public void initListener() {
        iv_back.setOnClickListener(this);
        iv_led.setOnClickListener(this);
        //tv_photo.setOnClickListener(this);
    }

    public void doBusiness() {
    }

    @Override
    protected void onResume() {
        if (is_photo) {
            iv_photo.setVisibility(View.VISIBLE);
            ll_sweep.setVisibility(View.GONE);
        } else {
            ll_sweep.setVisibility(View.VISIBLE);
            iv_photo.setVisibility(View.GONE);
            try {
                scanView.setResultHandler(this);
                scanView.startCamera(-1);
                scanView.setFlash(false);
                scanView.setAutoFocus(true);
            } catch (Exception e) {
                finish();
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            scanView.stopCamera();
        } catch (Exception e) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(iv_back)) {
            finish();
        } else if (v.equals(iv_led)) {
            led_flag = !led_flag;
            try {
                scanView.setFlash(led_flag);
            } catch (Exception e) {
                finish();
            }
            if (led_flag) {
                iv_led.setImageResource(R.drawable.icon_led_click);
            } else {
                iv_led.setImageResource(R.drawable.icon_led);
            }
        } else if (v.equals(tv_photo)) {
            is_photo = true;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, RESULT_IMG_OK);
        }
    }

    @Override
    public Rect getDetectRect() {
        View view = findViewById(R.id.scan_window);
        int top = ((View) view.getParent()).getTop() + view.getTop();
        int left = view.getLeft();
        int width = view.getWidth();
        int height = view.getHeight();
        Rect rect = null;
        if (width != 0 && height != 0) {
            rect = new Rect(left, top, left + width, top + height);
            addLineAnim(rect);
        }
        return rect;
    }

    @Override
    public void handleResult(Result rawResult) {
        String result = rawResult.toString();
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        formats.add(BarcodeFormat.EAN_13);
        if (scanView != null) {
            try {
                scanView.setFormats(formats);
            } catch (Exception e) {
                finish();
            }
        }
    }

    private void addLineAnim(Rect rect) {
        ImageView imageView = (ImageView) findViewById(R.id.scanner_line);
        imageView.setVisibility(View.VISIBLE);
        if (imageView.getAnimation() == null) {
            TranslateAnimation anim = new TranslateAnimation(0, 0, 0, rect.height());
            anim.setDuration(3000);
            anim.setRepeatCount(Animation.INFINITE);
            imageView.startAnimation(anim);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_IMG_OK) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    final Bitmap bitmapScan = BitmapUtils.createBitmapBySize(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4);
                    iv_photo.setImageBitmap(bitmapScan);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                scanView.scanDecode(bitmapScan);
                            } catch (Exception e) {
                            }
                        }
                    }).start();
                } catch (FileNotFoundException e) {
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
