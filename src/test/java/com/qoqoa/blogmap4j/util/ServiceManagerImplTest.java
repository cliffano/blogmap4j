package com.qoqoa.blogmap4j.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

import com.qoqoa.blogmap4j.exception.BlogMap4JException;

public class ServiceManagerImplTest extends TestCase {

    private IMocksControl control;
    private IMocksControl control1;
    private HttpClient httpClient;
    private HttpMethod httpMethod;
    private BlogMap4JHelper helper;
    private ServiceManagerImpl serviceManagerImpl;
    private Map params;

    protected void setUp() {

        control = EasyMock.createStrictControl();
        control1 = EasyMock.createNiceControl();
        httpClient = (HttpClient) control.createMock(HttpClient.class);
        httpMethod = (HttpMethod) control.createMock(HttpMethod.class);
        helper = (BlogMap4JHelper) control1.createMock(BlogMap4JHelper.class);
        serviceManagerImpl = new ServiceManagerImpl(httpClient, httpMethod, helper);
        params = new HashMap();
    }

    public void testExecuteFailureWithInvalidServiceUrlGivesBlogMap4JException() {
        serviceManagerImpl = new ServiceManagerImpl();
        try {
            serviceManagerImpl.execute("http://@*&^#!&*^$&$", new HashMap());
            fail("Test with invalid service url should have failed at this point.");
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testExecuteSuccess() throws Exception {
        params.put("method", "some.method");
        params.put("feed", "http://somefeedurl.com");

        httpMethod.setURI(new URI("http://someserviceurl.com", true));
        httpMethod.setFollowRedirects(true);
        NameValuePair[] nameValuePairs = new BlogMap4JHelper().convertMapToNameValuePairArray(params);
        helper.convertMapToNameValuePairArray(params);
        helper.equals(null);
        control1.andReturn(nameValuePairs);
        httpMethod.setQueryString(nameValuePairs);
        httpClient.executeMethod(httpMethod);
        control.andReturn(new Integer(1));
        httpMethod.getResponseBodyAsString();
        control.andReturn("some response");
        httpMethod.releaseConnection();

        control.replay();
        control1.replay();

        String response = serviceManagerImpl.execute("http://someserviceurl.com", params);
        assertEquals("some response", response);

        control.verify();
        control1.verify();
    }

    public void testExecuteFailureWithHttpClientThrowingHttpExceptionGivesBlogMap4JException() throws Exception {
        testExecuteFailureWithExecuteMethodThrowingException(new HttpException());
    }

    public void testExecuteFailureWithHttpClientThrowingIoExceptionGivesBlogMap4JException() throws Exception {
        testExecuteFailureWithExecuteMethodThrowingException(new IOException());
    }

    private void testExecuteFailureWithExecuteMethodThrowingException(Throwable cause) throws Exception {
        httpMethod.setURI(new URI("http://someserviceurl.com", true));
        httpMethod.setFollowRedirects(true);
        NameValuePair[] nameValuePairs = new BlogMap4JHelper().convertMapToNameValuePairArray(params);
        helper.convertMapToNameValuePairArray(params);
        control.andReturn(nameValuePairs);
        httpMethod.setQueryString(nameValuePairs);
        httpClient.executeMethod(httpMethod);
        control.andThrow(cause);
        httpMethod.releaseConnection();

        control.replay();

        try {
            serviceManagerImpl.execute("http://someserviceurl.com", params);
            fail("Test should have failed at this point.");
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }

        control.verify();
    }

    public void testExecuteSuccessWithProxy() throws Exception {

        HostConfiguration hostConfiguration = (HostConfiguration) control.createMock(HostConfiguration.class);

        params.put("method", "some.method");
        params.put("feed", "http://somefeedurl.com");

        httpClient.getHostConfiguration();
        control.andReturn(hostConfiguration);
        hostConfiguration.setProxy("some host", 80);
        httpMethod.setURI(new URI("http://someserviceurl.com", true));
        httpMethod.setFollowRedirects(true);
        helper.convertMapToNameValuePairArray(params);
        NameValuePair[] nameValuePairs = new BlogMap4JHelper().convertMapToNameValuePairArray(params);
        control.andReturn(nameValuePairs);
        httpMethod.setQueryString(nameValuePairs);
        httpClient.executeMethod(httpMethod);
        control.andReturn(new Integer(1));
        httpMethod.getResponseBodyAsString();
        control.andReturn("some response");
        httpMethod.releaseConnection();

        control.replay();

        serviceManagerImpl.setProxy("some host", 80);
        String response = serviceManagerImpl.execute("http://someserviceurl.com", params);
        assertEquals("some response", response);

        control.verify();
    }
}
