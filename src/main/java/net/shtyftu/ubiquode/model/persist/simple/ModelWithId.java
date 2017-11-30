package net.shtyftu.ubiquode.model.persist.simple;

import net.shtyftu.ubiquode.model.AModel;

/**
 * @author shtyftu
 */
public abstract class ModelWithId extends AModel {

    private String id;

    ModelWithId() {
    }

    protected ModelWithId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
