package com.brightlightsystems.core.datasctructure;

import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

/**
 * This class describes a single Phillips Hue light bulb.
 * @author Michael Gulenko Created on 10/7/2015.
 */
public class Lightbulb extends HueElement
{
    /**
     * Id that will be used for the next bulb. IMPORTANT: value must be in sync with the database.
     * TODO: Add mechanism that validates synchronization
     */
    private static int NEXT_BULB_ID = 1;
    /**
     * Factory name of the lightbulb. Can't be null.
     */
    private final String _factoryName;
    /**
     * Current trait of the lightbulb.
     */
    private Trait        _trait;
    /**
     * Flag that indicates current state of the lightbulb
     */
    private States      _state;

    /**
     * Sets next bulb id.
     * @param nextId next id
     */
    public static void nextBulbIdInit(int nextId)
    {
        assert (nextId > NEXT_BULB_ID);
        if(nextId < 1)
            throw new IllegalArgumentException("Cant initialize next bulb id counter");
        NEXT_BULB_ID = nextId;
    }

    /**
     * Constructs a lightbulb with specified parameters
     * @param factoryName factory name of the lightbulb
     * @param name user defined name of the light bulb e.g. "kitchen lightbulb"
     * @param trait current traits of the light bulb. If null uses default settings.
     * @param state current state of the lightbulb.
     * @throws IllegalArgumentException if parameters are nulls
     */
    public Lightbulb(String factoryName, String name, Trait trait, States state)
    {
        super(NEXT_BULB_ID, name);
        if(factoryName == null || state == null)
            throw new IllegalArgumentException("Can't create a lightbulb.One ore more parameters is null");

        _factoryName = factoryName;
        _state = state;
        if(trait == null)
            _trait = Trait.Default_Trait;
        else
            _trait = trait;
        repOk();
    }

    /**
     * Create a bulb from the specified bulb
     * @param bulb specified bulb that is used to create a new instance of the bulb.
     * Can't be null.
     */
    public Lightbulb(Lightbulb bulb)
    {
        this(bulb.getFactoryName(), bulb.getName(), bulb.getTrait(), bulb.getState());
    }

    /**
     * Get factory name of the bulb
     * @return factory name
     */
    public String getFactoryName()
    {
        return _factoryName;
    }

    /**
     * Get current state of the bulb
     * @return state of the bulb
     */
    public States getState()
    {
        return _state;
    }

    /**
     * Get current trait of the bulb
     * @return current trait of the bulb.
     */
    public Trait getTrait()
    {
        return _trait;
    }

    /**
     * Sets a new trait for the light bulb
     * @param trait new trait to se. If null, te default value will be used
     * @see Trait
     */
    public void setTrait(Trait trait)
    {
        if(trait == null)
            _trait = Trait.Default_Trait;
        else
            _trait = trait;
    }

    /**
     * Sets a new state for the lightbulb
     * @param state new state to set
     * @throws IllegalArgumentException is parameter is null
     */
    public void setState(States state)
    {
        if(state == null)
            throw new IllegalArgumentException("Can't change current state. parameter is null");
        _state = state;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public <T> void onNotify(SystemMessage<T> message) {

    }

    /**
     * Validate class representation
     */
    private void repOk()
    {
        assert(_factoryName != null);
        assert(_trait       != null);
        assert(_state       != null);
    }



    /**
     * Defines a state of the lightbulb.
     */
    public enum States
    {
        ON,                 // lightbulb is turned on
        OFF,                // lightbulb is turned off
        DISCONNECTED        // lightbulb is disconnected
    }

    /******************** end of class********************************/
}
