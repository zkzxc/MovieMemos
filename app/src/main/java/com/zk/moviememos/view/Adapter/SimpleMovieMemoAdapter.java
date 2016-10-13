package com.zk.moviememos.view.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.databinding.SimpleMovieMemoItemBinding;
import com.zk.moviememos.util.BitmapUtils;
import com.zk.moviememos.util.DisplayUtils;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.vo.SimpleMovieMemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SimpleMovieMemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        StickyRecyclerHeadersAdapter {

    private Context context;

    private List<SimpleMovieMemo> memos;

    public SimpleMovieMemoAdapter(Context context, List<SimpleMovieMemo> memos) {
        this.context = context;
        setList(memos);
    }

    public void setList(List<SimpleMovieMemo> memos) {
        if (memos != null) {
            this.memos = memos;
        } else {
            this.memos = new ArrayList<SimpleMovieMemo>();
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtils.d(this, "create item");
        SimpleMovieMemoItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.simple_movie_memo_item, parent, false);
        ItemBindingHolder holder = new ItemBindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        LogUtils.d(this, "bind item");
        final ItemBindingHolder itemBindingHolder = (ItemBindingHolder) holder;
        SimpleMovieMemo memo = memos.get(position);
        itemBindingHolder.binding.setSimpleMovieMemo(memo);
        itemBindingHolder.binding.executePendingBindings();

        Glide.with(context).load(memo.getImages().getLarge()).asBitmap().into(new SimpleTarget<Bitmap>(100, 150) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                itemBindingHolder.binding.ivMovieImageLarge.setImageBitmap(resource);
                Bitmap blurredPoster = BitmapUtils.doBlur(resource, 40, false);
                blurredPoster = BitmapUtils.setBrightness(blurredPoster, -40);
                itemBindingHolder.binding.ivTitleBg.setImageBitmap(blurredPoster);
            }
        });

        // 动态设置title的宽度，以适应不同屏幕的手机
        int windowWidth = DisplayUtils.getWindowWidth(App.getContext());
        float tvYear = DisplayUtils.getTextViewWidth(itemBindingHolder.binding.tvYear,
                (String) itemBindingHolder.binding.tvYear.getText());
        float tvTVWidth1 = DisplayUtils.getTextViewWidth(itemBindingHolder.binding.tvTV,
                (String) itemBindingHolder.binding.tvTV.getText());
        float tvRatingWidth1 = DisplayUtils.getTextViewWidth(itemBindingHolder.binding.tvRating,
                (String) itemBindingHolder.binding
                        .tvRating.getText());
        int titleWidth = 0;
        if (itemBindingHolder.binding.getSimpleMovieMemo().isTv()) {

            titleWidth = (int) (windowWidth - DisplayUtils.dip2px(App.getContext(), 149) - tvTVWidth1 - tvYear -
                    tvRatingWidth1 + 0.5f);
        } else {
            titleWidth = (int) (windowWidth - DisplayUtils.dip2px(App.getContext(), 149) - tvYear -
                    tvRatingWidth1 + 0.5f);
        }
        itemBindingHolder.binding.tvMovieTitle.setMaxWidth(titleWidth);
    }

    @Override
    public long getHeaderId(int position) {
        long headerId = Long.valueOf(memos.get(position).getViewingYear());
        LogUtils.d(this, headerId + "!!!!!!!     " + headerId);
        return headerId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        LogUtils.d(this, "create header");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_movie_memo_section, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        LogUtils.d(this, "bind header");
        String headerText = memos.get(position).getViewingYear();
        LogUtils.d(this, "headerText:         " + headerText);
        ((HeaderHolder) holder).tvHeaderText.setText(headerText);
//        TextView tv = (TextView)holder.itemView;
//        String headerText = memos.get(position).getViewingYear();
//        LogUtils.d(this, "headerText:         " + headerText);
//        tv.setText(headerText);
    }

    @Override
    public int getItemCount() {
        return memos.size();
    }

    public class ItemBindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleMovieMemoItemBinding binding;

        public ItemBindingHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public SimpleMovieMemoItemBinding getBinding() {
            return binding;
        }

        public void setBinding(SimpleMovieMemoItemBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {
            String memoId = binding.getSimpleMovieMemo().getMemoId();
            LogUtils.d(this, memoId + "is clicked!");
            if (onItemClickListener != null) {
                onItemClickListener.onItemclick(memoId);
            }
        }
    }

    public interface OnItemClickListener {

        void onItemclick(String memoId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        private TextView tvHeaderText;

        public HeaderHolder(View itemView) {
            super(itemView);
            tvHeaderText = (TextView) itemView.findViewById(R.id.tv_section_text_left);
        }
    }
}
