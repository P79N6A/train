package com.petzm.training.module.my.bean;

import java.util.List;

public class Lucky {


        private List<ImgVoBean> imgVo;

        public List<ImgVoBean> getImgVo() {
            return imgVo;
        }

        public void setImgVo(List<ImgVoBean> imgVo) {
            this.imgVo = imgVo;
        }

        public static class ImgVoBean {
            /**
             * name : 轮播1
             * pushType : 1
             * iconUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542370745548&di=5caab73cf65e7d5faee36bbc276f9614&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fe61190ef76c6a7efea25c01af6faaf51f3de668e.jpg
             * sortField : 1
             * type : 1
             * homeId : 1
             * state : 1
             * videoVos : null
             */

            private String name;
            private int pushType;
            private String iconUrl;
            private int sortField;
            private int type;
            private int homeId;
            private int state;
            private Object videoVos;

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

            public Object getVideoVos() {
                return videoVos;
            }

            public void setVideoVos(Object videoVos) {
                this.videoVos = videoVos;
            }
        }

}
