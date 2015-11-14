package com.brightlightsystems.core.utilities.notificationsystem;

import com.brightlightsystems.core.datastructure.Group;

/**
 * Created by Michael on 11/11/2015.
 */
public class GroupMessage
{
    /**
     * Notifies the system that a new group needs to be added.
     */
    public static final int MSG_ADD_GROUP               = 0x01;
    /**
     * Notifies the system that a group needs to be removed.
     */
    public static final int MSG_REMOVE_GROUP            = 0x02;
    /**
     * Notifies the system that a group needs to be removed from complex groups.
     */
    public static final int MSG_REMOVE_SUBGROUPS        = 0x03;
    /**
     * Notifies the system that a group needs to be updated.
     */
    public static final int MSG_UPDATE_GROUP            = 0x04;
    /**
     * Notifies the system that multiple groups needs to be updated.
     */
    public static final int MSG_UPDATE_MULTI_GROUP      = 0x05;
    /**
     * Notifies the system that a group needs to be activated.
     */
    public static final int MSG_ACTIVATE_GROUP          = 0x06;
    /**
     * Notifies the system that a group needs to be deactivated.
     */
    public static final int MSG_DEACTIVATE_GROUP        = 0x07;
    //TODO: add description
    public static final int MSG_SYNC_GROUPS             = 0x08;

    public final int _id;
    public final Group _group;

    public GroupMessage(int id, Group group)
    {
        _id = id;
        _group = group;
    }
}
