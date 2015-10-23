package com.brightlightsystems.core.database;

import android.provider.BaseColumns;

/**
 * This class is a contract class for the table Bulbs in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
public final class GroupsContract
{
    /**Empty constructor to prevent from instantiating */
    public GroupsContract(){}

    /**Inner class that defines table content*/
    public abstract class GroupEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "groups";
        public static final String COLUMN_NAME_GROUP_ID         = "group_id";
        public static final String COLUMN_NAME_GROUP_NAME       = "group_name";
    }
}
