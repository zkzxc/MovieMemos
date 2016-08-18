package com.zk.moviememos.constants;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class DefaultConfigs {

    /**
     * 授权类常量
     */
    public static final String API_KEY = "049bafa6ee77ee5f0dd30ef4cb67cb1d";
    public static final String SECRET_KEY = "4973107c6df439c2";
    public static final String AUTH_URL = "https://www.douban.com/service/auth2/auth";
    public static final String ACCESS_TOKEN_URL = "https://www.douban.com/service/auth2/token";
    public static final String REDIRECT_URI = "https://moviememo.zk.com";
    public static final String RESPONSE_TYPE = "code";
    public static final String GRANT_TYPE = "authorization_code";

    /**
     *查询类常量
     */
    public static final String DOUBAN_BASE_URL = "https://api.douban.com/v2/movie/";
    public static final String DOUBAN_MOVIE_SEARCH = "search";
    public static final String DOUBAN_MOVIE_INFO = "subject";

    /**
     * 设置类常量
     */
    public static final String DATABASE_NAME = "moviememo";
}
