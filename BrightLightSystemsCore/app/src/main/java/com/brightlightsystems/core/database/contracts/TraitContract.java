package com.brightlightsystems.core.database.contracts;

import android.provider.BaseColumns;

/**
 * This class is a contract class for the table Traits in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
public final class TraitContract
{
    /**Empty constructor to prevent from instantiating */
    public TraitContract(){}

    /**Inner class that defines table content*/
    public static abstract class TraitsEntry implements BaseColumns
    {
        public static final String TABLE_NAME                       = "traits";
        public static final String COLUMN_NAME_TRAIT_ID             = "trait_id";
        public static final String COLUMN_NAME_THEME_ID             = "theme_id";
        public static final String COLUMN_NAME_BULB_ID              = "bulb_id";
        public static final String COLUMN_NAME_BULB_COLOR           = "bulb_color";
        public static final String COLUMN_NAME_BULB_BRIGHTNESS      = "bulb_brightness";
        public static final String COLUMN_NAME_COLOR_SATURATION     = "color_saturation";
    }
}
