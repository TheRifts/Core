package me.Lozke.data.PersistentDataType;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import java.io.*;

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
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(aBoolean);
        } catch (IOException ignored) {
        }
        return byteOut.toByteArray();
    }

    @Override
    public Boolean fromPrimitive(byte[] bytes, PersistentDataAdapterContext persistentDataAdapterContext) {
        Boolean aBoolean = false;
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(byteIn);
            aBoolean = (Boolean) in.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        return aBoolean;
    }
}
