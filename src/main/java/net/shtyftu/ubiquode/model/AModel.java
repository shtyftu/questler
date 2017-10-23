package net.shtyftu.ubiquode.model;

import com.google.gson.Gson;

/**
 * @author shtyftu
 */
public abstract class AModel {
    private static final Gson GSON = new Gson();

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    protected long now() {
        return System.currentTimeMillis();

    }
}
