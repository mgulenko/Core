package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * Class that responsible for publishing messages.
 * It uses {@link Dispatcher} class to notify all subscribed
 * to a particular message classes.
 * @author  Michael Gulenko. Created on 09/06/2015
 */
public abstract class Publisher
{

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
