package com.brightlightsystems.core.datastructure;

import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class describes a Phillips Hue bridge device. The bridge can not control bulbs
 * that other bridge controls. TODO: implement mechanism to prevent that
 * The bridge can not control more than 50 bulbs at once.
 * @author Michael Gulenko Created on 10/16/2015.
 */
public class Bridge extends HueElement
{

    public static final byte INIT_BULB_COUNT = 50;
    /**Factory name of the bridge. Not an empty string. Database ensures that*/
    public final String _factoryName;
    /**Set of light bulbs they this bridge controls. Can't be null, can't contains nulls*/
    private Set<Lightbulb> _bulbs;


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
        _bulbs = new LinkedHashSet<>(INIT_BULB_COUNT);
        repOk();
    }

    /**
     * Constructs a Hue element with specified id, name, factory name, and set of bulbs
     * @param id   a specified id for new element. Has to be > 0
     * @param name name of the element.
     * @param factoryName factory name of the bridge
     * @throws IllegalArgumentException if id < 1 or name == null
     * @throws IllegalArgumentException if bulbs == null or contains nulls or size is greater than
     *                                  INIT_BULB_COUNT.
     */
    public Bridge(int id, String name, String factoryName, LinkedHashSet<Lightbulb> bulbs)
    {
        super(id, name);
        if(bulbs == null || bulbs.contains(null)||bulbs.size()>INIT_BULB_COUNT)
                throw new IllegalArgumentException("Can't create bridge.Invalid parameter");
        _factoryName = factoryName;
        _bulbs = bulbs;
        repOk();
    }


    /**
     * Get a set of light bulbs that are controlled by this bridge
     * @return set of light bulbs
     */
    public Set<Lightbulb> getBulbs()
    {
        return _bulbs;
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
        if(_bulbs.contains(bulb))
            _bulbs.remove(bulb);
        if(_bulbs.size()==INIT_BULB_COUNT)
            return false;
        return _bulbs.add(bulb);
    }

    /**
     * Removes bulb from the set
     * @param bulb bulb to be removed
     * @return true on success, false otherwise
     */
    public boolean removeBulb(Lightbulb bulb)
    {
        assert(_bulbs != null);
        return _bulbs.remove(bulb);
    }


    private void repOk()
    {
        assert(_bulbs != null);
        assert(_bulbs.size() <= INIT_BULB_COUNT);
        assert(!_bulbs.contains(null));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public <T> boolean onRecieve(SystemMessage<T> message) {

    }

}
