package com.zk.moviememos.view.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.moviememos.R;
import com.zk.moviememos.databinding.SimpleMovieMemoItemSlimBinding;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.vo.SimpleMovieMemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SimpleMovieMemoSlimAdapter extends RecyclerView.Adapter<SimpleMovieMemoSlimAdapter.BindingHolder> {

    private Context context;

    private List<SimpleMovieMemo> memos;

    public SimpleMovieMemoSlimAdapter(Context context, List<SimpleMovieMemo> memos) {
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
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final SimpleMovieMemoItemSlimBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.simple_movie_memo_item_slim, parent, false);
        BindingHolder holder = new BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        SimpleMovieMemo memo = memos.get(position);
        holder.binding.setSimpleMovieMemo(memo);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return memos.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleMovieMemoItemSlimBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public SimpleMovieMemoItemSlimBinding getBinding() {
            return binding;
        }

        public void setBinding(SimpleMovieMemoItemSlimBinding binding) {
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
}
