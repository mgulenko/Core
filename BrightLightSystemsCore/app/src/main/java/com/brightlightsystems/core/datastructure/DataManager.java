package com.brightlightsystems.core.datastructure;

import com.brightlightsystems.core.utilities.definitions.DataStructureHelper;
import com.brightlightsystems.core.utilities.notificationsystem.Publisher;
import com.brightlightsystems.core.utilities.notificationsystem.Subscribable;
import com.brightlightsystems.core.utilities.notificationsystem.Subscriber;
import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

import java.util.LinkedHashMap;
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

    /**Map of themes, where K is a theme's id and V is the actual theme*/
    private static Map<Integer,Theme>      _themeCollection;
    /**Map of groups, where K is a group's id and V is the actual group*/
    private static Map<Integer,Group>      _groupCollection;
    /**Map of bridges, where K is a bridge's id and V is the actual bridge*/
    private static Map<Integer,Bridge>     _bridgeCollection;


    /**
     * Constructs an empty instance
     */
    private DataManager()
    {
        _themeCollection  = new LinkedHashMap<>();
        _groupCollection  = new LinkedHashMap<>();
        _bridgeCollection = new LinkedHashMap<>();
        subscribe();
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

    /**
     * Adds the specified group into collection of groups
     * @param group new group to add.
     * @throws IllegalArgumentException if null.
     */
    public void addGroup(Group group)
    {
        if(group == null)
            throw new IllegalArgumentException("Failed to add group.");
        _groupCollection.put(group.getId(), group);
    }

    /**
     * Removes group by specified id.
     * @param id group id
     */
    public void removeGroup(int id)
    {
        _groupCollection.remove(id);
        Publisher.publish(new SystemMessage<Integer>(Publisher.DELETE_SUBGROUP, id));
    }

    /**
     * Adds the specified bridge into collection of bridges
     * @param bridge new group to add.
     * @throws IllegalArgumentException if null.
     */
    public void addBridge(Bridge bridge)
    {
        if(bridge == null)
            throw new IllegalArgumentException("Failed to add bridge.");
        _bridgeCollection.put(bridge.getId(), bridge);
    }

    /**
     * Removes bridge by specified id.
     * @param id bridge id
     */
    public void removeBridge(int id)
    {
        _bridgeCollection.remove(id);
    }


    @Override
    public void subscribe()
    {
        Subscriber.subscribe(this, Publisher.ACTIVATE_THEME);
        Subscriber.subscribe(this, Publisher.DEACTIVATE_THEME);
        Subscriber.subscribe(this, Publisher.UPDATE_THEME);
        Subscriber.subscribe(this, Publisher.DELETE_THEME);

        Subscriber.subscribe(this, Publisher.ACTIVATE_GROUP);
        Subscriber.subscribe(this, Publisher.DEACTIVATE_GROUP);
        Subscriber.subscribe(this, Publisher.UPDATE_GROUP);
        Subscriber.subscribe(this, Publisher.DELETE_GROUP);
    }

    @Override
    public void unsubscribe()
    {
        Subscriber.unsubscribe(this, Publisher.ACTIVATE_THEME);
        Subscriber.unsubscribe(this, Publisher.DEACTIVATE_THEME);
        Subscriber.unsubscribe(this, Publisher.UPDATE_THEME);
        Subscriber.unsubscribe(this, Publisher.DELETE_THEME);

        Subscriber.unsubscribe(this, Publisher.ACTIVATE_GROUP);
        Subscriber.unsubscribe(this, Publisher.DEACTIVATE_GROUP);
        Subscriber.unsubscribe(this, Publisher.UPDATE_GROUP);
        Subscriber.unsubscribe(this, Publisher.DELETE_GROUP);
    }

    @Override
    public <T> void onRecieve(SystemMessage<T> message)
    {
        Integer id;
        Theme theme;
        Group group;
        switch(message.ID)
        {
            case Publisher.ACTIVATE_THEME:
                id = (Integer) message.getAttachment();
                _themeCollection.get(id).activate();
                break;
            case Publisher.DEACTIVATE_THEME:
                id = (Integer) message.getAttachment();
                _themeCollection.get(id).deactivate();
                break;
            case Publisher.UPDATE_THEME:
                theme = (Theme)message.getAttachment();
                _themeCollection.get(theme.getId()).updateBulbs(theme.getBulbs());
                break;
            case Publisher.DELETE_THEME:
                id = (Integer) message.getAttachment();
                _themeCollection.remove(id);
                break;
            case Publisher.ACTIVATE_GROUP:
                id = (Integer) message.getAttachment();
                _groupCollection.get(id).activate();
                break;
            case Publisher.DEACTIVATE_GROUP:
                id = (Integer) message.getAttachment();
                _groupCollection.get(id).deactivate();
                break;
            case Publisher.UPDATE_GROUP:
                group = (Group) message.getAttachment();
                _groupCollection.get(group.getId()).updateBulbs((Set<Lightbulb>) group.getBulbs());
                break;
            case Publisher.DELETE_GROUP:
                id = (Integer) message.getAttachment();
                _groupCollection.remove(id);
                break;
        }
    }
}
