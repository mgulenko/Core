package com.brightlightsystems.core.database;

import android.provider.BaseColumns;

/**
 * This class is a contract class for the table Bulbs_Groups in the database. This table is a
 * resolution of the many-to-many relationship between Bulbs and Groups.
 * @author Michael Gulenko. Created on 10/20/2015.
 */
public final class BulbGroupContract
{
    /**Empty constructor to prevent from instantiating */
    public BulbGroupContract(){}

    /**Inner class that defines table content*/
    public abstract class BulbGroupEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "bulbs_groups";
        public static final String COLUMN_NAME_GROUP_ID         = "group_id";
        public static final String COLUMN_NAME_BULB_ID          = "bulb_id";
        public static final String COLUMN_NAME_THEME_ID         = "theme_id";
    }
}
