package com.zk.moviememos.vo;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class DoubanSearchObject {
    private Integer count;
    private Integer start;
    private Integer total;
    private List<SimpleDoubanMovie> subjects;
    private String title;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<SimpleDoubanMovie> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SimpleDoubanMovie> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
