package com.brightlightsystems.core.datastructure;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is a trait contract class for the trait table in the database.
 * Created by Michael on 11/10/2015.
 */
abstract class TraitContract
{
    /**Empty constructor to prevent from instantiating */
    private TraitContract(){}

    /**
     * Method loads data from the traits table
     * @param db data base to read the data from
     * @param themeId theme id that is used to load appropriate traits
     * @throws IllegalArgumentException when db or context == null
     */
    static void load(SQLiteDatabase db, int themeId)
    {
        if(db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        DataManager dm = DataManager.getInstance();

        Cursor cursor = db.rawQuery("SELECT * FROM  " + TraitsEntry.TABLE_NAME  + " WHERE " +
                                    TraitsEntry.COLUMN_NAME_THEME_ID +" = " + themeId, null);
        if (cursor .moveToFirst())
        {
            Map<Integer,Trait> traits = new LinkedHashMap<>();
            while (!cursor.isAfterLast())
            {
                int bulbId = cursor.getInt(cursor.getColumnIndex(TraitsEntry.COLUMN_NAME_BULB_ID));
                int color  = cursor.getInt(cursor.getColumnIndex(TraitsEntry.COLUMN_NAME_BULB_COLOR));
                int brightness  = cursor.getInt(cursor.getColumnIndex(TraitsEntry.COLUMN_NAME_BULB_BRIGHTNESS));
                int transparency  = cursor.getInt(cursor.getColumnIndex(TraitsEntry.COLUMN_NAME_COLOR_TRANSPARENCY));

                BulbColor bulbColor = new BulbColor(color,transparency);
                Trait trait = new Trait(bulbColor,brightness);
                traits.put(bulbId, trait);
                dm.getThemeMap().get(themeId).addTraits(traits);
                cursor.moveToNext();
            }
            cursor.close();
        }

    }

    /**
     * Method adds new theme into the database.
     * @param db data base to read the data from
     * @param theme theme that contain collection of traits to add.
     * @throws IllegalArgumentException when theme or db == null
     * @throws Error if was not able to add the entry
     */
    static void add(SQLiteDatabase db, Theme theme)
    {
        if(db == null || theme == null)
            throw new IllegalArgumentException("Incorrect parameters");
        Map<Integer,Trait>traits = theme.getTraitMap();
        if(!traits.isEmpty())
        {
            ContentValues values = new ContentValues();
            for(Map.Entry<Integer,Trait> t: traits.entrySet())
            {
                int themeid = theme.getId();
                int bulbId = t.getKey();
                int brightness = t.getValue().getBrightness();
                int color = t.getValue().getColor().getColor();
                int transparency = t.getValue().getColor().getTransparency();

                values.put(TraitsEntry.COLUMN_NAME_THEME_ID,themeid);
                values.put(TraitsEntry.COLUMN_NAME_BULB_ID,bulbId);
                values.put(TraitsEntry.COLUMN_NAME_BULB_COLOR,color);
                values.put(TraitsEntry.COLUMN_NAME_BULB_BRIGHTNESS,brightness);
                values.put(TraitsEntry.COLUMN_NAME_COLOR_TRANSPARENCY, transparency);

                if(db.insert(TraitsEntry.TABLE_NAME,null,values) == -1)
                    throw new Error("Failed to add data into themes");
            }
        }

    }

    /**
     * Removes a theme from the database
     * @param theme theme to be removed
     * @param db database to modify
     * @throws IllegalArgumentException if db == null
     * @throws Error if was not able to remove the entry
     */
    void remove(SQLiteDatabase db, Theme theme)
    {
        if( db == null)
            throw new IllegalArgumentException("Incorrect parameters");
    }

    /**
     * Updates a theme in the database
     * @param theme theme which traits are need to be update.
     * @param db database to modify
     * @throws IllegalArgumentException when bulb or db == null
     * @throws Error if was not able to update the entry
     */
    void update(Theme theme, SQLiteDatabase db)
    {
        if( db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        Map<Integer,Trait> traits = theme.getTraitMap();
        assert(traits != null);
        String bulbIds = "(";
        String insertStatement = "INSERT OR REPLACE INTO " + TraitsEntry.TABLE_NAME + "(" +
                                 TraitsEntry.COLUMN_NAME_THEME_ID           + ", " +
                                 TraitsEntry.COLUMN_NAME_BULB_ID            + ", " +
                                 TraitsEntry.COLUMN_NAME_BULB_COLOR         + ", " +
                                 TraitsEntry.COLUMN_NAME_BULB_BRIGHTNESS    + ", " +
                                 TraitsEntry.COLUMN_NAME_COLOR_TRANSPARENCY + ") VALUES(";

        for(Map.Entry<Integer,Trait> e: traits.entrySet())
        {
            String query = insertStatement + theme.getId() + ", " +
                             e.getKey() + ", " + e.getValue().getColor().getColor() + ", "+
                             e.getValue().getBrightness() + ", " +
                             e.getValue().getColor().getTransparency() + ")";

            db.rawQuery(query,null);
            bulbIds += (e.getKey() + ",");
        }


        //remove old entries
        bulbIds = bulbIds.substring(0,bulbIds.length() - 1);
        String  query  = "DELETE FROM " + TraitsEntry.TABLE_NAME +
                " WHERE " + TraitsEntry.COLUMN_NAME_THEME_ID + " = " + theme.getId()+
                " AND " +
                TraitsEntry.COLUMN_NAME_BULB_ID + " NOT IN " + bulbIds + ")";
        db.rawQuery(query, null);
    }

    /**Inner class that defines table content*/
    abstract class TraitsEntry implements BaseColumns
    {
        public static final String TABLE_NAME                       = "traits";
        public static final String COLUMN_NAME_THEME_ID             = "theme_id";
        public static final String COLUMN_NAME_BULB_ID              = "bulb_id";
        public static final String COLUMN_NAME_BULB_COLOR           = "bulb_color";
        public static final String COLUMN_NAME_BULB_BRIGHTNESS      = "bulb_brightness";
        public static final String COLUMN_NAME_COLOR_TRANSPARENCY   = "color_transparency";
    }
}