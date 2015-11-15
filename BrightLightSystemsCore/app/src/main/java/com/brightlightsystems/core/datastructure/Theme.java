package com.brightlightsystems.core.datastructure;


import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class describes a single theme. A theme on its very
 * basic level is a collection of traits for light bulbs.
 * A theme can also contain another themes or collection of themes and traits.
 * Currently, if a light bulb is already present in one of other themes,
 * we allow to have the same bulb in other themes. The conflict will be resolved by the bridge
 * the trait for that light bulb stored in the last theme will be applied to that bulb.
 * TODO: Do we need better solution for that?
 * TODO: Implement thread safe features
 * @author Micahel Gulenko
 */
public class Theme extends HueElement
{
    /**
     * Initial indicator how much elements to store in themes
     */
    private static final byte INIT_THEME_COUNT = 16;

    /**
     * Id that will be used for the next theme. IMPORTANT: value must be in sync with the database.
     * TODO: Add mechanism that validates synchronization
     */
    private static int NEXT_THEME_ID = 1;

    /**
     * Collection of bulbs with traits.Can't be null, can't contains nulls
     * K is  an Integer id of the Lightbulb to the trait.
     */
    private Map<Integer,Trait> _traits;
    /**
     * Collection of themes.Can't be null, can't contains nulls.
     * K is an Integer id of the Theme
     */
    private Map<Integer, Theme> _themes;
    /**
     * Flag that indicates if the current theme has been selected and applied to physical bulbs.
     */
    private boolean _activated;

    /**Flag that indicates whether this theme belongs to favorite category or not*/
    private boolean _favorite;

    /**
     * Synch next bulb id with the last value in data base.
     * @param id next id
     */
    private static void synchNextId(int id)
    {
        if(id >= NEXT_THEME_ID)
            NEXT_THEME_ID = id + 1;
    }

    public static int getNextThemeId(){return NEXT_THEME_ID;}

    /**
     * Constructs an empty theme.
     * @param id id of the theme
     * @param name collection of bulbs with assigned traits
     * @param activated flag that indicates if this theme is activated or not
     * @param favorite indicates if this theme is in favorites
     */
    public Theme(int id, String name, boolean activated, boolean favorite)
    {
        super(id, name);
        _traits = new LinkedHashMap<>(Bridge.INIT_BULB_COUNT);
        _themes = new LinkedHashMap<>(INIT_THEME_COUNT);
        _activated = activated;
        _favorite = favorite;
        synchNextId(id);
    }

