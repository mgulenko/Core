package com.brightlightsystems.core.datastructure;

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
     * Current trait of the lightbulb. Can't be null.
     */
    private Trait        _trait;
    /**
     * Flag that indicates current state of the lightbulb
     */
    private States      _state;

    /**
     * Synch next bulb id with the last value in data base.
     * @param id next id
     */
    private void synchNextId(int id)
    {
        if(id >= NEXT_BULB_ID)
            NEXT_BULB_ID = id + 1;
    }

    /**
     * Constructs a lightbulb with specified parameters
     * @param id specified id of the light bulb
     * @param factoryName factory name of the lightbulb
     * @param name user defined name of the light bulb e.g. "kitchen lightbulb"
     * @param trait current traits of the light bulb. If null uses default settings.
     * @param state current state of the lightbulb.
     * @throws IllegalArgumentException if parameters are nulls
     */
    public Lightbulb(int id, String factoryName, String name, Trait trait, States state)
    {
        super(id, name);
        if(factoryName == null || state == null)
            throw new IllegalArgumentException("Can't create a lightbulb.One ore more parameters is null");

        _factoryName = factoryName;
        _state = state;
        if(trait == null)
            _trait = Trait.Default_Trait;
        else
            _trait = trait;
        repOk();
        synchNextId(id);
    }

    /**
     * Create a bulb from the specified bulb
     * @param bulb specified bulb that is used to create a new instance of the bulb.
     * Can't be null.
     */
    public Lightbulb(Lightbulb bulb)
    {
        this(bulb.getId(), bulb.getFactoryName(), bulb.getName(), bulb.getTrait(), bulb.getState());
    }

    /**
     * Get next bulb id
     * @return next bulb id
     */
    public static int getNextBulbId()
    {
        return NEXT_BULB_ID;
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
     * Converts id value of the state tro the state of the light bulb
     * @param stateId id of the state to be converted
     * @return enum value of the States
     */
    public static States intToState(int stateId)
    {
        switch (stateId)
        {
            case 1:
                return States.ON;
            case 2:
                return States.OFF;
            case 3:
                return States.DISCONNECTED;
        }
        return States.UNDEFINED;
    }

    /**
     * Converts sate value value into int
     * @param state  of the state to be converted
     * @return enum value of the States
     */
    public static int stateToInt(States state)
    {
        switch (state)
        {
            case ON:
                return 1;
            case OFF:
                return 2;
            case DISCONNECTED:
                return 3;
            case UNDEFINED:
                return 4;
        }

        return -1;
    }

    @Override
    public String toString()
    {
        String state = "UNDEFINED";
        switch(_state)
        {
            case ON:
                state = "ON";
                break;
            case OFF:
                state = "OFF";
                break;
            case DISCONNECTED:
                state = "DISCONNECTED";
        }
        return "Bulb: " + _factoryName +"\n" + super.toString() +
                "\n\nTrait: \n" + _trait.toString() + "\n State: " + state +"\n\n";
    }


    /**
     * Defines a state of the lightbulb.
     */
    public enum States
    {
        ON,                 // lightbulb is turned on
        OFF,                // lightbulb is turned off
        DISCONNECTED,       // lightbulb is disconnected
        UNDEFINED           // Undefined state
    }

    /******************** end of class********************************/
}
