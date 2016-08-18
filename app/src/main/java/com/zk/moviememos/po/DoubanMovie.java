package com.zk.moviememos.po;

import android.databinding.BaseObservable;

import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.util.ListUtils;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class DoubanMovie extends BaseObservable {

    public static final Float DEFAULT_SCORE = new Float(0.0);

    private String id;
    private String title;
    private String original_title;
    private List<String> aka;
    private String mobile_url;
    private DoubanMovieScore rating;
    private Integer rating_count;
    private DoubanMovieImages images;
    private String subtype;
    private List<DoubanCelebrity> directors;
    private List<DoubanCelebrity> casts;
    private String year;
    private List<String> genres;
    private List<String> countries;
    private String summary;
    private String viewingFlag;
    private List<Memo> memoList;
    private List<Tag> tagList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getAka() {
        return ListUtils.stringListToString(aka);
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public DoubanMovieScore getRating() {
        return rating;
    }

    public void setRating(DoubanMovieScore rating) {
        this.rating = rating;
    }

    public Integer getRating_count() {
        return rating_count;
    }

    public void setRating_count(Integer rating_count) {
        this.rating_count = rating_count;
    }

    public DoubanMovieImages getImages() {
        return images;
    }

    public void setImages(DoubanMovieImages images) {
        this.images = images;
    }

    public String getSubtype() {
        if ("movie".equals(subtype)) {  //从豆瓣得到的字符串为"movie"或者"tv"，转换成“M”或“T”
            return BusinessConstans.SUBTYPE_MOVIE;
        }else if ("tv".equals(subtype)) {
            return BusinessConstans.SUBTYPE_TV;
        }else {
            return subtype;
        }
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public List<DoubanCelebrity> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DoubanCelebrity> directors) {
        this.directors = directors;
    }

    public List<DoubanCelebrity> getCasts() {
        return casts;
    }

    public void setCasts(List<DoubanCelebrity> casts) {
        this.casts = casts;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenres() {
        return ListUtils.stringListToString(genres);
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getCountries() {
        return ListUtils.stringListToString(countries);
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getViewingFlag() {
        return viewingFlag;
    }

    public void setViewingFlag(String viewingFlag) {
        this.viewingFlag = viewingFlag;
    }

    public List<Memo> getMemoList() {
        return memoList;
    }

    public void setMemoList(List<Memo> movieMemoList) {
        this.memoList = movieMemoList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public class DoubanMovieImages {

        private String small;
        private String medium;
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }
    public class DoubanMovieScore {

        private Integer max;
        private Float average;
        private String stars;
        private Integer min;

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Float getAverage() {
            return average;
        }

        public void setAverage(Float average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }
    }
}
