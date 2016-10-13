package com.zk.moviememos.vo;

import android.text.TextUtils;

import com.zk.moviememos.R;
import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.util.ResourseUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SimpleMovieMemo {

    public static final String NO_SHORT_COMMENT = ResourseUtils.getString(R.string.no_short_comment);
    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_SECTION = 1;

    private String movieId;
    private String title;
    private String original_title;
    private Images images;
    private Rating rating;
    private String year;
    private String subtype;
    private String directors;
    private String casts;
    private String countries;
    private String genres;
    private String titleAndYear;
    private boolean tv;

    private String memoId;
    private Date viewingDate;
    private String viewingWay;
    private String viewingVersion1;
    private String viewingVersion2;
    private String movieVersion;
    private Float averageScore;
    private String shortComment;
    private Date addTime;
    private Calendar calendar;
    private String viewingYear;
    private String viewingMonthAndDate;

    private int viewType;
    private String sectionTextLeft;
    private String sectionTextRight;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getSectionTextLeft() {
        return sectionTextLeft;
    }

    public void setSectionTextLeft(String sectionTextLeft) {
        this.sectionTextLeft = sectionTextLeft;
    }

    public String getSectionTextRight() {
        return sectionTextRight;
    }

    public void setSectionTextRight(String sectionTextRight) {
        this.sectionTextRight = sectionTextRight;
    }

    public String getDirectors() {
        if (!TextUtils.isEmpty(directors)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ResourseUtils.getString(R.string.director))
                    .append(":")
                    .append(directors);
            if (directors.contains("/")) {
                stringBuilder.append(" ").append(ResourseUtils.getString(R.string.etc));
            }
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getCasts() {
        if (!TextUtils.isEmpty(casts)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ResourseUtils.getString(R.string.cast))
                    .append(":")
                    .append(casts);
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getViewingYear() {
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public String getViewingMonthAndDate() {
        return String.valueOf((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH));
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public boolean isTv() {
        if (!TextUtils.isEmpty(subtype)) {

            return subtype.equals("tv");
        } else {
            return false;
        }
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

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMemoId() {
        return memoId;
    }

    public void setMemoId(String memoId) {
        this.memoId = memoId;
    }

    public Date getViewingDate() {
        return viewingDate;
    }

    public void setViewingDate(Date viewingDate) {
        this.viewingDate = viewingDate;
        calendar = Calendar.getInstance();
        calendar.setTime(viewingDate);
    }

    public String getViewingWay() {
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
    }

    public void setViewingWay(String viewingWay) {
        this.viewingWay = viewingWay;
    }

    public String getViewingVersion1() {
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

    public void setViewingVersion1(String viewingVersion1) {
        this.viewingVersion1 = viewingVersion1;
    }

    public String getViewingVersion2() {
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

    public void setViewingVersion2(String viewingVersion2) {
        this.viewingVersion2 = viewingVersion2;
    }

    public String getMovieVersion() {
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

    public void setMovieVersion(String movieVersion) {
        this.movieVersion = movieVersion;
    }

    public String getAverageScore() {
        return String.valueOf(averageScore);
    }

    public void setAverageScore(Float averageScore) {
        this.averageScore = averageScore;
    }

    public String getShortComment() {
        return shortComment;
    }

    public void setShortComment(String shortComment) {
        this.shortComment = shortComment;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
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
