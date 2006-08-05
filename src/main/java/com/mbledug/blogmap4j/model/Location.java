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
package com.mbledug.blogmap4j.model;

/**
 * Representation of a location.
 * @author Cliffano Subagio
 */
public class Location {

    /**
     * The latitude value.
     */
    private double mLatitude;
    /**
     * The longitude value.
     */
    private double mLongitude;
    /**
     * The name of the city.
     */
    private String mCity;
    /**
     * The name of the state.
     */
    private String mDiv;
    /**
     * The name of the country.
     */
    private String mRegion;

    /**
     * Create a Location instance.
     * @param latitude the latitude
     * @param longitude the longitude
     * @param city the name of the city
     * @param div the name of the state
     * @param region the name of the country
     */
    public Location(
            final double latitude,
            final double longitude,
            final String city,
            final String div,
            final String region) {
        mLatitude = latitude;
        mLongitude = longitude;
        mCity = city;
        mDiv = div;
        mRegion = region;
    }

    /**
     * @return Returns the city.
     */
    public final String getCity() {
        return mCity;
    }
    /**
     * @return Returns the div.
     */
    public final String getDiv() {
        return mDiv;
    }
    /**
     * @return Returns the latitude.
     */
    public final double getLatitude() {
        return mLatitude;
    }
    /**
     * @return Returns the longitude.
     */
    public final double getLongitude() {
        return mLongitude;
    }
    /**
     * @return Returns the region.
     */
    public final String getRegion() {
        return mRegion;
    }
}
