package com.brightlightsystems.core.datastructure;

import com.brightlightsystems.core.utilities.definitions.DataStructureHelper;
import com.brightlightsystems.core.utilities.notificationsystem.Publisher;
import com.brightlightsystems.core.utilities.notificationsystem.Subscribable;
import com.brightlightsystems.core.utilities.notificationsystem.Subscriber;
import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;
import com.brightlightsystems.core.utilities.notificationsystem.Messages;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Singleton class that holds all structural key components
 * and responicble for managing data flow within it
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



    /**id of the current bridge that is controlled by the application.*/
    private static int _activeBridgeId;


    /**
     * Constructs an empty instance
     */
    private DataManager()
    {
        _themeCollection  = new LinkedHashMap<>();
        _groupCollection  = new LinkedHashMap<>();
        _bridgeCollection = new LinkedHashMap<>();
        _activeBridgeId = 0;
        subscribe();
    }
    /**
     * Returns an instance of this class
     * @return - instance of the class
     */
    public static DataManager getInstance()
    {

        if(_instance == null)
            _instance = new DataManager();
        return _instance;
    }

    /**
     * Get an id of the bridge that is controlled by the app
     * @return bridge id
     */
    public static int getActiveBridgeId() {
        return _activeBridgeId;
    }

    /**
     * Sets id for the current bridge that is controlled by the app
     * @param activeBridgeId
     * @throws IllegalArgumentException if < 1
     */
    public static void setActiveBridgeId(int activeBridgeId)
    {
        if(activeBridgeId < 1)
            throw new IllegalArgumentException("Illegal argument for the bridge id");
        _activeBridgeId = activeBridgeId;
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
        Publisher.publish(new SystemMessage<Integer>(Messages.MSG_REMOVE_SUBTHEMES, id));
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
        Publisher.publish(new SystemMessage<Integer>(Messages.MSG_REMOVE_SUBGROUPS, id));
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
     * Clears entire data structure.
     */
    public void removeAll()
    {
        _bridgeCollection.clear();;
        _groupCollection.clear();
        _themeCollection.clear();
        _activeBridgeId = 0;
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
        Subscriber.subscribe(this, Messages.MSG_ADD_BULB);
        Subscriber.subscribe(this, Messages.MSG_REMOVE_BULB);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_SINGLE_BULB);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_MULTI_BULB);
        Subscriber.subscribe(this, Messages.MSG_SYNC_BULB_STATE);

        Subscriber.subscribe(this, Messages.MSG_ADD_THEME);
        Subscriber.subscribe(this, Messages.MSG_REMOVE_THEME);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_SINGLE_THEME);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_COMPLEX_THEME);
        Subscriber.subscribe(this, Messages.MSG_ACTIVATE_THEME);
        Subscriber.subscribe(this, Messages.MSG_DEACTIVATE_THEME);
        Subscriber.subscribe(this, Messages.MSG_SYNC_THEMES);

        Subscriber.subscribe(this, Messages.MSG_ADD_GROUP);
        Subscriber.subscribe(this, Messages.MSG_REMOVE_GROUP);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_SINGLE_GROUP);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_COMPLEX_GROUP);
        Subscriber.subscribe(this, Messages.MSG_ACTIVATE_GROUP);
        Subscriber.subscribe(this, Messages.MSG_DEACTIVATE_GROUP);
        Subscriber.subscribe(this, Messages.MSG_SYNC_GROUPS);
    }

    @Override
    public void unsubscribe()
    {
        Subscriber.unsubscribe(this, Messages.MSG_ADD_BULB);
        Subscriber.unsubscribe(this, Messages.MSG_REMOVE_BULB);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_SINGLE_BULB);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_MULTI_BULB);
        Subscriber.unsubscribe(this, Messages.MSG_SYNC_BULB_STATE);

        Subscriber.unsubscribe(this, Messages.MSG_ADD_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_REMOVE_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_SINGLE_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_COMPLEX_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_ACTIVATE_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_DEACTIVATE_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_SYNC_THEMES);

        Subscriber.unsubscribe(this, Messages.MSG_ADD_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_REMOVE_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_SINGLE_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_COMPLEX_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_ACTIVATE_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_DEACTIVATE_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_SYNC_GROUPS);
    }

    @Override
    public <T> void onRecieve(SystemMessage<T> message)
    {

        switch(message.ID)
        {
            case Messages.MSG_ADD_BULB:
                _bridgeCollection.get(_activeBridgeId).addBulb((Lightbulb)message.getAttachment());
                break;
            case Messages.MSG_REMOVE_BULB:
                _bridgeCollection.get(_activeBridgeId).removeBulb((Integer)message.getAttachment());
                break;
            case Messages.MSG_UPDATE_SINGLE_BULB:
                _bridgeCollection.get(_activeBridgeId).update((Lightbulb) message.getAttachment());
                break;
            case Messages.MSG_UPDATE_MULTI_BULB:
            {
                Set<Lightbulb> bs = (Set<Lightbulb>) message.getAttachment();
                _bridgeCollection.get(_activeBridgeId).updateAll(bs);
            }
                break;
            case Messages.MSG_SYNC_BULB_STATE:
                //TODO: implement this message handler
                break;
            case Messages.MSG_ADD_GROUP:
            {
                Group g = (Group)message.getAttachment();
                _groupCollection.put(g.getId(),g);
            }
                break;
            case Messages.MSG_REMOVE_GROUP:
                removeGroup(((Integer)message.getAttachment()));
                break;
            case Messages.MSG_UPDATE_SINGLE_GROUP:
            {
                Group g = (Group)message.getAttachment();
                _groupCollection.get(g.getId()).updateBulbs((Set)g.getBulbs());
            }
                break;
            case Messages.MSG_UPDATE_COMPLEX_GROUP:
            {
                Group g = (Group)message.getAttachment();
                _groupCollection.get(g.getId()).updateBulbs((Set)g.getBulbs());
                _groupCollection.get(g.getId()).updateGroup((Set)g.getGroups());
            }
                break;
            case Messages.MSG_ACTIVATE_GROUP:
            {
                Group g = (Group) message.getAttachment();
                _groupCollection.get(g.getId()).activate();
            }
                break;
            case Messages.MSG_DEACTIVATE_GROUP:
            {
                Group g = (Group) message.getAttachment();
                _groupCollection.get(g.getId()).deactivate();
            }
                break;
            case Messages.MSG_SYNC_GROUPS:
                //TODO: implement message handler
                break;
            case Messages.MSG_ADD_THEME:
            {
                Theme t = (Theme) message.getAttachment();
                _themeCollection.put(t.getId(),t);
            }
                break;
            case Messages.MSG_REMOVE_THEME:
                removeTheme((Integer) message.getAttachment());
                break;
            case Messages.MSG_UPDATE_SINGLE_THEME:
            {
                Theme t = (Theme)message.getAttachment();
                _themeCollection.get(t.getId()).updateBulbs(t.getBulbs());
            }
                break;
            case Messages.MSG_UPDATE_COMPLEX_THEME:
            {
                Theme t = (Theme)message.getAttachment();
                _themeCollection.get(t.getId()).updateBulbs(t.getBulbs());
                _themeCollection.get(t.getId()).updateThemes((Set)t.getThemes());
            }
                break;
            case Messages.MSG_ACTIVATE_THEME:
            {
                Theme t = (Theme) message.getAttachment();
                _themeCollection.get(t.getId()).activate();
            }
                break;
            case Messages.MSG_DEACTIVATE_THEME:
                Theme t = (Theme) message.getAttachment();
                _themeCollection.get(t.getId()).deactivate();
                break;
            case Messages.MSG_SYNC_THEMES:
                //TODO: Implement message handler
                break;
        }
    }
}
