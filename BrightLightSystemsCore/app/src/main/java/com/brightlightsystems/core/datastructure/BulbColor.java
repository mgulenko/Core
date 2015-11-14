package com.brightlightsystems.core.datastructure;


import android.graphics.Color;

/**
 * Class describes a color of a light bulb
 * TODO: Add color rules for the light bulbs
 * @author Michael Gulenko
 */

public class BulbColor
{
    /**Default red component value */
    public static final int DEF_RED_COMPONENT = 0;
    /**Default green component value */
    public static final int DEF_GREEN_COMPONENT = 0;
    /**Default blue component value */
    public static final int DEF_BLUE_COMPONENT = 0;
    /**Default transparency value */
    public static final int DEF_TRANSPARENCY  = 100;

    /**Red component of the color*/
    private int _red;
    /**Blue component of the color*/
    private int _blue;
    /**Green component of he color*/
    private int _green;
    /**Color transparency*/
    private int _transparency;


    /**
     * Constructs color with default settings
     */
    public BulbColor()
    {
        _red   = DEF_RED_COMPONENT;
        _green = DEF_GREEN_COMPONENT;
        _blue  = DEF_BLUE_COMPONENT;
        _transparency = DEF_TRANSPARENCY;
        repOk();
    }

    public BulbColor(int color, int transparency)
    {
        if(color < 0 )
        {
            _red   = DEF_RED_COMPONENT;
            _green = DEF_GREEN_COMPONENT;
            _blue  = DEF_BLUE_COMPONENT;
        }
        else
        {
            _red   = Color.red(color);
            _green = Color.green(color);
            _blue  = Color.blue(color);
        }
        _transparency = transparency;
    }

    /**
     * Constructs color with specified red, green, and blue components.
     * If one of them is negative, then value fot that component will be replaced with the default
     * @param r - value for red component
     * @param g - value for green component
     * @param b - value for blue component
     */
    public BulbColor(int r, int g, int b)
    {
        _red   = (r > -1) ? r: DEF_RED_COMPONENT;
        _green = (g > -1) ? g: DEF_GREEN_COMPONENT;
        _blue  = (b > -1) ? b: DEF_BLUE_COMPONENT;
        _transparency = DEF_TRANSPARENCY;
        repOk();
    }

    /**
     * Constructs color with specified red, green, blue components, and transparency value.
     * If one of them is negative, then value fot that component will be replaced with the default
     * @param r - value for red component
     * @param g - value for green component
     * @param b - value for blue component
     * @param transparency - color transparency value.
     */
    public BulbColor(int r, int g, int b, int transparency)
    {
        this(r,g,b);
        _transparency = (transparency > -1) ? transparency : DEF_TRANSPARENCY;
        repOk();
    }

    private void repOk()
    {
        assert(_red < 0);
        assert(_green < 0);
        assert(_blue < 0);
        assert( _transparency < 0);
    }

    /**
     * Returns value for the red component
     * @return - red component value
     */
    public int getRed() {
        return _red;
    }

    /**
     * Returns value for the blue component
     * @return - blue component value
     */
    public int getBlue() {
        return _blue;
    }

    /**
     * Returns value for the green component
     * @return - green component value
     */
    public int getGreen() {
        return _green;
    }

    /**
     * Get color from rgb and alpha components.
     * @return color value of the light bulb
     */
    public int getColor()
    {
        return Color.argb(_transparency,_red,_green,_blue);
    }
    /**
     * Returns value for the transparency component
     * @return - transparency component value
     */
    public int getTransparency() {
        return _transparency;
    }

    /**
     * Sets red component value.
     * @param r - specified red component value.
     *            If < 0, then it'll be substituted with the default.
     */
    public void setRed(int r) {
        _red   = (r > -1) ? r: DEF_RED_COMPONENT;
    }

    /**
     * Sets blue component value.
     * @param b - specified red component value.
     *            If < 0, then it'll be substituted with the default.
     */
    public void setBlue(int b) {
        _blue  = (b > -1) ? b: DEF_BLUE_COMPONENT;
    }

    /**
     * Sets green component value.
     * @param g - specified red component value.
     *            If < 0, then it'll be substituted with the default.
     */
    public void setGreen(int g) {
        _green = (g > -1) ? g: DEF_GREEN_COMPONENT;
    }

    /**
     * Sets transparency value
     * @param alfa - specified transparency value.
     *            If < 0, then it'll be substituted with the default.
     */
    public void setTransparency(int alfa) {
        _transparency = (alfa > -1) ? alfa : DEF_TRANSPARENCY;
    }


    @Override
    public String toString()
    {
        return "R: " + _red + "  G:" + _green + "  B: " + _blue + "  Transparency: " + _transparency +"\n";
    }
    /******************** end of class********************************/
}