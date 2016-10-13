package com.zk.moviememos.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.vo.SimpleMovieMemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class LocalMemoModel implements MemoModel {

    public static final String MOVIE_MEMO_COLUMN_ADD_TIME = "add_time";
    public static final String MOVIE_MEMO_COLUMN_VIEWING_DATE = "viewing_date";
    public static final String MOVIE_MEMO_COLUMN_AVERAGE_SCORE = "average_score";

    private static LocalMemoModel mModel;
    private static MovieMemoSQLiteOpenHelper helper;

    private LocalMemoModel() {
    }

    public static LocalMemoModel getInstance(Context context) {
        helper = MovieMemoSQLiteOpenHelper.getInstance(context);
        if (mModel == null) {
            mModel = new LocalMemoModel();
        }
        return mModel;
    }

    @Override
    public void saveMemo(DoubanMovie movie, Memo memo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = null;
        try {
            // 查询在movie表中是否存在该movie，存在且标记为想看的则修改为已看，不存在则插入一条movie
            cursor = db.rawQuery("select viewing_flag from movie where id = ?", new String[]{movie.getId()});
            if (cursor.moveToNext()) {
                String viewingFlag = cursor.getString(0);
                if (BusinessConstans.VIEWING_FLAG_WANT.equals(viewingFlag)) {
                    db.execSQL("update movie set viewing_flag = ? where id = ?", new String[]{
                            BusinessConstans.VIEWING_FLAG_SEEN, movie.getId()});
                }
            } else {
                db.execSQL("insert into movie values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{
                        movie.getId(), movie.getTitle(), movie.getOriginal_title(), movie.getAka(), movie
                        .getMobile_url(),
                        movie.getRating().getAverage(), movie.getRating_count(), movie.getImages().getSmall(),
                        movie.getImages().getMedium(), movie.getImages().getLarge(), movie.getSubtype(),
                        movie.getDirectorsNames(), movie.getCastsNames(), movie.getYear(), movie.getGenres(),
                        movie.getCountries(), movie.getSummary(), movie.getSeasons_count(), movie.getEpisodes_count(),
                        BusinessConstans.VIEWING_FLAG_SEEN
                });
            }
            // 查询在memo表中是否存在未删除的该memo，存在则修改该memo，不存在则插入一条memo
            cursor = db.rawQuery("select moviememo_id from moviememo where moviememo_id = ?",
                    new String[]{memo.getMovieMemoId()});
            if (cursor.moveToNext()) {
                ContentValues values = new ContentValues();
                values.put("viewing_date", memo.getViewingDate());
                values.put("viewing_way", memo.getViewingWay());
                values.put("viewing_version1", memo.getViewingVersion1());
                values.put("viewing_version2", memo.getViewingVersion2());
                values.put("movie_version", memo.getMovieVersion());
                values.put("viewing_mood", memo.getViewingMood());
                values.put("story_score", memo.getStoryScore());
                values.put("visual_score", memo.getVisualScore());
                values.put("aural_score", memo.getAuralScore());
                values.put("average_score", memo.getAverageScore());
                values.put("short_comment", memo.getShortComment());
                db.update("moviememo", values, "moviememo_id = ?", new String[]{memo.getMovieMemoId()});
            } else {
                db.execSQL("insert into moviememo (douban_movie_id, add_time, viewing_date, viewing_way," +
                                "viewing_version1, viewing_version2, movie_version, viewing_mood, story_score, " +
                                "visual_score, aural_score, average_score, short_comment, is_available) " +
                                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{movie.getId(), memo.getAddTime(), memo.getViewingDate(), memo.getViewingWay(),
                                memo.getViewingVersion1(), memo.getViewingVersion2(), memo.getMovieVersion(),
                                memo.getViewingMood(), memo.getStoryScore(), memo.getVisualScore(),
                                memo.getAuralScore(), memo.getAverageScore(), memo.getShortComment(),
                                BusinessConstans.IS_AVAILABLE_YES
                        });
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public void loadSeenMemos(SimpleMovieMemo simpleMovieMemo, int pageSize, int offset, String orderBy,
                              String orderWay, LoadMemosCallback callback) {
        List<SimpleMovieMemo> memos = findSimpleMovieMemoList(simpleMovieMemo, pageSize, offset, orderBy, orderWay);
        callback.onMemosLoaded(memos);
    }

    @Override
    public void findMemosByMovieId(String movieId, LoadMemosCallback callback) {
        List<SimpleMovieMemo> memos = new ArrayList<>();
        SimpleMovieMemo condition = new SimpleMovieMemo();
        condition.setMovieId(movieId);
        memos = findSimpleMovieMemoList(condition, 0, 0, "viewing_date", BusinessConstans.ORDER_WAY_DESC);
        callback.onMemosLoaded(memos);
    }

    @Override
    public void findMemoByMemoId(String memoId, LoadMemoCallback callback) {
        Memo memo = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select m.id, m.title, m.original_title, m.aka, m.douban_score, m.image_small, m.image_medium," +
                "m.image_large, m.subtype, m.directors, m.casts, m.year, m.genres, m.countries, m.summary, " +
                "m.seasons_count, m.episodes_count, " +
                "mm.add_time, mm.viewing_date, mm.viewing_way, mm.viewing_version1, mm.viewing_version2, " +
                "mm.movie_version, mm.viewing_mood, mm.story_score, mm.visual_score, mm.aural_score, mm" +
                ".average_score," +
                "mm.short_comment " +
                "from movie m, moviememo mm where m.id = mm.douban_movie_id and mm.is_available = 1 " +
                "and mm.moviememo_id = " + memoId;
        LogUtils.i(this, sql);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            memo = new Memo();
            DoubanMovie movie = new DoubanMovie();
            movie.setId(cursor.getString(0));
            movie.setTitle(cursor.getString(1));
            movie.setOriginal_title(cursor.getString(2));
            String akaStr = cursor.getString(3);
            if (!TextUtils.isEmpty(akaStr)) {
                movie.setAka(Arrays.asList(akaStr.split("/")));
            }
            DoubanMovie.DoubanMovieScore doubanMovieScore = movie.new DoubanMovieScore();
            doubanMovieScore.setAverage(cursor.getFloat(4));
            movie.setRating(doubanMovieScore);
            DoubanMovie.DoubanMovieImages doubanMovieImages = movie.new DoubanMovieImages();
            doubanMovieImages.setSmall(cursor.getString(5));
            doubanMovieImages.setMedium(cursor.getString(6));
            doubanMovieImages.setLarge(cursor.getString(7));
            movie.setImages(doubanMovieImages);
            movie.setSubtype(cursor.getString(8));
            movie.setDirectorsNames(cursor.getString(9));
            movie.setCastsNames(cursor.getString(10));
            movie.setYear(cursor.getString(11));
            String genresStr = cursor.getString(12);
            if (!TextUtils.isEmpty(genresStr)) {
                movie.setGenres(Arrays.asList(genresStr.split("/")));
            }
            String countriesStr = cursor.getString(13);
            if (!TextUtils.isEmpty(countriesStr)) {
                movie.setCountries(Arrays.asList(countriesStr.split("/")));
            }
            movie.setSummary(cursor.getString(14));
            movie.setSeasons_count(cursor.getInt(15));
            movie.setEpisodes_count(cursor.getString(16));
            memo.setDoubanMovie(movie);
            memo.setAddTime(cursor.getLong(17));
            memo.setViewingDate(cursor.getLong(18));
            memo.setViewingWay(cursor.getString(19));
            memo.setViewingVersion1(cursor.getString(20));
            memo.setViewingVersion2(cursor.getString(21));
            memo.setMovieVersion(cursor.getString(22));
            memo.setViewingMood(cursor.getString(23));
            memo.setStoryScore(cursor.getInt(24));
            memo.setVisualScore(cursor.getInt(25));
            memo.setAuralScore(cursor.getInt(26));
            memo.setAverageScore(cursor.getFloat(27));
            memo.setShortComment(cursor.getString(28));
        }
        db.close();
        callback.onMemoLoaded(memo);
    }

    @Override
    public void deleteMemoById(String memoId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = null;
        // 先更新memo表中的记录，再删除movie表中的记录
        try {
            db.execSQL("update moviememo set is_available = ? where moviememo_id = ?", new String[]{
                    BusinessConstans.IS_AVAILABLE_NO, memoId});
            cursor = db.rawQuery("select douban_movie_id from moviememo where moviememo_id = " + memoId, null);
            if (cursor.moveToNext()) {
                String movieId = cursor.getString(0);
                db.execSQL("delete from movie where id = " + movieId);
            } else {
                throw new RuntimeException("movie表中没有该movieId");
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 根据传入的条件分页查询简化的观影记录列表
     *
     * @param simpleMovieMemo 传入的筛选条件
     * @param pageSize        每页大小
     * @param offset          跳过前几条，从下一条开始返回
     * @param orderBy         排序字段，观影日期或评分
     * @param orderWay        排序方式，升序或降序
     * @return 简化的观影记录列表
     */
    public List<SimpleMovieMemo> findSimpleMovieMemoList(SimpleMovieMemo simpleMovieMemo, int pageSize, int offset,
                                                         String orderBy, String orderWay) {
        List<SimpleMovieMemo> memos = new ArrayList<SimpleMovieMemo>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String selectSimpleMemoSqlPrefix = "select m.id, m.title, m.original_title, m.image_small, m.image_medium," +
                "m.image_large, m.douban_score, m.year, m.subtype, m.directors, m.casts,  m.countries, m.genres," +
                "mm.moviememo_id, mm.viewing_date, mm.viewing_way, mm.viewing_version1, mm.viewing_version2, " +
                "mm.movie_version, mm.average_score, mm.short_comment, mm.add_time " +
                "from movie m, moviememo mm where m.id = mm.douban_movie_id and mm.is_available = 1 ";
        StringBuilder sb = new StringBuilder(selectSimpleMemoSqlPrefix);
        if (simpleMovieMemo != null) {
            if (simpleMovieMemo.getMovieId() != null) {
                sb.append("and m.id = ").append(simpleMovieMemo.getMovieId());
            }
            if (simpleMovieMemo.getCountries() != null) {
                sb.append("and countries like '%").append(simpleMovieMemo.getCountries()).append("%' ");
            }
            if (simpleMovieMemo.getGenres() != null) {
                sb.append("and genres like '%").append(simpleMovieMemo.getGenres()).append("%' ");
            }
            if (simpleMovieMemo.getSubtype() != null) {
                sb.append("and subtype = '").append(simpleMovieMemo.getSubtype()).append("' ");
            }
        }
        if (!TextUtils.isEmpty(orderBy)) {
            sb.append(" order by mm.").append(orderBy).append(" ").append(orderWay);
        }
        if (pageSize != 0) {
            sb.append(" limit ").append(offset).append(",").append(pageSize);
        }
        String sql = sb.toString();
        LogUtils.i(this, sql);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            SimpleMovieMemo simpleMemo = new SimpleMovieMemo();
            simpleMemo.setMovieId(cursor.getString(0));
            simpleMemo.setTitle(cursor.getString(1));
            simpleMemo.setOriginal_title(cursor.getString(2));
            SimpleMovieMemo.Images images = simpleMemo.new Images();
            images.setSmall(cursor.getString(3));
            images.setMedium(cursor.getString(4));
            images.setLarge(cursor.getString(5));
            simpleMemo.setImages(images);
            SimpleMovieMemo.Rating rating = simpleMemo.new Rating();
            rating.setAverage(cursor.getFloat(6));
            simpleMemo.setRating(rating);
            simpleMemo.setYear(cursor.getString(7));
            simpleMemo.setSubtype(cursor.getString(8));
            simpleMemo.setDirectors(cursor.getString(9));
            simpleMemo.setCasts(cursor.getString(10));
            simpleMemo.setCountries(cursor.getString(11));
            simpleMemo.setGenres(cursor.getString(12));
            simpleMemo.setMemoId(cursor.getString(13));
            Date viewingDate = new Date(cursor.getLong(14));
            simpleMemo.setViewingDate(viewingDate);
            simpleMemo.setViewingWay(cursor.getString(15));
            simpleMemo.setViewingVersion1(cursor.getString(16));
            simpleMemo.setViewingVersion2(cursor.getString(17));
            simpleMemo.setMovieVersion(cursor.getString(18));
            simpleMemo.setAverageScore(cursor.getFloat(19));
            simpleMemo.setShortComment(cursor.getString(20));
            Date addTime = new Date(cursor.getLong(21));
            simpleMemo.setAddTime(addTime);
            memos.add(simpleMemo);
        }
        cursor.close();
        db.close();
        LogUtils.i(this, "memos' size:" + memos.size());
        return memos;
    }
}
