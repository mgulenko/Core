package com.brightlightsystems.core.datastructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.brightlightsystems.core.R;
import com.brightlightsystems.core.datastructure.DataManager;
import com.brightlightsystems.core.datastructure.Group;
import com.brightlightsystems.core.datastructure.Lightbulb;

import java.util.Map;

/**
 * This class is a contract class for the table Bulbs in the database
 * @author Michael Gulenko. Created on 10/20/2015.
 */
abstract class GroupsContract
{
    /**Empty constructor to prevent from instantiating */
    private GroupsContract(){}

    /**
     * Method loads data from the group table
     * @param db data base to read the data from
     * @param context context of the application to access resources
     * @throws IllegalArgumentException when db or context == null
     */
    static void load(SQLiteDatabase db, Context context)
    {
        if(context == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        //container for

        DataManager dm = DataManager.getInstance();
        //selecting  groups
        Cursor cursor = db.rawQuery("SELECT * FROM  " + GroupEntry.TABLE_NAME, null);
        if (cursor .moveToFirst())
        {
            Group group;
            while (!cursor.isAfterLast())
            {
                int id = cursor.getInt(cursor.getColumnIndex(GroupEntry.COLUMN_NAME_GROUP_ID));
                String name = cursor.getString(cursor.getColumnIndex(GroupEntry.COLUMN_NAME_GROUP_NAME));
                int bridgeId = cursor.getInt(cursor.getColumnIndex(GroupEntry.COLUMN_NAME_BRIDGE_ID));
                int favorites = cursor.getInt(cursor.getColumnIndex(GroupEntry.COLUMN_NAME_FAVORITES));
                int activated = cursor.getInt(cursor.getColumnIndex(GroupEntry.COLUMN_NAME_ACTIVATED));

                if(name == null)
                    name = context.getResources().getString(R.string.default_group);

                group = new Group(id, name, bridgeId, (favorites == 1), activated == 1);
                String selectBulbQuery =    "SELECT " + BulbGroupEntry.COLUMN_NAME_BULB_ID +
                                            " FROM " + BulbGroupEntry.TABLE_NAME +
                                            " WHERE " + BulbGroupEntry.COLUMN_NAME_GROUP_ID + " = " + id;

                //selecting bulb ids
                Cursor bulbCursor = db.rawQuery(selectBulbQuery, null);
                if (bulbCursor.moveToFirst())
                {
                    while (!bulbCursor.isAfterLast())
                    {
                        //adding to the group
                        int bulbId = bulbCursor.getInt(bulbCursor.getColumnIndex(BulbGroupEntry.COLUMN_NAME_BULB_ID));
                        Lightbulb bulb = dm.getBulbById(bulbId);
                        if (bulb != null)
                            group.addBulb(bulb);
                        bulbCursor.moveToNext();
                    }
                    bulbCursor.close();
                }

                dm.addGroup(group);
                cursor.moveToNext();
            }
            cursor.close();
        }

        //Loading sub groups for home edition version.
        processHomeEditionSG(db);
        //TODO: Add multi bridge support here:
    }

    /**
     * Load all subgroups for home edition version. Because, home edition does not require multiple
     * bridges support, the read of subgroup data and setting up data structure will differ from
     * the corporate edition.
     * @param db database to read the data from
     */
    private static void processHomeEditionSG(SQLiteDatabase db)
    {
        DataManager dm = DataManager.getInstance();
        Map<Integer, Group> map = dm.getGroupCollection().get(DataManager.getActiveBridgeId());

        for(Map.Entry<Integer,Group> e: map.entrySet())
        {
            int id = e.getKey();
            //selecting subgroups
            String selectSubgroupsQuery =   "SELECT " + SubGroupEntry.COLUMN_NAME_SUBGROUP_ID +
                                            " FROM "  + SubGroupEntry.TABLE_NAME +
                                            " WHERE " + SubGroupEntry.COLUMN_NAME_GROUP_ID + " = " + id;

            Cursor subGroupCursor = db.rawQuery(selectSubgroupsQuery, null);
            if (subGroupCursor .moveToFirst())
            {
                while(!subGroupCursor.isAfterLast())
                {
                    int subgroupId = subGroupCursor.getInt(subGroupCursor.getColumnIndex(SubGroupEntry.COLUMN_NAME_SUBGROUP_ID));
                    map.get(id).addGroup(map.get(subgroupId));
                    subGroupCursor.moveToNext();
                }
                subGroupCursor.close();
            }
        }
    }

    /**
     * Method adds new group into the database.
     * @param group group to add.
     * @param db data base to read the data from
     * @throws IllegalArgumentException when bulb or db == null
     * @throws Error if was not able to add the entry
     */
    static void add(Group group, SQLiteDatabase db)
    {
        if(group == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        ContentValues values = initValues(group);
        if(db.insert(GroupEntry.TABLE_NAME, null, values) == -1)
            throw new Error("Failed to add data into groups");

        //adding light bulbs for that group
        if(!group.getBulbMap().isEmpty())
        {
            values = new ContentValues();
            for(Lightbulb b : group.getBulbCollection())
            {
                values.put(BulbGroupEntry.COLUMN_NAME_GROUP_ID, group.getId());
                values.put(BulbGroupEntry.COLUMN_NAME_BULB_ID, b.getId());
                if(db.insert(BulbGroupEntry.TABLE_NAME,null,values) == -1)
                    throw new Error("Failed to add data into groups");
                values.clear();
            }
        }

        //adding complex groups
        if(!group.getGroupMap().isEmpty())
        {
            values = new ContentValues();
            for(Group g: group.getGroupCollection())
            {
                values.put(SubGroupEntry.COLUMN_NAME_GROUP_ID, group.getId());
                values.put(SubGroupEntry.COLUMN_NAME_SUBGROUP_ID, g.getId());
                if(db.insert(SubGroupEntry.TABLE_NAME,null,values) == -1)
                    throw new Error("Failed to add data into groups");
                values.clear();
            }
        }
    }

    /**
     * Removes a group from the database
     * @param id id of the light bulb to be removed
     * @param db database to modify
     * @throws IllegalArgumentException if db == null
     * @throws Error if was not able to remove the entry
     */
    static void remove(int id, SQLiteDatabase db)
    {
        if(db == null)
            throw new IllegalArgumentException("Incorrect parameters");
        if (db.delete(GroupEntry.TABLE_NAME, GroupEntry.COLUMN_NAME_GROUP_ID + "=" + id, null) != 1)
            throw new Error("Failed while removing data");
    }

    /**
     * Updates a group in the database
     * @param group a group to update.
     * @param db database to modify
     * @throws IllegalArgumentException when bulb or db == null
     * @throws Error if was not able to update the entry
     */
    static void update(Group group, SQLiteDatabase db)
    {
        if(group == null || db == null)
            throw new IllegalArgumentException("Incorrect parameters");

        int id = group.getId();

        ContentValues values = initValues(group);
        if(db.update(GroupEntry.TABLE_NAME, values, "_id = " + id, null) < 1)
            throw new Error("Something went wrong while updating");

        //First create a string of of bulb ids for the remove query
        //insert new rows if needed
        String bulbIds = "(";
        String insertStatement = "INSERT OR IGNORE INTO " + BulbGroupEntry.TABLE_NAME +"(" +
                                     BulbGroupEntry.COLUMN_NAME_GROUP_ID + ", " +
                                     BulbGroupEntry.COLUMN_NAME_BULB_ID +") VALUES(";
        for(Lightbulb b: group.getBulbCollection())
        {
            String query = insertStatement + group.getId() + b.getId() + ")";
            db.rawQuery(query,null);
            bulbIds += (b.getId() + ",");
        }

        //remove old entries
        bulbIds = bulbIds.substring(0,bulbIds.length() - 1);
        String query  = "DELETE FROM " + BulbGroupEntry.TABLE_NAME +
                        " WHERE " + BulbGroupEntry.COLUMN_NAME_GROUP_ID + " = " + group.getId()+
                        " AND " +
                        BulbGroupEntry.COLUMN_NAME_BULB_ID + " NOT IN " + bulbIds + ")";
        db.rawQuery(query, null);

        //update complex group table

        //First create a string of of group ids for the remove query
        //insert new rows if needed
        String groupIds = "(";
        insertStatement = "INSERT OR IGNORE INTO " + SubGroupEntry.TABLE_NAME +"(" +
                          SubGroupEntry.COLUMN_NAME_GROUP_ID + ", " +
                          SubGroupEntry.COLUMN_NAME_SUBGROUP_ID +") VALUES(";
        for(Group g: group.getGroupCollection())
        {
            query = insertStatement + group.getId() + g.getId() + ")";
            db.rawQuery(query,null);
            groupIds += (g.getId() + ",");
        }

        //remove old entries
        groupIds = groupIds.substring(0,groupIds.length() - 1);
        query  = "DELETE FROM " + SubGroupEntry.TABLE_NAME +
                        " WHERE " + SubGroupEntry.COLUMN_NAME_GROUP_ID + " = " + group.getId()+
                        " AND " +
                        SubGroupEntry.COLUMN_NAME_SUBGROUP_ID + " NOT IN " + groupIds + ")";
        db.rawQuery(query, null);

    }

    /**
     * Subroutine that initializes values for add and update procedures of the Groups table
     * @param group group parameters of which is to stroe
     * @return map of values to add or update
     */
    private static ContentValues initValues(Group group)
    {
        assert(group != null);
        String groupName = group.getName();
        int bridgeId = group.getBridgeId();
        int activated = group.isActivated()? 1 : 0;
        int favorite  = group.isFavorite() ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put(GroupEntry.COLUMN_NAME_GROUP_NAME, groupName);
        values.put(GroupEntry.COLUMN_NAME_BRIDGE_ID,  bridgeId);
        values.put(GroupEntry.COLUMN_NAME_ACTIVATED,  activated);
        values.put(GroupEntry.COLUMN_NAME_FAVORITES,  favorite);
        return values;
    }


    /**Inner class that defines table content*/
    abstract class GroupEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "groups";
        public static final String COLUMN_NAME_GROUP_ID         = "_id";
        public static final String COLUMN_NAME_GROUP_NAME       = "group_name";
        public static final String COLUMN_NAME_BRIDGE_ID        = "bridge_id";
        public static final String COLUMN_NAME_ACTIVATED        = "activated";
        public static final String COLUMN_NAME_FAVORITES        = "favorite";

    }

    /**Inner class that defines table content*/
    public abstract class BulbGroupEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "bulbs_groups";
        public static final String COLUMN_NAME_GROUP_ID         = "group_id";
        public static final String COLUMN_NAME_BULB_ID          = "bulb_id";
        public static final String COLUMN_NAME_THEME_ID         = "theme_id";
    }

    /**Inner class that defines table content*/
    abstract class SubGroupEntry implements BaseColumns
    {
        public static final String TABLE_NAME                   = "groups_subgroups";
        public static final String COLUMN_NAME_GROUP_ID         = "group_id";
        public static final String COLUMN_NAME_SUBGROUP_ID      = "subgroup_id";
    }
}
