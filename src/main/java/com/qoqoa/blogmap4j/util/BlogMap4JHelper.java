package com.qoqoa.blogmap4j.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

/**
 * Provides helper methods for BlogMap4J.
 * @author Cliffano Subagio
 */
public class BlogMap4JHelper {

    /**
     * Adds a key value entry to a map only when the value is not null.
     * @param map the map to add to
     * @param key the key of the entry to add
     * @param value the value of the entry to add
     */
    public final void addKeyValueToMap(
            final Map map, final Object key, final Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    /**
     * Converts a Map into an array of NameValuePair. The key value entry within
     * the Map will be converted as name value pair in an array.
     * @param map the Map to convert
     * @return an array of NameValuePair with content from the Map
     */
    public final NameValuePair[] convertMapToNameValuePairArray(final Map map) {

        NameValuePair[] array = new NameValuePair[map.size()];
        int i = 0;
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String name = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            array[i++] = new NameValuePair(name, value);
        }
        return array;
    }
}
