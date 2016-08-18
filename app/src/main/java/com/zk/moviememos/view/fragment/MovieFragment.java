package com.zk.moviememos.view.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zk.moviememos.R;
import com.zk.moviememos.contract.MovieContract;
import com.zk.moviememos.databinding.FragmentMovieBinding;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.util.BitmapUtils;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MovieFragment extends BaseFragment<MovieContract.Presenter> implements MovieContract.View {

    private static final String ARGUMENT = "argument";

    private static MovieFragment mFramgnet;

    private String movieId;
    private FragmentMovieBinding fragmentMovieBinding;
    private View mRoot;

    public MovieFragment(){
        this.mFramgnet = this;
    }

    public static MovieFragment getInstance(String movieId) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, movieId);
        if (mFramgnet == null) {
            mFramgnet = new MovieFragment();
        }
        mFramgnet.setArguments(bundle);
        return mFramgnet;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            movieId = bundle.getString(ARGUMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        fragmentMovieBinding = FragmentMovieBinding.inflate(inflater, container, false);
        mRoot = fragmentMovieBinding.getRoot();
        mRoot.setVisibility(View.INVISIBLE);
        return mRoot;
    }


    @Override
    public void showMovie(DoubanMovie movie) {
        if (isActive()) {
            fragmentMovieBinding.setDoubanMovie(movie);
            mRoot.setVisibility(View.VISIBLE);
            Glide.with(mActivity).load(movie.getImages().getLarge()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    fragmentMovieBinding.ivMovieImageLarge.setImageBitmap(resource);
                    fragmentMovieBinding.llIntro.setBackground(new BitmapDrawable(BitmapUtils.doBlur(resource, 40, false)));
                }
            });

            showCelebreties(movie);

        }
    }

    private void showCelebreties(DoubanMovie movie) {
        LinearLayout llCelebrities = fragmentMovieBinding.llCelebrities;
        if (movie.getDirectors() != null) {
            for (int i = 0; i < movie.getDirectors().size(); i++) {
                View celebrityItem = View.inflate(mActivity, R.layout.celebrity_item, null);
                TextView tvCelebrityTag = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_tag);
                if (i == 0) {
                    tvCelebrityTag.setText(R.string.director);
                }
                ImageView ivCelebrityImg = (ImageView) celebrityItem.findViewById(R.id.iv_celebrity_img);
                if (movie.getDirectors().get(i).getAvatars() != null) {
                    Glide.with(mActivity).load(movie.getDirectors().get(i).getAvatars().getMedium()).centerCrop()
                            .into(ivCelebrityImg);
                }
                TextView tvCelebrityName = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_name);
                tvCelebrityName.setText(movie.getDirectors().get(i).getName());
                llCelebrities.addView(celebrityItem);
            }
        }
        if (movie.getCasts() != null) {
            for (int i = 0; i < movie.getCasts().size(); i++) {
                View celebrityItem = View.inflate(mActivity, R.layout.celebrity_item, null);
                TextView tvCelebrityTag = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_tag);
                if (i == 0) {
                    tvCelebrityTag.setText(R.string.cast);
                }
                ImageView ivCelebrityImg = (ImageView) celebrityItem.findViewById(R.id.iv_celebrity_img);
                if (movie.getCasts().get(i).getAvatars() != null) {
                    Glide.with(mActivity).load(movie.getCasts().get(i).getAvatars().getMedium()).centerCrop()
                            .into(ivCelebrityImg);
                }
                TextView tvCelebrityName = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_name);
                tvCelebrityName.setText(movie.getCasts().get(i).getName());
                llCelebrities.addView(celebrityItem);
            }
        }
    }
}
