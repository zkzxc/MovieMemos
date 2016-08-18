package com.zk.moviememos.po;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class Tag {

    private Integer tagId;
    private String content;
    private String isAvailable;
    private List<Memo> memoList;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public List<Memo> getMovieMemoList() {
        return memoList;
    }

    public void setMovieMemoList(List<Memo> movieMemoList) {
        this.memoList = movieMemoList;
    }
}
