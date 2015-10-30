package com.brightlightsystems.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brightlightsystems.core.utilities.notificationsystem.Subscribable;
import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

/**
 * Created by Michael on 10/20/2015.
 */
public class Database extends SQLiteOpenHelper implements Subscribable
{
    /**Holds the version of the database*/
    public static final int DATABASE_VERSION = 1;
    /**Holds the name of the database*/
    public static final String DATABASE_NAME = "core_data_base";

    /**
     * Construct a core database with specified context.
     * @param context context of the application.
     */
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        assert(db == null);
        //db.execSQL();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public <T> void onNotify(SystemMessage<T> message)
    {
        switch (message.getId())
        {

        }

    }
}
