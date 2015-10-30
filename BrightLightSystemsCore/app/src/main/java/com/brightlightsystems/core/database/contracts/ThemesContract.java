package com.brightlightsystems.core.database.contracts;

import android.provider.BaseColumns;

/**
 * This class is a contract class for the table Themes in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
public final class ThemesContract
{
    /**Empty constructor to prevent from instantiating */
    public ThemesContract(){}

    /**Inner class that defines table content*/
    public abstract class ThemeEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "themes";
        public static final String COLUMN_NAME_THEME_ID         = "theme_id";
        public static final String COLUMN_NAME_THEME_NAME       = "theme_name";
    }
}
