package com.zk.moviememos.constants;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class BusinessConstans {

    /**
     * 电影的观影标记状态 想看 0
     */
    public static final String VIEWING_FLAG_WANT = "0";
    /**
     * 电影的观影标记状态 已看 1
     */
    public static final String VIEWING_FLAG_SEEN = "1";

    /**
     * 分类 电影 M
     */
    public static final String SUBTYPE_MOVIE = "M";
    /**
     * 分类 电视剧 T
     */
    public static final String SUBTYPE_TV = "T";

    /**
     * 排序方式 升序
     */
    public static final String ORDER_WAY_ASC = "ASC";
    /**
     * 排序方式 降序
     */
    public static final String ORDER_WAY_DESC = "DESC";

    /**
     * 观影方式 电影院
     */
    public static final String VIEWING_WAY_CINEMA = "1";
    /**
     * 观影方式 下载
     */
    public static final String VIEWING_WAY_DOWNLOAD = "2";
    /**
     * 观影方式 在线观看
     */
    public static final String VIEWING_WAY_ONLINE = "3";
    /**
     * 观影方式 DVD
     */
    public static final String VIEWING_WAY_DVD = "4";

    /**
     * 观影方式 电视台
     */
    public static final String VIEWING_WAY_TV = "5";
    /**
     * 观影方式 其他
     */
    public static final String VIEWING_WAY_OTHER = "9";

    /**
     * 观影版本参数1 普通
     */
    public static final String VIEWING_VERSION_NORMAL = "1";
    /**
     * 观影版本参数1 IMAX
     */
    public static final String VIEWING_VERSION_IMAX = "2";
    /**
     * 观影版本参数1 中国巨幕
     */
    public static final String VIEWING_VERSION_DMAX = "3";
    /**
     * 观影版本参数1 其他
     */
    public static final String VIEWING_VERSION_OTHER1 = "9";

    /**
     * 观影版本参数2 2D
     */
    public static final String VIEWING_VERSION_2D = "1";
    /**
     * 观影版本参数2 3D
     */
    public static final String VIEWING_VERSION_3D = "2";
    /**
     * 观影版本参数2 其他
     */
    public static final String VIEWING_VERSION_OTHER2 = "9";

    /**
     * 电影版本 公映版
     */
    public static final String MOVIE_VERSION_PUBLIC_RELEASE = "1";
    /**
     * 电影版本 导演剪辑版
     */
    public static final String MOVIE_VERSION_DIRECTORS_CUT = "2";
    /**
     * 电影版本 加长版
     */
    public static final String MOVIE_VERSION_EXTENDED_EDITION = "3";
    /**
     * 电影版本 其他
     */
    public static final String MOVIE_VERSION_OTHER = "9";

    /**
     * 观影状态 好
     */
    public static final String VIEWING_MOOD_GOOD = "3";
    /**
     * 观影状态 中
     */
    public static final String VIEWING_MOOD_AVERAGE = "2";
    /**
     * 观影状态 差
     */
    public static final String VIEWING_MOOD_BAD = "1";

    /**
     * 删除标记 可用，未删除
     */
    public static final String IS_AVAILABLE_YES = "1";
    /**
     * 删除标记 不可用，已删除
     */
    public static final String IS_AVAILABLE_NO = "0";
}
