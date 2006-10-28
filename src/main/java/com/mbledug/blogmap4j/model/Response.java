/**
 * Copyright (c) 2005-2006, Cliffano Subagio
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

import java.util.List;

/**
 * Representation of BlogMap service response, which consists of a map url and
 * blog details.
 * @author Cliffano Subagio
 */
public class Response {

    /**
     * The String url of the generated map.
     */
    private String mMapUrl;
    /**
     * A list of Blogs included within the map.
     */
    private List mBlogs;

    /**
     * Create a {@link Response} instance.
     * @param mapUrl the url of the map
     * @param blogs the list of Blogs in the map
     */
    public Response(final String mapUrl, final List blogs) {
        mMapUrl = mapUrl;
        mBlogs = blogs;
    }

    /**
     * Returns the list of blogs.
     * @return the list of blogs
     */
    public final List getBlogs() {
        return mBlogs;
    }
    /**
     * Returns the mapUrl.
     * @return the mapUrl
     */
    public final String getMapUrl() {
        return mMapUrl;
    }
}
