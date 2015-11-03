package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * Interface that describes a behaviour for subscribable to the @link{notificationsystem} objects
 * @author Michael Gulenko Created on 10/11/2015.
 */
public interface Subscribable
{
    /**
     * Subscribes to receive messages
     */
    void subscribe();

    /**
     * Unsubscribes from recieving messages
     */
    void unsubscribe();

    /**
     * Gets invoked to receive a message
     * @param message - received message. Never null.
     */
    <T> boolean onRecieve(SystemMessage<T> message);

    /******************** end of class********************************/
}
