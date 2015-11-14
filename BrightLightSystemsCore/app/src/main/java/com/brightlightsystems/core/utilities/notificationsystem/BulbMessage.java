package com.brightlightsystems.core.utilities.notificationsystem;

import com.brightlightsystems.core.datastructure.Lightbulb;

/**
 * Class describes a message that passes to the bulb listener.
 * @author Michael Gulenko Created on 11/11/2015.
 */
public class BulbMessage
{
    /**
     * Notifies the system that a new light bulb needs to be added.
     */
    public static final int MSG_ADD_BULB                = 0x01;
    /**
     * Notifies the system that a light bulb needs to be removed.
     */
    public static final int MSG_REMOVE_BULB             = 0x02;
    /**
     * Notifies the system that a light bulb changed its state
     * or trait and needs to updated.
     */
    public static final int MSG_UPDATE_SINGLE_BULB      = 0x03;
    /**
     * Notifies the system that multiple light bulbs changed their
     * states or traits and needs to be added.
     */
    public static final int MSG_UPDATE_MULTI_BULB       = 0x04;
    /**
     * Notifies the system that the bulbs needs to be synchronized with
     * other components
     */
    public static final int MSG_SYNC_BULB_STATE         = 0x05;
    /**Message id. One of the defined above*/
    public final int _id;
    /**A Lightbulb object that is used while processing a notification*/
    public final Lightbulb _bulb;


    /**
     * Constructs new instance of the BulbMessage with specified message id and a Lightbulb
     * @param id specified message id. One of the static members that defined in this class.
     * @param bulb Lightbulb object that is used while processing a notification.
     * @throws IllegalArgumentException if bulb == null
     */
    public BulbMessage(int id, Lightbulb bulb)
    {
        if(bulb == null)
            throw new IllegalArgumentException("Incorrect parameter for the message.");
        _id = id;
        _bulb = bulb;
    }
}
