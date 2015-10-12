package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * Abstract class that describes a subscriber.
 * @author  Michael Gulenko. Created on 09/06/2015
 */
public abstract class Subscriber
{
    /**
     * Subscribes to receive specified message
     * @param messageId - message identification that is used for subscription
     */
    public static void subscribe(Subscribable subscribable, int messageId)
    {
        Dispatcher.getInstance().subscribe(messageId, subscribable);
    }

    /**
     * Unsubscribes from receiving specified message
     * @param messageId - message identification that is use to unsubscribe
     */
    public static void unsubscribe(Subscribable subscribable, int messageId)
    {
        Dispatcher.getInstance().unsubscribe(messageId, subscribable);
    }

}
