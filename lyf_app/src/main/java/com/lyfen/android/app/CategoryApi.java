package com.lyfen.android.app;

import com.lyfen.android.entity.network.category.CategoryLeveLTwoEntity;
import com.lyfen.android.entity.network.category.CategoryLevelOneEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qj on 2017/5/11.
 */

public interface CategoryApi {
    // 分类1级分类
    @GET("/api/category/list")
    Observable<CategoryLevelOneEntity> getCategoryOne(@Query("parentId") long parentId, @Query("level") int level);


    // 分类2级分类
    @GET("/api/category/list")
    Observable<CategoryLeveLTwoEntity> getCategoryTwo(@Query("parentId") String parentId, @Query("level") int level);

}
