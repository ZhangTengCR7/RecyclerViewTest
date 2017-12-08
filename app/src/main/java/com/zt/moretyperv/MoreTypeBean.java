package com.zt.moretyperv;

/**
 * Created by zhangteng on 2017/12/8.
 */

public class MoreTypeBean {

    private int type;
    private String title;

    public MoreTypeBean(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
