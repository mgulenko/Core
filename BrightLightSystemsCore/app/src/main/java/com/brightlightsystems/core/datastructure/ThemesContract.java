package com.brightlightsystems.core.datastructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.brightlightsystems.core.R;

/**
 * This class is a contract class for the table Themes in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
abstract class ThemesContract
{
    /**Empty constructor to prevent from instantiating */
    private ThemesContract(){}

    /**
     * Method loads data from the theme table
     * @param db data base to read the data from
     * @param context context of the application to access resources
     * @throws IllegalArgumentException when db or context == null
     */
    static void load(SQLiteDatabase db, Context context)
    {
        if(context == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        DataManager dm = DataManager.getInstance();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + ThemeEntry.TABLE_NAME, null);
        if (cursor .moveToFirst())
        {
            Theme theme;
            while (!cursor.isAfterLast())
            {
                int id = cursor.getInt(cursor.getColumnIndex(ThemeEntry.COLUMN_NAME_THEME_ID));
                String name = cursor.getString(cursor.getColumnIndex(ThemeEntry.COLUMN_NAME_THEME_NAME));
                int favorite = cursor.getInt(cursor.getColumnIndex(ThemeEntry.COLUMN_NAME_FAVORITE));
                int activated = cursor.getInt(cursor.getColumnIndex(ThemeEntry.COLUMN_NAME_ACTIVATED));

                if(name == null)
                    name = context.getResources().getString(R.string.default_theme);
                theme = new Theme(id,name,(activated == 1),(favorite == 1));

                dm.addTheme(theme);
                TraitContract.load(db,theme.getId());
                cursor.moveToNext();
            }
            cursor.close();
        }
        processHomeEditionST(db);
        //TODO: Add multi bridge support here:
    }

    /**
     * Load all subthemes for home edition version. Because, home edition does not require multiple
     * bridges support, the read of subthemes data and setting up data structure will differ from
     * the corporate edition.
     * @param db database to read the data from
     */
    private static void processHomeEditionST(SQLiteDatabase db)
    {
        DataManager dm = DataManager.getInstance();

        for(Theme t: dm.getThemeCollection())
        {
            int id = t.getId();
            //selecting subgroups
            String selectSubgroupsQuery =   "SELECT " + SubThemeEntry.COLUMN_NAME_SUBTHEME_ID +
                    " FROM "  + SubThemeEntry.TABLE_NAME +
                    " WHERE " + SubThemeEntry.COLUMN_NAME_THEME_ID + " = " + id;

            Cursor subGroupCursor = db.rawQuery(selectSubgroupsQuery, null);
            if (subGroupCursor .moveToFirst())
            {
                while(!subGroupCursor.isAfterLast())
                {
                    int subgroupId = subGroupCursor.getInt(subGroupCursor.getColumnIndex(SubThemeEntry.COLUMN_NAME_SUBTHEME_ID));
                    dm.getThemeMap().put(id,dm.getThemeMap().get(subgroupId));
                    subGroupCursor.moveToNext();
                }
                subGroupCursor.close();
            }
        }
    }

    /**
     * Method adds new theme into the database.
     * @param theme new theme to add.
     * @param db data base to read the data from
     * @throws IllegalArgumentException when bulb or db == null
     * @throws Error if was not able to add the entry
     */
    static void add(Theme theme, SQLiteDatabase db)
    {
        if(theme == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        ContentValues values = initValues(theme);
        if(db.insert(ThemeEntry.TABLE_NAME, null, values) == -1)
            throw new Error("Failed to add data into themes");

        TraitContract.add(db, theme);

        //adding complex groups
        if(!theme.getThemeMap().isEmpty())
        {
            values = new ContentValues();
            for(Theme t: theme.getThemeCollection())
            {
                values.put(SubThemeEntry.COLUMN_NAME_THEME_ID, theme.getId());
                values.put(SubThemeEntry.COLUMN_NAME_SUBTHEME_ID, t.getId());
                if(db.insert(SubThemeEntry.TABLE_NAME,null,values) == -1)
                    throw new Error("Failed to add data into themes");
                values.clear();
            }
        }

        db.close();
    }

    /**
     * Removes a theme from the database
     * @param id id of the theme that has to be removed
     * @param db database to modify
     * @throws IllegalArgumentException if db == null
     * @throws Error if was not able to remove the entry
     */
    static void remove(int id, SQLiteDatabase db)
    {
        if(db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        if (db.delete(ThemeEntry.TABLE_NAME, ThemeEntry.COLUMN_NAME_THEME_ID + "=" + id, null) != 1)
            throw new Error("Failed while removing data");

        db.close();
    }

    /**
     * Updates a theme in the database
     * @param theme theme to update.
     * @param db database to modify
     * @throws IllegalArgumentException when bulb or db == null
     * @throws Error if was not able to update the entry
     */
    static void update(Theme theme, SQLiteDatabase db)
    {
        if(theme == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        int id = theme.getId();

        //update themes table
        ContentValues values = initValues(theme);
        if(db.update(ThemeEntry.TABLE_NAME, values, "_id = " + id, null) < 1)
            throw new Error("Something went wrong while updating");

        //update them of themes table

        //First create a string of of group ids for the remove query
        //insert new rows if needed
        String themeIds = "(";
        String insertStatement = "INSERT OR IGNORE INTO " + SubThemeEntry.TABLE_NAME +"(" +
                SubThemeEntry.COLUMN_NAME_THEME_ID + ", " +
                SubThemeEntry.COLUMN_NAME_SUBTHEME_ID +") VALUES(";
        for(Theme t: theme.getThemeCollection())
        {
            String query = insertStatement + theme.getId()+", " + t.getId() + ")";
            db.rawQuery(query,null);
            themeIds += (t.getId() + ",");
        }

        //remove old entries
        themeIds = themeIds.substring(0,themeIds.length() - 1);
       String  query  = "DELETE FROM " + SubThemeEntry.TABLE_NAME +
                " WHERE " + SubThemeEntry.COLUMN_NAME_THEME_ID + " = " + theme.getId()+
                " AND " +
                SubThemeEntry.COLUMN_NAME_SUBTHEME_ID + " NOT IN " + themeIds + ")";
        db.rawQuery(query, null);

        db.close();
    }

    /**
     * Subroutine that initializes values for add and update procedures of the Theme table
     * @param theme theme parameters of which is to stroe
     * @return map of values to add or update
     */
    private static ContentValues initValues(Theme theme)
    {
        assert(theme != null);
        String themeName = theme.getName();
        int activated = theme.isActivated()? 1 : 0;
        int favorite  = theme.isFavorite() ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put(ThemeEntry.COLUMN_NAME_THEME_NAME,  themeName);
        values.put(ThemeEntry.COLUMN_NAME_ACTIVATED,  activated);
        values.put(ThemeEntry.COLUMN_NAME_FAVORITE,  favorite);
        return values;
    }

    /**Inner class that defines table content*/
    abstract class ThemeEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "themes";
        public static final String COLUMN_NAME_THEME_ID         = "_id";
        public static final String COLUMN_NAME_THEME_NAME       = "theme_name";
        public static final String COLUMN_NAME_ACTIVATED        = "activated";
        public static final String COLUMN_NAME_FAVORITE          = "favorite";
    }

    /**Inner class that defines table content*/
    abstract class SubThemeEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "themes_subthemes";
        public static final String COLUMN_NAME_THEME_ID         = "theme_id";
        public static final String COLUMN_NAME_SUBTHEME_ID      = "subtheme_id";
    }


}
