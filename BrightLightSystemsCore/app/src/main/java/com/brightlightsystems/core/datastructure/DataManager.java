package com.brightlightsystems.core.datastructure;

import com.brightlightsystems.core.utilities.notificationsystem.BulbListener;
import com.brightlightsystems.core.utilities.notificationsystem.BulbMessage;
import com.brightlightsystems.core.utilities.notificationsystem.GroupListener;
import com.brightlightsystems.core.utilities.notificationsystem.GroupMessage;
import com.brightlightsystems.core.utilities.notificationsystem.Subscriber;
import com.brightlightsystems.core.utilities.notificationsystem.ThemeListener;
import com.brightlightsystems.core.utilities.notificationsystem.ThemeMessage;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Singleton class that holds all structural key components
 * and responsible for managing data flow within it
 * @author Michael Gulenko. Created on 10/30/2015.
 */
public final class DataManager implements BulbListener,GroupListener,ThemeListener
{
    /**
     * Instance of this class
     */
    private static DataManager _instance;

    /**Map of themes, where K is a theme's id and V is the actual theme*/
    private static Map<Integer,Theme>                    _themeCollection;
    /**Map of groups, where K is a bridge id and V is another map of group id's to the V of the actual group*/
    private static Map<Integer, Map<Integer,Group>>      _groupCollection;
    /**Map of bridges, where K is a bridge's id and V is the actual bridge*/
    private static Map<Integer,Bridge>                    _bridgeCollection;



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
    static void setActiveBridgeId(int activeBridgeId)
    {
        if(activeBridgeId < 1)
            throw new IllegalArgumentException("Illegal argument for the bridge id");
        _activeBridgeId = activeBridgeId;
    }

    /**
     * Get a map representation of theme collection
     * @return map of themes
     */
    public Map<Integer, Theme> getThemeMap() {
        return _themeCollection;
    }

    /**
     * Get a collection representation of themes
     * @return map of themes
     */
    public Collection<Theme> getThemeCollection() {
        return _themeCollection.values();
    }

    /**
     * Sets a collection of themes
     * @param themeCollection - specified collection themes.
     * @throws IllegalArgumentException if null or contains nulls
     */
    void setThemeCollection(Map<Integer, Theme> themeCollection)
    {
        if(themeCollection == null || themeCollection.containsKey(null) || themeCollection.containsValue(null))
            throw new IllegalArgumentException("Failed to create collection of themes.");
        _themeCollection = new LinkedHashMap<>(themeCollection);
        assert(_themeCollection != null);
    }

    /**
     * Get a collection of groups
     * @return - collection of groups
     */
    public Map<Integer,Map<Integer,Group>> getGroupCollection() {
        return _groupCollection;
    }

