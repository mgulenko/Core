package com.brightlightsystems.core.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.brightlightsystems.core.database.contracts.BridgeContract;
import com.brightlightsystems.core.database.contracts.BulbsContract;
import com.brightlightsystems.core.database.contracts.GroupsContract;
import com.brightlightsystems.core.datastructure.Lightbulb;
import com.brightlightsystems.core.utilities.notificationsystem.Messages;
import com.brightlightsystems.core.utilities.notificationsystem.Subscribable;
import com.brightlightsystems.core.utilities.notificationsystem.Subscriber;
import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Class that creates and handles connection with SQLite database.
 * It also responsible for any data modification in the database.
 * Since the database comes preloaded, it never creates it, but rather uses .db file path
 * to open connection.
 * Created by Michael on 11/6/2015.
 */
public class DatabaseManager extends SQLiteOpenHelper implements Subscribable
{
    /**Default path for the database*/
    //TODO: this database path is a subject to change.
    private static  String CORE_DB_PATH;
    /**Data base name*/
    private static final String CORE_DB_NAME = "core_data_base";
    /**An instance of the database on the device*/
    private SQLiteDatabase _database;
    /**An instance of the context to access app resources and assets.*/
    private final Context _context;

    /**
     * Creates a DatabaseManager with context for assets and resources as well as version
     * @param context context of the app
     * @param version version of the data base
     */
    public DatabaseManager(Context context, int version)
    {
        super(context, CORE_DB_NAME, null, version);
        _context = context;
        if(android.os.Build.VERSION.SDK_INT >= 17)
            CORE_DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            CORE_DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        assert(_context != null);
        subscribe();
    }

    /**
     * Attempts to connect to the data base. If database exists on the system, method
     * will open read/write connection and loads the data.
     * If it doesn't, then the method will create an empty database
     * and will copy the content provided in assets data base over, and then will load all data
     * @throws IOException if failed to copy the data base
     */
    public boolean createDataBase() throws IOException
    {
        if(!openIfExists())
        {
            _database = this.getReadableDatabase();
            copyDataBase();
            _database.close();
        }

        assert(_database != null);
        loadData();
        return true;
    }


