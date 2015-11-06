package com.brightlightsystems.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.brightlightsystems.core.utilities.notificationsystem.Subscribable;
import com.brightlightsystems.core.utilities.notificationsystem.SystemMessage;

import java.io.IOException;

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
    private static final String CORE_DB_PATH = "/data/data/com.brightlightsystems.core/databases/";
    /**Data base name*/
    private static final String CORE_DB_NAME = "core_data_base";
    /**An instance of the database on the device*/
    private SQLiteDatabase _dataBase;
    /**An instance of the context to access app resources and assets.*/
    private final Context _context;

    /**
     * Creates a DatabaseManager with context fpr assets and resources and version name
     * @param context context of the app
     * @param version version of the data base
     */
    public DatabaseManager(Context context, int version)
    {
        super(context, CORE_DB_NAME, null, version);
        _context = context;
        assert(_context != null);
    }

    /**
     * Creates an empty database on the system and rewrites it with provided .db file.
     * */
    public boolean createDataBase() throws IOException
    {
        //check if the data base already exists on the system
        if(isAlreadyCreated())
            return true;

        //lets create an empty database..
        this.getReadableDatabase();
        //...and then overwrite with ours
        try
        {
            copyDataBase();

        } catch (IOException e) {

            throw new Error("Error copying database");

        }

        return true;

    }

    /**
     * Checks if the database already exist to avoid
     * re-factoring the file each time you open the app.
     * @return true if it exists, false otherwise
     */
    private boolean isAlreadyCreated()
    {

        SQLiteDatabase checkDB = null;

        try
        {
            String myPath = CORE_DB_PATH + CORE_DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }
        catch(SQLiteException e)
        {

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

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
    public <T> void onRecieve(SystemMessage<T> message) {

    }
}
