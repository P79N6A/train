package com.petzmall.training.module.socialCircle.bean;

import java.util.List;

public class CommentsBean {

        /**
         * draw : 0
         * recordsTotal : 6
         * recordsFiltered : 6
         * data : [{"id":7,"videoId":2,"fromUid":30,"content":"视频2下的第三个评论","topicType":22,"likeNum":22,"replyNum":22,"isReply":22,"state":22,"firstVideoReplyVos":[],"fromUname":"咸鱼","fromUimg":"http://p3.so.qhimgs1.com/bdr/_240_/t01656f08b5a9b58b83.png","replyTime":"11-21","isConcern":0},{"id":1,"videoId":2,"fromUid":1,"content":"视频2下的第一个评论","topicType":111,"likeNum":1,"replyNum":11,"isReply":1,"state":1,"firstVideoReplyVos":[{"id":1,"commentId":1,"replyId":1,"replyType":1,"content":"我是一级回复1","fromUid":2,"toUid":2,"state":2,"videoReplyVos":null},{"id":2,"commentId":1,"replyId":1,"replyType":1,"content":"我是一级回复2","fromUid":2,"toUid":2,"state":2,"videoReplyVos":null}],"fromUname":"wang","fromUimg":"http://p3.so.qhimgs1.com/bdr/_240_/t01656f08b5a9b58b83.png","replyTime":"11-21","isConcern":0}]
         */

        private int draw;
        private int recordsTotal;
        private int recordsFiltered;
        private List<DataBean> data;

        public int getDraw() {
            return draw;
        }

        public void setDraw(int draw) {
            this.draw = draw;
        }

        public int getRecordsTotal() {
            return recordsTotal;
        }

        public void setRecordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
        }

        public int getRecordsFiltered() {
            return recordsFiltered;
        }

        public void setRecordsFiltered(int recordsFiltered) {
            this.recordsFiltered = recordsFiltered;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 7
             * videoId : 2
             * fromUid : 30
             * content : 视频2下的第三个评论
             * topicType : 22
             * likeNum : 22
             * replyNum : 22
             * isReply : 22
             * state : 22
             * firstVideoReplyVos : []
             * fromUname : 咸鱼
             * fromUimg : http://p3.so.qhimgs1.com/bdr/_240_/t01656f08b5a9b58b83.png
             * replyTime : 11-21
             * isConcern : 0
             */

            private int id;
            private int videoId;
            private int fromUid;
            private String content;
            private int topicType;
            private int likeNum;
            private int replyNum;
            private int isReply;
            private int state;
            private String fromUname;
            private String fromUimg;
            private String replyTime;
            private int isConcern;
            private List<CommentsReply> firstVideoReplyVos;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getVideoId() {
                return videoId;
            }

            public void setVideoId(int videoId) {
                this.videoId = videoId;
            }

            public int getFromUid() {
                return fromUid;
            }

            public void setFromUid(int fromUid) {
                this.fromUid = fromUid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getTopicType() {
                return topicType;
            }

            public void setTopicType(int topicType) {
                this.topicType = topicType;
            }

            public int getLikeNum() {
                return likeNum;
            }

            public void setLikeNum(int likeNum) {
                this.likeNum = likeNum;
            }

            public int getReplyNum() {
                return replyNum;
            }

            public void setReplyNum(int replyNum) {
                this.replyNum = replyNum;
            }

            public int getIsReply() {
                return isReply;
            }

            public void setIsReply(int isReply) {
                this.isReply = isReply;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getFromUname() {
                return fromUname;
            }

            public void setFromUname(String fromUname) {
                this.fromUname = fromUname;
            }

            public String getFromUimg() {
                return fromUimg;
            }

            public void setFromUimg(String fromUimg) {
                this.fromUimg = fromUimg;
            }

            public String getReplyTime() {
                return replyTime;
            }

            public void setReplyTime(String replyTime) {
                this.replyTime = replyTime;
            }

            public int getIsConcern() {
                return isConcern;
            }

            public void setIsConcern(int isConcern) {
                this.isConcern = isConcern;
            }

            public List<CommentsReply> getFirstVideoReplyVos() {
                return firstVideoReplyVos;
            }

            public void setFirstVideoReplyVos(List<CommentsReply> firstVideoReplyVos) {
                this.firstVideoReplyVos = firstVideoReplyVos;
            }
        }
}
