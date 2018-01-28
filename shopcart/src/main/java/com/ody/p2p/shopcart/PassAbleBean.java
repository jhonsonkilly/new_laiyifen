package com.ody.p2p.shopcart;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by pzy on 2016/12/9.
 */

public class PassAbleBean extends BaseRequestBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private int addMoney;

        private String description;

        private int hasSelectedGifts;

        private int isAchieveCondition;

        private double totalPrice;

        private int type;

        public void setAddMoney(int addMoney){
            this.addMoney = addMoney;
        }
        public int getAddMoney(){
            return this.addMoney;
        }
        public void setDescription(String description){
            this.description = description;
        }
        public String getDescription(){
            return this.description;
        }
        public void setHasSelectedGifts(int hasSelectedGifts){
            this.hasSelectedGifts = hasSelectedGifts;
        }
        public int getHasSelectedGifts(){
            return this.hasSelectedGifts;
        }
        public void setIsAchieveCondition(int isAchieveCondition){
            this.isAchieveCondition = isAchieveCondition;
        }
        public int getIsAchieveCondition(){
            return this.isAchieveCondition;
        }
        public void setTotalPrice(double totalPrice){
            this.totalPrice = totalPrice;
        }
        public double getTotalPrice(){
            return this.totalPrice;
        }
        public void setType(int type){
            this.type = type;
        }
        public int getType(){
            return this.type;
        }

    }
}
