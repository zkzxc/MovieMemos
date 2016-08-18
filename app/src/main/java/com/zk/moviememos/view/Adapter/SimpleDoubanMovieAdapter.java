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
import com.zk.moviememos.BR;
import com.zk.moviememos.R;
import com.zk.moviememos.databinding.SimpleDoubanMovieItemBinding;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.util.ResourseUtils;
import com.zk.moviememos.vo.SimpleDoubanMovie;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SimpleDoubanMovieAdapter extends RecyclerView.Adapter<SimpleDoubanMovieAdapter.BindingHolder> {

    private Context context;

    private List<SimpleDoubanMovie> movies;

    public SimpleDoubanMovieAdapter(Context context, List<SimpleDoubanMovie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public void setList(List<SimpleDoubanMovie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SimpleDoubanMovieItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.simple_douban_movie_item, parent, false);
        BindingHolder holder = new BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        SimpleDoubanMovie movie = movies.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.ivMovieImageMedium.setTransitionName(ResourseUtils
                    .getString(R.string.transition_name_poster));
        }
        Glide.with(context).load(movie.getImages().getMedium()).override(100,150).into(holder.binding.ivMovieImageMedium);
        holder.binding.setVariable(BR.simpleDoubanMovie, movie);
        holder.binding.executePendingBindings();
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
            String movieId = binding.getSimpleDoubanMovie().getId();
            LogUtils.i(this, movieId + " is clicked!");
            if (onItemClickListener != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    onItemClickListener.onItemclick(movieId, binding.getSimpleDoubanMovie().getTitle(), binding
                            .getSimpleDoubanMovie().getImages().getMedium(), binding.ivMovieImageMedium,
                            ResourseUtils.getString(R.string.transition_name_poster));
                } else {
                    onItemClickListener.onItemclick(movieId, binding.getSimpleDoubanMovie().getTitle(), binding
                            .getSimpleDoubanMovie().getImages().getMedium(), null, null);
                }
            }
        }

    }

    public interface OnItemClickListener {

        void onItemclick(String movieId, String title, String posterUrl, ImageView imageView, String transitionName);
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
