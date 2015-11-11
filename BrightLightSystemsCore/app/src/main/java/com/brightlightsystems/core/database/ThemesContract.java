package com.brightlightsystems.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.brightlightsystems.core.datastructure.Theme;

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

    }

    /**
     * Removes a theme from the database
     * @param theme theme to be removed
     * @param db database to modify
     * @throws IllegalArgumentException if db == null
     * @throws Error if was not able to remove the entry
     */
    static void remove(Theme theme, SQLiteDatabase db)
    {
        if(theme == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");
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


    }


    /**Inner class that defines table content*/
    abstract class ThemeEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "themes";
        public static final String COLUMN_NAME_THEME_ID         = "theme_id";
        public static final String COLUMN_NAME_THEME_NAME       = "theme_name";
    }

    private abstract class TraitContract
    {
        /**Empty constructor to prevent from instantiating */
        private TraitContract(){}

        /**Inner class that defines table content*/
        abstract class TraitsEntry implements BaseColumns
        {
            public static final String TABLE_NAME                       = "traits";
            public static final String COLUMN_NAME_TRAIT_ID             = "trait_id";
            public static final String COLUMN_NAME_THEME_ID             = "theme_id";
            public static final String COLUMN_NAME_BULB_ID              = "bulb_id";
            public static final String COLUMN_NAME_BULB_COLOR           = "bulb_color";
            public static final String COLUMN_NAME_BULB_BRIGHTNESS      = "bulb_brightness";
            public static final String COLUMN_NAME_COLOR_TRANSPARENCY   = "color_transparency";
        }
    }
}
