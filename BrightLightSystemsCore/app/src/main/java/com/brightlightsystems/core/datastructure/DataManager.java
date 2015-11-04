package com.brightlightsystems.core.datastructure;

import com.brightlightsystems.core.utilities.definitions.DataStructureHelper;
import com.brightlightsystems.core.utilities.notificationsystem.Publisher;
import com.brightlightsystems.core.utilities.notificationsystem.Subscribable;
import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Singleton class that holds all structural key components
 * @author Michael Gulenko. Created on 10/30/2015.
 */
public class DataManager implements Subscribable
{
    /**
     * Instance of this class
     */
    private static DataManager _instance;

    private static Map<Integer,Theme>      _themeCollection;
    private static Map<Integer,Group>      _groupCollection;
    private static Map<Integer,Bridge>     _bridgeCollection;


    /**
     * Constructs an empty instance
     */
    private DataManager()
    {
        _themeCollection  = new LinkedHashMap<>();
        _groupCollection  = new LinkedHashMap<>();
        _bridgeCollection = new LinkedHashMap<>();
    }
    /**
     * Returns an instance of this class
     * @return - instance of the class
     */
    public static DataManager GetInstance()
    {

        if(_instance == null)
            _instance = new DataManager();
        return _instance;
    }


    /**
     * Get a collection of theme
     * @return - collection of themes
     */
    public Map<Integer, Theme> getThemeCollection() {
        return _themeCollection;
    }

    /**
     * Sets a collection of themes
     * @param themeCollection - specified collection themes.
     * @throws IllegalArgumentException if null or contains nulls
     */
    public void setThemeCollection(Set<Theme> themeCollection)
    {
        if(themeCollection == null ||themeCollection.contains(null))
            throw new IllegalArgumentException("Failed to create collection of themes.");
        _themeCollection = (Map<Integer, Theme>) DataStructureHelper.hueElementsToLinkedMap(themeCollection);
        assert(_themeCollection != null);
    }

    /**
     * Get a collection of groups
     * @return - collection of groups
     */
    public Map<Integer,Group> getGroupCollection() {
        return _groupCollection;
    }

    /**
     * Sets a collection of themes
     * @param groupCollection - a specified collection of groups
     * @throws IllegalArgumentException if null or contains nulls
     */
    public void setGroupCollection(Set<Group> groupCollection)
    {
        if(groupCollection == null ||groupCollection.contains(null))
            throw new IllegalArgumentException("Failed to create collection of groups.");
        _groupCollection = (Map<Integer, Group>) DataStructureHelper.hueElementsToLinkedMap(groupCollection);
        assert(_groupCollection != null);
    }

    /**
     * Get a collection of bridges
     * @return - collection of bridges
     */
    public Map<Integer,Bridge> getBridgeCollection() {
        return _bridgeCollection;
    }

    /**
     * Sets a collection of bridges
     * @param bridgeCollection - a specified collection of bridges
     * @throws IllegalArgumentException if null or contains nulls
     */
    public void setBridgeCollection(Set<Bridge> bridgeCollection)
    {
        if(bridgeCollection == null ||bridgeCollection.contains(null))
            throw new IllegalArgumentException("Failed to create collection of groups.");
        _bridgeCollection = (Map<Integer, Bridge>) DataStructureHelper.hueElementsToLinkedMap(bridgeCollection);
        assert (_bridgeCollection != null);
    }

    /**
     * Adds the specified theme into collection of themes
     * @param theme new theme to add.
     * @throws IllegalArgumentException if null.
     */
   public void addTheme(Theme theme)
   {
       if(theme == null)
           throw new IllegalArgumentException("Failed to add theme");
       _themeCollection.put(theme.getId(), theme);
   }

    /**
     * Removes theme by specified id.
     * @param id theme id
     */
    public void removeTheme(int id)
    {
        _themeCollection.remove(id);
        Publisher.publish(new SystemMessage<Integer>(Publisher.DELETE_SUBTHEME, id));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public <T> boolean onRecieve(SystemMessage<T> message) {
        return false;
    }
}
