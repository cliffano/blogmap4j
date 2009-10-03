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

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import com.cliffano.blogmap4j.exception.BlogMap4JException;

/**
 * A serviceManager implementation using Commons HttpClient to create the
 * connection and call method services.
 * @author Cliffano Subagio
 */
public class ServiceManagerImpl implements ServiceManager {

    /**
     * The client used to send request and receive response.
     */
    private HttpClient mHttpClient;

    /**
     * Method used to make the connection to BlogMap service.
     */
    private HttpMethod mHttpMethod;

    /**
     * Create a {@link ServiceManagerImpl} instance, initialise HttpClient,
     * HttpMethod, and ServiceManagerHelper.
     */
    public ServiceManagerImpl() {
        mHttpClient = new HttpClient();
        mHttpMethod = new GetMethod();
    }

    /**
     * Creates a {@link ServiceManagerImpl} instance with specified HttpClient
     * and HttpMethod.
     * @param httpClient the http client
     * @param httpMethod the http method
     */
    public ServiceManagerImpl(
            final HttpClient httpClient,
            final HttpMethod httpMethod) {
        mHttpClient = httpClient;
        mHttpMethod = httpMethod;
    }

    /**
     * Sets proxy details to HttpClient instance.
     * @param proxyHost proxy host name
     * @param proxyPort proxy port number
     */
    public final void setProxy(final String proxyHost, final int proxyPort) {
        mHttpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);
    }

    /**
     * Sets authenticated proxy details to HttpClient instance.
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
        setProxy(proxyHost, proxyPort);
        mHttpClient.getState().setProxyCredentials(
                new AuthScope(proxyHost, proxyPort, null),
                new UsernamePasswordCredentials(
                        proxyUsername,
                        proxyPassword));
    }

    /**
     * Use HttpClient instance to call method service.
     * @param url the base url which the params will be added to
     * @param params the parameters to be added to the url
     * @return the response String
     */
    public final String execute(final String url, final Map params) {

        try {
            mHttpMethod.setURI(new URI(url, true));
        } catch (URIException urie) {
            throw new BlogMap4JException("Unable to get xml response string "
                    + "due to invalid url: " + url, urie);
        }
        mHttpMethod.setFollowRedirects(true);
        mHttpMethod.setQueryString(convertMapToNameValuePairArray(params));

        String response = null;
        try {
            mHttpClient.executeMethod(mHttpMethod);
            response = mHttpMethod.getResponseBodyAsString();
        } catch (HttpException he) {
            throw new BlogMap4JException(
                    "Unable to get xml response string",
                    he);
        } catch (IOException ioe) {
            throw new BlogMap4JException(
                    "Unable to get xml response string",
                    ioe);
        } finally {
            mHttpMethod.releaseConnection();
        }

        return response;
    }

    /**
     * Converts a Map into an array of NameValuePair. The key value entry within
     * the Map will be converted as name value pair in an array.
     * @param map the Map to convert
     * @return an array of NameValuePair with content from the Map
     */
    private NameValuePair[] convertMapToNameValuePairArray(final Map map) {

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
