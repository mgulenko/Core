package com.brightlightsystems.core.datastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.brightlightsystems.core.utilities.notificationsystem.BulbListener;
import com.brightlightsystems.core.utilities.notificationsystem.BulbMessage;
import com.brightlightsystems.core.utilities.notificationsystem.GroupListener;
import com.brightlightsystems.core.utilities.notificationsystem.GroupMessage;
import com.brightlightsystems.core.utilities.notificationsystem.Subscriber;
import com.brightlightsystems.core.utilities.notificationsystem.ThemeListener;
import com.brightlightsystems.core.utilities.notificationsystem.ThemeMessage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class that creates and handles connection with SQLite database.
 * It also responsible for any data modification in the database.
 * Since the database comes preloaded, it never creates it, but rather uses .db file path
 * to open connection.
 * Created by Michael on 11/6/2015.
 */
public final class DatabaseManager extends SQLiteOpenHelper implements BulbListener,GroupListener,ThemeListener
{
    /**Default path for the database*/
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
        GroupsContract.load(_database, _context);
        _database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        assert (db != null);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void subscribe()
    {
        Subscriber.addBulbListener(this);
        Subscriber.addGroupListener(this);
        Subscriber.addThemeListener(this);
    }

    @Override
    public void onAddBulb(BulbMessage message)
    {
        BulbsContract.add(message._bulb,_database);
    }

    @Override
    public void onRemoveBulb(BulbMessage message)
    {
        BulbsContract.remove(message._bulb.getId(),_database);
    }

    @Override
    public void onUpdateBulb(BulbMessage message)
    {
        BulbsContract.update(message._bulb,_database);
    }

    @Override
    public void onUpdateMultiBulbs(BulbMessage message)
    {
        //TODO: implement message handler
    }

    @Override
    public void onSynchBulbs(BulbMessage bulmessagebs)
    {
        //TODO: implement message handler
    }

    @Override
    public void onAddGroup(GroupMessage message)
    {
        GroupsContract.add(message._group,_database);
    }

    @Override
    public void onRemoveGroup(GroupMessage message)
    {
        GroupsContract.remove(message._group.getId(),_database);
    }

    @Override
    public void onUpdateGroup(GroupMessage message)
    {
        GroupsContract.update(message._group,_database);
    }

    @Override
    public void onUpdateMultiGroups(GroupMessage message)
    {
        //TODO: implement message handler
    }

    @Override
    public void onActivatedGroup(GroupMessage message)
    {
        GroupsContract.update(message._group,_database);
    }

    @Override
    public void onDeactivateGroup(GroupMessage message)
    {
        GroupsContract.update(message._group,_database);
    }

    @Override
    public void onSyncGroups(GroupMessage message)
    {
        //TODO: implement message handler
    }

    @Override
    public void onRemoveSubgroups(GroupMessage message)
    {
        //TODO: implement messaeg handler
    }

    @Override
    public void onAddTheme(ThemeMessage message)
    {
        ThemesContract.add(message._theme,_database);
    }

    @Override
    public void onRemoveTheme(ThemeMessage message)
    {
        ThemesContract.remove(message._theme.getId(), _database);
    }

    @Override
    public void onUpdateTheme(ThemeMessage message)
    {
        ThemesContract.update(message._theme,_database);
    }

    @Override
    public void onUpdateMultiThemes(ThemeMessage message)
    {
        //TODO: implement message handler
    }

    @Override
    public void onActivatedTheme(ThemeMessage message)
    {
        ThemesContract.update(message._theme,_database);
    }

    @Override
    public void onDeactivateTheme(ThemeMessage message)
    {
        ThemesContract.update(message._theme,_database);
    }

    @Override
    public void onRemoveSubthemes(ThemeMessage message)
    {
        //TODO: implement message handler
    }

    @Override
    public void onSyncThemes(ThemeMessage message)
    {
        //TODO: implement message handler
    }
}
