package com.netease.nim.demo.main.model;

/**
 * Created by jasmin on 17/2/25.
 */

public class ContactsInfoModel {
    private String contact_name;//联系人姓名
    private String phone_no;//电话
    private String email;//邮件
    private String organization;//公司
    private String address;//地址
    private String birthday;//生日
    private String job_title;//职位
    private String nickname;//称呼
    private String note;//备注



    private String name;
    private String number;
    private String sortKey;
    private int id;


    public ContactsInfoModel(String name, String number, String sortKey, int id) {
        setName(name);
        setNumber(number);
        setSortKey(sortKey);
        setId(id);

    }
    public ContactsInfoModel() {
       setContact_name("");
        setPhone_no("");
        setEmail("");
        setOrganization("");
        setAddress("");
        setBirthday("");
        setJob_title("");
        setNickname("");
        setNote("");
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
