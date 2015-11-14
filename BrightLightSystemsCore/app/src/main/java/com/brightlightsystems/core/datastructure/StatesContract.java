package com.brightlightsystems.core.datastructure;

import android.provider.BaseColumns;

/**
 * This class is a contract class for the table States in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
abstract class StatesContract
{
    /**Empty constructor to prevent from instantiating */
    private StatesContract(){}

    /**Inner class that defines table content*/
    abstract class StateEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "states";
        public static final String COLUMN_NAME_STATES_ID        = "state_id";
        public static final String COLUMN_NAME_STATE_NAME       = "state_name";
    }
}
