package net.shtyftu.ubiquode.model.persist.composite;

import net.shtyftu.ubiquode.model.persist.simple.ModelWithId;

/**
 * @author shtyftu
 */
public abstract class ModelWithCompositeId<P> extends ModelWithId {

    private final P clusteringId;

    protected ModelWithCompositeId(String id, P clusteringId) {
        super(id);
        this.clusteringId = clusteringId;
    }

    protected P getClusteringId() {
        return clusteringId;
    }

}
