package com.netease.nim.demo.main.model;

import java.util.List;

/**
 * Created by jasmin on 2018/1/10.
 */

public class RecognizeFilterModel {


    /**
     * errMsg :
     * code : 1
     * filterData : ["苹果","习大大","党"]
     * message : 查询成功
     * version : 20180110161451
     * recognizeData : ["来伊份","小核桃","坚果","巧克力","核桃仁","花生","帝王蟹","波士顿龙虾","猕猴桃","猪肉铺","牛肉"]
     */

    private String errMsg;
    private String code;
    private String message;
    private String version;
    private List<String> filterData;
    private List<String> recognizeData;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getFilterData() {
        return filterData;
    }

    public void setFilterData(List<String> filterData) {
        this.filterData = filterData;
    }

    public List<String> getRecognizeData() {
        return recognizeData;
    }

    public void setRecognizeData(List<String> recognizeData) {
        this.recognizeData = recognizeData;
    }
}
