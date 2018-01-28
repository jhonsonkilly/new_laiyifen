package com.ody.p2p.addressmanage.city;

import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ody.p2p.addressmanage.R;
import com.ody.p2p.retrofit.city.MultiCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class CityAdapter extends BaseMultiItemQuickAdapter<MultiCity, BaseViewHolder> {

    public CityAdapter() {
        this(new ArrayList<MultiCity>());
    }

    public CityAdapter(List<MultiCity> data) {
        super(data);
        addItemType(MultiCity.SELECTION, R.layout.addressmanager_item_selection);
        addItemType(MultiCity.TEXT, R.layout.addressmanager_item_text);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MultiCity multiCity) {
        switch (baseViewHolder.getItemViewType()) {
            case MultiCity.SELECTION:
                baseViewHolder.setText(R.id.key, multiCity.areaName);
                break;
            case MultiCity.TEXT:
                baseViewHolder.setText(R.id.name, multiCity.areaName);
                baseViewHolder.setOnClickListener(R.id.name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (click != null){
                            click.click(multiCity);
                        }
                    }
                });
                break;
        }
    }

    public interface OnItemClick{
        void click(MultiCity multiCity);
    }

    public OnItemClick click;

    public void setItemClick(OnItemClick click){
        this.click = click;
    }


}
