package net.shtyftu.ubiquode.model.persist.composite;

import net.shtyftu.ubiquode.model.persist.simple.PersistEntity;

/**
 * @author shtyftu
 */
public interface CompositeKeyPersistEntity<P> extends PersistEntity {

    P getPartitionKey();

}
