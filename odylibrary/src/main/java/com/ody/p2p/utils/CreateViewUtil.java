package com.ody.p2p.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ody.p2p.R;

/**
 * Created by Administrator on 2016/12/7.
 */

public class CreateViewUtil {

    public static View createSearchEmptyView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.empty_search_view, parent, false);
    }
}
