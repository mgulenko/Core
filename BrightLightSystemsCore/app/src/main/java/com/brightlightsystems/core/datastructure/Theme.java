package com.brightlightsystems.core.datastructure;


import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class describes a single theme. A theme on its very
 * basic level is a collection of light bulbs with assigned
 * traits.A theme can also contain another themes or
 * collection of themes and light bubs.
 * Currently, if a light bulb is already present in one of other groups,
 * we allow to have the same bulb in other themes. The conflict will be resolved by the bridge
 * the trait for that light bulb stored in the last theme will be applied to that bulb.
 * TODO: Do we need better solution for that?
 * TODO: Implement thread safe features
 * @author Micahel Gulenko
 */
public class Theme extends HueElement
{
    /**
     * Initial indicator how much elements to store in the _bulb s
     */
    private static final byte INIT_BULB_COUNT  = 32;
    /**
     * Initial indicator how much elemnts to store in the _collection
     */
    private static final byte INIT_THEME_COUNT = 16;

    /**
     * Id that will be used for the next theme. IMPORTANT: value must be in sync with the database.
     * TODO: Add mechanism that validates synchronization
     */
    private static int NEXT_THEME_ID = 1;

    /**
     * Collection of bulbs with traits.Can't be null, can't contains nulls
     */
    private Map<Lightbulb,Trait> _bulbs;
    /**
     * Collection of themes.Note that this data structure allows dupe entries, thus it is
     * essential to implement a mechanism that prevents adding dupe entries from the Front End.
     * Can't be null, can't contains nulls.
     */
    private List<Theme> _collection;
    /**
     * Flag that indicates if the current theme has been selected and applied to physical bulbs.
     */
    private boolean _activated;

    /**
     * Sets next theme id.
     * @param nextId next id
     */
    public static void nextThemeIdInit(int nextId)
    {
        assert (nextId > NEXT_THEME_ID);
        if(nextId < 1)
            throw new IllegalArgumentException("Cant initialize next theme id counter");
        NEXT_THEME_ID = nextId;
    }

    /**
     * Constructs an empty theme.
     * @param name list of bulbs with assigned traits
     */
    public Theme(String name)
    {
        super(NEXT_THEME_ID, name);
        _bulbs       = new HashMap<>(INIT_BULB_COUNT);
        _collection  = new ArrayList<>(INIT_THEME_COUNT);
        subscribe();
        NEXT_THEME_ID++;
    }

    /**
     * Constructs a theme from specified id, name a and a list of bulbs with traits
     * @param name name of the theme
     * @param bulbs list of bulbs with assigned traits.
     * @throws IllegalArgumentException if bulbs == null or contains nulls
     */
    public Theme(String name, Map<Lightbulb, Trait> bulbs)
    {
       super(NEXT_THEME_ID, name);
        if(bulbs == null || bulbs.containsKey(null) || bulbs.containsValue(null))
            throw new IllegalArgumentException("Can't create theme due to incorrect argument");

        _bulbs = bulbs;
        _collection  = new ArrayList<>(INIT_THEME_COUNT);
        _activated = true;
        subscribe();
        NEXT_THEME_ID++;

    }

    /**
     * Constructs a theme from specified theme. Note it does not add specified theme to the list.
     * It rather copies map of bulbs and traits.
     * @param name - name of the theme
     * @param theme - specified theme that is used to create a new theme
     * @throws IllegalArgumentException if theme == null or containing map has nulls
     */
    public Theme(String name, Theme theme)
    {
        super(NEXT_THEME_ID, name);
        if(!validateTheme(theme))
            throw new IllegalArgumentException("Can't create theme due to incorrect argument");

        _bulbs = theme._bulbs;
        _collection  = new ArrayList<>(INIT_THEME_COUNT);
        _activated = true;
        subscribe();
        NEXT_THEME_ID++;
    }

    /**
     * Constructs a complex theme from specified list of themes.
     * @param name - a name for the theme
     * @param collection - collection of themes that are added to the theme
     * @throws IllegalArgumentException if any of the themes == null or containing map has nulls
     */
    public Theme(String name, List<Theme> collection)
    {
        super(NEXT_THEME_ID, name);
        for(Theme t:collection)
        {
            if(!validateTheme(t))
                throw new IllegalArgumentException("Can't create theme due to incorrect argument");
        }

        _collection = collection;
        _bulbs       = new HashMap<>(INIT_BULB_COUNT);
        _activated = true;
        subscribe();
        NEXT_THEME_ID++;
    }

    /**
     * Constructs a complex theme from specified list of themes.
     * @param name - a name for the theme
     * @param collection - collection of themes that are added to the theme
     * @throws IllegalArgumentException if any of the themes == null or containing map has nulls
     */
    public Theme(String name, Map<Lightbulb, Trait> bulbs, List<Theme> collection )
    {
        super(NEXT_THEME_ID, name);
        if(bulbs == null || bulbs.containsKey(null) || bulbs.containsValue(null))
            throw new IllegalArgumentException("Can't create theme due to incorrect argument");
        _bulbs = bulbs;

        for(Theme t:collection)
        {
            if(!validateTheme(t))
                throw new IllegalArgumentException("Can't create theme due to incorrect argument");
        }

        _collection = collection;
        _activated = true;
        subscribe();
        NEXT_THEME_ID ++;
    }

