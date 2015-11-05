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
    public static final int MSG_ADD_BULB                = 0x0001;
    public static final int MSG_REMOVE_BULB             = 0x0002;
    public static final int MSG_UPDATE_SINGLE_BULB      = 0x0003;
    public static final int MSG_UPDATE_MULTI_BULB       = 0x0004;
    public static final int MSG_SYNC_BULB_STATE         = 0x0005;

    public static final int MSG_ADD_GROUP               = 0x0006;
    public static final int MSG_REMOVE_GROUP            = 0x0007;
    public static final int MSG_REMOVE_SUBGROUPS        = 0x0008;
    public static final int MSG_UPDATE_SINGLE_GROUP     = 0x0009;
    public static final int MSG_UPDATE_COMPLEX_GROUP    = 0x000A;
    public static final int MSG_ACTIVATE_GROUP          = 0x000B;
    public static final int MSG_DEACTIVATE_GROUP        = 0x000C;
    public static final int MSG_SYNC_GROUPS             = 0x000D;

    public static final int MSG_ADD_THEME               = 0x000E;
    public static final int MSG_REMOVE_THEME            = 0x000F;
    public static final int MSG_REMOVE_SUBTHEMES        = 0x0010;
    public static final int MSG_UPDATE_SINGLE_THEME     = 0x0011;
    public static final int MSG_UPDATE_COMPLEX_THEME    = 0x0012;
    public static final int MSG_ACTIVATE_THEME          = 0x0013;
    public static final int MSG_DEACTIVATE_THEME        = 0x0014;
    public static final int MSG_SYNC_THEMES             = 0x0015;


}
