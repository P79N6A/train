package com.petzmall.training.dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Channel {

    /**
     * 栏目对应ID
     */
    public Integer id;
    /**
     * 栏目对应name
     */

    public String name;
    /**
     * 栏目在整体中的排序顺序  rank
     */
    public Integer orderId;
    /**
     * 栏目是否选中
     */
    public Integer selected;
    @Generated(hash = 1586721553)
    public Channel(Integer id, String name, Integer orderId, Integer selected) {
        this.id = id;
        this.name = name;
        this.orderId = orderId;
        this.selected = selected;
    }
    @Generated(hash = 459652974)
    public Channel() {
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getOrderId() {
        return this.orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Integer getSelected() {
        return this.selected;
    }
    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}
