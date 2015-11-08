package com.brightlightsystems.core.datastructure;


/**
 * Abstract class that represents basic Hue element.
 * @author Michael Gulenko. Created on 10/07/2015
 */
public abstract class HueElement
{
    /**
     * Unique identifier > 0
     */
    private int _id;
    /**
     * Name of the element.
     */
    private String _name;


    /**
     * Constructs a Hue element with specified id, and name
     * @param id a specified id for new element. Has to be > 0
     * @param name name of the element.
     * @throws IllegalArgumentException if id < 1 or name == null
     */
    public HueElement(int id, String name)
    {
        assert (id > 0);
        if(id < 1  || name == null)
            throw new IllegalArgumentException("Can't create a hue element. Wrong arguments.");

        _id   = id;
        _name = name;
        repOK();
    }

    //checks representation
    private void repOK()
    {
        assert(_id > 0);
        assert(_name != null);
    }

    /**
     * Method returns an id of the element.
     * @return element's identifier.
     */
    public int getId()
    {
        return _id;
    }

    /**
     * Method returns a name of the element.
     * @return - element's name.
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Method sets a new name for the element
     * @param name element's new name.
     * @throws IllegalArgumentException if name == null
     */
    public void setName(String name)
    {
        if(name == null)
            throw new IllegalArgumentException("Can' change a name. Argument is null.");
        _name = name;
        repOK();
    }

    @Override
    public String toString()
    {
        return "id: " + _id + "\n" + "name: " + _name + "\n";
    }


}
