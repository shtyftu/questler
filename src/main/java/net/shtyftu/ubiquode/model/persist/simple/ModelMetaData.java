package net.shtyftu.ubiquode.model.persist.simple;

/**
 * @author shtyftu
 */
public class ModelMetaData {

    private String user;
    private long time;

    public ModelMetaData() {
    }

    public ModelMetaData(String user, long time) {
        this.user = user;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
