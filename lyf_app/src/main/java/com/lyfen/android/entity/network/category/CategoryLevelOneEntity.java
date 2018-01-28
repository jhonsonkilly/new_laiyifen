package com.lyfen.android.entity.network.category;

import java.util.LinkedList;

/**
 * Created by qj on 2017/5/11.
 */

public class CategoryLevelOneEntity {


    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"categorys":[{"categoryId":1008024400000022,"categoryName":"全部分类","pictureUrl":"http://cdn.oudianyun.com/1491041871296_99.26847983683054_ce91629c-4e6d-4159-80aa-d3ba04a58cb7.png","categoryTreeNodeId":1008024400000023,"children":null},{"categoryId":1006020900000001,"categoryName":"生鲜专区","pictureUrl":"http://cdn.oudianyun.com/1491041881121_26.62843266196705_0580d2ae-e87b-41ff-a883-5124192db41a.png","categoryTreeNodeId":1006020900000002,"children":null},{"categoryId":1008021300000010,"categoryName":"坚果炒货","pictureUrl":"http://cdn.oudianyun.com/1491041888839_87.22347306653961_79596e09-f762-4986-9e31-1ccb2116e51e.png","categoryTreeNodeId":1008021300000011,"children":null},{"categoryId":1006021900000013,"categoryName":"肉类即食","pictureUrl":"http://cdn.oudianyun.com/1491041898533_15.727699160609433_a2d5f357-df6a-46b6-85f5-3aff90f72764.png","categoryTreeNodeId":1006021900000014,"children":null},{"categoryId":1008022000000047,"categoryName":"糕点饼干","pictureUrl":"http://cdn.oudianyun.com/1491041909515_49.939501220738045_8b15a608-376a-43c7-8173-89860ee32d01.png","categoryTreeNodeId":1008022000000048,"children":null},{"categoryId":1008022000000040,"categoryName":"果脯蜜饯","pictureUrl":"http://cdn.oudianyun.com/1491041917007_4.846599420822095_fd628e73-14d0-40e9-bb95-309b8585f95a.png","categoryTreeNodeId":1008022000000041,"children":null},{"categoryId":1008022000000054,"categoryName":"豆菌笋类","pictureUrl":"http://cdn.oudianyun.com/1491041923634_14.85929771036022_c2238390-e75e-4e38-9109-ad3602478fb9.png","categoryTreeNodeId":1008022000000055,"children":null},{"categoryId":1006022000000066,"categoryName":"糖果果冻","pictureUrl":"http://cdn.oudianyun.com/1491041930299_2.237376234654298_60ab5526-a89f-4d93-8050-4c1600177f84.png","categoryTreeNodeId":1006022000000067,"children":null},{"categoryId":1008022000000061,"categoryName":"粮油饮品","pictureUrl":"http://cdn.oudianyun.com/1491041939432_12.32210304869813_07f5f48b-3efa-4fa6-af66-7a752e989b59.png","categoryTreeNodeId":1008022000000062,"children":null},{"categoryId":1007022000000061,"categoryName":"全球尖货","pictureUrl":"http://cdn.oudianyun.com/1491041945580_32.16470222802364_ce15b656-3d54-488a-a86c-045c52bf510d.png","categoryTreeNodeId":1007022000000062,"children":null}]}
     * trace : 82!$1#@18%&10!$,153537,62683364265276629341808
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public LinkedList<CategorysEntity> categorys;

        public static class CategorysEntity {
            /**
             * categoryId : 1008024400000022
             * categoryName : 全部分类
             * pictureUrl : http://cdn.oudianyun.com/1491041871296_99.26847983683054_ce91629c-4e6d-4159-80aa-d3ba04a58cb7.png
             * categoryTreeNodeId : 1008024400000023
             * children : null
             */
            public boolean isSelected;
            public String categoryId;
            public String categoryName;
            public String pictureUrl;
            public long categoryTreeNodeId;
            public String children;
        }
    }
}