    /**
     * Opens the database if its already exists on the system.
     * @return true if it exists, false otherwise
     */
    private boolean openIfExists()
    {
        try
        {
            _database = SQLiteDatabase.openDatabase(CORE_DB_PATH + CORE_DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e)
        {
            //database does not exist yet.
        }

        return _database != null;
    }


    /**
     * Copies database from the asset folder to the empty database on the
     * system folder.
     */
    private void copyDataBase() throws IOException
    {
        //open in and out streams
        InputStream  input  = _context.getAssets().open(CORE_DB_NAME);
        OutputStream output = new FileOutputStream(CORE_DB_PATH + CORE_DB_NAME);

        //transfer data from the input to output
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer))>0)
        {
            output.write(buffer, 0, length);
        }

        //Close the streams
        output.flush();
        output.close();
        input.close();

    }

    /**
     * Loads entire data base. Usually done on startup.
     * IMPORTANT: THE INNER ORDER OF METHOD CALLS MATTERS.
     */
    public void loadData()
    {
        _database = this.getReadableDatabase();
        //loading bridges data
        BridgeContract.load(_database, _context);
        //loading light bulbs data
        BulbsContract.load(_database, _context);
        //loading groups data
        GroupsContract.load(_database,_context);
        _database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @SuppressLint("NewApi")
    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void subscribe()
    {
        Subscriber.subscribe(this, Messages.MSG_ADD_BULB);
        Subscriber.subscribe(this, Messages.MSG_REMOVE_BULB);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_SINGLE_BULB);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_MULTI_BULB);
        Subscriber.subscribe(this, Messages.MSG_SYNC_BULB_STATE);

        Subscriber.subscribe(this, Messages.MSG_ADD_THEME);
        Subscriber.subscribe(this, Messages.MSG_REMOVE_THEME);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_SINGLE_THEME);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_COMPLEX_THEME);
        Subscriber.subscribe(this, Messages.MSG_ACTIVATE_THEME);
        Subscriber.subscribe(this, Messages.MSG_DEACTIVATE_THEME);
        Subscriber.subscribe(this, Messages.MSG_SYNC_THEMES);

        Subscriber.subscribe(this, Messages.MSG_ADD_GROUP);
        Subscriber.subscribe(this, Messages.MSG_REMOVE_GROUP);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_SINGLE_GROUP);
        Subscriber.subscribe(this, Messages.MSG_UPDATE_COMPLEX_GROUP);
        Subscriber.subscribe(this, Messages.MSG_ACTIVATE_GROUP);
        Subscriber.subscribe(this, Messages.MSG_DEACTIVATE_GROUP);
        Subscriber.subscribe(this, Messages.MSG_SYNC_GROUPS);
    }

    @Override
    public void unsubscribe()
    {
        Subscriber.unsubscribe(this, Messages.MSG_ADD_BULB);
        Subscriber.unsubscribe(this, Messages.MSG_REMOVE_BULB);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_SINGLE_BULB);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_MULTI_BULB);
        Subscriber.unsubscribe(this, Messages.MSG_SYNC_BULB_STATE);

        Subscriber.unsubscribe(this, Messages.MSG_ADD_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_REMOVE_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_SINGLE_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_COMPLEX_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_ACTIVATE_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_DEACTIVATE_THEME);
        Subscriber.unsubscribe(this, Messages.MSG_SYNC_THEMES);

        Subscriber.unsubscribe(this, Messages.MSG_ADD_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_REMOVE_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_SINGLE_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_UPDATE_COMPLEX_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_ACTIVATE_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_DEACTIVATE_GROUP);
        Subscriber.unsubscribe(this, Messages.MSG_SYNC_GROUPS);
    }

    @Override
    public <T> void onRecieve(SystemMessage<T> message)
    {
        _database = this.getWritableDatabase();
        switch(message.ID)
        {
            case Messages.MSG_ADD_BULB:
                BulbsContract.add((Lightbulb)message.getAttachment(),_database);
                break;
            case Messages.MSG_REMOVE_BULB:
                BulbsContract.remove((Integer)message.getAttachment(),_database);
                break;
            case Messages.MSG_UPDATE_SINGLE_BULB:
                BulbsContract.update((Lightbulb)message.getAttachment(),_database);
                break;
            case Messages.MSG_UPDATE_MULTI_BULB:
            {
                Set<Lightbulb> bulbs = (Set<Lightbulb>) message.getAttachment();
                for(Lightbulb bulb : bulbs)
                {
                    BulbsContract.update(bulb,_database);
                }
            }
                break;
            case Messages.MSG_SYNC_BULB_STATE:
                //TODO: implement this message handler
                break;
            case Messages.MSG_ADD_GROUP:
                break;
            case Messages.MSG_REMOVE_GROUP:
                break;
            case Messages.MSG_UPDATE_SINGLE_GROUP:
                break;
            case Messages.MSG_UPDATE_COMPLEX_GROUP:
                break;
            case Messages.MSG_ACTIVATE_GROUP:
                break;
            case Messages.MSG_DEACTIVATE_GROUP:
                break;
            case Messages.MSG_SYNC_GROUPS:
                //TODO: implement message handler
                break;
            case Messages.MSG_ADD_THEME:
                break;
            case Messages.MSG_REMOVE_THEME:
                break;
            case Messages.MSG_UPDATE_SINGLE_THEME:
                break;
            case Messages.MSG_UPDATE_COMPLEX_THEME:
                break;
            case Messages.MSG_ACTIVATE_THEME:
                break;
            case Messages.MSG_DEACTIVATE_THEME:
                break;
            case Messages.MSG_SYNC_THEMES:
                //TODO: Implement message handler
                break;
        }
        _database.close();
    }

}
