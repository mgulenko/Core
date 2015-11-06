package com.brightlightsystems.core.datastructure;
import com.brightlightsystems.core.utilities.definitions.DataStructureHelper;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
    /**Set of light bulbs they this bridge controls. Can't be null, can't contains nulls*/
    private Map<Integer, Lightbulb> _bulbs;


    /**
     * Constructs a Hue element with specified id, name, and factory name
     * @param id   a specified id for new element. Has to be > 0
     * @param name name of the element.
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
     * @throws IllegalArgumentException if id < 1 or name == null
     * @throws IllegalArgumentException if bulbs == null or contains nulls or size
     * is greater than INIT_BULB_COUNT.
     */
    public Bridge(int id, String name, String factoryName, Set<Lightbulb> bulbs)
    {
        super(id, name);
        if(bulbs == null || bulbs.contains(null)||bulbs.size()>INIT_BULB_COUNT)
                throw new IllegalArgumentException("Can't create bridge.Invalid parameter");
        _factoryName = factoryName;
        _bulbs = (Map<Integer, Lightbulb>) DataStructureHelper.hueElementsToLinkedMap(bulbs);
        repOk();
    }


    /**
     * Get a set of light bulbs that are controlled by this bridge
     * @return set of light bulbs
     */
    public Collection<Lightbulb> getBulbs()
    {
        return  _bulbs.values();
    }

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
     * Adds a set of new light bulbs to the set of bulbs.If the bulb is already present in the set,
     * then old instance will be removed and replaced with the new one. If the bridge can't
     * control any more bulbs (_bulbs.size() + bulb.size() ==INIT_BULB_COUNT)) then method does nothing.
     * @param bulbs set of bulbs that is to be added to the set
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if bulb is null
     */
    public boolean addBulbs(Set<Lightbulb> bulbs)
    {
        if(bulbs == null || bulbs.contains(null))
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_bulbs != null);
        if(_bulbs.size() + bulbs.size() > INIT_BULB_COUNT)
            return false;
        _bulbs.putAll((Map<Integer, Lightbulb>) DataStructureHelper.hueElementsToLinkedMap(bulbs));
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
     * Updates all light bulbs state. If the bulb is not present in the set, then that bulb will be skipped.
     * @param bulbs bulb that is to be updated within the set
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if bulb is null
     */
    public boolean updateAll(Set<Lightbulb> bulbs)
    {
        if(bulbs== null)
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_bulbs != null);
        for(Lightbulb b : bulbs)
        {
            update(b);
        }
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


    private void repOk()
    {
        assert(_bulbs != null);
        assert(_bulbs.size() <= INIT_BULB_COUNT);
        for(Map.Entry<Integer,Lightbulb> e: _bulbs.entrySet())
        {
            assert (e.getValue() != null);
        }
    }

    /******************** end of the class********************************/
}
