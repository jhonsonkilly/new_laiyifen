package com.odysaxx.photograph.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


import com.odysaxx.photograph.beans.Photo;
import com.odysaxx.photograph.beans.PhotoFloder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class: PhotoUtils
 * @Description:
 * @author: lling(www.liuling123.com)
 * @Date: 2015/11/4
 */
public class PhotoUtils {


    public static Map<String, PhotoFloder> getPhotos(Context context) {
        Map<String, PhotoFloder> floderMap = new HashMap<String, PhotoFloder>();
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();
        // 只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(imageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);

        int pathIndex = mCursor
                .getColumnIndex(MediaStore.Images.Media.DATA);

        mCursor.moveToFirst();
        while (mCursor.moveToNext()) {
            // 获取图片的路径
            String path = mCursor.getString(pathIndex);
            // 获取该图片的父路径名
            String parentName = new File(path).getParentFile().getName();

            if (!floderMap.containsKey(parentName)) {
                PhotoFloder photoFloder=new PhotoFloder();
                photoFloder.setName(parentName);
                List<String> childList = new ArrayList<String>();
                childList.add(path);
                photoFloder.setPhotoList(childList);
                floderMap.put(parentName, photoFloder);
            } else {
                floderMap.get(parentName).getPhotoList().add(path);
            }
        }
        mCursor.close();
        return floderMap;
    }

}
