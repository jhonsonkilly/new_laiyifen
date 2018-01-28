package com.ody.p2p.main.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.main.R;

/**
 * Created by meijunqiang on 2017/3/3.
 * 描述：简单的文本——编辑框cell
 */

public class SimpleCellLinearLayout extends LinearLayout {

    private TextView cell_tv;
    private EditText cell_et;
    private View cell_buttom_line;

    public SimpleCellLinearLayout(Context context) {
        this(context, null);
    }

    public SimpleCellLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleCellLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.cell_simple_t_e, this);
        cell_tv = (TextView) findViewById(R.id.cell_tv);
        cell_et = (EditText) findViewById(R.id.cell_et);
        cell_buttom_line = findViewById(R.id.cell_buttom_line);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleCellLinearLayout);
            int width = typedArray.getDimensionPixelSize(R.styleable.SimpleCellLinearLayout_tv_width, 0);
            int et_max_length = typedArray.getInteger(R.styleable.SimpleCellLinearLayout_et_max_length, 0);
            int tv_visible = typedArray.getInteger(R.styleable.SimpleCellLinearLayout_tv_visible, 0);
            boolean line_visible = typedArray.getBoolean(R.styleable.SimpleCellLinearLayout_line_visible, true);
            String text = typedArray.getString(R.styleable.SimpleCellLinearLayout_tv_text);
            String hint = typedArray.getString(R.styleable.SimpleCellLinearLayout_et_hint_text);
            int color = typedArray.getColor(R.styleable.SimpleCellLinearLayout_tv_text_color, Color.parseColor("#333333"));
            cell_tv.setTextColor(color);
            if (!TextUtils.isEmpty(text)) {
                cell_tv.setText(text);
            }
            if (!TextUtils.isEmpty(hint)) {
                cell_et.setHint(hint);
            }
            if (width > 0) {
                cell_tv.setWidth(width);
            }
            if (et_max_length > 0) {
                cell_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(et_max_length)});
            }
            setTvVisible(tv_visible);
            cell_buttom_line.setVisibility(line_visible ? View.VISIBLE : View.INVISIBLE);
            typedArray.recycle();
        }
    }

    /**
     * 获取编辑框内容
     *
     * @return
     */
    public String getEtText() {
        return cell_et.getText().toString().trim();
    }

    /**
     * 设置编辑框文本
     *
     * @param et_text
     */
    public void setEtText(String et_text) {
        cell_et.setText(et_text);
        if (!TextUtils.isEmpty(et_text)) {
            cell_et.setSelection(et_text.length());
        }
    }

    /**
     * 设置编辑框默认显示文本
     *
     * @param et_hint_text
     */
    public void setEtHintText(String et_hint_text) {
        cell_et.setText(et_hint_text);
    }

    public void etRequestFocus() {
        if (getCell_et().hasFocus()) {
            getCell_et().clearFocus();
        }
        getCell_et().requestFocus();
    }

    /**
     * 检查输入，并弹出错误提示
     *
     * @param prompt
     */
    public void checkInputEmpty(String prompt) {
        if (TextUtils.isEmpty(getEtText())) {
            Toast.makeText(getContext(), prompt, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 是否显示文本
     *
     * @param visible
     */
    public void setTvVisible(int visible) {
        cell_tv.setVisibility(visible);
    }

    public TextView getCell_tv() {
        return cell_tv;
    }

    public EditText getCell_et() {
        return cell_et;
    }

    public View getCell_buttom_line() {
        return cell_buttom_line;
    }
}
