package com.brightlightsystems.core.datastructure;

import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * This class describes a group of lightbulbs.
 * The group can contain other groups as well.
 * @author Michael Gulenko Created on 10/7/2015.
 */
public class Group extends HueElement
{
    /**
     * Initial indicator how much elements to store in the _bulb s
     */
    private static final byte INIT_BULB_COUNT  = 32;
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
    Set<Lightbulb> _bulbs;
    /**
     * List of groups that represent this group. Can't be null, can't contains nulls
     */
    List<Group> _groups;

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
     * @param name name of the group
     */
    public Group(String name)
    {
        super(NEXT_GROUP_ID, name);
        _bulbs  = new LinkedHashSet<>(INIT_BULB_COUNT);
        _groups = new ArrayList<>(INIT_GROUP_COUNT);
        assert(_bulbs  != null);
        assert(_groups != null);
        subscribe();
        NEXT_GROUP_ID ++;

    }

    /**
     * Constructs a group with specified name and a set of light bulbs that are in that group.
     * @param name name of the group
     * @param bulbs set of light bulbs.
     * @throws IllegalArgumentException if bulbs == null or contain nulls
     */
    public Group(String name, LinkedHashSet<Lightbulb> bulbs)
    {
        super(NEXT_GROUP_ID, name);
        if(bulbs == null || bulbs.contains(null))
            throw new IllegalArgumentException("Can't create a group.Wrong parameter.");
        _bulbs  = bulbs;
        _groups = new ArrayList<>(INIT_GROUP_COUNT);
        assert(_bulbs  != null);
        assert(_groups != null);
        subscribe();
        NEXT_GROUP_ID ++;
    }

    /**
     * Constructs a group with specified name and list of groups.
     * @param name name for the group
     * @param groups list of groups that are to be contained in new group
     * @throw IllegalArgumentException if groups == null or contain nulls.
     */
    public Group(String name, List<Group> groups)
    {
        super(NEXT_GROUP_ID, name);
        if(groups == null || groups.contains(null))
            throw new IllegalArgumentException("Can't create a group.Wrong parameter.");
        _bulbs  = new LinkedHashSet<>(INIT_BULB_COUNT);;
        _groups = groups;

        assert(_bulbs  != null);
        assert(_groups != null);
        subscribe();
        NEXT_GROUP_ID ++;
    }

    /**
     * Constructs a group with specified name, set of light bulbs. and the list of groups.
     * @param name name for the group
     * @param bulbs set of light bulbs.
     * @param groups list of groups that are to be contained in new group
     * @throw IllegalArgumentException if groups or bulbs are null or contain nulls
     */

    public Group(String name, LinkedHashSet<Lightbulb> bulbs, List<Group> groups)
    {
        super(NEXT_GROUP_ID, name);
        if(groups == null || groups.contains(null))
            throw new IllegalArgumentException("Can't create a group.Wrong parameter.");
        if(bulbs == null || bulbs.contains(null))
            throw new IllegalArgumentException("Can't create a group.Wrong parameter.");
        _bulbs  = bulbs;
        _groups = groups;
        assert(_bulbs  != null);
        assert(_groups != null);
        subscribe();
        NEXT_GROUP_ID ++;
    }

    /**
     * Adds a new bulb to the set of light bulbs
     * @param bulb a light bulb to be added to the set
     * @return true on success false otherwise.
     * @throws IllegalArgumentException if bulb is null.
     */
    public boolean addBulb(Lightbulb bulb)
    {
        if(bulb == null)
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_bulbs != null);
        return _bulbs.add(bulb);
    }

    /**
     * Adds a set of the bulbs to the existing set.
     * @param bulbs set of light bulbs that is to be added
     * @return true on succes, false otherwise
     * @throws IllegalArgumentException if bulbs is null or contain nulls
     */
    public boolean addBulbs(LinkedHashSet<Lightbulb> bulbs)
    {
        if(bulbs == null || bulbs.contains(null))
            throw new IllegalArgumentException("Can't add bulbs. Parameter is invalid.");
        assert(_bulbs != null);
        return _bulbs.addAll(bulbs);

    }

    /**
     * Adds new group to the list of groups. Insurance that the group was not already been
     * added has to be implemented on the front end.
     * @param group new group to be added into existing list.
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if group is null.
     */
    public boolean addGroup(Group group)
    {
        if(group == null)
            throw new IllegalArgumentException("Can't add bulb. Parameter is null.");
        assert(_groups != null);
        return _groups.add(group);
    }

    /**
     * Adds a list of groups to the existing list. Insurance that the group was not already been
     * added has to be implemented on the front end.
     * @param groups new list of groups to be added into existing list.
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if group is null or contains null.
     */
    public boolean addGroups(List<Group> groups)
    {
        if(groups == null || groups.contains(null))
            throw new IllegalArgumentException("Can't create a group.Wrong parameter.");
        assert(_groups != null);
        return _groups.addAll(groups);
    }

    /**
     * Acquires a set of light bulbs in the group
     * @return set of light bulbs
     */
    public Set<Lightbulb> getBulbs()
    {
        assert (_bulbs != null);
        return _bulbs;
    }

    /**
     * Acquires a list of groups within the group
     * @return list of groups
     */
    public List<Group> getGroups()
    {
        assert(_groups != null);
        return _groups;
    }

    /**
     * Removes a bulb from the group
     * @param bulb bulb that needs to be removed
     * @return true on success, false otherwise
     */
    public boolean removeBulb(Lightbulb bulb)
    {
        assert(_bulbs != null);
        return _bulbs.remove(bulb);
    }

    /**
     * Removes group from the list of the groups
     * @param group group that needs to be removed
     * @return true on success, false otherwise
     */
    public boolean removeGroup(Group group)
    {
        assert(_groups != null);
        return _groups.remove(group);
    }

    /**
     * Remove group, from specified position in the list.
     * @param pos position to use for removing the group
     * @return true on success, false otherwise
     * @throws IllegalArgumentException if pos < 0 or >= size of the group
     */
    public Group removeGroup(int pos)
    {
        assert(_groups != null);
        if(pos < 0 || pos >= _groups.size())
            throw new IllegalArgumentException("Can't remove the group. Invalid parameter");
        return _groups.remove(pos);
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
     * Updates group with specified list of  groups. All entries will be removed and replaced
     * with new from the list. Does nothing if groups == null
     * @param groups new list opf groups
     * @throws IllegalArgumentException if new list contains nulls
     */
    public void updateGroup(List<Group>groups)
    {
        if(groups == null)
            return;
        if(groups.contains(null))
            throw new IllegalArgumentException("Can't update the group. parameter contains nulls");
        assert(_groups != null);
        _groups.clear();
        _groups = groups;
    }

    /**
     * Updates group with specified set of th. All entries will be removed and replaced
     * with new from the set. Does nothing if bulbs == null
     * @param bulbs new list opf groups
     * @throws IllegalArgumentException if new set contains nulls
     */
    public void updateBulbs(LinkedHashSet<Lightbulb>bulbs)
    {
        if(bulbs == null)
            return;
        if(bulbs.contains(null))
            throw new IllegalArgumentException("Can't update bulbs. parameter contains nulls");
        assert(_bulbs != null);
        _bulbs.clear();
        _bulbs = bulbs;
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
     * Returns a count of bulbs for this group
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
        for(Group g:_groups)
            total += g.bulbCount();
        return total;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public <T> void onNotify(SystemMessage<T> message) {

    }

    /******************** end of class********************************/
}