    /**
     * Constructs a theme from specified id, name a and a collection of bulbs with traits
     * @param id id of the theme
     * @param name name of the theme
     * @param traits collection of bulbs ids with assigned traits.
     * @param activated flag that indicates if this theme is activated or not
     * @param favorite indicates if this theme is in favorites
     * @throws IllegalArgumentException if bulbs == null or contains nulls
     */
    public Theme(int id, String name, Map<Integer, Trait> traits, boolean activated, boolean favorite)
    {
       super(id, name);
        if(traits == null || traits.containsKey(null) || traits.containsValue(null))
            throw new IllegalArgumentException("Can't create theme due to incorrect argument");

        _traits = new LinkedHashMap<>(traits);
        _themes = new LinkedHashMap<>(INIT_THEME_COUNT);
        _activated = activated;
        _favorite = favorite;
        synchNextId(id);
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
     * @param theme specified theme to add.
     * @return true if the theme was successfully added
     * @throws IllegalArgumentException if theme == null or contains nulls
     */
    public boolean addTheme(Theme theme)
    {
        assert(_themes != null);
        if(!validateTheme(theme))
            throw new IllegalArgumentException("Can't create theme due to incorrect argument");

        _themes.put(theme.getId(), theme);
        return true;
    }

    /**
     * Removes theme from the list of theme by specified id
     * @param id specified id of the theme in the collection of themes.
     * @return true if successfully removed, false otherwise
     * @throws IllegalArgumentException if pos < 0 or if pos >= size of the list
     *
     */
    public boolean removeTheme(int id)
    {
        assert(_themes != null);
        return _themes.remove(id) != null;
    }

    /**
     * Removes theme from the collection
     * @param theme theme to be removed
     * @return true if successfully removed, false otherwise
     *
     */
    public boolean removeTheme(Theme theme)
    {
        assert(_themes !=null);
        return _themes.remove(theme.getId())!= null;
    }


    /**
     * Removes all themes and traits that are in this theme.
     */
    public void clearAll()
    {
        assert(_traits != null);
        assert(_themes != null);
        _traits.clear();
        _themes.clear();
    }

    /**
     * Get count of themes that are within this theme
     * @return theme count
     */
    public int themeCount()
    {
        assert(_themes != null);
        return _themes.size();
    }

    /**
     * Get a collection of themes
     * @return collection of themes that represent this theme
     */
    public Collection<Theme> getThemeCollection()
    {
        return _themes.values();
    }

    public Map<Integer,Theme> getThemeMap(){return _themes;}

    /**
     * Stores specified trait with into this theme. If the bulb is already exists
     * then trait value will be updated for that bulb.
     * @param bulbId specified bulb id to store
     * @param trait characteristics that are assigned to the bulb
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if parameters are nulls
     */
    public Trait addTrait(int bulbId, Trait trait)
    {
        assert(_traits != null);
        if(trait == null)
            throw new IllegalArgumentException("Can't add trait. One or more parameters is null");

        return _traits.put(bulbId, trait);
    }

    /**
     * Add collection of bulbs ids and traits to the theme.
     * @param traits collection of bulbs ids and traits that needs to be added. Must be LinkedHashMap
     * @throws IllegalArgumentException if the parameter is null or contains nulls
     */
    public void addTraits(Map<Integer,Trait> traits)
    {
        assert(_traits != null);
        if(traits == null || traits.containsKey(null) || traits.containsValue(null))
            throw new IllegalArgumentException("Can't add trait. Parameter is null or contains nulls");
        _traits.putAll(traits);
    }

    /**
     * Get a map of traits
     * @return collection traits.
     */
    public Map<Integer,Trait> getTraitMap()
    {
        return _traits;
    }

    /**
     * Get count of bulbs in the theme
     * @return amount of bulbs contained in the collection of bulbs.
     */
    public int traitCount()
    {
        assert(_traits != null);
        return _traits.size();
    }

    /**
     * Counts all traits including traits in sub themes.
     * @return total count of all traits affected by this theme.
     */
    public int countAllTraits()
    {
        assert(_themes != null);
        int total = traitCount();
        for(Map.Entry<Integer,Theme> t: _themes.entrySet())
            total += t.getValue().traitCount();
        return total;
    }


    /**
     * Updates collection of traits in the theme.
     * @param traits collection of traits that is used for update. Must be LinkedHashMap
     * @throws IllegalArgumentException if the parameter is null or contains nulls
     */
    public void updateTraits(Map<Integer,Trait> traits)
    {
        if(traits == null)
            return;
        _traits.clear();
        addTraits(traits);
    }

    /**
     * Updates the theme collection. If value does not exist, then it will be added into the map
     * @param themes collection of theme that is used for update. Must be LinkedHashMap
     * @throws IllegalArgumentException if the parameter is null or contains nulls
     */
    public void updateThemes(Map<Integer, Theme> themes)
    {
        if(themes == null)
            return;
        if(themes.containsKey(null)||themes.containsKey(null))
            throw new IllegalArgumentException("Error while updating themes");
        assert(_themes != null);
        _themes.clear();
        _themes.putAll(themes);
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
        Map<Integer, Trait> bulbs = theme._traits;
        if(bulbs == null || bulbs.containsKey(null) || bulbs.containsValue(null))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        String str = "\nTheme: " + getId() + "   Name: "+ getName() + "\n\n" +
                "Contains following Traits: \n\n\t\t";
        for(Map.Entry<Integer, Trait> e: _traits.entrySet())
        {
            str += e.getValue().toString();
        }

        if(_themes.isEmpty())
            return str;

        str += "Contains following subgroups: \n\n\t\t";
        for(Map.Entry<Integer,Theme> e: _themes.entrySet())
        {
            str += e.getValue().toString();
        }
        return str;
    }

    /**
     * Asks the theme if its belong to favorite category
     * @return true if was added to favorites, false otherwise
     */
    public boolean isFavorite()
    {
        return _favorite;
    }

    /**
     * Adds theme to favorite
     */
    public void addToFavorites()
    {
        _favorite = true;
    }

    /**
     * Removes from favorite category.
     */
    public void removeFromFavorites(){_favorite = false;}


    /******************** end of class********************************/
}
