package com.brightlightsystems.core.database;

import android.provider.BaseColumns;

/**
 * This class is a contract class for the table Bridges in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
public final class BridgeContract
{
    /**Empty constructor to prevent from instantiating */
    public BridgeContract(){}

    /**Inner class that defines table content*/
    public abstract class BridgeEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "bridges";
        public static final String COLUMN_NAME_BRIDGE_ID        = "bridge_id";
        public static final String COLUMN_NAME_FACTORY_NAME     = "factory_name";
        public static final String COLUMN_NAME_USER_DEF_NAME    = "user_def_name";
    }

}
