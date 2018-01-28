package com.ody.p2p.addressmanage.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ody on 2016/6/15.
 */
public class RequestAddressBean  {
    public List<Data> data;

    public class Data implements Serializable {
        public String code;
        public String name;
        public String id;

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return name;
        }
        public  Data(String code,String name,String id){
            this.code = code;
            this.name = name;
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
