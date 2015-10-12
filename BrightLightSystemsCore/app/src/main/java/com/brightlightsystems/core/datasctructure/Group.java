package com.brightlightsystems.core.datasctructure;

/**
 * This class describes a group of lightbulbs.
 * The group can contain other groups as well.
 * @author Michael Gulenko Created on 10/7/2015.
 */
public class Group extends HueElement
{
    /**
     * Set of the light bulbs that are stored in this group
     */
    Set<Lightbulb> _bulbs;
    /**
     * List of groups that represent this group
     */
    List<Group> _groups;

    public Group(String name)
    {

    }

    public Group(String name, Set<Lightbulbs> bulbs)
    {

    }

    public Group(String name, List<Group> groups)
    {

    }

    public Group(String name, Set<Lightbulb> bulbs, List<Group> groups)
    {

    }

    public boolean addBulb(Lightbulb bulb)
    {

    }

    public boolean addBulbs(Set<Lightbulb> bulbs)
    {

    }

    public boolean addGroup(Group group)
    {

    }

    public boolean addGroups(List<Group> groups)
    {

    }

    public Set<Lightbulb> getBulbs()
    {

    }

    public Link<Group> getGroups()
    {

    }

    public void removeBulb(Lightbulb)
    {

    }

    public Lightbulb removeBulb(int pos)
    {

    }

    public void removeGroup(Group group)
    {

    }

    public Group removeGroup(int pos)
    {

    }

    public void clearAll()
    {

    }


    public boolean updateGroup()
    {

    }





}
