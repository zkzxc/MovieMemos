package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.MovieContract;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.model.MovieModel;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.util.LogUtils;

import java.util.Arrays;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MoviePresenter implements MovieContract.Presenter {

    private DoubanMovieModel movieModel;
    private MovieContract.View mView;
    private String movieId;

    public static MoviePresenter getInstance(DoubanMovieModel movieModel, MovieContract.View view, String movieId) {
        MoviePresenter presenter = new MoviePresenter(movieModel, view, movieId);
        return presenter;
    }


    private MoviePresenter(DoubanMovieModel movieModel, MovieContract.View view, String movieId) {
        this.movieModel = movieModel;
        this.mView = view;
        this.movieId = movieId;
        mView.setPresenter(this);
    }

    @Override
    public void initOnCreate() {
    }

    @Override
    public void loadOnResume() {

    }

    @Override
    public void loadOnHiddenChanged(boolean hidden) {

    }

    public void getMovie(final Memo memo) {
        // memo==null，即action==show_detail或add_memo的时候，直接从网络获取电影信息并显示
        if (memo == null) {
            mView.showProgress();
            movieModel.getMovieById(movieId, new MovieModel.GetMovieCallBack() {
                @Override
                public void onSuccess(DoubanMovie doubanMovie) {
                    if (mView.isActive()) {
                        mView.hideProgress();
                        mView.showMovie(doubanMovie);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }
        // memo!=null，即action==show_memo的时候，先从数据库查询已保存的电影信息，再从网络上获取并比较，
        // 如果有不同的字段则刷新显示的电影信息并修改数据库中保存的信息
        else {
            mView.showMovie(memo.getDoubanMovie());
            movieModel.getMovieById(movieId, new MovieModel.GetMovieCallBack() {
                @Override
                public void onSuccess(DoubanMovie doubanMovie) {
                    compareAndUpdateMovie(memo.getDoubanMovie(), doubanMovie);
                    mView.showMovie(memo.getDoubanMovie());
                }

                @Override
                public void onFailure() {

                }
            });
        }

    }

    private DoubanMovie compareAndUpdateMovie(DoubanMovie movieFromLocal, DoubanMovie movieFromWeb) {
        DoubanMovie updateMovie = new DoubanMovie();
        updateMovie.setId(movieFromLocal.getId());
        boolean update = false;
        DoubanMovie.DoubanMovieImages images = updateMovie.new DoubanMovieImages();
        if ((movieFromLocal.getTitle() != null && !movieFromLocal.getTitle().equals(movieFromWeb.getTitle()))
                || (movieFromLocal.getTitle() == null && movieFromWeb.getTitle() != null)) {
            LogUtils.d(this, "local title:    " + movieFromLocal.getTitle() + "    web title:    " +
                    movieFromWeb.getTitle());
            updateMovie.setTitle(movieFromWeb.getTitle());
            movieFromLocal.setTitle(movieFromWeb.getTitle());
            update = true;
        }
        if ((movieFromLocal.getOriginal_title() != null && !movieFromLocal.getOriginal_title().equals(movieFromWeb
                .getOriginal_title()))
                || (movieFromLocal.getOriginal_title() == null && movieFromWeb.getOriginal_title() != null)) {
            LogUtils.d(this, "local original title:    " + movieFromLocal.getOriginal_title() +
                    "    web original title:    " + movieFromWeb.getOriginal_title());
            updateMovie.setOriginal_title(movieFromWeb.getOriginal_title());
            movieFromLocal.setOriginal_title(movieFromWeb.getOriginal_title());
            update = true;
        }
        if ((movieFromLocal.getAka() != null && !movieFromLocal.getAka().equals(movieFromWeb.getAka()))
                || (movieFromLocal.getAka() == null && movieFromWeb.getAka() != null)) {
            LogUtils.d(this, "local aka:    " + movieFromLocal.getAka() + "    web aka:    " + movieFromWeb.getAka());
            updateMovie.setAka(Arrays.asList(movieFromWeb.getAka().split("/")));
            movieFromLocal.setAka(Arrays.asList(movieFromWeb.getAka().split("/")));
            update = true;
        }
        if (Math.abs(movieFromLocal.getRating().getAverage() - movieFromWeb.getRating().getAverage()) > 0.09) {
            LogUtils.d(this, "local average score:    " + movieFromLocal.getRating().getAverage() +
                    "    web average score:    " + movieFromWeb.getRating().getAverage());
            DoubanMovie.DoubanMovieScore rating = updateMovie.new DoubanMovieScore();
            rating.setAverage(movieFromWeb.getRating().getAverage());
            updateMovie.setRating(rating);
            movieFromLocal.setRating(rating);
            update = true;
        }
        if ((movieFromLocal.getImages().getLarge() != null && !movieFromLocal.getImages().getLarge()
                .equals(movieFromWeb.getImages().getLarge()))
                || (movieFromLocal.getImages().getLarge() == null && movieFromWeb.getImages().getLarge() != null)) {
            LogUtils.d(this, "local large image:    " + movieFromLocal.getImages().getLarge() +
                    "    web large image:    " + movieFromWeb.getImages().getLarge());
            images.setLarge(movieFromWeb.getImages().getLarge());
            updateMovie.setImages(images);
            movieFromLocal.setImages(images);
            update = true;
        }
        if ((movieFromLocal.getImages().getMedium() != null && !movieFromLocal.getImages().getMedium()
                .equals(movieFromWeb.getImages().getMedium()))
                || (movieFromLocal.getImages().getMedium() == null && movieFromWeb.getImages().getMedium() != null)) {
            LogUtils.d(this, "local medium image:    " + movieFromLocal.getImages().getMedium() +
                    "    web medium image:    " + movieFromWeb.getImages().getMedium());
            images.setMedium(movieFromWeb.getImages().getMedium());
            updateMovie.setImages(images);
            movieFromLocal.setImages(images);
            update = true;
        }
        if ((movieFromLocal.getImages().getSmall() != null && !movieFromLocal.getImages().getSmall()
                .equals(movieFromWeb.getImages().getSmall()))
                || (movieFromLocal.getImages().getSmall() == null && movieFromWeb.getImages().getSmall() != null)) {
            LogUtils.d(this, "local small image:    " + movieFromLocal.getImages().getSmall() +
                    "    web small image:    " + movieFromWeb.getImages().getSmall());
            images.setSmall(movieFromWeb.getImages().getSmall());
            updateMovie.setImages(images);
            movieFromLocal.setImages(images);
            update = true;
        }
        if ((movieFromLocal.getSubtype() != null && !movieFromLocal.getSubtype().equals(movieFromWeb.getSubtype()))
                || (movieFromLocal.getSubtype() == null && movieFromWeb.getSubtype() != null)) {
            LogUtils.d(this, "local subtype:    " + movieFromLocal.getSubtype() + "    web subtype:    " +
                    movieFromWeb.getSubtype());
            updateMovie.setSubtype(movieFromWeb.getSubtype());
            movieFromLocal.setSubtype(movieFromWeb.getSubtype());
            update = true;
        }
        if ((movieFromLocal.getDirectorsNames() != null && !movieFromLocal.getDirectorsNames()
                .equals(movieFromWeb.getDirectorsNames()))
                || (movieFromLocal.getDirectorsNames() == null && movieFromWeb.getDirectorsNames() != null)) {
            LogUtils.d(this, "local directors' names:    " + movieFromLocal.getDirectorsNames() +
                    "    web directors' names:    " + movieFromWeb.getDirectorsNames());
            updateMovie.setDirectorsNames(movieFromWeb.getDirectorsNames());
            movieFromLocal.setDirectorsNames(movieFromWeb.getDirectorsNames());
            update = true;
        }
        if ((movieFromLocal.getCastsNames() != null && !movieFromLocal.getCastsNames()
                .equals(movieFromWeb.getCastsNames()))
                || (movieFromLocal.getCastsNames() == null && movieFromWeb.getCastsNames() != null)) {
            LogUtils.d(this, "local casts' names:    " + movieFromLocal.getCastsNames() +
                    "    web casts' names:    " + movieFromWeb.getCastsNames());
            updateMovie.setCastsNames(movieFromWeb.getCastsNames());
            movieFromLocal.setCastsNames(movieFromWeb.getCastsNames());
            update = true;
        }
        if ((movieFromLocal.getYear() != null && !movieFromLocal.getYear().equals(movieFromWeb.getYear()))
                || (movieFromLocal.getYear() == null && movieFromWeb.getYear() != null)) {
            LogUtils.d(this, "local year:    " + movieFromLocal.getYear() + "    web year:    " +
                    movieFromWeb.getYear());
            updateMovie.setYear(movieFromWeb.getYear());
            movieFromLocal.setYear(movieFromWeb.getYear());
            update = true;
        }
        if ((movieFromLocal.getGenres() != null && !movieFromLocal.getGenres().equals(movieFromWeb.getGenres()))
                || (movieFromLocal.getGenres() == null && movieFromWeb.getGenres() != null)) {
            LogUtils.d(this, "local genres:    " + movieFromLocal.getGenres() +
                    "    web genres:    " + movieFromWeb.getGenres());
            updateMovie.setGenres(Arrays.asList(movieFromWeb.getGenres().split("/")));
            movieFromLocal.setGenres(Arrays.asList(movieFromWeb.getGenres().split("/")));
            update = true;
        }
        if ((movieFromLocal.getCountries() != null && !movieFromLocal.getCountries()
                .equals(movieFromWeb.getCountries()))
                || (movieFromLocal.getCountries() == null && movieFromWeb.getCountries() != null)) {
            LogUtils.d(this, "local countries:    " + movieFromLocal.getCountries() +
                    "    web countries:    " + movieFromWeb.getCountries());
            updateMovie.setCountries(Arrays.asList(movieFromWeb.getCountries().split("/")));
            movieFromLocal.setCountries(Arrays.asList(movieFromWeb.getCountries().split("/")));
            update = true;
        }
        if ((movieFromLocal.getSummary() != null && !movieFromLocal.getSummary().equals(movieFromWeb.getSummary()))
                || (movieFromLocal.getSummary() == null && movieFromWeb.getSummary() != null)) {
            LogUtils.d(this, "local summary:    " + movieFromLocal.getSummary() + "    web summary:    " +
                    movieFromWeb.getSummary());
            updateMovie.setSummary(movieFromWeb.getSummary());
            movieFromLocal.setSummary(movieFromWeb.getSummary());
            update = true;
        }
        if ((movieFromLocal.getSeasons_count() != null && !movieFromLocal.getSeasons_count()
                .equals(movieFromWeb.getSeasons_count()))
                || (movieFromLocal.getSeasons_count() == null && movieFromWeb.getSeasons_count() != null)) {
            LogUtils.d(this, "local seasons count:    " + movieFromLocal.getSeasons_count() +
                    "    web seasons count:    " + movieFromWeb.getSeasons_count());
            updateMovie.setSeasons_count(movieFromWeb.getSeasons_count());
            movieFromLocal.setSeasons_count(movieFromWeb.getSeasons_count());
            update = true;
        }
        if ((movieFromLocal.getEpisodes_count() != null && !movieFromLocal.getEpisodes_count()
                .equals(movieFromWeb.getEpisodes_count()))
                || (movieFromLocal.getEpisodes_count() == null && movieFromWeb.getEpisodes_count() != null)) {
            LogUtils.d(this, "local episodes count:    " + movieFromLocal.getEpisodes_count() +
                    "    web episodes count:    " + movieFromWeb.getEpisodes_count());
            updateMovie.setEpisodes_count(movieFromWeb.getEpisodes_count());
            movieFromLocal.setEpisodes_count(movieFromWeb.getEpisodes_count());
            update = true;
        }
        if (update) {
            movieModel.updateMovie(updateMovie);
        }
        return movieFromLocal;
    }
}
