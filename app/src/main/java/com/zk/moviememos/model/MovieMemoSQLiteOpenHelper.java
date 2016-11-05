package com.zk.moviememos.model;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zk.moviememos.constants.DefaultConfigs;


/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MovieMemoSQLiteOpenHelper extends SQLiteOpenHelper {

    private static MovieMemoSQLiteOpenHelper helper;

    private MovieMemoSQLiteOpenHelper(Context context) {
        super(context, DefaultConfigs.DATABASE_NAME + ".db", null, 1);
    }

    public static MovieMemoSQLiteOpenHelper getInstance(Context context) {
        if (helper == null) {
            helper = new MovieMemoSQLiteOpenHelper(context);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        try {
            db.execSQL("CREATE TABLE [movie]([id] varchar(10) PRIMARY KEY,[title] varchar(256), [original_title] varchar" +
                    "(256),[pinyin_title] varchar(256), [aka] varchar(1024)," +
                    "[douban_score] float,[image_small] varchar(256),[image_medium] varchar(256)," +
                    "[image_large] varchar(256),[subtype] char(1),[directors] varchar(256),[casts] varchar(1024)," +
                    "[year] char(4),[genres] varchar(256), [countries] varchar(256),[summary] varchar(1024)," +
                    "[seasons_count] char(2), [episodes_count] char(3), [viewing_flag] char(1));");
            db.execSQL("CREATE TABLE [moviememo] ([moviememo_id] integer PRIMARY KEY AUTOINCREMENT, " +
                    "[douban_movie_id] varchar(10) CONSTRAINT [moviememo_movie_foreignkey] REFERENCES [movie]" +
                    "([id]), [add_time] long, [viewing_date] long, [viewing_way] char(1), [viewing_version1] " +
                    "varchar(1), [viewing_version2] char(1), [movie_version] char(1), [viewing_mood] char(1), " +
                    "[story_score] integer, [visual_score] integer, [aural_score] integer, [average_score] " +
                    "float, [short_comment] varchar(1024), [is_available] char(1));");
            db.execSQL("CREATE TABLE [tag]([tag_id] integer PRIMARY KEY AUTOINCREMENT,[content] varchar(256), " +
                    "[is_available] char(1));");
            db.execSQL("CREATE TABLE [moviememo_tag] ([moviememo_tag_id] integer PRIMARY KEY AUTOINCREMENT, " +
                    "[moviememo_id] integer CONSTRAINT [moviememo_tag_moviememo_foreignkey1] REFERENCES " +
                    "[moviememo]([moviememo_id]), [tag_id] integer CONSTRAINT [moviememo_tag_tag_foreignkey2] " +
                    "REFERENCES [tag]([tag_id]));");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
