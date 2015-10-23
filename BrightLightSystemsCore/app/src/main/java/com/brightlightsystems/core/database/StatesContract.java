package com.brightlightsystems.core.database;

import android.provider.BaseColumns;

/**
 * This class is a contract class for the table States in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
public final class StatesContract
{
    /**Empty constructor to prevent from instantiating */
    public StatesContract(){}

    /**Inner class that defines table content*/
    public abstract class StateEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "states";
        public static final String COLUMN_NAME_STATES_ID        = "state_id";
        public static final String COLUMN_NAME_STATE_NAME       = "state_name";
    }
}
