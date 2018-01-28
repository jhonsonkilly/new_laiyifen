package com.ody.p2p.addressmanage.city;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ody.p2p.addressmanage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public class CharacterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CharacterAdapter(){
        this(R.layout.item_character, new ArrayList<String>());
    }
    public CharacterAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.character, s);
    }
}
