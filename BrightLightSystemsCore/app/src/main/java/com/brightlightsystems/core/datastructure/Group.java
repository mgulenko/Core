package com.brightlightsystems.core.datastructure;

import com.brightlightsystems.core.utilities.definitions.DataStructureHelper;
import com.brightlightsystems.core.utilities.notificationsystem.Messages;
import com.brightlightsystems.core.utilities.notificationsystem.Subscribable;
import com.brightlightsystems.core.utilities.notificationsystem.Subscriber;
import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class describes a group of lightbulbs.
 * The group can contain other groups as well.
 * @author Michael Gulenko Created on 10/7/2015.
 */
public class Group extends HueElement implements Subscribable
{
    /**
     * Initial indicator how much elemnts to store in the _collection
     */
    private static final byte INIT_GROUP_COUNT = 16;

    /**
     * Id that will be used for the next bulb. IMPORTANT: value must be in sync with the database.
     * TODO: Add mechanism that validates synchronization
     */
    private static int NEXT_GROUP_ID = 1;

    /**
     * Set of the light bulbs that are stored in this group. Can't contain dupes.
     * Can't be null, can't contains nulls
     */
    private Map<Integer,Lightbulb> _bulbs;
    /**
     * List of groups that represent this group. Can't be null, can't contains nulls
     */
    private Map<Integer,Group> _groups;

    /**Flag that indicates if this group is active*/
    private boolean _activated;

    /**
     * Identifier of the bridge  that contains this group
     */
    private final int _bridgeID;

    /**
     * Sets next group id.
     * @param nextId next id
     */
    public static void nextGroupIdInit(int nextId)
    {
        assert (nextId > NEXT_GROUP_ID);
        if(nextId < 1)
            throw new IllegalArgumentException("Cant initialize next bulb id counter");
        NEXT_GROUP_ID = nextId;
    }

    /**
     * Constructs an empty group with the specified name.
     * @param id group id
     * @param name name of the group
     */
    public Group(int id, String name, int bridgeId)
    {
        super(id, name);
        _bulbs  = new LinkedHashMap<>(Bridge.INIT_BULB_COUNT);
        _groups = new LinkedHashMap<>(INIT_GROUP_COUNT);
        _activated = false;
        _bridgeID = bridgeId;
        assert(_bulbs  != null);
        assert(_groups != null);
    }

    /**
     * Constructs a group with specified name and a set of light bulbs that are in that group.
     * @param id group id
     * @param name name of the group
     * @param bulbs set of light bulbs.
     * @throws IllegalArgumentException if bulbs == null or contain nulls
     */
    public Group(int id, String name, Set<Lightbulb> bulbs, int bridgeId)
    {
        super(id, name);
        if(bulbs == null || bulbs.contains(null))
            throw new IllegalArgumentException("Can't create a group.Wrong parameter.");

        _bulbs  = (Map<Integer, Lightbulb>) DataStructureHelper.hueElementsToLinkedMap(bulbs);
        _groups = new LinkedHashMap<>(INIT_GROUP_COUNT);
        _activated = false;
        _bridgeID = bridgeId;
        assert(_bulbs  != null);
        assert(_groups != null);
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
     * @param bulbs set of light bulbs that is to be added
     * @throws IllegalArgumentException if bulbs is null or contain nulls
     */
    public void addBulbs(Set<Lightbulb> bulbs)
    {
        if(bulbs == null || bulbs.contains(null))
            throw new IllegalArgumentException("Can't add bulbs. Parameter is invalid.");
        assert(_bulbs != null);
        _bulbs.putAll((Map<Integer, Lightbulb>) DataStructureHelper.hueElementsToLinkedMap(bulbs));

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
        subscribe();
    }

    /**
     * Adds a list of groups to the existing list. If the group already exists, then its value will be
     * replaced with a new one.
     * @param groups new list of groups to be added into existing list.
     * @throws IllegalArgumentException if group is null or contains null.
     */
    public void addGroups(Set<Group> groups)
    {
        if(groups == null || groups.contains(null))
            throw new IllegalArgumentException("Can't create a group.Wrong parameter.");
        assert(_groups != null);
        _groups.putAll((Map<Integer, Group>) DataStructureHelper.hueElementsToLinkedMap(groups));
        subscribe();
    }

    /**
     * Acquires a set of light bulbs in the group
     * @return set of light bulbs
     */
    public Collection<Lightbulb> getBulbs()
    {
        assert (_bulbs != null);
        return _bulbs.values();
    }

    /**
     * Acquires a list of groups within the group
     * @return list of groups
     */
    public Collection<Group> getGroups()
    {
        assert(_groups != null);
        return _groups.values();
    }

    /**
     * Removes a bulb from the group by bulb id
     * @param bulbId bulb that needs to be removed
     * @return true on success, false otherwise
     */
    public void removeBulb(int bulbId)
    {
        assert(_bulbs != null);
         ;_bulbs.remove(bulbId);
    }

    /**
     * Removes group from the list of the groups
     * @param groupId group that needs to be removed
     */
    public void removeGroup(int groupId)
    {
        assert(_groups != null);
        _groups.remove(groupId);
        if(_groups.size() == 0)
            unsubscribe();
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
        unsubscribe();
    }


    /**
     * Updates group with specified list of  groups.
     * Does nothing if groups == null
     * @param groups new set opf groups
     * @throws IllegalArgumentException if new list contains nulls
     */
    public void updateGroup(Set<Group>groups)
    {
        if(groups == null)
            return;
        if(groups.contains(null))
            throw new IllegalArgumentException("Can't update the group. parameter contains nulls");
        assert(_groups != null);
        _groups.putAll((Map<Integer, Group>) DataStructureHelper.hueElementsToLinkedMap(groups));
        subscribe();
    }

    /**
     * Updates group with specified set of light bulbs.
     * Does nothing if bulbs == null
     * @param bulbs new set of groups
     * @throws IllegalArgumentException if new set contains nulls
     */
    public void updateBulbs(Set<Lightbulb>bulbs)
    {
        if(bulbs == null)
            return;
        if(bulbs.contains(null))
            throw new IllegalArgumentException("Can't update bulbs. parameter contains nulls");
        assert(_bulbs != null);
        _bulbs.putAll((Map<Integer, Lightbulb>) DataStructureHelper.hueElementsToLinkedMap(bulbs));
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

    @Override
    public void subscribe()
    {
        Subscriber.subscribe(this, Messages.MSG_REMOVE_SUBTHEMES);
    }

    @Override
    public void unsubscribe()
    {
        Subscriber.unsubscribe(this, Messages.MSG_REMOVE_SUBTHEMES);
    }

    @Override
    public <T> void onRecieve(SystemMessage<T> message)
    {

        switch (message.ID)
        {
            case Messages.MSG_REMOVE_SUBTHEMES:
                _groups.remove((Integer)message.getAttachment());
                break;
        }
    }

    /******************** end of class********************************/
}
