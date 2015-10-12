package com.brightlightsystems.core.datasctructure;

/**
 * This class describes a single Phillips Hue light bulb.
 * @author Michael Gulenko Created on 10/7/2015.
 */
public class Lightbulb
{
    /**
     * Factory name of the lightbulb. Can't be null.
     */
    private final String _factoryName;
    /**
     * User defined name of the lightbulb. Can't be null.
     */
    private String       _name;
    /**
     * Current trait of the lightbulb.
     */
    private Trait        _trait;
    /**
     * Flag that indicates current state of the lightbulb
     */
    private States      _state;

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
        if(factoryName == null || name == null || state == null)
            throw new IllegalArgumentException("Can't create a lightbulb.One ore more parameters is null");

        _factoryName = factoryName;
        _name = name;
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
     * Get a user defined name of the bulb
     * @return user defined name of the bulb.
     */
    public String getName()
    {
        return _name;
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
     * Gives a new name for the lightbulb
     * @param newName new name for te lightbulb.
     * @throws IllegalArgumentException if newName is null
     */
    public void setName(String newName)
    {
        if(newName == null)
            throw new IllegalArgumentException("Can't change name. New value is null");

        _name = newName;
        repOk();
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
        assert(_name        != null);
        assert(_trait       != null);
        assert(_state       != null);
    }

    public enum States
    {
        ON,
        OFF,
        DISCONNECTED
    }

}