    /**
     * Check if current theme is activated
     * @return true if activated, false otherwise
     */
    public boolean isActivated()
    {
        return _activated;
    }

    /**
     * Activate a theme
     */
    public void activate()
    {
        _activated = true;
    }

    /**
     * Deactivate a theme
     */
    public void deactivate()
    {
        _activated = false;
    }

    /**
     * Adds specified theme to a collection of themes within the theme
     * @param theme pecified theme to add.
     * @return true if the theme was successfully added
     * @throws IllegalArgumentException if theme == null or contains nulls
     */
    public boolean addTheme(Theme theme)
    {
        assert(_collection != null);
        if(!validateTheme(theme))
            throw new IllegalArgumentException("Can't create theme due to incorrect argument");

        _collection.add(theme);
        return true;
    }

    /**
     * Removes theme from the list of theme by specified position
     * @param pos specified position of the theme in the list of themes.
     * @return removed theme. Never null.
     * @throws IllegalArgumentException if pos < 0 or if pos >= size of the list
     *
     */
    public Theme removeTheme(int pos)
    {
        assert(_collection != null);
        if(pos < 0 || pos >= _collection.size())
            throw new IllegalArgumentException("Can't remove theme. Position value is negative");

        return _collection.remove(pos);
    }

    /**
     * Removes theme from the list of theme by specified position
     * @param theme specified position of the theme in the list of themes.
     * @return true on success. Flase if theme is not present in the list.
     *
     */
    public boolean removeTheme(Theme theme)
    {
        assert(_collection!=null);
        return _collection.remove(theme);
    }


    /**
     * Removes all themes and bulbs that are in this theme.
     * @return true on success, false otherwise.
     */
    public void clear()
    {
        assert(_bulbs      != null);
        assert(_collection != null);
        _bulbs.clear();
        _collection.clear();
    }

    /**
     * Get count of themes that are withing this theme
     * @return theme count
     */
    public int themeCount()
    {
        assert(_collection != null);
        return _collection.size();
    }

    /**
     * Get a collection of themes
     * @return collection of themes that represent this theme
     */
    public List<Theme> getThemes()
    {
        return _collection;
    }

    /**
     * Stores specified bulb with trait for this bulb into theme. If the bulb is already exists
     * than trait value will be updated for that bulb.
     * @param bulb specified bulb to store
     * @param trait characteristics that are assigned to the bulb
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if parameters are nulls
     */
    public Trait addBulb(Lightbulb bulb, Trait trait)
    {
        assert(_bulbs != null);
        if(bulb == null || trait == null)
            throw new IllegalArgumentException("Can't add bulb. One or more parameters is null");

        return _bulbs.put(bulb, trait);
    }

    /**
     * Add collection of bulbs and traits to the theme.
     * @param bulbs collection of bulbs and traits that needs to be added.
     * @throws IllegalArgumentException if the parameter is null or contains nulls
     */
    public void addBulbs(Map<Lightbulb,Trait> bulbs)
    {
        assert(_bulbs != null);
        if(bulbs == null || bulbs.containsKey(null) || _bulbs.containsValue(null))
            throw new IllegalArgumentException("Can't add bulbs. Parameter is null or contains nulls");
        _bulbs.putAll(bulbs);
    }

    /**
     * Get a collection of bulbs and traits
     * @return collection of bulbs and traits.
     */
    public Map<Lightbulb,Trait> getBulbs()
    {
        return _bulbs;
    }

    /**
     * Get count of bulbs in the theme
     * @return amount of bulbs contained in the collection of bulbs.
     */
    public int bulbCount()
    {
        assert(_bulbs != null);
        return _bulbs.size();
    }

    /**
     * Counts all bulbs including bulbs in sub themes.
     * @return total count of all bulbs affected by this theme.
     */
    public int countAllBulbs()
    {
        assert(_collection != null);
        int total = bulbCount();
        for(Theme t:_collection)
            total += t.bulbCount();
        return total;
    }


    /**
     * Updates collection of bulbs and traits in the theme.
     * @param bulbs collection of bulbs and traits that needs to be added.
     * @throws IllegalArgumentException if the parameter is null or contains nulls
     */
    public void updateBulbs(Map<Lightbulb,Trait> bulbs)
    {
        if(bulbs == null)
            return;
       addBulbs(bulbs);
    }

    public void updateThemes(List<Theme> themes)
    {
        if(themes == null)
            return;

        assert(_collection != null);
        for(Theme t:themes)
        {
            if(!validateTheme(t))
                throw new IllegalArgumentException("Can't update theme due to incorrect argument");
        }
        _collection = themes;
    }


    /**
     * Routine that validates constraints on the specified theme
     * @param theme specified theme constraints of which to check
     * @return true if passes validation, false otherwise.
     */
    private boolean validateTheme (Theme theme)
    {
        if(theme == null)
            return false;
        Map<Lightbulb, Trait> bulbs = theme._bulbs;
        if(bulbs == null || bulbs.containsKey(null) || bulbs.containsValue(null))
            return false;
        return true;
    }

    //////////////////////////OVERRIDES/////////////////////////////////////////////////////////////

    @Override
    public void subscribe()
    {

    }

    @Override
    public  void unsubscribe()
    {

    }

    @Override
    public <T> boolean onRecieve(SystemMessage<T> message)
    {

    }

    /******************** end of class********************************/
}