    /**
     * Adds a collection of group
     * @param groupCollection - a specified collection of groups
     * @param bridgeId Id of the bridge owner of this group
     * @throws IllegalArgumentException if null or contains nulls
     */
    void addGroupCollection(Map<Integer, Group> groupCollection, int bridgeId)
    {
        if(groupCollection == null ||groupCollection.containsKey(null) ||groupCollection.containsValue(null))
            throw new IllegalArgumentException("Failed to create collection of groups.");

        _groupCollection.put(bridgeId,groupCollection);
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
    public void setBridgeCollection(Map<Integer, Bridge> bridgeCollection)
    {
        if(bridgeCollection == null ||bridgeCollection.containsKey(null) || bridgeCollection.containsValue(null))
            throw new IllegalArgumentException("Failed to create collection of groups.");
        _bridgeCollection = new LinkedHashMap<>(bridgeCollection);
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
    void removeTheme(int id)
    {
        _themeCollection.remove(id);
        //TODO: add remove subthemes message
    }

    /**
     * Adds the specified group into collection of groups
     * @param group new group to add.
     * @throws IllegalArgumentException if null.
     */
    void addGroup(Group group)
    {
        if(group == null)
            throw new IllegalArgumentException("Failed to add group.");
        int bridgeId = group.getBridgeId();
        if(_groupCollection.containsKey(bridgeId))
           _groupCollection.get(bridgeId).put(group.getId(), group);
        else
        {
            Map<Integer,Group> groups = new LinkedHashMap<>();
            groups.put(group.getId(),group);
            _groupCollection.put(bridgeId,groups);
        }
    }

    /**
     * Removes group.
     * @param group group to be removed
     * @throws IllegalArgumentException if null
     */
    void removeGroup(Group group)
    {
        if (group == null)
            throw new IllegalArgumentException("Error removing the group");

        int bridgeId = group.getBridgeId();
        _groupCollection.get(bridgeId).remove(group.getId());
        if(_groupCollection.get(bridgeId).isEmpty())
            _bridgeCollection.remove(bridgeId);
        //TODO: add remove subgroups message
    }

    /**
     * Adds the specified bridge into collection of bridges
     * @param bridge new group to add.
     * @throws IllegalArgumentException if null.
     */
    void addBridge(Bridge bridge)
    {
        if(bridge == null)
            throw new IllegalArgumentException("Failed to add bridge.");
        _bridgeCollection.put(bridge.getId(), bridge);
    }

    /**
     * Clears entire data structure.
     */
    void removeAll()
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
    void removeBridge(int id)
    {
        _bridgeCollection.remove(id);
    }

    /**
     * Gets a lightbulb from the collection of bulbs on the ACTIVE bridge.
     * @param id bulb id
     * @return an instance of the lightbulb
     */
    public Lightbulb getBulbById(int id)
    {
        assert(_bridgeCollection != null);
        return _bridgeCollection.get(_activeBridgeId).getBulb(id);
    }

    private void subscribe()
    {
        Subscriber.addBulbListener(this);
        Subscriber.addGroupListener(this);
        Subscriber.addThemeListener(this);
    }

    @Override
    public void onAddBulb(BulbMessage message)
    {
        _bridgeCollection.get(_activeBridgeId)
                         .addBulb(message._bulb);
    }

    @Override
    public void onRemoveBulb(BulbMessage message)
    {
        _bridgeCollection.get(_activeBridgeId)
                         .removeBulb(message._bulb.getId());
    }

    @Override
    public void onUpdateBulb(BulbMessage message)
    {
        _bridgeCollection.get(_activeBridgeId)
                         .update( message._bulb);
    }

    @Override
    public void onUpdateMultiBulbs(BulbMessage message)
    {
        //TODO: implement this message handler
    }

    @Override
    public void onSynchBulbs(BulbMessage message)
    {
        //TODO:Update this message handler
    }

    @Override
    public void onAddGroup(GroupMessage message)
    {
        addGroup(message._group);
    }

    @Override
    public void onRemoveGroup(GroupMessage message)
    {
        removeGroup(message._group);
    }

    @Override
    public void onUpdateGroup(GroupMessage message)
    {
        Group g = message._group;
        _groupCollection.get(g.getBridgeId())
                        .get(g.getId())
                        .updateBulbs(g.getBulbMap());

        _groupCollection.get(g.getBridgeId())
                        .get(g.getId())
                        .updateGroup(g.getGroupMap());
    }

    @Override
    public void onUpdateMultiGroups(GroupMessage message)
    {
        //TODO: implement message handlers
    }

    @Override
    public void onActivatedGroup(GroupMessage message)
    {
        Group g = message._group;
        _groupCollection.get(g.getBridgeId()).get(g.getId()).activate();
    }

    @Override
    public void onDeactivateGroup(GroupMessage message)
    {
        Group g = message._group;
        _groupCollection.get(g.getBridgeId()).get(g.getId()).deactivate();
    }

    @Override
    public void onSyncGroups(GroupMessage message)
    {
        //TODO: implement message handlers
    }

    @Override
    public void onRemoveSubgroups(GroupMessage message)
    {
        //TODO: implement message handler
    }

    @Override
    public void onAddTheme(ThemeMessage message)
    {
        _themeCollection.put(message._theme.getId(),message._theme);
    }

    @Override
    public void onRemoveTheme(ThemeMessage message)
    {
        removeTheme(message._theme.getId());
    }

    @Override
    public void onUpdateTheme(ThemeMessage message)
    {
        Theme t = message._theme;
        _themeCollection.get(t.getId()).updateTraits(t.getTraitMap());
        _themeCollection.get(t.getId()).updateThemes(t.getThemeMap());

    }

    @Override
    public void onUpdateMultiThemes(ThemeMessage message)
    {
        //TODO: implement message handler
    }

    @Override
    public void onActivatedTheme(ThemeMessage message)
    {
        _themeCollection.get(message._theme.getId()).activate();
    }

    @Override
    public void onDeactivateTheme(ThemeMessage message)
    {
        _themeCollection.get(message._theme.getId()).deactivate();
    }

    @Override
    public void onRemoveSubthemes(ThemeMessage message)
    {
        //TODO: implement message handler
    }

    @Override
    public void onSyncThemes(ThemeMessage message)
    {
        //TODO: implement message handler
    }
}
