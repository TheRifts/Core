package me.Lozke.data.PersistentDataType;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class BooleanDataType implements PersistentDataType<byte[], Boolean> {
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    @Override
    public byte[] toPrimitive(Boolean aBoolean, PersistentDataAdapterContext persistentDataAdapterContext) {
        aBoolean = aBoolean == null ? false : aBoolean;
        return new byte[]{(byte) (aBoolean ? 1 : 0)};
    }

    @Override
    public Boolean fromPrimitive(byte[] bytes, PersistentDataAdapterContext persistentDataAdapterContext) {
        return bytes[0] == 1;
    }
}
