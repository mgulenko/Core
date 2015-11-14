package com.brightlightsystems.core.datastructure;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class describes a Phillips Hue bridge device. The bridge can not control bulbs
 * that other bridge controls.
 * The bridge can not control more than 50 bulbs at once.
 * @author Michael Gulenko Created on 10/16/2015.
 */
public class Bridge extends HueElement
{
    /**Initial light bulb count. Since bridge can control only 50 of them thus init count is 50*/
    public static final int INIT_BULB_COUNT = 50;
    /**Factory name of the bridge. Not an empty string. Database ensures that*/
    public final String _factoryName;
    /**
     * Set of light bulbs they this bridge controls. Can't be null, can't contains nulls.
     * K is an Integer value of light bulb id that maps to the actual Lightbulb object
     */
    private Map<Integer, Lightbulb> _bulbs;


    /**
     * Constructs a Hue element with specified id, name, and factory name
     *
     * @param factoryName factory name of the bridge
     * @throws IllegalArgumentException if id < 1 or name == null
     */
    public Bridge(int id, String name, String factoryName)
    {
        super(id, name);
        _factoryName = factoryName;
        _bulbs = new LinkedHashMap<>(INIT_BULB_COUNT);
        repOk();
    }

    /**
     * Constructs a Hue element with specified id, name, factory name, and set of bulbs
     * @param id   a specified id for new element. Has to be > 0
     * @param name name of the element.
     * @param factoryName factory name of the bridge
     * @param bulbs a collection of light bulbs to add
     * @throws IllegalArgumentException if id < 1 or name == null
     * @throws IllegalArgumentException if bulbs == null or contains nulls or size
     * is greater than INIT_BULB_COUNT.
     */
    public Bridge(int id, String name, String factoryName, Map<Integer,Lightbulb> bulbs)
    {
        super(id, name);
        if(bulbs == null || bulbs.containsKey(null)|| bulbs.containsValue(null)||bulbs.size()>INIT_BULB_COUNT)
                throw new IllegalArgumentException("Can't create bridge.Invalid parameter");
        _factoryName = factoryName;
        _bulbs = new LinkedHashMap<>(bulbs);
        repOk();
    }


    /**
     * Get a collection of light bulbs that are controlled by this bridge
     * @return set of light bulbs
     */
    public Collection<Lightbulb> getBulbsCollection()
    {
        return  _bulbs.values();
    }

    /**
     * Get a map of light bulbs that are controlled by this bridge
     * @return
     */
    public Map<Integer,Lightbulb> getBulbsMap(){return _bulbs;}

    /**
     * Adds new light bulb to the set of bulbs.If the bulb is already present in the set,
     * then old instance will be removed and replaced with the new one. If the bridge can't
     * control any more bulbs (_bulbs.size()==INIT_BULB_COUNT)) then method does nothing.
     * @param bulb bulb that is to be added to the set
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if bulb is null
     */
    public boolean addBulb(Lightbulb bulb)
    {
        if(bulb == null)
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_bulbs != null);
        if(_bulbs.size()==INIT_BULB_COUNT)
            return false;
        _bulbs.put(bulb.getId(),bulb);
        return true;
    }

    /**
     * Adds a map of new light bulbs to the set of bulbs.If the bulb is already present in the set,
     * then old instance will be removed and replaced with the new one. If the bridge can't
     * control any more bulbs (_bulbs.size() + bulb.size() ==INIT_BULB_COUNT)) then method does nothing.
     * @param bulbs map of bulbs that is to be added to the collection. Must be LinkedHashMap,
     *              since we care about the order
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if bulb is null
     */
    public boolean addBulbs(Map<Integer,Lightbulb> bulbs)
    {
        if(bulbs == null || bulbs.containsKey(null) || bulbs.containsValue(null))
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_bulbs != null);
        if(_bulbs.size() + bulbs.size() > INIT_BULB_COUNT)
            return false;
        _bulbs.putAll(bulbs);
        return true;
    }

    /**
     * Updates a light bulb state.
     * @param bulb bulb that is to be updated within the set
     * @return true on success, false if the bulb is not present in the set
     * @throws IllegalArgumentException if bulb is null
     */
    public boolean update(Lightbulb bulb)
    {
        if(bulb == null)
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_bulbs != null);
        if(!_bulbs.containsKey(bulb.getId()))
            return false;
        _bulbs.put(bulb.getId(), bulb);
        return true;
    }

    /**
     * Updates all light bulbs state. Bulbs that are not present in the new collecion, but
     * are present in the old will be removed
     * @param bulbs bulb that is to be updated within the bridge. Must be LinkedHashMap,
     *              since we care about the order
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if bulbs is null or contain nulls
     */
    public boolean updateAll(Map<Integer,Lightbulb> bulbs)
    {
        if(bulbs== null || bulbs.containsKey(null)||bulbs.containsValue(null))
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_bulbs != null);
        _bulbs.clear();
        _bulbs.putAll(bulbs);
        return true;
    }

    /**
     * Removes bulb from the set
     * @param bulbId bulb to be removed
     * @return true on success, false otherwise
     */
    public Lightbulb removeBulb(int bulbId)
    {
        assert(_bulbs != null);
        return _bulbs.remove(bulbId);
    }

    /**
     * Gets a bulb by its id
     * @param bulbId bulb id
     * @return and instance of a lightbulb
     */
    public Lightbulb getBulb(int bulbId)
    {
        assert (_bulbs != null);
        return _bulbs.get(bulbId);
    }


    private void repOk()
    {
        assert(_bulbs != null);
        assert(_bulbs.size() <= INIT_BULB_COUNT);
        for(Map.Entry<Integer,Lightbulb> e: _bulbs.entrySet())
        {
            assert (e.getValue() != null);
        }
    }

    @Override
    public String toString()
    {
        return "\nBridge: " + _factoryName + "\n" + super.toString();
    }


    /******************** end of the class********************************/
}
