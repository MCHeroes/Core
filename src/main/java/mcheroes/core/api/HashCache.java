package mcheroes.core.api;

import java.util.HashMap;

public abstract class HashCache<K, V> extends HashMap<K, V> {
    /**
     * Loads a value in from a persistent data source if not available in cache.
     *
     * @param k the key
     * @return the loaded value
     */
    public abstract V load(K k);

    /**
     * Saves the key/value into a persistent data source.
     *
     * @param k the key
     * @param v the value
     */
    public abstract void save(K k, V v);

    @Override
    public V get(Object key) {
        if (containsKey(key)) return get(key);

        final V loaded = load((K) key);
        super.put((K) key, loaded);

        return loaded;
    }

    @Override
    public V put(K key, V value) {
        save(key, value);
        return super.put(key, value);
    }
}
