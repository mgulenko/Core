package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * Class that responsible for publishing notifications.
 * It uses {@link Dispatcher} class to notify all subscribed
 * to a particular types of notification classes.
 * @author  Michael Gulenko. Created on 09/06/2015
 */
public final class Publisher
{

    /**prevents from instantiation*/
    private Publisher(){}

    /**
     * Posts notification for bulb notification listeners
     * @param message message to post
     * @throws IllegalArgumentException if message == null
     */
    public static void postBulbNotification(BulbMessage message)
    {
        if(message == null)
            throw new IllegalArgumentException();
        Dispatcher.getInstance().notifyBulbListeners(message);
    }

    /**
     * Posts notification for group notification listeners
     * @param message message to post
     * @throws IllegalArgumentException if message == null
     */
    public static void postGroupNotification(GroupMessage message)
    {
        if(message == null)
            throw new IllegalArgumentException();
        Dispatcher.getInstance().notifyGroupListeners(message);
    }

    /**
     * Posts notification for theme notification listeners
     * @param message message to post
     * @throws IllegalArgumentException if message == null
     */
    public static void postThemeNotification(ThemeMessage message)
    {
        if(message == null)
            throw new IllegalArgumentException();
        Dispatcher.getInstance().notifyThemeListeners(message);
    }


    /******************** end of class********************************/
}
