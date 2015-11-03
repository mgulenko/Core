package com.brightlightsystems.core.utilities.notificationsystem;

/**
 * Abstract class that describe a single message.
 * It uses {@link Dispatcher} class to notify all subscribed
 * to a particular message classes.
 * @author  Michael Gulenko. Created on 09/06/2015
 */
public class SystemMessage <T>
{

    /**
     * Unique message identifier. Can't be < 1.
     */
    public final int ID;
    /**
     * Message attachment
     */
    private T _attachment;

    /**
     * Constructs SystemMessage object with specified messgae identifier
     * @param messageId - unique message identifier. can't be < 1
     */
    public SystemMessage(int messageId, T attachment)
    {
        assert(messageId > 0);
        ID = messageId;
        _attachment = attachment;
    }

    /**
     * Returns a message id
     * @return - message id
     */
    public int getId()
    {
        return ID;
    }

    /**
     * Returns attachment of the message
     * @return attachment of type T.
     */
    public T getAttachment()
    {
        return _attachment;
    }


    /******************** end of class********************************/
}


