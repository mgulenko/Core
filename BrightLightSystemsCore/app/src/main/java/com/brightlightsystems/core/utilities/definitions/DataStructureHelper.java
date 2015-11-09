package com.brightlightsystems.core.utilities.definitions;

import com.brightlightsystems.core.datastructure.HueElement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class provides  generic utility methods to manipulate data.
 * Currently methods are using Java 7 functionality. When Android API will start to support
 * Java 8, reimplement old ones as needed.
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
}
