/**
 * Copyright (c) 2005-2007, Cliffano Subagio
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
 *   * Neither the name of Studio Cliffano nor the names of its contributors
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
package com.cliffano.blogmap4j.model;

/**
 * Representation of a blog.
 * @author Cliffano Subagio
 */
public class Blog {

    /**
     * The id of the blog, match this with the blog ids on the map.
     */
    private int mId;
    /**
     * The name of the blog.
     */
    private String mName;
    /**
     * The url of the blog's feed.
     */
    private String mXmlUrl;
    /**
     * The url of the blog.
     */
    private String mUrl;
    /**
     * The pixel coordinate of the blog location on the map.
     */
    private Coordinate mCoordinate;
    /**
     * The location of the blog.
     */
    private Location mLocation;

    /**
     * Creates a {@link Blog} instance.
     * @param id the id of the blog
     * @param name the name of the blog
     * @param xmlUrl the url of the blog's feed
     * @param url the url of the blog
     * @param coordinate the pixel coordinate of the blog location on the map
     * @param location the location of the blog
     */
    public Blog(
            final int id,
            final String name,
            final String xmlUrl,
            final String url,
            final Coordinate coordinate,
            final Location location) {
        mId = id;
        mName = name;
        mXmlUrl = xmlUrl;
        mUrl = url;
        mCoordinate = coordinate;
        mLocation = location;
    }

    /**
     * Returns the coordinate.
     * @return the coordinate
     */
    public final Coordinate getCoordinate() {
        return mCoordinate;
    }
    /**
     * Returns the id.
     * @return the id
     */
    public final int getId() {
        return mId;
    }
    /**
     * Returns the location.
     * @return the location
     */
    public final Location getLocation() {
        return mLocation;
    }
    /**
     * Returns the name.
     * @return the name
     */
    public final String getName() {
        return mName;
    }
    /**
     * Returns the url.
     * @return the url
     */
    public final String getUrl() {
        return mUrl;
    }
    /**
     * Returns the xmlUrl.
     * @return the xmlUrl
     */
    public final String getXmlUrl() {
        return mXmlUrl;
    }
}
