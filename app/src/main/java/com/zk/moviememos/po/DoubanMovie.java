package com.zk.moviememos.po;

import android.databinding.BaseObservable;

import com.zk.moviememos.R;
import com.zk.moviememos.util.ListUtils;
import com.zk.moviememos.util.ResourseUtils;

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
    private Integer seasons_count;
    private String episodes_count;

    private boolean tv;
    private String seasonCountStr;
    private String episodesCountStr;

    private String directorsNames;
    private String castsNames;

    public boolean isTv() {
        return subtype.equals("tv");
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public String getSeasonsCountStr() {
        if (seasons_count != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ResourseUtils.getString(R.string.total))
                    .append(seasons_count)
                    .append(ResourseUtils.getString(R.string.season));
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public void setSeasonsCountStr(String seasonsCountStr) {
        this.seasonCountStr = seasonsCountStr;
    }

    public String getEpisodesCountStr() {
        if (episodes_count != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ResourseUtils.getString(R.string.this_season))
                    .append(ResourseUtils.getString(R.string.total))
                    .append(episodes_count)
                    .append(ResourseUtils.getString(R.string.episode));
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public void setEpisodesCountStr(String episodesCountStr) {
        this.episodesCountStr = episodesCountStr;
    }


    public String getDirectorsNames() {
        return ListUtils.doubanCelebrityListToString(directors);
    }

    public void setDirectorsNames(String directorsNames) {
        this.directorsNames = directorsNames;
    }

    public String getCastsNames() {
        return ListUtils.doubanCelebrityListToString(casts);
    }

    public void setCastsNames(String castsNames) {
        this.castsNames = castsNames;
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

    public String getAka() {
        if (aka.size() > 3) {
            List<String> newAka = aka.subList(0, 3);
            return ListUtils.stringListToString(newAka);
        } else {
            return ListUtils.stringListToString(aka);
        }
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
        return subtype;
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

    public Integer getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Integer seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(String episodes_count) {
        this.episodes_count = episodes_count;
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

        private Float ratingStar;
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

        public Float getRatingStar() {
            return average / 2;
        }

        public void setRatingStar(Float ratingStar) {
            this.ratingStar = ratingStar;
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
