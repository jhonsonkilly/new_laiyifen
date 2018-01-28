package com.ody.p2p.Contact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.recycleviewutils.RecycleUtils;

/**
 * 读取联系人信息（电话薄）
 */
public class ContactsActivity extends BaseActivity {
    public static String CONTACTS_NAME="contacts_name";
    public static String CONTACTS_PHONE="contacts_phone";
    TextView tv_name;
    RelativeLayout rl_big_back;
    RecyclerView recycle_contacts;
    ContactsAdapter adapter;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_contacts;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText("选择联系人");
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        rl_big_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recycle_contacts = (RecyclerView) view.findViewById(R.id.recycle_contacts);
        recycle_contacts.setLayoutManager(RecycleUtils.getLayoutManager(getContext()));
        adapter = new ContactsAdapter(getContext(), ContactInfoParser.findAllContactInfo(getContext()));
        recycle_contacts.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<ContactInfoData>() {
            @Override
            public void onItemClick(View view, int position, ContactInfoData model) {
                Intent data = new Intent();
                data.putExtra(CONTACTS_NAME,adapter.getDatas().get(position).getName());
                data.putExtra(CONTACTS_PHONE,adapter.getDatas().get(position).getPhone());
                setResult(RESULT_OK, data);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position, ContactInfoData model) {

            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

}
