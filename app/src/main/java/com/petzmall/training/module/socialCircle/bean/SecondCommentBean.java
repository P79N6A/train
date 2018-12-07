package com.petzmall.training.module.socialCircle.bean;

import java.util.List;

public class SecondCommentBean {

        /**
         * draw : 0
         * recordsTotal : 2
         * recordsFiltered : 2
         * data : {"id":1,"videoId":2,"fromUid":1,"content":"视频2下的第一个评论","topicType":111,"likeNum":1,"replyNum":11,"isReply":1,"state":1,"firstVideoReplyVos":[{"id":1,"commentId":1,"replyId":1,"replyType":1,"content":"我是一级回复1","fromUid":35,"toUid":2,"state":2,"videoReplyVos":null,"fromUname":"zhangxu","fromUimg":null,"replyTime":"11-28 17:32"},{"id":2,"commentId":1,"replyId":1,"replyType":1,"content":"我是一级回复2","fromUid":35,"toUid":2,"state":2,"videoReplyVos":null,"fromUname":"zhangxu","fromUimg":null,"replyTime":"11-28 13:31"}],"fromUname":"wang","fromUimg":null,"replyTime":"11-21 15:18","isConcern":0}
         */

        private int draw;
        private int recordsTotal;
        private int recordsFiltered;
        private DataBean data;

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

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1
             * videoId : 2
             * fromUid : 1
             * content : 视频2下的第一个评论
             * topicType : 111
             * likeNum : 1
             * replyNum : 11
             * isReply : 1
             * state : 1
             * firstVideoReplyVos : [{"id":1,"commentId":1,"replyId":1,"replyType":1,"content":"我是一级回复1","fromUid":35,"toUid":2,"state":2,"videoReplyVos":null,"fromUname":"zhangxu","fromUimg":null,"replyTime":"11-28 17:32"},{"id":2,"commentId":1,"replyId":1,"replyType":1,"content":"我是一级回复2","fromUid":35,"toUid":2,"state":2,"videoReplyVos":null,"fromUname":"zhangxu","fromUimg":null,"replyTime":"11-28 13:31"}]
             * fromUname : wang
             * fromUimg : null
             * replyTime : 11-21 15:18
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
            private Object fromUimg;
            private String replyTime;
            private int isConcern;
            private List<FirstVideoReplyVosBean> firstVideoReplyVos;

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

            public Object getFromUimg() {
                return fromUimg;
            }

            public void setFromUimg(Object fromUimg) {
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

            public List<FirstVideoReplyVosBean> getFirstVideoReplyVos() {
                return firstVideoReplyVos;
            }

            public void setFirstVideoReplyVos(List<FirstVideoReplyVosBean> firstVideoReplyVos) {
                this.firstVideoReplyVos = firstVideoReplyVos;
            }

            public static class FirstVideoReplyVosBean {
                /**
                 * id : 1
                 * commentId : 1
                 * replyId : 1
                 * replyType : 1
                 * content : 我是一级回复1
                 * fromUid : 35
                 * toUid : 2
                 * state : 2
                 * videoReplyVos : null
                 * fromUname : zhangxu
                 * fromUimg : null
                 * replyTime : 11-28 17:32
                 */

                private int id;
                private int commentId;
                private int replyId;
                private int replyType;
                private String content;
                private int fromUid;
                private int toUid;
                private int state;
                private Object videoReplyVos;
                private String fromUname;
                private Object fromUimg;
                private String replyTime;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getCommentId() {
                    return commentId;
                }

                public void setCommentId(int commentId) {
                    this.commentId = commentId;
                }

                public int getReplyId() {
                    return replyId;
                }

                public void setReplyId(int replyId) {
                    this.replyId = replyId;
                }

                public int getReplyType() {
                    return replyType;
                }

                public void setReplyType(int replyType) {
                    this.replyType = replyType;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getFromUid() {
                    return fromUid;
                }

                public void setFromUid(int fromUid) {
                    this.fromUid = fromUid;
                }

                public int getToUid() {
                    return toUid;
                }

                public void setToUid(int toUid) {
                    this.toUid = toUid;
                }

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public Object getVideoReplyVos() {
                    return videoReplyVos;
                }

                public void setVideoReplyVos(Object videoReplyVos) {
                    this.videoReplyVos = videoReplyVos;
                }

                public String getFromUname() {
                    return fromUname;
                }

                public void setFromUname(String fromUname) {
                    this.fromUname = fromUname;
                }

                public Object getFromUimg() {
                    return fromUimg;
                }

                public void setFromUimg(Object fromUimg) {
                    this.fromUimg = fromUimg;
                }

                public String getReplyTime() {
                    return replyTime;
                }

                public void setReplyTime(String replyTime) {
                    this.replyTime = replyTime;
                }
            }
        }
    }
