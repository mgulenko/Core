package com.brightlightsystems.core.utilities.notificationsystem;

import com.brightlightsystems.core.datastructure.Theme;

/**
 * Created by Michael on 11/11/2015.
 */
public class ThemeMessage
{
    /**
     * Notifies the system that a new theme needs to be added.
     */
    public static final int MSG_ADD_THEME               = 0x01;
    /**
     * Notifies the system that a theme needs to be removed.
     */
    public static final int MSG_REMOVE_THEME            = 0x02;
    /**
     * Notifies the system that a sub-theme needs to be removed from complex themes.
     */
    public static final int MSG_REMOVE_SUBTHEMES        = 0x03;
    /**
     * Notifies the system that a theme needs to be updated.
     */
    public static final int MSG_UPDATE_SINGLE_THEME     = 0x04;
    /**
     * Notifies the system that a complex theme needs to be updated
     */
    public static final int MSG_UPDATE_MULTI_THEME    = 0x05;
    /**
     * Notifies the system that a theme needs to be activated.
     */
    public static final int MSG_ACTIVATE_THEME          = 0x06;
    /**
     * Notifies the system that a theme needs to be deactivated.
     */
    public static final int MSG_DEACTIVATE_THEME        = 0x07;
    //TODO: Add mesage description
    public static final int MSG_SYNC_THEMES             = 0x08;

    public final int _id;
    public final Theme _theme;

    public ThemeMessage(int id, Theme theme)
    {
        _id = id;
        _theme = theme;
    }
}
