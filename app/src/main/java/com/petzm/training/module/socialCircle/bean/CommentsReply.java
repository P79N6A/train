package com.petzm.training.module.socialCircle.bean;

public class CommentsReply {


    /**
     * id : 1
     * commentId : 1
     * replyId : 1
     * replyType : 1
     * content : 我是一级回复1
     * fromUid : 2
     * toUid : 2
     * state : 2
     * videoReplyVos : null
     */

    private int id;
    private int commentId;
    private int replyId;
    private int replyType;
    private String content;
    private int fromUid;
    private String fromUname;
    private int toUid;
    private int state;
    private Object videoReplyVos;

    public String getFromUname() {
        return fromUname;
    }

    public void setFromUname(String fromUname) {
        this.fromUname = fromUname;
    }

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
}
