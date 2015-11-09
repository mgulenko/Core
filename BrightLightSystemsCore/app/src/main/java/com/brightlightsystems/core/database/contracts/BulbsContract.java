package com.brightlightsystems.core.database.contracts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.brightlightsystems.core.R;
import com.brightlightsystems.core.datastructure.BulbColor;
import com.brightlightsystems.core.datastructure.DataManager;
import com.brightlightsystems.core.datastructure.Lightbulb;
import com.brightlightsystems.core.datastructure.Trait;

/**
 * This class is a contract class for the table Bulbs in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
public abstract class BulbsContract
{
    /**Empty constructor to prevent from instantiating */
    private BulbsContract(){}

    /**
     * Method loads data from the bridges table
     * @param db data base to read the data from
     * @param context context of the application to access resources
     * @throws IllegalArgumentException when db or context == null
     */
    public static void load(SQLiteDatabase db, Context context)
    {
        if(context == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");
        Cursor cursor = db.rawQuery("select * from " + BulbsEntry.TABLE_NAME, null);

        if (cursor .moveToFirst())
        {
            Lightbulb bulb;

            while (!cursor.isAfterLast())
            {
                int id = cursor.getInt(cursor.getColumnIndex(BulbsEntry.COLUMN_NAME_BULB_ID));
                String factoryName = cursor.getString(cursor.getColumnIndex(BulbsEntry.COLUMN_NAME_FACTORY_NAME));
                String userDefName = cursor.getString(cursor.getColumnIndex(BulbsEntry.COLUMN_NAME_USER_DEF_NAME));
                int stateId  = cursor.getInt(cursor.getColumnIndex(BulbsEntry.COLUMN_NAME_STATE_ID));
                int bridgeId = cursor.getInt(cursor.getColumnIndex(BulbsEntry.COLUMN_NAME_BRIDGE_ID));
                int color = cursor.getInt(cursor.getColumnIndex(BulbsEntry.COLUMN_NAME_BULB_COLOR));
                int brightness = cursor.getInt(cursor.getColumnIndex(BulbsEntry.COLUMN_NAME_BULB_BRIGHTNESS));

                if(userDefName == null)
                  userDefName = context.getResources().getString(R.string.default_lightbulb);

                Lightbulb.synchNextId(id);
                Trait trait = new Trait(new BulbColor(color),brightness);
                bulb = new Lightbulb(id, factoryName, userDefName, trait, Lightbulb.intToState(stateId));

                DataManager.getInstance().getBridgeCollection().get(bridgeId).addBulb(bulb);
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    /**
     * Method adds new lightbulb into the database.
     * @param bulb lightbulb to add.
     * @param db data base to read the data from
     * @throws IllegalArgumentException when bulb or db == null
     * @throws Error if was not able to add the entry
     */
    public static void add(Lightbulb bulb, SQLiteDatabase db)
    {
        if(bulb == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        ContentValues values = initValues(bulb);
        if(db.insert(BulbsEntry.TABLE_NAME, null, values) == -1)
            throw new Error("Failed to add data into bulbs");
    }

    /**
     * Removes a bulb from the database
     * @param id id of the light bulb to be removed
     * @param db database to modify
     * @throws IllegalArgumentException if db == null
     * @throws Error if was not able to remove the entry
     */
    public static void remove(int id, SQLiteDatabase db)
    {
        if(db == null)
            throw new IllegalArgumentException("Incorrect parameters");
        if (db.delete(BulbsEntry.TABLE_NAME, BulbsEntry.COLUMN_NAME_BULB_ID + "=" + id, null) != 1)
            throw new Error("Failed while removing data");
    }

    /**
     * Updates a bulb in the database
     * @param bulb lightbulb to add.
     * @param db database to modify
     * @throws IllegalArgumentException when bulb or db == null
     * @throws Error if was not able to update the entry
     */
    public static void update(Lightbulb bulb, SQLiteDatabase db)
    {
        if(bulb == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        int id = bulb.getId();

        ContentValues values = initValues(bulb);
        if(db.update(BulbsEntry.TABLE_NAME, values, "_id = " + id, null) < 1)
            throw new Error("Something went wrong while updating");
    }


    /**
     * Routine that initializes content values.
     * @param bulb a light bulb whose metadata is used to fill up content values container
     * @return container that consists of values for the database query.
     */
    private static ContentValues initValues(Lightbulb bulb)
    {
        String factoryName = bulb.getFactoryName();
        String userDefName = bulb.getName();
        int stateId  = Lightbulb.stateToInt(bulb.getState());
        if(stateId < 1)
            throw new Error("Negative state identifier");

        int bridgeId = DataManager.getActiveBridgeId();
        int color = bulb.getTrait().getColor().getColor();
        int brightness = bulb.getTrait().getBrightness();

        ContentValues values = new ContentValues();
        values.put(BulbsEntry.COLUMN_NAME_FACTORY_NAME, factoryName);
        values.put(BulbsEntry.COLUMN_NAME_USER_DEF_NAME, userDefName);
        values.put(BulbsEntry.COLUMN_NAME_STATE_ID, stateId);
        values.put(BulbsEntry.COLUMN_NAME_BRIDGE_ID, bridgeId);
        values.put(BulbsEntry.COLUMN_NAME_BULB_COLOR, color);
        values.put(BulbsEntry.COLUMN_NAME_BULB_BRIGHTNESS, brightness);
        return values;
    }


    /**Inner class that defines table content*/
    public static abstract class BulbsEntry implements BaseColumns
    {
        public static final String TABLE_NAME                       = "bulbs";
        public static final String COLUMN_NAME_BULB_ID              = "_id";
        public static final String COLUMN_NAME_FACTORY_NAME         = "factory_name";
        public static final String COLUMN_NAME_USER_DEF_NAME        = "user_def_name";
        public static final String COLUMN_NAME_STATE_ID             = "state_id";
        public static final String COLUMN_NAME_BRIDGE_ID            = "bridge_id";
        public static final String COLUMN_NAME_BULB_COLOR           = "bulb_color";
        public static final String COLUMN_NAME_BULB_BRIGHTNESS      = "bulb_brightness";
    }
}
