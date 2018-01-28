package com.ody.p2p.check.aftersale.Bean;


import java.io.Serializable;

public class SalesReturn implements Serializable {
    int id;
    boolean falge;
    int thisNum;
    InitApplyRefundBean.AfterSalesProductVOs vos;


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the thisNum
     */
    public int getThisNum() {
        return thisNum;
    }

    /**
     * @param thisNum the thisNum to set
     */
    public void setThisNum(int thisNum) {
        this.thisNum = thisNum;
    }

    /**
     * @return the falge
     */
    public boolean isFalge() {
        return falge;
    }

    /**
     * @param falge the falge to set
     */
    public void setFalge(boolean falge) {
        this.falge = falge;
    }

    /**
     * @return the vos
     */
    public InitApplyRefundBean.AfterSalesProductVOs getVos() {
        return vos;
    }

    /**
     * @param vos the vos to set
     */
    public void setVos(InitApplyRefundBean.AfterSalesProductVOs vos) {
        this.vos = vos;
    }


}
