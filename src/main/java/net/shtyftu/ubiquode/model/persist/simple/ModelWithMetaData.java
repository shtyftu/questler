package net.shtyftu.ubiquode.model.persist.simple;

/**
 * @author shtyftu
 */
public abstract class ModelWithMetaData extends ModelWithId {

    private ModelMetaData metaData;

    ModelWithMetaData() {
    }

    ModelWithMetaData(String id) {
        super(id);
    }

    public ModelMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ModelMetaData metaData) {
        this.metaData = metaData;
    }
}
