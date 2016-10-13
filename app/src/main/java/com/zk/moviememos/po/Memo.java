package com.zk.moviememos.po;

import android.text.TextUtils;

import com.zk.moviememos.R;
import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.util.ResourseUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class Memo {

    private String movieMemoId;
    private DoubanMovie doubanMovie;
    private Long viewingDate;  //观影日期
    private String viewingWay;   //观影方式（1：电影院、2：下载、3：DVD、4：在线观看、5：电视台、9：其他）
    private String viewingVersion1;   //观影版本参数1（1:普通、2：IMAX、3：中国巨幕、9：其他）
    private String viewingVersion2;   //观影版本参数2（1:2D、2:3D、9：其他）
    private String movieVersion;    //电影版本（1：公映版、2：导演剪辑版、3：加长版、9：其他）
    private String viewingMood;   //观影状态（3：好，2：中，1：差）
    private Integer storyScore;   //故事分
    private Integer visualScore;    //视觉分
    private Integer auralScore;   //听觉分
    private Float averageScore;    //平均分
    private String shortComment;    //短评
    private String isAvailable;   //删除标记（1：正常，0：删除）
    private Long addTime;     //插入时间

    private String viewingDateStr;
    private String viewingWayStr;
    private String viewingVersion1Str;
    private String viewingVersion2Str;
    private String movieVersionStr;
    private String viewingMoodStr;
    private String storyScoreStr;
    private String visualScoreStr;
    private String auralScoreStr;
    private String averageScoreStr;

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

    public String getViewingWay() {
        return viewingWay;
    }

    public void setViewingWay(String viewingWay) {
        this.viewingWay = viewingWay;
    }

    public String getViewingVersion1() {
        return viewingVersion1;
    }

    public void setViewingVersion1(String viewingVersion1) {
        this.viewingVersion1 = viewingVersion1;
    }

    public String getViewingVersion2() {
        return viewingVersion2;
    }

    public void setViewingVersion2(String viewingVersion2) {
        this.viewingVersion2 = viewingVersion2;
    }

    public String getMovieVersion() {
        return movieVersion;
    }

    public void setMovieVersion(String movieVersion) {
        this.movieVersion = movieVersion;
    }

    public Long getViewingDate() {
        return viewingDate;
    }

    public void setViewingDate(Long viewingDate) {
        this.viewingDate = viewingDate;
    }

    public String getShortComment() {
        return shortComment;
    }

    public void setShortComment(String shortComment) {
        this.shortComment = shortComment;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getViewingMood() {
        return viewingMood;
    }

    public void setViewingMood(String viewingMood) {
        this.viewingMood = viewingMood;
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

    public Integer getAuralScore() {
        return auralScore;
    }

    public void setAuralScore(Integer auralScore) {
        this.auralScore = auralScore;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Float averageScore) {
        this.averageScore = averageScore;
    }

    public String getViewingDateStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(viewingDate));
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR))
                .append(ResourseUtils.getString(R.string.year))
                .append(calendar.get(Calendar.MONTH) + 1)
                .append(ResourseUtils.getString(R.string.month))
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(ResourseUtils.getString(R.string.day));
        return sb.toString();
    }

    public String getViewingWayStr() {
        if (!TextUtils.isEmpty(viewingWay)) {
            switch (viewingWay) {
                case BusinessConstans.VIEWING_WAY_CINEMA:
                    return ResourseUtils.getString(R.string.cinema);
                case BusinessConstans.VIEWING_WAY_DOWNLOAD:
                    return ResourseUtils.getString(R.string.download);
                case BusinessConstans.VIEWING_WAY_ONLINE:
                    return ResourseUtils.getString(R.string.online);
                case BusinessConstans.VIEWING_WAY_DVD:
                    return ResourseUtils.getString(R.string.dvd);
                case BusinessConstans.VIEWING_WAY_TV:
                    return ResourseUtils.getString(R.string.tv);
                case BusinessConstans.VIEWING_WAY_OTHER:
                    return ResourseUtils.getString(R.string.other);
                default:
                    return null;
            }
        } else return null;
    }

    public String getViewingVersion1Str() {
        if (!TextUtils.isEmpty(viewingVersion1)) {
            switch (viewingVersion1) {
                case BusinessConstans.VIEWING_VERSION_NORMAL:
                    return ResourseUtils.getString(R.string.normal);
                case BusinessConstans.VIEWING_VERSION_IMAX:
                    return ResourseUtils.getString(R.string.imax);
                case BusinessConstans.VIEWING_VERSION_DMAX:
                    return ResourseUtils.getString(R.string.dmax);
                case BusinessConstans.VIEWING_VERSION_OTHER1:
                    return ResourseUtils.getString(R.string.other);
                default:
                    return null;
            }
        } else return null;
    }

    public String getViewingVersion2Str() {
        if (!TextUtils.isEmpty(viewingVersion2)) {
            switch (viewingVersion2) {
                case BusinessConstans.VIEWING_VERSION_2D:
                    return ResourseUtils.getString(R.string._2d);
                case BusinessConstans.VIEWING_VERSION_3D:
                    return ResourseUtils.getString(R.string._3d);
                case BusinessConstans.VIEWING_VERSION_OTHER2:
                    return ResourseUtils.getString(R.string.other);
                default:
                    return null;
            }
        } else return null;
    }

    public String getMovieVersionStr() {
        if (!TextUtils.isEmpty(movieVersion)) {
            switch (movieVersion) {
                case BusinessConstans.MOVIE_VERSION_PUBLIC_RELEASE:
                    return ResourseUtils.getString(R.string.public_release);
                case BusinessConstans.MOVIE_VERSION_DIRECTORS_CUT:
                    return ResourseUtils.getString(R.string.directors_cut);
                case BusinessConstans.MOVIE_VERSION_EXTENDED_EDITION:
                    return ResourseUtils.getString(R.string.extended_edition);
                case BusinessConstans.MOVIE_VERSION_OTHER:
                    return ResourseUtils.getString(R.string.other);
                default:
                    return null;
            }
        } else return null;
    }

    public String getViewingMoodStr() {
        if (!TextUtils.isEmpty(viewingMood)) {
            switch (viewingMood) {
                case BusinessConstans.VIEWING_MOOD_GOOD:
                    return ResourseUtils.getString(R.string.good);
                case BusinessConstans.VIEWING_MOOD_AVERAGE:
                    return ResourseUtils.getString(R.string.average);
                case BusinessConstans.VIEWING_MOOD_BAD:
                    return ResourseUtils.getString(R.string.bad);
                default:
                    return null;
            }
        } else return null;
    }

    public String getStoryScoreStr() {
        return String.valueOf(storyScore);
    }

    public String getVisualScoreStr() {
        return String.valueOf(visualScore);
    }

    public String getAuralScoreStr() {
        return String.valueOf(auralScore);
    }

    public String getAverageScoreStr() {
        return String.valueOf(averageScore);
    }
}
