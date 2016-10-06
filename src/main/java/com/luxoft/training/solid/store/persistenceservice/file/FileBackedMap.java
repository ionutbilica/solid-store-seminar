package com.luxoft.training.solid.store.persistenceservice.file;

import com.luxoft.training.solid.store.persistence.PersistenceException;

import java.io.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileBackedMap<K extends Serializable,V extends Serializable> extends AbstractMap<K,V> {

    private final Map<K, V> map;
    private final File file;

    @SuppressWarnings("unchecked")
	public FileBackedMap(File file) {
        this.file = file;
        if (file.exists()) {
            try(
                ObjectInput input = new ObjectInputStream (new BufferedInputStream(new FileInputStream(file)))
            ) {
                map = (Map<K, V>) input.readObject();
            } catch(ClassNotFoundException | IOException ex) {
                throw new PersistenceException(ex);
            }
        } else {
            map = new HashMap<>();
        }
    }

    private void save() {
        try (
            ObjectOutput output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))
        ){
            output.writeObject(map);
        }
        catch(IOException ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public V put(K key, V value) {
        V prevVal = map.put(key, value);
        save();
        return prevVal;
    }
}
