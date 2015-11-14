package com.brightlightsystems.core.utilities.notificationsystem;

import java.util.ArrayList;
import java.util.List;


/**
 * Singleton class that is responsible for message delivery to all subscribers for that message.
 * @author Micahel Gulenko.Created on 09/06/2015
 */
final class Dispatcher
{
    List<BulbListener>  _bulbListeners;
    List<GroupListener> _groupListener;
    List<ThemeListener> _themeListener;

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
        _bulbListeners = new ArrayList<>();
        _groupListener = new ArrayList<>();
        _themeListener = new ArrayList<>();
    }

    /**
     * Adds a listener to listen to bulb's notifications
     * @param listener listener to add.
     */
    void addBulbListener(BulbListener listener)
    {
        assert(listener != null);
        _bulbListeners.add(listener);
    }

    /**
     * Adds a listener to listen to group's notifications
     * @param listener listener to add
     */
    void addGroupListener(GroupListener listener)
    {
        assert(listener != null);
        _groupListener.add(listener);
    }

    /**
     * Adds a listener to listen to theme's notifications
     * @param listener listener to add
     */
    void addThemeListener(ThemeListener listener)
    {
        assert(listener != null);
        _themeListener.add(listener);
    }

    /**
     * Posts message for bulbs listener
     * @param message message to post
     */
    void notifyBulbListeners(BulbMessage message)
    {
        assert(message != null);
        for(BulbListener listener:_bulbListeners)
        {
            switch (message._id)
            {
                case BulbMessage.MSG_ADD_BULB:
                    listener.onAddBulb(message);
                    break;
                case BulbMessage.MSG_REMOVE_BULB:
                    listener.onRemoveBulb(message);
                    break;
                case BulbMessage.MSG_UPDATE_SINGLE_BULB:
                    listener.onUpdateBulb(message);
                    break;
                case BulbMessage.MSG_UPDATE_MULTI_BULB:
                    listener.onUpdateMultiBulbs(message);
                    break;
                case BulbMessage.MSG_SYNC_BULB_STATE:
                    listener.onSynchBulbs(message);
                    break;
            }
        }
    }

    /**
     *Posts message for group listeners
     * @param message message to post
     */
    void notifyGroupListeners(GroupMessage message)
    {
        for(GroupListener listener : _groupListener)
        {
            switch (message._id)
            {
                case GroupMessage.MSG_ADD_GROUP:
                    listener.onAddGroup(message);
                    break;
                case GroupMessage.MSG_REMOVE_GROUP:
                    listener.onRemoveGroup(message);
                    break;
                case GroupMessage.MSG_UPDATE_GROUP:
                    listener.onUpdateGroup(message);
                    break;
                case GroupMessage.MSG_UPDATE_MULTI_GROUP:
                    listener.onUpdateMultiGroups(message);
                    break;
                case GroupMessage.MSG_ACTIVATE_GROUP:
                    listener.onActivatedGroup(message);
                    break;
                case GroupMessage.MSG_DEACTIVATE_GROUP:
                    listener.onDeactivateGroup(message);
                    break;
                case GroupMessage.MSG_SYNC_GROUPS:
                    listener.onSyncGroups(message);
                    break;
                case GroupMessage.MSG_REMOVE_SUBGROUPS:
                    listener.onRemoveSubgroups(message);
            }
        }
    }

    /**
     * Post message to theme listeners
     * @param message message to post
     */
    void notifyThemeListeners(ThemeMessage message)
    {
        assert(message != null);
        for(ThemeListener listener : _themeListener)
        {
            switch(message._id)
            {
                case ThemeMessage.MSG_ADD_THEME:
                    listener.onAddTheme(message);
                    break;
                case ThemeMessage.MSG_REMOVE_THEME:
                    listener.onRemoveTheme(message);
                    break;
                case ThemeMessage.MSG_REMOVE_SUBTHEMES:
                    listener.onRemoveSubthemes(message);
                    break;
                case ThemeMessage.MSG_UPDATE_SINGLE_THEME:
                    listener.onUpdateTheme(message);
                    break;
                case ThemeMessage.MSG_ACTIVATE_THEME:
                    listener.onActivatedTheme(message);
                    break;
                case ThemeMessage.MSG_DEACTIVATE_THEME:
                    listener.onDeactivateTheme(message);
                    break;
                case ThemeMessage.MSG_UPDATE_MULTI_THEME:
                    listener.onUpdateMultiThemes(message);
                    break;
                case ThemeMessage.MSG_SYNC_THEMES:
                    listener.onSyncThemes(message);
            }
        }
    }

    /******************** end of class********************************/
}
