package com.brightlightsystems.core.datastructure;

import android.graphics.Color;

/**
 * This class describes a single trait of a lightbulb.
 * Trait is a collection of physical characteristics such as
 * color, brightness.
 * Created by Michael on 10/11/2015.
 */
public class Trait
{
    /**Default trait value.*/
    public static final Trait Default_Trait = new Trait();
    /**Default color value*/
    public static final Color Default_Color = new Color();
    /**Default brightness value*/
    public static final int Default_Brightness = 0;

    /**Current color of a light bulb. Can't be null*/
    private Color _color;
    /**Brightness of a light bulb. Can't be < 0*/
    private int _brightness;


    /**
     * Constructs a trait with default values
     */
    public Trait()
    {
        _color = Default_Color;
        _brightness = Default_Brightness;
        assert(_color != null);
    }

    /**
     * Constrcuts a trait with specified color and default brightness.
     * @param color color value for the new trait. If null trait will have default color value
     */
    public Trait(Color color)
    {
        if(color == null)
            _color = Default_Color;
        else
            _color = color;

        _brightness = Default_Brightness;
        assert(color != null);

    }

    /**
     * Constructs trait with specified color and brightness
     * @param color color value for the new trait. If null trait will have default color value
     * @param brightness brightness value for the new trait. If < 0 trait will have default brightness value
     */
    public Trait(Color color, int brightness)
    {
        if(color == null)
            _color = Default_Color;
        else
            _color = color;
        if(brightness < 0)
            _brightness = Default_Brightness;
        else
            _brightness = brightness;

        assert(color != null);
    }

    /**
     * Sets a new color for the trait.
     * @param color new color value for the  trait. If null method does nothing.
     * @return true if color was successfully changed, false otherwise.
     */
    public boolean setColor(Color color)
    {
        if(color == null)
            return false;

        _color = color;
        assert(_color != null);
        return true;
    }

    /**
     * Sets a new brightness for the trait.
     * @param brightness new brightness value for the  trait. If < 0 method does nothing.
     * @return true if color was successfully changed, false otherwise.
     */
    public boolean setBrightness(int brightness)
    {
        if(brightness < 0)
            return false;
        _brightness = brightness;
        return true;
    }

    /**
     * Ger current color value
     * @return current color value
     */
    public Color getColor()
    {
        return _color;
    }


    /**
     * Get current brightness value
     * @return current brightness value
     */
    public int getBrightness()
    {
        return _brightness;
    }

    //TODO: Implement additional methods that provide mechanisms to mix colors or...
    //TODO: ...Implement color class that derives from android.graphics.Color

/******************** end of class********************************/
}
