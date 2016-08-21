package com.zk.moviememos.view.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.databinding.ActivityMovieBinding;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.model.MovieModel;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.util.BitmapUtils;
import com.zk.moviememos.util.LogUtils;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MovieActivity extends AppCompatActivity {

    private ActivityMovieBinding binding;

    private Drawable mediumPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Bundle bundle = getIntent().getExtras();
        String movieId = bundle.getString("movieId");
        String title = bundle.getString("title");
        String mediumPosterUrl = bundle.getString("posterUrl");
        binding.collapsingToolbar.setTitle(title);
        Glide.with(this).load(mediumPosterUrl).asBitmap().priority(Priority.HIGH).into(new SimpleTarget<Bitmap>(100,150) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mediumPoster = new BitmapDrawable(resource);
                LogUtils.d(this, resource.getHeight() + "!!!!!!");
                binding.ivMovieImageLarge.setImageBitmap(resource);
                Bitmap blurredPoster = BitmapUtils.doBlur(resource, 20, false);
                blurredPoster = BitmapUtils.setBrightness(blurredPoster, -40);
                binding.ivMovieImageLargeBlur.setImageBitmap(blurredPoster);
            }
        });
        DoubanMovieModel.getInstance().getMovieById(movieId, new MovieModel.GetMovieCallBack() {
            @Override
            public void onSuccess(DoubanMovie doubanMovie) {
                binding.setDoubanMovie(doubanMovie);
                Glide.with(MovieActivity.this).load(doubanMovie.getImages().getLarge()).priority(Priority.NORMAL)
                        .placeholder(mediumPoster).dontAnimate().into(binding.ivMovieImageLarge);

                LinearLayout llCelebrities = (LinearLayout) findViewById(R.id.ll_celebrities);
                if (doubanMovie.getDirectors() != null) {
                    for (int i = 0; i < doubanMovie.getDirectors().size(); i++) {
                        View celebrityItem = View.inflate(MovieActivity.this, R.layout.celebrity_item, null);
                        TextView tvCelebrityTag = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_tag);
                        if (i == 0) {
                            tvCelebrityTag.setText("导演");
                        }
                        ImageView ivCelebrityImg = (ImageView) celebrityItem.findViewById(R.id.iv_celebrity_img);
                        if (doubanMovie.getDirectors().get(i).getAvatars() != null) {
                            Glide.with(MovieActivity.this).load(doubanMovie.getDirectors().get(i).getAvatars()
                                    .getMedium()).priority(Priority.LOW).centerCrop().into(ivCelebrityImg);
                        }
                        TextView tvCelebrityName = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_name);
                        tvCelebrityName.setText(doubanMovie.getDirectors().get(i).getName());
                        llCelebrities.addView(celebrityItem);
                    }
                }
                if (doubanMovie.getCasts() != null) {
                    for (int i = 0; i < doubanMovie.getCasts().size(); i++) {
                        View celebrityItem = View.inflate(MovieActivity.this, R.layout.celebrity_item, null);
                        TextView tvCelebrityTag = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_tag);
                        if (i == 0) {
                            tvCelebrityTag.setText("主演");
                        }
                        ImageView ivCelebrityImg = (ImageView) celebrityItem.findViewById(R.id.iv_celebrity_img);
                        if (doubanMovie.getCasts().get(i).getAvatars() != null) {
                            Glide.with(MovieActivity.this).load(doubanMovie.getCasts().get(i).getAvatars().getMedium
                                    ()).priority(Priority.LOW).centerCrop().into(ivCelebrityImg);
                        }
                        TextView tvCelebrityName = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_name);
                        tvCelebrityName.setText(doubanMovie.getCasts().get(i).getName());
                        llCelebrities.addView(celebrityItem);
                    }
                }
                TextView buttonAddToSeen = (TextView) findViewById(R.id.bn_add_to_seen);
                buttonAddToSeen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(App.getContext(), "add to seen", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
