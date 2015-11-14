package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * This interface describes behaviour of the bulb listener.
 * All calls to described bellow methods are done through
 * Dispatcher when certain notifications was posted through Publisher
 * @author Michael Gulenko. Created on 11/11/2015.
 */
public interface BulbListener
{
    /**
     * Occurs when new bulb needs to be added into the system
     * @param message notification message
     */
    public void onAddBulb(BulbMessage message);

    /**
     * Occurs when the bulb needs to be removed from the system
     * @param message notification message
     */
    public void onRemoveBulb(BulbMessage message);
    /**
     * Occurs when the bulb needs to be updated.
     * @param message notification message
     */
    public void onUpdateBulb(BulbMessage message);
    /**
     * Occurs when multiple bulb need to be updated
     * @param message notification message
     */
    public void onUpdateMultiBulbs(BulbMessage message);
    /**
     * Occurs when all bulbs data needs to be synchronized with other components oof the system
     * @param message notification message
     */
    public void onSynchBulbs(BulbMessage message);
}
