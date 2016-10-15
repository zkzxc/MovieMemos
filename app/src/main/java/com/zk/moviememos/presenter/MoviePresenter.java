package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.MovieContract;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.model.MovieModel;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.view.Adapter.SimpleDoubanMovieAdapter;
import com.zk.moviememos.view.fragment.SeenMemosFragment;

import java.util.Arrays;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MoviePresenter implements MovieContract.Presenter {

    private DoubanMovieModel movieModel;
    private MovieContract.View mView;
    private String movieId;
    private Memo memo;

    public static MoviePresenter getInstance(DoubanMovieModel movieModel, MovieContract.View view, String movieId,
                                             Memo memo) {
        MoviePresenter presenter = new MoviePresenter(movieModel, view, movieId, memo);
        return presenter;
    }


    private MoviePresenter(DoubanMovieModel movieModel, MovieContract.View view, String movieId, Memo memo) {
        this.movieModel = movieModel;
        this.mView = view;
        this.movieId = movieId;
        this.memo = memo;
        mView.setPresenter(this);
    }

    @Override
    public void init() {
    }

    public void getMovie(String todo) {
        // 直接从网络获取电影信息
        if (SimpleDoubanMovieAdapter.SHOW_MOVIE_DETAIL.equals(todo)) {
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
        // 先从数据库查询已保存的电影信息，再从网络上获取并比较，如果有不同的字段则刷新显示的电影信息并修改数据库中保存的信息
        else if (SeenMemosFragment.SHOW_MEMO.equals(todo)) {
            if (mView.isActive()) {
                mView.showMovieFromMemo(memo);
                movieModel.getMovieById(movieId, new MovieModel.GetMovieCallBack() {
                    @Override
                    public void onSuccess(DoubanMovie doubanMovie) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        }

    }

    private DoubanMovie compareAndUpdateMovie(DoubanMovie movieFromLocal, DoubanMovie movieFromWeb) {
        boolean update = false;
        DoubanMovie.DoubanMovieImages images = movieFromLocal.getImages();
        if (!movieFromLocal.getTitle().equals(movieFromWeb.getTitle())) {
            LogUtils.d(this, "local title:    " + movieFromLocal.getTitle() + "    web title:    " +
                    movieFromWeb.getTitle());
            movieFromLocal.setTitle(movieFromWeb.getTitle());
            update = true;
        }
        if (!movieFromLocal.getOriginal_title().equals(movieFromWeb.getOriginal_title())) {
            LogUtils.d(this, "local original title:    " + movieFromLocal.getOriginal_title() +
                    "    web original title:    " + movieFromWeb.getOriginal_title());
            movieFromLocal.setOriginal_title(movieFromWeb.getOriginal_title());
            update = true;
        }
        if (!movieFromLocal.getAka().equals(movieFromWeb.getAka())) {
            LogUtils.d(this, "local aka:    " + movieFromLocal.getAka() + "    web aka:    " + movieFromWeb.getAka());
            movieFromLocal.setAka(Arrays.asList(movieFromWeb.getAka().split("/")));
            update = true;
        }
        if (Math.abs(movieFromLocal.getRating().getAverage() - movieFromWeb.getRating().getAverage()) > 0.09) {
            LogUtils.d(this, "local average score:    " + movieFromLocal.getRating().getAverage() +
                    "    web average score:    " + movieFromWeb.getRating().getAverage());
            DoubanMovie.DoubanMovieScore rating = movieFromLocal.new DoubanMovieScore();
            rating.setAverage(movieFromWeb.getRating().getAverage());
            movieFromLocal.setRating(rating);
            update = true;
        }
        if (!movieFromLocal.getImages().getLarge().equals(movieFromWeb.getImages().getLarge())) {
            LogUtils.d(this, "local large image:    " + movieFromLocal.getImages().getLarge() +
                    "    web large image:    " + movieFromWeb.getImages().getLarge());
            images.setLarge(movieFromWeb.getImages().getLarge());
            update = true;
        }
        if (!movieFromLocal.getImages().getMedium().equals(movieFromWeb.getImages().getMedium())) {
            LogUtils.d(this, "local medium image:    " + movieFromLocal.getImages().getMedium() +
                    "    web medium image:    " + movieFromWeb.getImages().getMedium());
            images.setMedium(movieFromWeb.getImages().getMedium());
            update = true;
        }
        if (!movieFromLocal.getImages().getSmall().equals(movieFromWeb.getImages().getSmall())) {
            LogUtils.d(this, "local small image:    " + movieFromLocal.getImages().getSmall() +
                    "    web small image:    " + movieFromWeb.getImages().getSmall());
            images.setSmall(movieFromWeb.getImages().getSmall());
            update = true;
        }
        if (!movieFromLocal.getSubtype().equals(movieFromWeb.getSubtype())) {
            LogUtils.d(this, "local subtype:    " + movieFromLocal.getSubtype() + "    web subtype:    " +
                    movieFromWeb.getSubtype());
            movieFromLocal.setSubtype(movieFromWeb.getSubtype());
            update = true;
        }
        if (!movieFromLocal.getDirectorsNames().equals(movieFromWeb.getDirectorsNames())) {
            LogUtils.d(this, "local directors' names:    " + movieFromLocal.getDirectorsNames() +
                    "    web directors' names:    " + movieFromWeb.getDirectorsNames());
            movieFromLocal.setDirectorsNames(movieFromWeb.getDirectorsNames());
            update = true;
        }
        if (!movieFromLocal.getCastsNames().equals(movieFromWeb.getCastsNames())) {
            LogUtils.d(this, "local casts' names:    " + movieFromLocal.getCastsNames() +
                    "    web casts' names:    " + movieFromWeb.getCastsNames());
            movieFromLocal.setCastsNames(movieFromWeb.getCastsNames());
            update = true;
        }
        if (!movieFromLocal.getYear().equals(movieFromWeb.getYear())) {
            LogUtils.d(this, "local year:    " + movieFromLocal.getYear() + "    web year:    " +
                    movieFromWeb.getYear());
            movieFromLocal.setYear(movieFromWeb.getYear());
            update = true;
        }
        if (!movieFromLocal.getGenres().equals(movieFromWeb.getGenres())) {
            LogUtils.d(this, "local genres:    " + movieFromLocal.getGenres() +
                    "    web genres:    " + movieFromWeb.getGenres());
            movieFromLocal.setGenres(Arrays.asList(movieFromWeb.getGenres().split("/")));
            update = true;
        }
        if (!movieFromLocal.getCountries().equals(movieFromWeb.getCountries())) {
            LogUtils.d(this, "local countries:    " + movieFromLocal.getCountries() +
                    "    web countries:    " + movieFromWeb.getCountries());
            movieFromLocal.setCountries(Arrays.asList(movieFromWeb.getCountries().split("/")));
            update = true;
        }
        if (!movieFromLocal.getSummary().equals(movieFromWeb.getSummary())) {
            LogUtils.d(this, "local summary:    " + movieFromLocal.getSummary() + "    web summary:    " +
                    movieFromWeb.getSummary());
            movieFromLocal.setSummary(movieFromWeb.getSummary());
            update = true;
        }
        if ((movieFromLocal.getSeasons_count() != null &&
                !movieFromLocal.getSeasons_count().equals(movieFromWeb.getSeasons_count())) ||
                (movieFromLocal.getSeasons_count() == null && movieFromWeb.getSeasons_count() != null)) {
            LogUtils.d(this, "local seasons count:    " + movieFromLocal.getSeasons_count() +
                    "    web seasons count:    " + movieFromWeb.getSeasons_count());
            movieFromLocal.setSeasons_count(movieFromWeb.getSeasons_count());
            update = true;
        }
        if ((movieFromLocal.getEpisodes_count() != null &&
                !movieFromLocal.getEpisodes_count().equals(movieFromWeb.getEpisodes_count())) ||
                (movieFromLocal.getEpisodes_count() == null && movieFromWeb.getEpisodes_count() != null)) {
            LogUtils.d(this, "local episodes count:    " + movieFromLocal.getEpisodes_count() +
                    "    web episodes count:    " + movieFromWeb.getEpisodes_count());
            movieFromLocal.setEpisodes_count(movieFromWeb.getEpisodes_count());
            update = true;
        }
        return movieFromLocal;
    }
}
