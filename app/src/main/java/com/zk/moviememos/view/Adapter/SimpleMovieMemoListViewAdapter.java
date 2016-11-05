package com.zk.moviememos.view.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.databinding.SimpleMovieMemoItemBinding;
import com.zk.moviememos.util.BitmapUtils;
import com.zk.moviememos.util.DisplayUtils;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.vo.SimpleMovieMemo;
import com.zk.moviememos.widget.PinnedSectionListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SimpleMovieMemoListViewAdapter extends BaseAdapter implements
        PinnedSectionListView.PinnedSectionListAdapter {

    private Context context;
    private List<SimpleMovieMemo> memos;
    private int currentType;

    public SimpleMovieMemoListViewAdapter(Context context, List<SimpleMovieMemo> memos) {
        this.context = context;
        setList(memos);
    }

    public void setList(List<SimpleMovieMemo> memos) {
        if (memos != null) {
            this.memos = memos;
        } else {
            memos = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public void addToList(List<SimpleMovieMemo> memos) {
        this.memos.addAll(memos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return memos.get(position).getViewType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == SimpleMovieMemo.VIEW_TYPE_SECTION;
    }

    @Override
    public int getCount() {
        return memos.size();
    }

    @Override
    public SimpleMovieMemo getItem(int position) {
        return memos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SimpleMovieMemo memo = getItem(position);
        SectionViewHolder sectionViewHolder = null;
        SimpleMovieMemoItemBinding binding = null;
        int type = getItemViewType(position);
        if (convertView == null || currentType != type) {
            if (type == SimpleMovieMemo.VIEW_TYPE_SECTION) {
                LogUtils.d(this, "create section");
                convertView = LayoutInflater.from(context).inflate(R.layout.simple_movie_memo_section, parent, false);
                sectionViewHolder = new SectionViewHolder();
                sectionViewHolder.sectionTextLeft = (TextView) convertView.findViewById(R.id.tv_section_text_left);
                sectionViewHolder.sectionTextRight = (TextView) convertView.findViewById(R.id.tv_section_text_right);
                convertView.setTag(sectionViewHolder);
                currentType = SimpleMovieMemo.VIEW_TYPE_SECTION;
            } else if (type == SimpleMovieMemo.VIEW_TYPE_ITEM) {
                LogUtils.d(this, "create item");
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.simple_movie_memo_item, parent, false);
                convertView = binding.getRoot();
                convertView.setTag(binding);
                currentType = SimpleMovieMemo.VIEW_TYPE_ITEM;
            }
        } else {
            if (type == SimpleMovieMemo.VIEW_TYPE_SECTION) {
                sectionViewHolder = (SectionViewHolder) convertView.getTag();
            } else if (type == SimpleMovieMemo.VIEW_TYPE_ITEM) {
                binding = (SimpleMovieMemoItemBinding) convertView.getTag();
            }
        }
        if (type == SimpleMovieMemo.VIEW_TYPE_SECTION) {
            sectionViewHolder.sectionTextLeft.setText(memo.getSectionTextLeft());
            sectionViewHolder.sectionTextRight.setText(memo.getSectionTextRight());
        } else if (type == SimpleMovieMemo.VIEW_TYPE_ITEM) {
            binding.setSimpleMovieMemo(memo);
            binding.executePendingBindings();

            final SimpleMovieMemoItemBinding finalBinding = binding;

            final File posterFile = new File(context.getFilesDir(), memo.getMovieId() + ".jpg");
            final File blurredPosterFile = new File(context.getFilesDir(), "B" + memo.getMovieId() + ".jpg");
            if (posterFile.exists() && blurredPosterFile.exists()) {
                Glide.with(context).load(posterFile).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        LogUtils.d(this, "从缓存读取图片：   " + posterFile.getPath());
                        finalBinding.ivMovieImageLarge.setImageBitmap(resource);
                    }
                });
                Glide.with(context).load(blurredPosterFile).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        LogUtils.d(this, "从缓存读取图片：   " + blurredPosterFile.getPath());
                        finalBinding.ivTitleBg.setImageBitmap(resource);
                    }
                });
            } else {
                Glide.with(context).load(memo.getImages().getLarge()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        LogUtils.d(this, "image:    " + memo.getImages().getLarge());
                        finalBinding.ivMovieImageLarge.setImageBitmap(resource);
                        Bitmap blurredPoster = BitmapUtils.doBlur(resource, 40, false);
                        blurredPoster = BitmapUtils.setBrightness(blurredPoster, -40);
                        finalBinding.ivTitleBg.setImageBitmap(blurredPoster);
                        BitmapUtils.saveBitmapToCacheDir(context, resource, memo.getMovieId() + ".jpg");
                        BitmapUtils.saveBitmapToCacheDir(context, blurredPoster, "B" + memo.getMovieId() + ".jpg");
                    }
                });
            }

            // 动态设置title的宽度，以适应不同屏幕的手机
            int windowWidth = DisplayUtils.getWindowWidth(App.getContext());
            float tvYear = DisplayUtils.getTextViewWidth(binding.tvYear, (String) binding.tvYear.getText());
            float tvTVWidth1 = DisplayUtils.getTextViewWidth(binding.tvTV, (String) binding.tvTV.getText());
            float tvRatingWidth1 = DisplayUtils.getTextViewWidth(binding.tvRating, (String) binding.tvRating.getText());
            int titleWidth = 0;
            if (binding.getSimpleMovieMemo().isTv()) {

                titleWidth = (int) (windowWidth - DisplayUtils.dip2px(App.getContext(), 149) - tvTVWidth1 - tvYear -
                        tvRatingWidth1 + 0.5f);
            } else {
                titleWidth = (int) (windowWidth - DisplayUtils.dip2px(App.getContext(), 149) - tvYear -
                        tvRatingWidth1 + 0.5f);
            }
            binding.tvMovieTitle.setMaxWidth(titleWidth);
        }
        return convertView;
    }

    class SectionViewHolder {

        public TextView sectionTextLeft;
        public TextView sectionTextRight;
    }

}
