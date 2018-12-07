package com.petzmall.training.module.socialCircle.bean;


import java.util.List;

public class Video {

        /**
         * introduce : {"userName":"蓝瓶的盖好喝的钙","introduction":"非凡等待","headUrl":"http://p0.so.qhmsg.com/t01c8e252970741d135.jpg","label":[{"name":"蓝瓶的盖\r\n\r\n好喝的钙"},{"name":"鲜花蛋糕"},{"name":"鲜花蛋糕1"}]}
         * videolist : [{"name":"精品推荐1","pushType":1,"iconUrl":"http://p0.so.qhmsg.com/t01c8e252970741d135.jpg","sortField":1,"type":1,"homeId":2,"state":1,"videoVos":{"id":28,"updateTime":"2018-11-16 13:54:33","videoId":"a41940091bc8444a88a18c200d70decc","videoName":"精品推荐1","content":"精品推荐","typeCode":null,"typeName":null,"tag":null,"size":null,"duration":null,"cover":null,"videoAddress":"http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/4b6661db-1671b16437c-\r\n\r\n0005-fb7f-ce5-6a5f7.mp4","template":null,"state":0,"createId":0,"updateId":0}},{"name":"精品推荐2","pushType":1,"iconUrl":"http://p0.so.qhmsg.com/t01e6774b16fea70b02.jpg","sortField":1,"type":1,"homeId":2,"state":1,"videoVos":{"id":29,"updateTime":"2018-11-16 13:54:36","videoId":"74b6fc5a78db44dfa1fe8df10cbb9268","videoName":"精品推荐2","content":"精品推荐","typeCode":null,"typeName":null,"tag":null,"size":null,"duration":null,"cover":null,"videoAddress":"http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/10b8d026-1671b1654ce-\r\n\r\n0005-fb7f-ce5-6a5f7.mp4","template":null,"state":0,"createId":0,"updateId":0}},{"name":"精品推荐3","pushType":1,"iconUrl":"http://p0.so.qhmsg.com/t01e6774b16fea70b02.jpg","sortField":1,"type":1,"homeId":2,"state":1,"videoVos":{"id":30,"updateTime":"2018-11-16 13:54:39","videoId":"dfb114bd2f1d48f399b21acfdcc610f3","videoName":"精品推荐3","content":"精品推荐","typeCode":null,"typeName":null,"tag":null,"size":null,"duration":null,"cover":null,"videoAddress":"http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/1e0999e4-1671b166222-\r\n\r\n0005-fb7f-ce5-6a5f7.mp4","template":null,"state":0,"createId":0,"updateId":0}}]
         */

        private IntroduceBean introduce;
        private List<VideolistBean> videolist;

        public IntroduceBean getIntroduce() {
            return introduce;
        }

        public void setIntroduce(IntroduceBean introduce) {
            this.introduce = introduce;
        }

        public List<VideolistBean> getVideolist() {
            return videolist;
        }

        public void setVideolist(List<VideolistBean> videolist) {
            this.videolist = videolist;
        }

        public static class IntroduceBean {
            /**
             * userName : 蓝瓶的盖好喝的钙
             * introduction : 非凡等待
             * headUrl : http://p0.so.qhmsg.com/t01c8e252970741d135.jpg
             * label : [{"name":"蓝瓶的盖\r\n\r\n好喝的钙"},{"name":"鲜花蛋糕"},{"name":"鲜花蛋糕1"}]
             */

            private String userName;
            private String introduction;
            private String headUrl;
            private List<LabelBean> label;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public List<LabelBean> getLabel() {
                return label;
            }

            public void setLabel(List<LabelBean> label) {
                this.label = label;
            }

            public static class LabelBean {
                /**
                 * name : 蓝瓶的盖

                 好喝的钙
                 */

                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class VideolistBean {
            /**
             * name : 精品推荐1
             * pushType : 1
             * iconUrl : http://p0.so.qhmsg.com/t01c8e252970741d135.jpg
             * sortField : 1
             * type : 1
             * homeId : 2
             * state : 1
             * videoVos : {"id":28,"updateTime":"2018-11-16 13:54:33","videoId":"a41940091bc8444a88a18c200d70decc","videoName":"精品推荐1","content":"精品推荐","typeCode":null,"typeName":null,"tag":null,"size":null,"duration":null,"cover":null,"videoAddress":"http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/4b6661db-1671b16437c-\r\n\r\n0005-fb7f-ce5-6a5f7.mp4","template":null,"state":0,"createId":0,"updateId":0}
             */

            private String name;
            private int pushType;
            private String iconUrl;
            private int sortField;
            private int type;
            private int homeId;
            private int state;
            private VideoVosBean videoVos;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPushType() {
                return pushType;
            }

            public void setPushType(int pushType) {
                this.pushType = pushType;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public int getSortField() {
                return sortField;
            }

            public void setSortField(int sortField) {
                this.sortField = sortField;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getHomeId() {
                return homeId;
            }

            public void setHomeId(int homeId) {
                this.homeId = homeId;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public VideoVosBean getVideoVos() {
                return videoVos;
            }

            public void setVideoVos(VideoVosBean videoVos) {
                this.videoVos = videoVos;
            }

            public static class VideoVosBean {
                /**
                 * id : 28
                 * updateTime : 2018-11-16 13:54:33
                 * videoId : a41940091bc8444a88a18c200d70decc
                 * videoName : 精品推荐1
                 * content : 精品推荐
                 * typeCode : null
                 * typeName : null
                 * tag : null
                 * size : null
                 * duration : null
                 * cover : null
                 * videoAddress : http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/4b6661db-1671b16437c-

                 0005-fb7f-ce5-6a5f7.mp4
                 * template : null
                 * state : 0
                 * createId : 0
                 * updateId : 0
                 */

                private int id;
                private String updateTime;
                private String videoId;
                private String videoName;
                private String content;
                private Object typeCode;
                private Object typeName;
                private Object tag;
                private Object size;
                private Object duration;
                private Object cover;
                private String videoAddress;
                private Object template;
                private int state;
                private int createId;
                private int updateId;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public String getVideoId() {
                    return videoId;
                }

                public void setVideoId(String videoId) {
                    this.videoId = videoId;
                }

                public String getVideoName() {
                    return videoName;
                }

                public void setVideoName(String videoName) {
                    this.videoName = videoName;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public Object getTypeCode() {
                    return typeCode;
                }

                public void setTypeCode(Object typeCode) {
                    this.typeCode = typeCode;
                }

                public Object getTypeName() {
                    return typeName;
                }

                public void setTypeName(Object typeName) {
                    this.typeName = typeName;
                }

                public Object getTag() {
                    return tag;
                }

                public void setTag(Object tag) {
                    this.tag = tag;
                }

                public Object getSize() {
                    return size;
                }

                public void setSize(Object size) {
                    this.size = size;
                }

                public Object getDuration() {
                    return duration;
                }

                public void setDuration(Object duration) {
                    this.duration = duration;
                }

                public Object getCover() {
                    return cover;
                }

                public void setCover(Object cover) {
                    this.cover = cover;
                }

                public String getVideoAddress() {
                    return videoAddress;
                }

                public void setVideoAddress(String videoAddress) {
                    this.videoAddress = videoAddress;
                }

                public Object getTemplate() {
                    return template;
                }

                public void setTemplate(Object template) {
                    this.template = template;
                }

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public int getCreateId() {
                    return createId;
                }

                public void setCreateId(int createId) {
                    this.createId = createId;
                }

                public int getUpdateId() {
                    return updateId;
                }

                public void setUpdateId(int updateId) {
                    this.updateId = updateId;
                }
            }
    }
}
