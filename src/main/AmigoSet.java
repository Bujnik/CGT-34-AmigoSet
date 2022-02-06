package main;

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
        return null;
    }

    @Override
    public int size() {
        return 0;
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
}