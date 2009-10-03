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
package com.cliffano.blogmap4j.util;

import java.util.Map;

/**
 * {@link ServiceManager} manages method call to service provider (i.e.
 * BlogMap).
 * @author Cliffano Subagio
 */
public interface ServiceManager {

    /**
     * Sets proxy details.
     * @param proxyHost proxy host name
     * @param proxyPort proxy port number
     */
    void setProxy(final String proxyHost, final int proxyPort);

    /**
     * Sets authenticated proxy details.
     * @param proxyHost proxy host name
     * @param proxyPort proxy port number
     * @param proxyUsername proxy username
     * @param proxyPassword proxy password
     */
    void setProxy(
            final String proxyHost,
            final int proxyPort,
            final String proxyUsername,
            final String proxyPassword);
    /**
     * Create a URL connection and retrieve the response String.
     * @param url the base url which the params will be added to
     * @param params the parameters to be added to the url
     * @return the response String
     */
    String execute(final String url, final Map params);
}
