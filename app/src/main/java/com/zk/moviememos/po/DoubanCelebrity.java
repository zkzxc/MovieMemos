package com.zk.moviememos.po;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class DoubanCelebrity {

    private String id;
    private String name;
    private String alt;
    private DoubanCelebrityImages avatars;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public DoubanCelebrityImages getAvatars() {
        return avatars;
    }

    public void setAvatars(DoubanCelebrityImages avatars) {
        this.avatars = avatars;
    }

    public class DoubanCelebrityImages {

        private String small;
        private String medium;
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }
}
