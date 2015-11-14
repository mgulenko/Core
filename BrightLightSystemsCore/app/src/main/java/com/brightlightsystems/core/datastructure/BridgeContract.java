package com.brightlightsystems.core.datastructure;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.brightlightsystems.core.R;
import com.brightlightsystems.core.datastructure.Bridge;
import com.brightlightsystems.core.datastructure.DataManager;


/**
 * This class is a contract class for the table Bridges in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
abstract class BridgeContract
{
    /**Empty constructor to prevent from instantiating */
    private BridgeContract(){}

    /**
     * Method loads data from the bridges table
     * @param db data base to read the data frim
     */
    static void load(SQLiteDatabase db, Context context)
    {
        assert (db != null);
        assert (context != null);
        Cursor cursor = db.rawQuery("select * from " + BridgeEntry.TABLE_NAME, null);

        if (cursor .moveToFirst())
        {
            Bridge bridge;

            while (!cursor.isAfterLast())
            {
                int id = cursor.getInt(cursor.getColumnIndex(BridgeEntry.COLUMN_NAME_BRIDGE_ID));
                String userDefName = cursor.getString(cursor.getColumnIndex(BridgeEntry.COLUMN_NAME_USER_DEF_NAME));
                if(userDefName == null)
                    userDefName = context.getResources().getString(R.string.default_bridge);
                bridge = new Bridge(id, userDefName, cursor.getString(cursor.getColumnIndex(BridgeEntry.COLUMN_NAME_FACTORY_NAME)));

                DataManager.getInstance().addBridge(bridge);
                if(cursor.getInt(cursor.getColumnIndex(BridgeEntry.COLUMN_NAME_ACTIVE)) == 1)
                    DataManager.getInstance().setActiveBridgeId(id);


                cursor.moveToNext();
            }

            cursor.close();
        }

    }



    /**Inner class that defines table content*/
    abstract class BridgeEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "bridges";
        public static final String COLUMN_NAME_BRIDGE_ID        = "_id";
        public static final String COLUMN_NAME_FACTORY_NAME     = "factory_name";
        public static final String COLUMN_NAME_USER_DEF_NAME    = "user_def_name";
        public static final String COLUMN_NAME_ACTIVE           = "active";
    }

}
