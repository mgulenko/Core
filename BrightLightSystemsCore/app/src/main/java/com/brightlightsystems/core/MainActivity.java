package com.brightlightsystems.core;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.brightlightsystems.core.datastructure.Bridge;
import com.brightlightsystems.core.datastructure.Lightbulb;
import com.brightlightsystems.core.datastructure.Trait;
import com.brightlightsystems.core.utilities.annotations.Gulenko;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**Hollend, this is your entry point for testing
         * Start with creating a Bridge then add light bulbs to it.
         * Then change traits on them, group them, and create themes.
         * Important to test the system's integrity as a whole. That means that
         * every piece of it has to maintain its constraints based on  definitions.
         * For example if the description of the field "_bulbs" in the "Bridge" class states that
         * it can't be null then by adding null to it the system will break because of assert or exception.
         *
         * Which is good!
         *
         * Bad thing is when you add null or any other value that is prohibited and the system will
         * say yeah that's cool lets have -1 for the brightness.  So you are looking for
         * the ways around those safety triggers.
         * While testing keep track of some methods that you think that will be useful for us.
         *
         *
         * CLASSES TO TEST:
         *      1. Bridge
         *      2. Group
         *      3. HueElement
         *      4. Lightbulb
         *      5. Theme
         *      6. Trait
         *      7. MultiMap (under utilities.definitions)
         *
         * PROCEDURE:
         *      1. Create instances of testing classes and fill up with data.
         *      2. Try out each method in the class.
         *      3. Try to break the system's integrity.
         *
         *
         * To leave me a comment about whatever, use this annotation:
         * @Gulenko(description = "Your feedback") see example bellow
         *
         * If you want you can write in each class a method toString that will return a String
         * representation of the class. That would be helpful to view the values of the data.
         * e.g. you can call System.out.print(bridge.toString());
         * I think I covered everything, but let me know
         */

        //CREATING BRIDGE EXAMPLE

        Bridge bridge = new Bridge(1,"Bridge", "Factory Bridge");
        bridge.addBulb(new Lightbulb("BULB", "MY BULB", Trait.Default_Trait, Lightbulb.States.OFF));

        //ANNOTATION EXAMPLE
        //NOTE: You can do that on any methods field etc. as well.
        @Gulenko(description = "Your comment goes here")
        int i = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
