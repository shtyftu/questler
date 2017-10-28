package net.shtyftu.ubiquode.model.persist.simple;

import net.shtyftu.ubiquode.model.AModel;

/**
 * @author shtyftu
 */
public abstract class ModelWithId extends AModel {

    private final String id;

    protected ModelWithId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
