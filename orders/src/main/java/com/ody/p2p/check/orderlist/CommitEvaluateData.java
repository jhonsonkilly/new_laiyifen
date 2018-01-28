package com.ody.p2p.check.orderlist;

import java.util.List;

/**
 * Created by ody on 2016/8/29.
 */
public class CommitEvaluateData {

//    public userMPCInputVO UserMPCInputVO;
//
//    public userMPCInputVO getUserMPCInputVO() {
//        return UserMPCInputVO;
//    }
//
//    public void setUserMPCInputVO(userMPCInputVO UserMPCInputVO) {
//        this.UserMPCInputVO = UserMPCInputVO;
//    }
//
//    public class userMPCInputVO{
        private int isHideUserName;
        public List<EvaluateData> userMPCommentVOList;

        public int getIsHideUserName() {
            return isHideUserName;
        }

        public void setIsHideUserName(int isHideUserName) {
            this.isHideUserName = isHideUserName;
        }

        public List<EvaluateData> getUserMPCommentVOList() {
            return userMPCommentVOList;
        }

        public void setUserMPCommentVOList(List<EvaluateData> userMPCommentVOList) {
            this.userMPCommentVOList = userMPCommentVOList;
        }
//    }

}
