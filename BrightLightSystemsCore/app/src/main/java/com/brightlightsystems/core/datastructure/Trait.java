package com.brightlightsystems.core.datastructure;

import android.graphics.Color;

import java.text.DecimalFormat;

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
        _color = new Color();
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
            _color = new Color();
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
            _color = new Color();
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


    /**
     * Class describes a color of a light bulb
     * TODO: Add color rules for the light bulbs
     */
    public class Color extends android.graphics.Color
    {
        /**Default red component value */
        public static final int RED   = 0;
        /**Default green component value */
        public static final int GREEN = 0;
        /**Default blue component value */
        public static final int BLUE  = 0;
        /**Default transparency value */
        public static final int DEF_TRANSPARENCY  = 100;

        private int _red;
        private int _blue;
        private int _green;
        /**Color transparency*/
        private int _transparency;


        /**
         * Constructs color with default settings

         */
        public Color()
        {
            _red   = RED;
            _green = GREEN;
            _blue  = BLUE;
            _transparency = DEF_TRANSPARENCY;
            repOk();
        }

        /**
         * Constructs color with specified red, green, and blue components.
         * If one of them is negative, then value fot that component will be replaced with the default
         * @param r - value for red component
         * @param g - value for green component
         * @param b - value for blue component
         */
        public Color(int r, int g, int b)
        {
            _red   = (r > -1) ? r: RED;
            _green = (g > -1) ? g: GREEN;
            _blue  = (b > -1) ? b: BLUE;
            _transparency = DEF_TRANSPARENCY;
            repOk();
        }

        /**
         * Constructs color with specified red, green, blue components, and transparency value.
         * If one of them is negative, then value fot that component will be replaced with the default
         * @param r - value for red component
         * @param g - value for green component
         * @param b - value for blue component
         * @param alfa - color transparency value.
         */
        public Color(int r, int g, int b, int alfa)
        {
             this(r,g,b);
            _transparency = (alfa > -1) ? alfa : DEF_TRANSPARENCY;
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
            _red   = (r > -1) ? r: RED;
        }

        /**
         * Sets blue component value.
         * @param b - specified red component value.
         *            If < 0, then it'll be substituted with the default.
         */
        public void setBlue(int b) {
            _blue  = (b > -1) ? b: BLUE;
        }

        /**
         * Sets green component value.
         * @param g - specified red component value.
         *            If < 0, then it'll be substituted with the default.
         */
        public void setGreen(int g) {
            _green = (g > -1) ? g: GREEN;
        }

        /**
         * Sets transparency value
         * @param alfa - specified transparency value.
         *            If < 0, then it'll be substituted with the default.
         */
        public void setTransparency(int alfa) {
            _transparency = (alfa > -1) ? alfa : DEF_TRANSPARENCY;
        }


        /******************** end of class********************************/
    }


/******************** end of class********************************/
}
