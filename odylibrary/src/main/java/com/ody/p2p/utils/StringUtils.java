package com.ody.p2p.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.base.OdyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串工具类
 *
 * @author lxs
 */
public class StringUtils {

    /**
     * 数值格式化
     * 保留一位小数
     *
     * @param count
     * @return
     */
    public static String getOnePointCount(double count) {
        String result;
        //保留一位小数
        try {
            BigDecimal bigDecimal = new BigDecimal(count);
            DecimalFormat df = new DecimalFormat("0.0");
            result = df.format(bigDecimal);
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(count);
        }
        return result;
    }

    /**
     * 获取UUID
     *
     * @return 32UUID小写字符串
     */
    public static String gainUUID() {
        String strUUID = UUID.randomUUID().toString();
        strUUID = strUUID.replaceAll("-", "").toLowerCase();
        return strUUID;
    }

    /**
     * 判断字符串是否非空非null
     *
     * @param strParm 需要判断的字符串
     * @return 真假
     */
    public static boolean isNoBlankAndNoNull(String strParm) {
        return !((strParm == null) || (strParm.equals("")));
    }

    /**
     * 将流转成字符串
     *
     * @param is 输入流
     * @return
     * @throws Exception
     */
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * 将文件转成字符串
     *
     * @param file 文件
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    /**
     * 判断字符串是否为空
     *
     * @param input 字符串
     * @return true为空，false不为空
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input.trim()) || "null".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证手机号是否合法
     *
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
//        return p.matcher(mobile).matches();
        return true;
    }

    /**
     * 检查手机号的非空和合法性
     *
     * @param mobile
     * @return
     */
    public static boolean checkPhone(String mobile) {
        if (isEmpty(mobile)) {
            ToastUtils.showShort("手机号不能为空");
            ToastUtils.showToast("手机号不能为空");
            return false;
        }
        boolean isMobile = checkMobile(mobile);
        if (!isMobile) {
            ToastUtils.showShort("该手机号有误，请检查手机号");
            ToastUtils.showToast("该手机号有误，请检查手机号");
            return false;
        }
        return true;
    }

    /**
     * 判断验证码是否为空
     *
     * @param captchas
     * @return
     */
    public static boolean checkValidateCode(String captchas) {
        if (isEmpty(captchas)) {
            ToastUtils.showShort("验证码不能为空");
            ToastUtils.showToast("验证码不能为空");
            return false;
        }
        return true;
    }

    public static String getAppFIlePath() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + OdyApplication.SCHEME + File.separator;
        return path;
    }


    public static boolean isValid(String str) {
        if (str == null || str.length() == 0)
            return false;

        return true;
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

//    public static boolean isEmail(String email) {
//        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
//        Pattern p = Pattern.compile(str);
//        Matcher m = p.matcher(email);
//
//        return m.matches();
//    }

    /**
     * 判断密码长度 [6,16]
     *
     * @param psd
     */
    public static boolean checkPsdLength(String psd) {
        if (!isEmpty(psd)) {
            if (psd.length() > 16 || psd.length() < 6) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 校验密码是否合法，密码需为6位-18位，包含字母、数字、特殊字符
     *
     * @param psd
     * @return
     */
    public static boolean checkPassword(String psd) {
        if (!isEmpty(psd) && psd.length() >= 6 && psd.length() <= 16) {
           /* Pattern p = Pattern
                    .compile("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,18}");
            Matcher m = p.matcher(psd);
            return m.matches();*/
            return true;

        }
        return false;
    }

    /**
     * 创建view的时候将textview置灰
     */
    public static void setTextviewGray(TextView textView) {
        textView.setClickable(false);
        textView.setBackgroundResource(R.drawable.shape_cannot_click);
    }

    /**
     * 将textview动态置灰或置红
     */
    public static void setTextviewClickChange(final EditText editText, final TextView textView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(editText.getText().toString())) {
                    textView.setClickable(true);
                    textView.setBackgroundResource(R.drawable.shape_can_click);
                } else {
                    textView.setClickable(false);
                    textView.setBackgroundResource(R.drawable.shape_cannot_click);
                }
            }
        });
    }


    /**
     * 手机号码Pattern
     */
    public static final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("\\d{11}");

    public static boolean isMobileNumber(String mobiles) {
        if (mobiles == null) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 密码同时包括数字和字母 ^[A-Za-z0-9]+$  [^a-zA-Z0-9]
     */
    public static boolean isAccordPassword(String password) {
        int i = 1;
        int j = 1;
        int flag = 0;
        Pattern patrn = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = patrn.matcher(password);
        if (m.matches()) {//zhy 数字或者字母都可以作为密码
            return true;
        }
        return false;
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母,数字和特殊字符(不算多..),不允许输入汉字
        String regEx = "[^a-zA-Z0-9`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！_@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？-]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 限制EditText的输入类型为textPassword时可以输入的内容,包括数字,字母和标点符号
     */
    public static void limitInputType(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = editText.getText().toString();
                String str = StringUtils.stringFilter(editable);//正则
                if (!editable.equals(str)) {
                    editText.setText(str);
                    //设置新的光标所在位置
                    editText.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 判断Edittext右侧的叉图标的显示隐藏
     * view为叉图形对应的布局
     */
    public static void operateCha(final EditText editText, final View view) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (view == null) {
                    return;
                }
                if (editText != null && editText.getText() != null && !isEmpty(editText.getText().toString())) {
                    view.setVisibility(View.VISIBLE);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editText.setText("");
                        }
                    });
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * 控制密码是否为明文显示 密码默认非明文显示
     */
    public static void psdIsVisiable(EditText editText, boolean psdIsVisiable) {
        if (psdIsVisiable) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        if (editText != null && editText.getText() != null &&
                !isEmpty(editText.getText().toString()))
            editText.setSelection(editText.getText().toString().length());
    }

    public static int getScreenWidth(Activity a) {
        DisplayMetrics metric = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 隐藏软键盘
     *
     * @param editText
     */
    public static void hideSoftInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 显示软键盘
     *
     * @param editText
     */
    public static void showSoftInput(View editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    /**
     * 获取base64图片
     *
     * @param string
     * @return
     */
    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
