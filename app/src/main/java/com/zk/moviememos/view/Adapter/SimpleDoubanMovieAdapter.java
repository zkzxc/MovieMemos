package com.zk.moviememos.view.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.databinding.SimpleDoubanMovieItemBinding;
import com.zk.moviememos.util.DisplayUtils;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.util.ResourseUtils;
import com.zk.moviememos.vo.SimpleDoubanMovie;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SimpleDoubanMovieAdapter extends RecyclerView.Adapter<SimpleDoubanMovieAdapter.BindingHolder> {

    private Context context;

    private List<SimpleDoubanMovie> movies;

    public SimpleDoubanMovieAdapter(Context context, List<SimpleDoubanMovie> movies) {
        this.context = context;
        setList(movies);
    }

    public void setList(List<SimpleDoubanMovie> movies) {
        if (movies != null) {
            this.movies = movies;
        } else {
            this.movies = new ArrayList<SimpleDoubanMovie>();
        }
        notifyDataSetChanged();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final SimpleDoubanMovieItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.simple_douban_movie_item, parent, false);
        BindingHolder holder = new BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        binding.btnAddToSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(BusinessConstans.MOVIE_ACTIVITY_ACTION_ADD_MEMO, binding);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        SimpleDoubanMovie movie = movies.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.ivMovieImageMedium.setTransitionName(ResourseUtils
                    .getString(R.string.transition_name_poster));
        }
        Glide.with(context).load(movie.getImages().getMedium()).override(100, 150).into(holder.binding
                .ivMovieImageMedium);
        holder.binding.setSimpleDoubanMovie(movie);
        holder.binding.executePendingBindings();

        // 动态设置title的宽度，以适应不同屏幕的手机
        int windowWidth = DisplayUtils.getWindowWidth(App.getContext());
        float tvYear = DisplayUtils.getTextViewWidth(holder.binding.tvYear, (String) holder.binding.tvYear.getText());
        float tvTVWidth1 = DisplayUtils.getTextViewWidth(holder.binding.tvTV, (String) holder.binding.tvTV.getText());
        float tvRatingWidth1 = DisplayUtils.getTextViewWidth(holder.binding.tvRating, (String) holder.binding
                .tvRating.getText());
        int titleWidth = 0;
        if (holder.binding.getSimpleDoubanMovie().isTv()) {

            titleWidth = (int) (windowWidth - DisplayUtils.dip2px(App.getContext(), 149) - tvTVWidth1 - tvYear -
                    tvRatingWidth1 + 0.5f);
        } else {
            titleWidth = (int) (windowWidth - DisplayUtils.dip2px(App.getContext(), 149) - tvYear -
                    tvRatingWidth1 + 0.5f);
        }
        LogUtils.d(this, "titlewidth = " + titleWidth);
        holder.binding.tvMovieTitle.setMaxWidth(titleWidth);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleDoubanMovieItemBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public SimpleDoubanMovieItemBinding getBinding() {
            return binding;
        }

        public void setBinding(SimpleDoubanMovieItemBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {
            click(BusinessConstans.MOVIE_ACTIVITY_ACTION_SHOW_MOVIE_DETAIL, binding);
        }

    }

    private void click(String action, SimpleDoubanMovieItemBinding binding) {
        String movieId = binding.getSimpleDoubanMovie().getId();
        LogUtils.i(this, movieId + " is clicked!");
        if (onItemClickListener != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                onItemClickListener.onItemclick(action, movieId,
                        binding.getSimpleDoubanMovie().getTitle(),
                        binding.getSimpleDoubanMovie().isTv(),
                        binding.getSimpleDoubanMovie().getImages().getLarge(),
                        binding.ivMovieImageMedium, ResourseUtils.getString(R.string.transition_name_poster));
            } else {
                onItemClickListener.onItemclick(action, movieId,
                        binding.getSimpleDoubanMovie().getTitle(),
                        binding.getSimpleDoubanMovie().isTv(),
                        binding.getSimpleDoubanMovie().getImages().getLarge(), null, null);
            }
        }
    }

    public interface OnItemClickListener {

        void onItemclick(String action, String movieId, String title, boolean isTv, String posterUrl, ImageView
                imageView, String transitionName);
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
