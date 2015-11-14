package com.brightlightsystems.core.datastructure;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class describes a group of light bulbs.
 * The group can contain other groups as well.
 * @author Michael Gulenko Created on 10/7/2015.
 */
public class Group extends HueElement
{
    /**Initial indicator how much elemnts to store in the _collection*/
    private static final byte INIT_GROUP_COUNT = 16;

    /**Id that will be used for the next bulb. IMPORTANT: value must be in sync with the database.*/
     // TODO: Add mechanism that validates synchronization
    private static int NEXT_GROUP_ID = 1;

    /**
     * Map of the light bulbs that are stored in this group.Can't be null, can't contains nulls.
     * Key of the map is the light bulb Integer id to the Lightbulb object
     */
    private Map<Integer,Lightbulb> _bulbs;

    /**
     * List of groups that represent this group. Can't be null, can't contains nulls
     * Key of the map is the light bulb Integer id to the Group object
     */
    private Map<Integer,Group> _groups;

    /**Flag that indicates if this group is active*/
    private boolean _activated;

    /**Identifier of the bridge  that contains this group*/
    private final int _bridgeID;

    /**Flag that indicates whether this group belongs to favorite category or not*/
    private boolean _favorite;

    /**
     * Synch next bulb id with the last value in data base.
     * @param id next id
     */
    public static void synchNextId(int id)
    {
        if(id >= NEXT_GROUP_ID)
            NEXT_GROUP_ID = id + 1;
    }

    /**
     * Constructs an empty group with the specified name.
     * @param id group id
     * @param name name of the group
     */
    public Group(int id, String name, int bridgeId, boolean favorite,boolean activated)
    {
        super(id, name);
        _bulbs  = new LinkedHashMap<>(Bridge.INIT_BULB_COUNT);
        _groups = new LinkedHashMap<>(INIT_GROUP_COUNT);
        _activated = false;
        _bridgeID = bridgeId;
        _favorite = favorite;
        _activated = activated;
        assert(_bulbs  != null);
        assert(_groups != null);
        synchNextId(id);
    }

    /**
     * Constructs a group with specified name and a set of light bulbs that are in that group.
     * @param id group id
     * @param name name of the group
     * @param bulbs collection of light bulbs. Must be LinkedHashMap since we care about the order
     * @throws IllegalArgumentException if bulbs == null or contain nulls
     */
    public Group(int id, String name, Map<Integer,Lightbulb> bulbs, int bridgeId, boolean favorite, boolean activated)
    {
        super(id, name);
        if(bulbs == null || bulbs.containsKey(null) || bulbs.containsValue(null))
            throw new IllegalArgumentException("Can't create a group.Wrong parameter.");

        _bulbs  = new LinkedHashMap<>(bulbs);
        _groups = new LinkedHashMap<>(INIT_GROUP_COUNT);
        _activated = false;
        _bridgeID = bridgeId;
        _favorite = favorite;
        _activated = activated;
        assert(_bulbs  != null);
        assert(_groups != null);
        synchNextId(id);
    }

    private void repOk()
    {
        assert(_bulbs != null);
        assert(_bulbs.size() <= Bridge.INIT_BULB_COUNT);
        for(Map.Entry<Integer,Lightbulb> e: _bulbs.entrySet())
        {
            assert (e.getValue() != null);
        }
        for(Map.Entry<Integer,Group>e:_groups.entrySet())
        {
            assert(e.getValue() != null);
        }
    }

    /**
     * Gets a bridge id that owns this group
     * @return bridge owner of this group
     */
    public int getBridgeId()
    {
        return _bridgeID;
    }


