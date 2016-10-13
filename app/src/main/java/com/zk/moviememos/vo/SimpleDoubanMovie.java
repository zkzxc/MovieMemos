package com.zk.moviememos.vo;

import android.databinding.BaseObservable;
import android.text.TextUtils;

import com.zk.moviememos.R;
import com.zk.moviememos.po.DoubanCelebrity;
import com.zk.moviememos.util.ResourseUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SimpleDoubanMovie extends BaseObservable implements Serializable {

    private String id;
    private String title;
    private String original_title;
    private Images images;
    private Rating rating;
    private String year;
    private String subtype;
    private List<DoubanCelebrity> directors;
    private List<DoubanCelebrity> casts;
    private String titleAndYear;
    private boolean tv;

    public String getDirectors() {
        if (directors != null && directors.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ResourseUtils.getString(R.string.director))
                    .append(":")
                    .append(directors.get(0).getName());
            if (directors.size() > 1) {
                stringBuilder.append(" ").append(ResourseUtils.getString(R.string.etc));
            }
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public void setDirectors(List<DoubanCelebrity> directors) {
        this.directors = directors;
    }

    public String getCasts() {
        if (casts != null && casts.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ResourseUtils.getString(R.string.cast))
                    .append(":")
                    .append(casts.get(0).getName());
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public void setCasts(List<DoubanCelebrity> casts) {
        this.casts = casts;
    }


    public boolean isTv() {
        return subtype.equals("tv");
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public String getTitleAndYear() {
        if (TextUtils.isEmpty(year)) {
            return title;
        } else {
            return title + " (" + year + ")";
        }
    }

    public void setTitleAndYear(String titleAndYear) {
        this.titleAndYear = titleAndYear;
    }

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

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getYear() {
        if (TextUtils.isEmpty(year)) {
            return null;
        } else {
            return "(" + year + ")";
        }
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public class Images {

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

    public class Rating {

        private Integer max;
        private Float average;
        private String stars;
        private Integer min;

        private String averageStr;

        public String getAverageStr() {
            if (average == 0.0) {
                return ResourseUtils.getString(R.string.no_raing);
            } else {
                return String.valueOf(average);
            }
        }

        public void setAverageStr(String averageStr) {
            this.averageStr = averageStr;
        }

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
