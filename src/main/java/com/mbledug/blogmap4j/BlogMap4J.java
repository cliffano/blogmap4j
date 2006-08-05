/**
 * Copyright (c) 2005, Cliffano Subagio
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of Mbledug nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.mbledug.blogmap4j;

import java.util.HashMap;
import java.util.Map;

import com.mbledug.blogmap4j.exception.BlogMap4JException;
import com.mbledug.blogmap4j.model.Response;
import com.mbledug.blogmap4j.util.ResponseParser;
import com.mbledug.blogmap4j.util.ResponseParserImpl;
import com.mbledug.blogmap4j.util.ServiceManager;
import com.mbledug.blogmap4j.util.ServiceManagerImpl;

/**
 * Java API for interacting with BlogMap Service APIs.
 * More information at http://www.feedmap.net/BlogMap/Services/ .
 * <p>
 * Usage: simply create an instance of BlogMap4J. For example:
 *   <pre>
 *     BlogMap4J blogMap4J = new BlogMap4J();
 *     Response getBlogMapResponse = blogMap4J.getBlogMap(
 *             "http://www.csthota.com/blog/rss.aspx", null, null);
 *     Response searchResponseWithPlace = blogMap4J.search("redmond,wa",
 *             null, null, null, null, null, null);
 *     Response searchResponseWithLatLonCount = blogMap4J.search(null,
 *             new Double(47), new Double(-122), null, new Integer(10),
 *             null, null);
 *   </pre>
 * </p>
 * @author Cliffano Subagio
 */
public class BlogMap4J {

    /**
     * The REST API service end point.
     */
    private static final String SERVICE_END_POINT =
            "http://www.feedmap.net/blogmap/services/rest.ashx";
    /**
     * The method value to get blog map.
     */
    private static final String METHOD_GET_BLOG_MAP = "blogmap.getblogmap";
    /**
     * The method value to do a search.
     */
    private static final String METHOD_SEARCH = "blogmap.search";

    /**
     * ServiceManager to manage method calls.
     */
    private ServiceManager mServiceManager;

    /**
     * The parser used to parse the XML response.
     */
    private ResponseParser mResponseParser;

    /**
     * Create an instance of BlogMap4J.
     */
    public BlogMap4J() {
        mServiceManager = new ServiceManagerImpl();
        mResponseParser = new ResponseParserImpl();
    }

    /**
     * Sets proxy details.
     * @param proxyHost proxy host name
     * @param proxyPort proxy port number
     */
    public final void setProxy(final String proxyHost, final int proxyPort) {
        mServiceManager.setProxy(proxyHost, proxyPort);
    }

    /**
     * Sets authenticated proxy details.
     * @param proxyHost proxy host name
     * @param proxyPort proxy port number
     * @param proxyUsername proxy username
     * @param proxyPassword proxy password
     */
    public final void setProxy(
            final String proxyHost,
            final int proxyPort,
            final String proxyUsername,
            final String proxyPassword) {
        mServiceManager.setProxy(
                proxyHost, proxyPort, proxyUsername, proxyPassword);
    }

    /**
     * Retrieve map url and blog details.
     * @param feed feed url that is registered with BlogMap
     * @param width width of the map image
     * @param height height of the map image
     * @return the response containing the map url and blog details
     * @throws BlogMap4JException when there's a problem with retrieving the xml
     *             response from BlogMap service, with parsing the xml response,
     *             or there's a failure response from BlogMap service
     */
    public final Response getBlogMap(
            final String feed,
            final Integer width,
            final Integer height)
            throws BlogMap4JException {

        Map params = new HashMap();
        addKeyValueToMap(params, "method", METHOD_GET_BLOG_MAP);
        addKeyValueToMap(params, "feed", feed);
        addKeyValueToMap(params, "width", width);
        addKeyValueToMap(params, "height", height);

        String responseString = mServiceManager.execute(
                SERVICE_END_POINT, params);
        return mResponseParser.parseBlogMap(responseString);
    }

    /**
     * Search for blog details around a place/coordinate.
     * When both place and lat/lon are passed, place is considered for
     * searching.
     * Default distance is 50 Kilometer.
     * Maximum number of blogs returned per request are 100.
     * Default height/width are 400/600 pixels.
     * @param place input place as a string, around which blogs are searched for
     * @param lat input latitude, around which blogs are searched for
     * @param lon input longitude, around which blogs are searched for
     * @param distance distance (in Kilometer) from the center of the input
     *             place; all blogs within this distance are returned
     * @param count number of blogs you want the search to return
     * @param width width of the returned map image
     * @param height height of the returned map image
     * @return the response containing the map url and blog details of the
     *             search result
     * @throws BlogMap4JException when there's a problem with retrieving the xml
     *             response from BlogMap service, with parsing the xml response,
     *             or there's a failure response from BlogMap service
     */
    public final Response search(
            final String place,
            final Double lat,
            final Double lon,
            final Double distance,
            final Integer count,
            final Integer width,
            final Integer height) throws BlogMap4JException {

        Map params = new HashMap();
        addKeyValueToMap(params, "method", METHOD_SEARCH);
        addKeyValueToMap(params, "place", place);
        addKeyValueToMap(params, "lat", lat);
        addKeyValueToMap(params, "lon", lon);
        addKeyValueToMap(params, "distance", distance);
        addKeyValueToMap(params, "count", count);
        addKeyValueToMap(params, "width", width);
        addKeyValueToMap(params, "height", height);

        String responseString = mServiceManager.execute(
                SERVICE_END_POINT, params);
        return mResponseParser.parseBlogMap(responseString);
    }

    /**
     * Adds a key value entry to a map only when the value is not null.
     * @param map the map to add to
     * @param key the key of the entry to add
     * @param value the value of the entry to add
     */
    private void addKeyValueToMap(
            final Map map, final Object key, final Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }
}
