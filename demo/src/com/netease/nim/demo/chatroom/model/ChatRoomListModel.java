package com.netease.nim.demo.chatroom.model;

import java.util.List;

/**
 * @author SevenCheng
 */

public class ChatRoomListModel {

    /**
     * status : 200
     * message : null
     * data : {"chatRoomOfMine":[{"code":null,"name":"么么","creatorAvatar":"http://www.jf258.com/uploads/2014-08-02/112428572.jpg","longitude":null,"latitude":null,"announcement":"","roomId":21090653,"creator":"祥仔"}],"chatRoomsContainMe":[{"code":null,"name":"么么","creatorAvatar":"http://www.jf258.com/uploads/2014-08-02/112428572.jpg","longitude":null,"latitude":null,"announcement":"","roomId":21090653,"creator":"祥仔"}]}
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
        private List<ChatRoomOfMineBean> chatRoomOfMine;
        private List<ChatRoomsContainMeBean> chatRoomsContainMe;

        public List<ChatRoomOfMineBean> getChatRoomOfMine() {
            return chatRoomOfMine;
        }

        public void setChatRoomOfMine(List<ChatRoomOfMineBean> chatRoomOfMine) {
            this.chatRoomOfMine = chatRoomOfMine;
        }

        public List<ChatRoomsContainMeBean> getChatRoomsContainMe() {
            return chatRoomsContainMe;
        }

        public void setChatRoomsContainMe(List<ChatRoomsContainMeBean> chatRoomsContainMe) {
            this.chatRoomsContainMe = chatRoomsContainMe;
        }

        public static class ChatRoomOfMineBean {
            /**
             * code : null
             * name : 么么
             * creatorAvatar : http://www.jf258.com/uploads/2014-08-02/112428572.jpg
             * longitude : null
             * latitude : null
             * announcement :
             * roomId : 21090653
             * creator : 祥仔
             */

            private Object code;
            private String name;
            private String creatorAvatar;
            private Object longitude;
            private Object latitude;
            private String announcement;
            private int    roomId;
            private String creator;

            public Object getCode() {
                return code;
            }

            public void setCode(Object code) {
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

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public Object getLatitude() {
                return latitude;
            }

            public void setLatitude(Object latitude) {
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

        public static class ChatRoomsContainMeBean {
            /**
             * code : null
             * name : 么么
             * creatorAvatar : http://www.jf258.com/uploads/2014-08-02/112428572.jpg
             * longitude : null
             * latitude : null
             * announcement :
             * roomId : 21090653
             * creator : 祥仔
             */

            private Object code;
            private String name;
            private String creatorAvatar;
            private Object longitude;
            private Object latitude;
            private String announcement;
            private int    roomId;
            private String creator;

            public Object getCode() {
                return code;
            }

            public void setCode(Object code) {
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

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public Object getLatitude() {
                return latitude;
            }

            public void setLatitude(Object latitude) {
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
