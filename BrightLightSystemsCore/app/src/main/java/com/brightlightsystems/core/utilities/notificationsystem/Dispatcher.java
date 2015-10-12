package com.brightlightsystems.core.utilities.notificationsystem;

import com.brightlightsystems.core.utilities.definitions.MultiMap;


/**
 * Singleton class that is responsible for message delivery to all subscribers for that message.
 * @author Micahel Gulenko.Created on 09/06/2015
 */
class Dispatcher
{
    /**
     * Holds a list of subscribers that are subscribed to a particular message
     * Key can't be < 1, value can't be null.
     * Actual map can be empty.
     */
    private MultiMap<Integer,Subscribable> _subscribers;
    /**
     * An instance of this class
     */
    private static Dispatcher _instance = new Dispatcher();

    /**
     * Get an instance of this class
     * @return - never null
     */
    public static Dispatcher getInstance()
    {
        return _instance;
    }

    /**
     * Constructs a Dispatcher object
     */
    private Dispatcher()
    {
        _subscribers = new MultiMap<>();
    }

    /**
     * Method adds a subscriber and a message that it's subscribed to
     * to a list of subscribers.
     * @param messageId  - a legal message identifier. Can't be < 0.
     * @param subscribable - a subscriber that needs to be add to the list.
     * @throws {@link IllegalArgumentException} if messageId < 0 or subscriber == null
     * @return true on success, false if the subscriber is already subscribed to the message.
     */
    void subscribe(int messageId, Subscribable subscribable)
    {
        if(messageId < 0 || subscribable == null)
            throw new IllegalArgumentException();
      _subscribers.put(messageId,subscribable);
    }

    /**
     * Method removes a subscriber from a list of subscription.
     * if there are no more subscribers to the specified message, method removes it from the list as well.
     * @param messageId  - a legal message identifier. Can't be < 0.
     * @param subscribable - a subscriber that needs to be add to the list.
     * @throws {@link IllegalArgumentException} if messageId < 0 or subscriber == null
     * @return true on success, false otherwise.
     */
    boolean unsubscribe(int messageId, Subscribable subscribable)
    {
        if(messageId < 0 || subscribable == null)
            throw new IllegalArgumentException();
        return _subscribers.remove(messageId,subscribable);
    }

    /**
     * Notifies all subscribers about with specified message.
     * @param message - message that is to notify subscribers.
     */
    void dispatch(SystemMessage message)
    {
        for(Subscribable s:_subscribers.get(message.ID))
        {
           s.onNotify(message);
        }
    }

}
