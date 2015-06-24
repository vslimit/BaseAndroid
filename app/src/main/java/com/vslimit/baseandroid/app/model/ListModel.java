package com.vslimit.baseandroid.app.model;

import java.util.List;

/**
 * Created by vslimit on 15/4/10.
 */
public class ListModel<T> {

    private Integer total;
    private Integer money;
    private List<T> list;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
