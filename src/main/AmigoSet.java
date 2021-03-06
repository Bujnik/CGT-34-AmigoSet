package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet(){
        this.map = new HashMap<>();
    }

    public AmigoSet(Collection <? extends E> collection) {
        int size = (int) Math.max(16, Math.ceil(collection.size()/.75f));
        this.map = new HashMap<>(size);
        addAll(collection);
    }

    @Override
    public boolean add(E e) {
        if (map.containsKey(e)) return false;
        else {
            map.put(e, PRESENT);
            return true;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Spliterator<E> spliterator() {
        return Set.super.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return super.removeIf(filter);
    }

    @Override
    public Stream<E> stream() {
        return super.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return super.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        super.forEach(action);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean remove(Object o) {
        map.remove(o);
        return !map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object clone() {
        try {
            AmigoSet<E> cloneSet = (AmigoSet<E>) super.clone();
            cloneSet.map = (HashMap<E, Object>) map.clone();
            return cloneSet;
        }
        catch (Exception e) {
            throw new InternalError(e);
        }
    }

    /**
     * Serialization / deserialization
     * @param oos
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        //Serialize the object
        oos.defaultWriteObject();

        //Serialize capacity and loadFactor (capacity is int, loadFactor - float)
        oos.writeInt(HashMapReflectionHelper.<Integer>callHiddenMethod(map, "capacity"));
        oos.writeFloat(HashMapReflectionHelper.<Float>callHiddenMethod(map, "loadFactor"));

        //Serialize all map contents, size first
        oos.writeInt(map.size());
        for (E e : map.keySet()) oos.writeObject(e);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        //Read the object
        ois.defaultReadObject();

        //Deserialize capacity, load factor, size
        int capacity = ois.readInt();
        float loadFactor = ois.readFloat();
        int size = ois.readInt();


        //Recreate the map
        map = new HashMap<>(capacity, loadFactor);


        //Read all map contents
        for (int i = 0; i < size; i++) {
            E e = (E) ois.readObject();
            map.put(e, PRESENT);
        }
    }
}