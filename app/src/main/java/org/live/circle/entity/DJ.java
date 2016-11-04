package org.live.circle.entity;

/**
 * Created by iBiandev on 16/6/22.
 */

public class DJ extends Base {

    enum Platform {
        DOUYU, QUANMIN, HUYA, PANDA;

    }
    private long id;
    private String name;

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    private Platform platform;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
