package com.ody.p2p.Contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import android.provider.ContactsContract.CommonDataKinds.Phone;

/**
 * Created by pzy on 2017/3/9.
 */

public class ContactInfoParser {
    /**
     * 获取系统全部联系人的api方法
     *
     * @param context
     * @return
     */
    public static List<ContactInfoData> findAllContactInfo(Context context) {
        List<ContactInfoData> infos = new ArrayList<ContactInfoData>();
        try {
            Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI,
                    null, null, null, null);
            //moveToNext方法返回的是一个boolean类型的数据
            while (cursor.moveToNext()) {
                //读取通讯录的姓名
                String name = cursor.getString(cursor
                        .getColumnIndex(Phone.DISPLAY_NAME));
                //读取通讯录的号码
                String number = cursor.getString(cursor
                        .getColumnIndex(Phone.NUMBER));
                ContactInfoData data = new ContactInfoData();
                data.setName(name);
                data.setPhone(number);
                infos.add(data);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infos;
    }
}
