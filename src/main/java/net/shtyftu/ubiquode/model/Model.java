package net.shtyftu.ubiquode.model;

import com.google.gson.Gson;

/**
 * @author shtyftu
 */
public class Model {
    private static final Gson GSON = new Gson();

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
