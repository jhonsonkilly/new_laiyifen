package com.lyfen.android.entity.local;

/**
 * 作者：qiujie on 16/4/25
 * 邮箱：qiujie@laiyifen.com
 */
public class MineMenuItemEntity {

//    "i": "0",
//            "img": "mine_services_2x",
//            "title": "在线客服",
//            "destination": ""

    private String i ;
    private String img;
    private String title;
    private String destination;
    private String login;
    private String webview;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getWebview() {
        return webview;
    }

    public void setWebview(String webview) {
        this.webview = webview;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
