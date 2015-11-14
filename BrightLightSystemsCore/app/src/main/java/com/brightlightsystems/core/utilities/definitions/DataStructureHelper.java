package com.brightlightsystems.core.utilities.definitions;

import android.util.Log;

import com.brightlightsystems.core.datastructure.Bridge;
import com.brightlightsystems.core.datastructure.BulbColor;
import com.brightlightsystems.core.datastructure.DataManager;
import com.brightlightsystems.core.datastructure.DatabaseManager;
import com.brightlightsystems.core.datastructure.HueElement;
import com.brightlightsystems.core.datastructure.Lightbulb;
import com.brightlightsystems.core.datastructure.Trait;
import com.brightlightsystems.core.utilities.notificationsystem.BulbMessage;
import com.brightlightsystems.core.utilities.notificationsystem.Publisher;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class provides  generic utility methods to manipulate data.
 * @author Michael Gulenko. Created on 11/2/2015
 */
public abstract class DataStructureHelper
{
    /**
     * Method converts a set of HueElement to the ordered map, where K is an Integer id value of the
     * element and V is the actual element.
     * @param set data set of elements that needs to be converted.
     * @return map with new values.
     * @throws IllegalArgumentException if set is null.
     */
    public static Map<Integer, ? extends HueElement>  hueElementsToLinkedMap(Set<? extends HueElement> set)
    {
        if(set == null)
            throw new IllegalArgumentException("Can't convert from null");
        Map<java.lang.Integer, HueElement> map = new LinkedHashMap<>();
        for(HueElement v: set)
        {
            map.put(v.getId(),v);
        }
        assert(map != null);
        return map;
    }

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

    private void Grouptest()
    {}

    private void ThemeTest()
    {}

}
