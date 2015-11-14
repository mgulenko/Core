package com.brightlightsystems.core.utilities.definitions;

import android.util.Log;

import com.brightlightsystems.core.datastructure.Bridge;
import com.brightlightsystems.core.datastructure.BulbColor;
import com.brightlightsystems.core.datastructure.DataManager;
import com.brightlightsystems.core.datastructure.DatabaseManager;
import com.brightlightsystems.core.datastructure.Group;
import com.brightlightsystems.core.datastructure.Lightbulb;
import com.brightlightsystems.core.datastructure.Trait;
import com.brightlightsystems.core.utilities.notificationsystem.BulbMessage;
import com.brightlightsystems.core.utilities.notificationsystem.GroupMessage;
import com.brightlightsystems.core.utilities.notificationsystem.Publisher;

import java.util.Map;

/**
 * This class provides  generic utility methods to test data.
 * @author Michael Gulenko. Created on 11/2/2015
 */
public abstract class DataStructureHelper
{

    public static void BulbTest(DatabaseManager databaseManager)
    {
        //add light bulb
        Trait trait = new Trait(new BulbColor(0,0,0,50));
        Lightbulb bulb = new Lightbulb(Lightbulb.getNextBulbId(),"FACTORYNAME_OMG", "Mablawa", trait,Lightbulb.States.OFF);
        Publisher.postBulbNotification(new BulbMessage(BulbMessage.MSG_ADD_BULB, bulb));
        printBulbs("DataStructure", "ADDED LIGHT BULB\n");
        databaseManager.loadData();
        printBulbs("Database", "ADDED LIGHT BULB\n");

        //update bulb
        bulb.setState(Lightbulb.States.ON);
        Publisher.postBulbNotification(new BulbMessage(BulbMessage.MSG_UPDATE_SINGLE_BULB,bulb));
        printBulbs("DataStructure", "UPDATE LIGHT BULB\n");
        databaseManager.loadData();
        printBulbs("Database", "UPDATE LIGHT BULB\n");

        //remove bulb
        Publisher.postBulbNotification(new BulbMessage(BulbMessage.MSG_REMOVE_BULB, bulb));
        printBulbs("DataStructure", "REMOVED LIGHT BULB\n");
        databaseManager.loadData();
        printBulbs("Database", "REMOVED LIGHT BULB\n");


    }

    public static void printBulbs(String logTag, String caption)
    {
        Log.d(logTag,caption);
        DataManager dm = DataManager.getInstance();

        for(Map.Entry<Integer,Bridge> entry: dm.getBridgeCollection().entrySet())
        {
            String print = "BRIDGE: \n\n" + entry.getValue().toString() +"\n CONTAINS LIGHT BULBS: "+
                    "\n*****************************************************************************\n\n";
            for(Lightbulb value: entry.getValue().getBulbsCollection())
            {
                print+=value.toString() +"\n______________\n";
            }
            Log.d(logTag, print);
        }
    }

    public static void groupTest(DatabaseManager databaseManager)
    {
        //add new group
        DataManager dm = DataManager.getInstance();
        Group group = new Group(Group.getNextGroupId(),"New Test Group",1,true,true);
        Lightbulb bulb = dm.getBulbById(11);
        Group subGroup = dm.getGroupCollection().get(DataManager.getActiveBridgeId()).get(3);
        group.addBulb(bulb);
        group.addGroup(subGroup);
        Publisher.postGroupNotification(new GroupMessage(GroupMessage.MSG_ADD_GROUP, group));

        printGroups("DataStructure", "ADDED GROUP\n");
        databaseManager.loadData();
        printGroups("Database", "ADDED GROUP\n");

        //update group
        group.removeGroup(3);
        group.deactivate();
        Publisher.postGroupNotification(new GroupMessage(GroupMessage.MSG_UPDATE_GROUP, group));
        printGroups("DataStructure", "UPDATE GROUP\n");
        databaseManager.loadData();
        printGroups("Database", "UPDATE GROUP\n");

        //remove bulb
        Publisher.postGroupNotification(new GroupMessage(GroupMessage.MSG_REMOVE_GROUP, group));
        printGroups("DataStructure", "REMOVED GROUP\n");
        databaseManager.loadData();
        printGroups("Database", "REMOVED GROUP\n");
    }

    public static void printGroups(String logTag, String caption)
    {
        Log.d(logTag,caption);
        DataManager dm = DataManager.getInstance();

        for(Map.Entry<Integer,Bridge> entry: dm.getBridgeCollection().entrySet())
        {
            String print = "BRIDGE: \n\n" + entry.getValue().toString() +"\n CONTAINS GROUPS: "+
                    "\n*****************************************************************************\n\n";
            for(Group value: dm.getGroupCollection().get(entry.getKey()).values())
            {
                print+=value.toString() +"\n______________\n";
            }
            Log.d(logTag, print);
        }
    }

    private void ThemeTest()
    {}

}