    /**
     * Adds a new bulb to the set of light bulbs
     * @param bulb a light bulb to be added to the set
     * @throws IllegalArgumentException if bulb is null.
     */
    public void addBulb(Lightbulb bulb)
    {
        if(bulb == null)
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_bulbs != null);
        _bulbs.put(bulb.getId(), bulb);
    }

    /**
     * Adds a set of the bulbs to the existing set.
     * @param bulbs collection of light bulbs that is to be added. Must be LinkedHashMap,
     *              since we care about the order.
     * @throws IllegalArgumentException if bulbs is null or contain nulls
     */
    public void addBulbs(Map<Integer, Lightbulb> bulbs)
    {
        if(bulbs == null || bulbs.containsKey(null) || bulbs.containsValue(null))
            throw new IllegalArgumentException("Can't add bulbs. Parameter is invalid.");
        assert(_bulbs != null);
        _bulbs.putAll(bulbs);

    }

    /**
     * Adds new group to the list of groups. If the group already exists, then its value will be
     * replaced with a new one.
     * @param group new group to be added into existing list.
     * @throws IllegalArgumentException if group is null.
     */
    public void addGroup(Group group)
    {
        if(group == null)
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_groups != null);
        _groups.put(group.getId(), group);
    }

    /**
     * Adds a collection of groups to the existing list. If the group already exists, then its value will be
     * replaced with a new one.
     * @param groups new collection of groups to be added into existing list. Mst be LinkedHashMap,
     *               since we care about the order.
     * @throws IllegalArgumentException if group is null or contains null.
     */
    public void addGroups(Map<Integer,Group> groups)
    {
        if(groups == null || groups.containsKey(null) ||groups.containsValue(null))
            throw new IllegalArgumentException("Can't create a group.Wrong parameter.");
        assert(_groups != null);
        _groups.putAll(groups);
    }

    /**
     * Acquires a collection of light bulbs in the group
     * @return collection of light bulbs
     */
    public Collection<Lightbulb> getBulbCollection()
    {
        assert (_bulbs != null);
        return _bulbs.values();
    }

    /**
     * Acquires a collection of groups within the group
     * @return collection of groups
     */
    public Collection<Group> getGroupCollection()
    {
        assert(_groups != null);
        return _groups.values();
    }

    /**
     * Acquires a map of light bulbs in the group
     * @return map of light bulbs
     */
    public Map<Integer,Lightbulb> getBulbMap()
    {
        assert (_bulbs != null);
        return _bulbs;
    }

    /**
     * Acquires a map of groups within the group
     * @return map of groups
     */
    public Map<Integer, Group> getGroupMap()
    {
        assert(_groups != null);
        return _groups;
    }

    /**
     * Removes a bulb from the group by bulb id
     * @param bulbId bulb that needs to be removed
     * @return true on success, false otherwise
     */
    public void removeBulb(int bulbId)
    {
        assert(_bulbs != null);
        _bulbs.remove(bulbId);
    }

    /**
     * Removes group from the list of the groups
     * @param groupId group that needs to be removed
     */
    public void removeGroup(int groupId)
    {
        assert(_groups != null);
        _groups.remove(groupId);

    }

    /**
     * Removes all bulbs and groups from the group
     */
    public void clearAll()
    {
        assert(_bulbs != null);
        assert(_groups != null);
        _bulbs.clear();
        _groups.clear();
    }

    /**
     * Updates group with specified map of groups.
     * Does nothing if groups == null. Values that arre not in the new map will be removed
     * from the old map.
     * @param groups new map of groups. Must be LinkedHashMap since we care about the order
     * @throws IllegalArgumentException if new list contains nulls
     */
    public void updateGroup(Map<Integer,Group>groups)
    {
        if(groups == null)
            return;
        if(groups.containsKey(null) || groups.containsValue((null)))
            throw new IllegalArgumentException("Can't update the group. parameter contains nulls");
        assert(_groups != null);
        _groups.clear();
        _groups.putAll(groups);
    }

    /**
     * Updates group with specified map of light bulbs.
     * Does nothing if bulbs == null
     * @param bulbs new map of groups. Must be LinkedHashMap since we care about the order
     * @throws IllegalArgumentException if new set contains nulls
     */
    public void updateBulbs(Map<Integer,Lightbulb>bulbs)
    {
        if(bulbs == null)
            return;
        if(bulbs.containsKey(null) || bulbs.containsValue(null))
            throw new IllegalArgumentException("Can't update bulbs. parameter contains nulls");
        assert(_bulbs != null);
        _bulbs.clear();
        _bulbs.putAll(bulbs);
    }

    /**
     * Returns a count of all groups within the group
     * @return group count.
     */
    public int groupCount()
    {
        assert(_groups != null);
        return _groups.size();
    }

    /**
     * Returns a count of bulbs within the group
     * @return bulb count.
     */
    public int bulbCount()
    {
        assert(_bulbs != null);
        return _bulbs.size();
    }

    /**
     * Counts all bulbs including bulbs in sub groups.
     * @return total count of all bulbs in this group and groups within the group
     */
    public int countAllBulbs()
    {
        assert(_groups != null);
        int total = bulbCount();
        for(Map.Entry<Integer,Group> g:_groups.entrySet())
            total += g.getValue().bulbCount();
        return total;
    }

    /**
     * Check if current group is activated
     * @return true if activated, false otherwise
     */
    public boolean isActivated()
    {
        return _activated;
    }

    /**
     * Activate the group
     */
    public void activate()
    {
        _activated = true;
    }

    /**
     * Deactivate the group
     */
    public void deactivate()
    {
        _activated = false;
    }

    @Override
    public String toString()
    {
        String str = "\nGroup: " + getId() + "   Name: "+ getName() + "   On a Bridge: " + _bridgeID + "\n\n" +
                "Contains following bulbs: \n\n\t\t";
        for(Map.Entry<Integer, Lightbulb> e: _bulbs.entrySet())
        {
            str += e.getValue().toString();
        }

        if(_groups.isEmpty())
            return str;

        str += "Contains following subgroups: \n\n\t\t";
        for(Map.Entry<Integer,Group> e: _groups.entrySet())
        {
            str += e.getValue().toString();
        }
        return str;
    }

    /**
     * Asks the group if its belong to favorite category
     * @return true if was added to favorites, false otherwise
     */
    public boolean isFavorite()
    {
        return _favorite;
    }

    /**
     * Adds group to favorite
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
