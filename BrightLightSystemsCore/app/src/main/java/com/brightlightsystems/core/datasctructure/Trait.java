package com.brightlightsystems.core.datasctructure;

import android.graphics.Color;

/**
 * This class describes a single trait of a lightbulb.
 * Trait is a collection of physical characteristics such as
 * color, brightness.
 * Created by Michael on 10/11/2015.
 */
public class Trait
{
    /**
     * Default settings for the trait
     */
    public static final Trait Default_Trait = new Trait();
    public static final Color Default_Color = new Color();
    public static final int Default_Brightness = 0;

    private Color _color;
    /**Brightness*/
    private int _brightness;


    public Trait()
    {
        _color = Default_Color;
        _brightness = Default_Brightness;
    }

    public Trait(Color color)
    {
        if(color == null)
            _color = Default_Color;
        else
            _color = color;

        _brightness = Default_Brightness;
        assert(color != null);

    }

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

    public boolean setColor(Color color)
    {
        if(color == null)
            return false;

        _color = color;
        assert(_color != null);
        return true;
    }

    public boolean setBrightness(int brightness)
    {
        if(brightness < 0)
            return false;
        _brightness = brightness;
        return true;
    }

    public Color getColor()
    {
        return _color;
    }


    public int getBrightness()
    {
        return _brightness;
    }

    //TODO: Implement additional methods that provide mechanisms to mix colors or...
    //TODO: ...Implement color class that derives from android.graphics.Color
/******************** end of class********************************/
}
