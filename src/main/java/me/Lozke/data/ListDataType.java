package me.Lozke.data;

import me.Lozke.utils.Logger;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListDataType implements PersistentDataType<byte[], List> {
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<List> getComplexType() {
        return List.class;
    }

    @Override
    public byte[] toPrimitive(List list, PersistentDataAdapterContext persistentDataAdapterContext) {
        list.forEach(obj -> Logger.broadcast(obj.toString() + " " + obj));
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(list);
        } catch (IOException ignored) {
        }
        return byteOut.toByteArray();
    }

    @Override
    public List<Object> fromPrimitive(byte[] bytes, PersistentDataAdapterContext persistentDataAdapterContext) {
        ArrayList<Object> list = new ArrayList<>();
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(byteIn);
            list = (ArrayList<Object>) in.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        return list;
    }
}
