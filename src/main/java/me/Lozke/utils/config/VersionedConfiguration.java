package me.Lozke.utils.config;

public interface VersionedConfiguration {

    String getVersion();

    String getLocalVersion();

    boolean needsToUpdate();

    boolean update();

    enum VersionUpdateType {
        BACKUP_NO_UPDATE, BACKUP_AND_UPDATE, BACKUP_AND_NEW, NOTHING
    }

}
