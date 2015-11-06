package com.brightlightsystems.core.database.contracts;

import android.provider.BaseColumns;

/**
 * This class is a contract class for the table Bulbs in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
public final class BulbsContract
{
    /**Empty constructor to prevent from instantiating */
    public BulbsContract(){}

    /**Inner class that defines table content*/
    public static abstract class BulbsEntry implements BaseColumns
    {
        public static final String TABLE_NAME                       = "bulbs";
        public static final String COLUMN_NAME_BULB_ID              = "bulb_id";
        public static final String COLUMN_NAME_FACTORY_NAME         = "factory_name";
        public static final String COLUMN_NAME_USER_DEF_NAME        = "user_def_name";
        public static final String COLUMN_NAME_STATE_ID             = "state_id";
        public static final String COLUMN_NAME_BRIDGE_ID            = "bridge_id";
        public static final String COLUMN_NAME_BULB_COLOR           = "bulb_color";
        public static final String COLUMN_NAME_BULB_BRIGHTNESS      = "bulb_brightness";
        public static final String COLUMN_NAME_COLOR_TRANSPARENCY   = "color_transparency";
    }
}
