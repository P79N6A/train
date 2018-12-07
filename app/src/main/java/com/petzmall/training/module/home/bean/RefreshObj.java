package com.petzmall.training.module.home.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RefreshObj {




            /**
             * id : 2
             * name : 精品推荐
             * type : 2
             * themeId : 1
             * state : 1
             * imgVo : [{"name":"精品推荐1","pushType":1,"iconUrl":"http://p0.so.qhmsg.com/t01c8e252970741d135.jpg","sortField":1,"type":1,"homeId":2,"state":1,"videoVos":{"id":28,"updateTime":"2018-11-16 13:54:33","videoId":"a41940091bc8444a88a18c200d70decc","videoName":"精品推荐1","content":"精品推荐","typeCode":null,"typeName":null,"tag":null,"size":null,"duration":null,"cover":null,"videoAddress":"http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/4b6661db-1671b16437c-0005-fb7f-ce5-6a5f7.mp4","template":null,"state":0,"createId":0,"updateId":0}},{"name":"精品推荐2","pushType":1,"iconUrl":"http://p0.so.qhmsg.com/t01e6774b16fea70b02.jpg","sortField":1,"type":1,"homeId":2,"state":1,"videoVos":{"id":29,"updateTime":"2018-11-16 13:54:36","videoId":"74b6fc5a78db44dfa1fe8df10cbb9268","videoName":"精品推荐2","content":"精品推荐","typeCode":null,"typeName":null,"tag":null,"size":null,"duration":null,"cover":null,"videoAddress":"http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/10b8d026-1671b1654ce-0005-fb7f-ce5-6a5f7.mp4","template":null,"state":0,"createId":0,"updateId":0}},{"name":"精品推荐3","pushType":1,"iconUrl":"http://p0.so.qhmsg.com/t01e6774b16fea70b02.jpg","sortField":1,"type":1,"homeId":2,"state":1,"videoVos":{"id":30,"updateTime":"2018-11-16 13:54:39","videoId":"dfb114bd2f1d48f399b21acfdcc610f3","videoName":"精品推荐3","content":"精品推荐","typeCode":null,"typeName":null,"tag":null,"size":null,"duration":null,"cover":null,"videoAddress":"http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/1e0999e4-1671b166222-0005-fb7f-ce5-6a5f7.mp4","template":null,"state":0,"createId":0,"updateId":0}}]
             */

            private int id;
            @SerializedName("name")
            private String nameX;
            @SerializedName("type")
            private int typeX;
            @SerializedName("themeId")
            private int themeIdX;
            @SerializedName("state")
            private int stateX;
            @SerializedName("imgVo")
            private List<ImgVoBean> imgVoX;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNameX() {
                return nameX;
            }

            public void setNameX(String nameX) {
                this.nameX = nameX;
            }

            public int getTypeX() {
                return typeX;
            }

            public void setTypeX(int typeX) {
                this.typeX = typeX;
            }

            public int getThemeIdX() {
                return themeIdX;
            }

            public void setThemeIdX(int themeIdX) {
                this.themeIdX = themeIdX;
            }

            public int getStateX() {
                return stateX;
            }

            public void setStateX(int stateX) {
                this.stateX = stateX;
            }

            public List<ImgVoBean> getImgVoX() {
                return imgVoX;
            }

            public void setImgVoX(List<ImgVoBean> imgVoX) {
                this.imgVoX = imgVoX;
            }

            public static class ImgVoBean {
                /**
                 * name : 精品推荐1
                 * pushType : 1
                 * iconUrl : http://p0.so.qhmsg.com/t01c8e252970741d135.jpg
                 * sortField : 1
                 * type : 1
                 * homeId : 2
                 * state : 1
                 * videoVos : {"id":28,"updateTime":"2018-11-16 13:54:33","videoId":"a41940091bc8444a88a18c200d70decc","videoName":"精品推荐1","content":"精品推荐","typeCode":null,"typeName":null,"tag":null,"size":null,"duration":null,"cover":null,"videoAddress":"http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/4b6661db-1671b16437c-0005-fb7f-ce5-6a5f7.mp4","template":null,"state":0,"createId":0,"updateId":0}
                 */

                @SerializedName("name")
                private String nameX;
                private int pushType;
                private String iconUrl;
                private int sortField;
                @SerializedName("type")
                private int typeX;
                private int homeId;
                @SerializedName("state")
                private int stateX;
                private VideoVosBean videoVos;

                public String getNameX() {
                    return nameX;
                }

                public void setNameX(String nameX) {
                    this.nameX = nameX;
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

                public int getTypeX() {
                    return typeX;
                }

                public void setTypeX(int typeX) {
                    this.typeX = typeX;
                }

                public int getHomeId() {
                    return homeId;
                }

                public void setHomeId(int homeId) {
                    this.homeId = homeId;
                }

                public int getStateX() {
                    return stateX;
                }

                public void setStateX(int stateX) {
                    this.stateX = stateX;
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
                     * videoAddress : http://vod.petzm618.com/customerTrans/509e89a621d33fea74d601ed60939f88/4b6661db-1671b16437c-0005-fb7f-ce5-6a5f7.mp4
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
                    @SerializedName("state")
                    private int stateX;
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

                    public int getStateX() {
                        return stateX;
                    }

                    public void setStateX(int stateX) {
                        this.stateX = stateX;
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
