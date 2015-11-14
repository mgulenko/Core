package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * Abstract class that describes a subscriber.Main responsibility of this class
 * is to add a notification listener to a dispatcher
 * @author  Michael Gulenko. Created on 09/06/2015
 */
public final class Subscriber
{
    /**
     * Adds a listener to listen to bulb's notifications
     * @param listener listener to add.
     * @throws IllegalArgumentException if listener == null
     */
    public static void addBulbListener(BulbListener listener)
    {
        if(listener == null)
            throw new IllegalArgumentException("Failed to add subscriber");
        Dispatcher.getInstance().addBulbListener(listener);
    }

    /**
     * Adds a listener to listen to group's notifications
     * @param listener listener to add
     * @throws IllegalArgumentException if listener == null
     */
    public static void addGroupListener(GroupListener listener)
    {
        if(listener == null)
            throw new IllegalArgumentException("Failed to add subscriber");
        Dispatcher.getInstance().addGroupListener(listener);
    }

    /**
     * Adds a listener to listen to theme's notifications
     * @param listener listener to add
     * @throws IllegalArgumentException if listener == null
     */
    public static void addThemeListener(ThemeListener listener)
    {
        if (listener == null)
            throw new IllegalArgumentException("Failed to add subscriber");
        Dispatcher.getInstance().addThemeListener(listener);
    }

    /******************** end of class********************************/
}
