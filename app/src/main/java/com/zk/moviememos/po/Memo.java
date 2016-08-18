package com.zk.moviememos.po;

import java.math.BigDecimal;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class Memo {

    private String movieMemoId;
    private DoubanMovie doubanMovie;
    private String viewingDate;  //观影日期（格式：xxxx/xx/xx）
    private Integer viewingTime;   //观影次数
    private String viewingWay;   //观影方式（1：电影院、2：DVD、3：下载、4：在线观看、5：电视台）
    private String location;    //观影地点
    private String version;   //观影版本（1：2D,2：3D,3：IMAX2D,4：IMAX3D,5：中国巨幕2D,6：中国巨幕3D）
    private String ViewingMood;   //观影状态（3：好，2：中，1：差）
    private Integer storyScore;   //故事分
    private Integer visualScore;    //画面分
    private Integer musicScore;   //音乐分
    private Double averageScore;    //平均分
    private String viewingFeeling;   //观影感受
    private String isAvailable;   //删除标记（1：正常，0：删除）

    public String getMovieMemoId() {
        return movieMemoId;
    }

    public void setMovieMemoId(String movieMemoId) {
        this.movieMemoId = movieMemoId;
    }

    public DoubanMovie getDoubanMovie() {
        return doubanMovie;
    }

    public void setDoubanMovie(DoubanMovie doubanMovie) {
        this.doubanMovie = doubanMovie;
    }

    public String getViewingDate() {
        return viewingDate;
    }

    public void setViewingDate(String viewingDate) {
        this.viewingDate = viewingDate;
    }

    public Integer getViewingTime() {
        return viewingTime;
    }

    public void setViewingTime(Integer viewingTime) {
        this.viewingTime = viewingTime;
    }

    public String getViewingWay() {
        return viewingWay;
    }

    public void setViewingWay(String viewingWay) {
        this.viewingWay = viewingWay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getViewingMood() {
        return ViewingMood;
    }

    public void setViewingMood(String viewingMood) {
        this.ViewingMood = viewingMood;
    }

    public Integer getStoryScore() {
        return storyScore;
    }

    public void setStoryScore(Integer storyScore) {
        this.storyScore = storyScore;
    }

    public Integer getVisualScore() {
        return visualScore;
    }

    public void setVisualScore(Integer visualScore) {
        this.visualScore = visualScore;
    }

    public Integer getMusicScore() {
        return musicScore;
    }

    public void setMusicScore(Integer musicScore) {
        this.musicScore = musicScore;
    }


    public String getViewingFeeling() {
        return viewingFeeling;
    }

    public void setViewingFeeling(String viewingFeeling) {
        this.viewingFeeling = viewingFeeling;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     *计算平均分
     * @return 平均分，精确到小数点后一位
     */
    public Double getAverageScore() {
        if(storyScore != null && visualScore != null && musicScore != null){
            Double as = (storyScore + visualScore + musicScore) / 3.0;
            BigDecimal bigDecimal = new BigDecimal(as);
            averageScore = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            return averageScore;
        }else {
            return null;
        }
    }
}
