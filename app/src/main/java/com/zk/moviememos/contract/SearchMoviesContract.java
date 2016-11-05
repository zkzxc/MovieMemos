package com.zk.moviememos.contract;

import android.widget.ImageView;

import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;
import com.zk.moviememos.vo.SimpleDoubanMovie;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface SearchMoviesContract {

    interface View extends BaseView<Presenter> {

        void hideBeforeSearch();

        void showResults(List<SimpleDoubanMovie> movies);

        void hideResults();

        void showItem(String action, String movieId, String title, boolean isTv,  String posterUrl, ImageView imageView,
                      String transitionName);

        void showNoResult();

        void hideNoResult();

        void hideInput();
    }

    interface Presenter extends BasePresenter {

        boolean onQueryTextSubmit(String query);

        boolean onQueryTextChange(String newText);
    }
}
