package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * Class that responsible for publishing messages.
 * It uses {@link Dispatcher} class to notify all subscribed
 * to a particular message classes.
 *
 * This class also contains definitions of all messages within the system.
 * Each message is represented by 32 bit integer.
 *
 * IMPORTANT:
 * Values Range: lowest 16 bits are reserved for messages related to the core components
 * e.g. themes traits bulbs, etc. Highest 16 bits are reserved for modules.
 * Please use hex to assign the values for new message value.
 * @author  Michael Gulenko. Created on 09/06/2015
 */
public abstract class Publisher
{

    ///////////////////CORE MESSAGE DEFINITIONS/////////////////////////////
    /**
     * Notifies that theme needs to be updated.
     */
    public static final int UPDATE_THEME                = 0x0001;
    /**
     * Notifies that theme needs to be activated
     */
    public static final int ACTIVATE_THEME              = 0x0002;
    /**
     * Notifies that theme needs to be deactivated
     */
    public static final int DEACTIVATE_THEME            = 0x0003;
    /**
     * Notifies that system needs to be deleted. FOREVER!!!
     */
    public static final int DELETE_THEME                = 0x0004;


    ///////////////////MODULES MESSAGE DEFINITIONS////////////////////////////


    /**
     * Method publishes specified message.
     * @param message - message that needs to be published. Not null.
     * @throws - {@link IllegalArgumentException} if message is null.
     */
    public static void publish(SystemMessage message)
    {
        if(message == null)
            throw new IllegalArgumentException();
        Dispatcher.getInstance().dispatch(message);
    }

    /******************** end of class********************************/
}
