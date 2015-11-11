package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * This class contains definitions of all messages within the system.
 * Each message is represented by 32 bit integer.
 *
 * IMPORTANT:
 * Values Range: lowest 16 bits are reserved for messages related to the core components
 * e.g. themes traits bulbs, etc. Highest 16 bits are reserved for modules.
 * Please use hex to assign the values for new message value.
 * @author Michael Gulenko. Created on 11/04/2015
 */
public abstract class Messages
{
    ///////////////////CORE MESSAGE DEFINITIONS/////////////////////////////
    /**
     * Notifies the system that a new light bulb needs to be added.
     * SystemMessage type : Lightbulb
     */
    public static final int MSG_ADD_BULB                = 0x0001;
    /**
     * Notifies the system that a light bulb needs to be removed.
     * SystemMessage type : Integer (for light bulb id)
     */
    public static final int MSG_REMOVE_BULB             = 0x0002;
    /**
     * Notifies the system that a light bulb changed its state
     * or trait and needs to updated.
     * SystemMessage type : Lightbulb
     */
    public static final int MSG_UPDATE_SINGLE_BULB      = 0x0003;
    /**
     * Notifies the system that multiple light bulbs changed their
     * states or traits and needs to be added.
     * SystemMessage type : Set<Lightbulb>
     */
    public static final int MSG_UPDATE_MULTI_BULB       = 0x0004;
    //TODO: add description
    public static final int MSG_SYNC_BULB_STATE         = 0x0005;


    /////////////////////////////////////////////////////////////

    /**
     * Notifies the system that a new group needs to be added.
     * SystemMessage type: Group
     */
    public static final int MSG_ADD_GROUP               = 0x0006;
    /**
     * Notifies the system that a group needs to be removed.
     * SystemMessage type: Group.
     */
    public static final int MSG_REMOVE_GROUP            = 0x0007;
    /**
     * Notifies the system that a group needs to be removed from complex groups.
     * SystemMessage type: Integer (for the group id)
     */
    public static final int MSG_REMOVE_SUBGROUPS        = 0x0008;
    /**
     * Notifies the system that a group needs to be updated.
     * SystemMessage type: Group
     */
    public static final int MSG_UPDATE_GROUP            = 0x0009;
    /**
     * Notifies the system that a complex group needs to be updated.
     * SystemMessage type: Set<Group>
     */
    public static final int MSG_UPDATE_MULTI_GROUP      = 0x000A;
    /**
     * Notifies the system that a group needs to be activated.
     * SystemMessage type: Group
     */
    public static final int MSG_ACTIVATE_GROUP          = 0x000B;
    /**
     * Notifies the system that a group needs to be deactivated.
     * SystemMessage type: Group
     */
    public static final int MSG_DEACTIVATE_GROUP        = 0x000C;
    //TODO: add description
    public static final int MSG_SYNC_GROUPS             = 0x000D;


    /////////////////////////////////////////////////////////////

    /**
     * Notifies the system that a new theme needs to be added.
     * SystemMessage type : Theme
     */
    public static final int MSG_ADD_THEME               = 0x000E;
    /**
     * Notifies the system that a theme needs to be removed.
     * SystemMessage type : Integer (for the theme id)
     */
    public static final int MSG_REMOVE_THEME            = 0x000F;
    /**
     * Notifies the system that a sub-theme needs to be removed from complex themes.
     * SystemMessage type : Integer (for the theme id)
     */
    public static final int MSG_REMOVE_SUBTHEMES        = 0x0010;
    /**
     * Notifies the system that a theme needs to be updated.
     * SystemMessage type : Theme
     */
    public static final int MSG_UPDATE_SINGLE_THEME     = 0x0011;
    /**
     * Notifies the system that a complex theme needs to be updated
     * SystemMessage type : Theme
     */
    public static final int MSG_UPDATE_COMPLEX_THEME    = 0x0012;
    /**
     * Notifies the system that a theme needs to be activated.
     * SystemMessage type : Theme
     */
    public static final int MSG_ACTIVATE_THEME          = 0x0013;
    /**
     * Notifies the system that a theme needs to be deactivated.
     * SystemMessage type : Theme
     */
    public static final int MSG_DEACTIVATE_THEME        = 0x0014;
    //TODO: Add mesage description
    public static final int MSG_SYNC_THEMES             = 0x0015;


}
