package org.live.circle.entity;

/**
 * Created by iBiandev on 16/6/16.
 */
public class DouyuEntity {
    private long id;
    private String cover;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    private String title;

    public DJ getDj() {
        return dj;
    }

    public void setDj(DJ dj) {
        this.dj = dj;
    }

    private DJ dj;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DouyuEntity() {
    }
}
