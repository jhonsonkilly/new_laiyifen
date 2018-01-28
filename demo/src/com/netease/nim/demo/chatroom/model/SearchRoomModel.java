package com.netease.nim.demo.chatroom.model;

import java.util.List;

/**
 * @author SevenCheng
 */

public class SearchRoomModel {


    /**
     * status : 200
     * message : null
     * data : {"type":3,"id":1,"name":"aa","chatRooms":[{"code":1,"name":"测试","creatorAvatar":"http://www.jf258.com/uploads/2014-08-02/112428572.jpg","longitude":"121.380911","latitude":"31.167658","announcement":"","roomId":21050723,"creator":"天霸"}]}
     */

    private int status;
    private Object message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : 3
         * id : 1
         * name : aa
         * chatRooms : [{"code":1,"name":"测试","creatorAvatar":"http://www.jf258.com/uploads/2014-08-02/112428572.jpg","longitude":"121.380911","latitude":"31.167658","announcement":"","roomId":21050723,"creator":"天霸"}]
         */

        private int type;
        private int                 id;
        private String name;
        private List<ChatRoomsBean> chatRooms;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ChatRoomsBean> getChatRooms() {
            return chatRooms;
        }

        public void setChatRooms(List<ChatRoomsBean> chatRooms) {
            this.chatRooms = chatRooms;
        }

        public static class ChatRoomsBean {
            /**
             * code : 1
             * name : 测试
             * creatorAvatar : http://www.jf258.com/uploads/2014-08-02/112428572.jpg
             * longitude : 121.380911
             * latitude : 31.167658
             * announcement :
             * roomId : 21050723
             * creator : 天霸
             */

            private int code;
            private String name;
            private String creatorAvatar;
            private String longitude;
            private String latitude;
            private String announcement;
            private int    roomId;
            private String creator;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCreatorAvatar() {
                return creatorAvatar;
            }

            public void setCreatorAvatar(String creatorAvatar) {
                this.creatorAvatar = creatorAvatar;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getAnnouncement() {
                return announcement;
            }

            public void setAnnouncement(String announcement) {
                this.announcement = announcement;
            }

            public int getRoomId() {
                return roomId;
            }

            public void setRoomId(int roomId) {
                this.roomId = roomId;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }
        }
    }
}
