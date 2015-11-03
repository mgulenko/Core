package com.brightlightsystems.core.utilities.definitions;

        import java.util.Collection;
        import java.util.HashMap;
        import java.util.HashSet;
        import java.util.Set;
        import java.util.Map;

/**
 * This class is designed to simulate MultiMap behaviour. Essentially it is a wrapper around HashMap
 * with a Key to a HashSet of objects. NOTE: This class DOES NOT implement Map interface.
 * Use this class whenever you need to associate multiple values with a single key.
 * K - the type of keys. Can be mapped to multiple values.
 * V - the type of associated values.
 * TODO: Implement thread safe features
 * @author Michael Gulenko. Created on 09/06/2015
 */
@SuppressWarnings( {"unused"})
public class MultiMap<K,V>
{
    /**
     * Data container. Never null. Can't contain nulls.
     */
    protected Map<K,Set<V>> _map;

    public MultiMap()
    {
        _map = new HashMap<>();
    }

    /**
     * Removes everything from the map
     */
    public void clear()
    {
        _map.clear();
    }

    /**
     * Tests if the map contains specified key
     * @param k - the key which presence to be tested.
     * @return - true if the specified key is present in the map.
     *           false otherwise.
     */
    public boolean containsKey(K k)
    {
        return _map.containsKey(k);
    }

    /**
     * Tests if the map maps one or more keys to the specified set of values.
     * @param v - set, presence of which to be tested
     * @return - true if the map maps specified set. false otherwise.
     */
    public boolean containsSet (Set<V> v)
    {
        return _map.containsValue(v);
    }

    /**
     * Tests if the map has associated to the specified  key a specified value
     * @param k - key that is used to test an association of specified value
     * @param v - value association of which needs to be tested
     * @return - true if specified value is associated to the specified key. false otherwise.
     */
    public boolean containsAssociated(K k, V v)
    {
        assert (_map.get(k) != null);
        return _map.get(k).contains(v);
    }

    /**
     * Get a Set representation of the map
     * @return - Set representation. Never null.
     */
    public Set<Map.Entry<K,Set<V>>> entrySet()
    {
        return _map.entrySet();
    }

    /**
     * Get a set that is mapped to specified key
     * @param k - key which mapped set is to be returned.
     * @return - Set that is mapped to specified key, which contains all values that
     *           are associated to the specified key. Never Null.
     */
    public Set<V> get(K k)
    {
        return _map.get(k);
    }

    /**
     * Get a set of keys that specified value is associated with.
     * @param v - value that is used to get a set of keys
     * @return - Set of keys with the size of > 0, or null if specified value is not associated
     *           with any keys in the map.
     */
    public Set<K> getKeys(V v)
    {
        Set<K> keys = new HashSet<>();
        for(Map.Entry<K,Set<V>> e : _map.entrySet() )
        {
            if(e.getValue().contains(v))
                keys.add(e.getKey());
        }

        if(keys.size() > 0)
            return keys;
        return null;
    }

    /**
     * Tests if map contains any entries.
     * @return - true if the map contains entries. false otherwise.
     */
    public boolean isEmpty()
    {
        return _map.isEmpty();
    }

    /**
     * Get a Set representation of all keys containing in the map
     * @return - Set of keys containing in the map, or null if map is empty.
     */
    public Set<K> keySet()
    {
        if(_map.isEmpty())
            return null;
        return _map.keySet();

    }

    /**
     * Add specified value to the set of associated values
     * with the specified key. The key can be associated with many values,
     * but can not be associated with the same value more than once at a time.
     *
     * @param k - key with which the specified value is to be added to the list of associated values
     * @param v - value that needs to be added to the set of associated keys with the specified value.
     */
    public void put(K k, V v)
    {
        //  create new set if we don't have specified key
        if(!_map.containsKey(k))
        {
            Set<V> set = new HashSet<>();
            set.add(v);
            _map.put(k,set);
        }
        else
        {
            // something is REALLY f-ed up if set == null
            assert (_map.get(k) != null);
            _map.get(k).add(v);
        }
    }

    /**
     * Maps specified set of values with specified key. If the specified key already has
     * mapped set, then method will combine those 2 sets removing duplicated values.
     *
     * @param k - key with which the specified set is to be associated with.
     * @param values - values that needs to be associated with specified key.
     * @throws IllegalArgumentException if set is null.
     */
    public void put(K k, Set<V> values)
    {
        if(values == null)
            throw new IllegalArgumentException("Value can't be null");

        //add set
        if(!_map.containsKey(k))
            _map.put(k,values);
        else
        {
            // something is REALLY f-ed up if set == null
            assert (_map.get(k) != null);
            _map.get(k).addAll(values);
        }
    }

    /**
     * Replaces mapped Set of the specified key with a new set. Method does nothing if
     * v.size() == 0. If there were no previous mapping, method creates new entry.
     * @param k - key which mapped value is to be replaced.
     * @param v - Set that is to replace current mapped value.
     * @return - replaced value, or null if there were no mapping.
     * @throws   IllegalArgumentException if k == null, or v == null.
     */
    public Set<V> replace(K k, Set<V> v)
    {
        if(k == null || v == null)
            throw new IllegalArgumentException();
        return _map.put(k, v);
    }

    /**
     * Removes all values from specified key.
     * @param k - key which mapping is needs to be removed.
     * @return - a set representation of removed values or null if there were no mapping
     */
    public Set<V> removeAll(K k)
    {
        return _map.remove(k);
    }

    /**
     * Removes associated value from the set of associated values for the specified key.
     * @param k - a key association of which is to be removed for specified value.
     * @param v - values that is to be removed from the set of associated values for specified key.
     * @return - returns true on success, false otherwise.
     */
    public boolean remove(K k, V v)
    {
        assert(_map.get(k) != null);
        return _map.get(k).remove(v);
    }

    /**
     * Removes all associations of the specified value from the map
     * @param v - value that is to be dissociate from the map
     * @return - a map where key is the value that needs to be removed, and a set of
     *           keys that it was removed from. Returns null if the value
     *            was not associated to any keys.
     */
    public MultiMap<V,K> dissociateAll(V v)
    {
        MultiMap<V,K> map = new MultiMap<>();
        for(Map.Entry<K,Set<V>> e : _map.entrySet())
        {
            if(e.getValue().remove(v))
                map.put(v,e.getKey());
        }

        if(!map.isEmpty())
            return map;
        return null;
    }

    /**
     * Get number of mappings in the map
     * @return - amount of key-set pairs.
     */
    public int size()
    {
        return _map.size();
    }

    /**
     * Get amount of values that are associated to the specified key
     * @param k - key of which number of associated values is to be returned.
     * @return - return amount of associated values. returned value is > -1
     */
    public int associatedSize(K k)
    {
        assert(_map.get(k)!=null);
        return _map.get(k).size();
    }

    /**
     * Get a Collection representation of sets that are in the map.
     * @return - collection of sets. null if map is empty.
     */
    public Collection<Set<V>> values()
    {
        if(_map.isEmpty())
            return null;
        return _map.values();
    }

    public  Map<K,V> setToLinkedMap()
    {
        return null;
    }
    /******************** end of class********************************/
}

