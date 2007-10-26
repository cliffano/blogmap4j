package com.qoqoa.blogmap4j.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.easymock.classextension.EasyMock;

import com.qoqoa.blogmap4j.exception.BlogMap4JException;

public class ServiceManagerImplTest extends TestCase {

    private HttpClient httpClient;
    private HttpMethod httpMethod;
    private ServiceManager serviceManager;
    private Map params;

    protected void setUp() {

        httpClient = (HttpClient) EasyMock.createStrictMock(HttpClient.class);
        httpMethod = (HttpMethod) EasyMock.createStrictMock(HttpMethod.class);
        serviceManager = new ServiceManagerImpl(httpClient, httpMethod);
        params = new LinkedHashMap();
    }

    public void testExecuteFailureWithInvalidServiceUrlGivesBlogMap4JException() {
        serviceManager = new ServiceManagerImpl();
        try {
            serviceManager.execute("http://@*&^#!&*^$&$", new HashMap());
            fail("Test with invalid service url should have failed at this point.");
        } catch (BlogMap4JException bme) {
            assertEquals("Unable to get xml response string", bme.getMessage());
        }
    }

    public void testExecuteSuccess() throws Exception {
        params.put("method", "some.method");
        params.put("feed", "http://somefeedurl.com");

        httpMethod.setURI(new URI("http://someserviceurl.com", true));
        httpMethod.setFollowRedirects(true);
        NameValuePair[] nameValuePairs = new NameValuePair[]{new NameValuePair("method", "some.method"), new NameValuePair("feed", "http://somefeedurl.com")};
        httpMethod.setQueryString((NameValuePair[]) EasyMock.aryEq(nameValuePairs));
        EasyMock.expect(new Integer(httpClient.executeMethod(httpMethod))).andReturn(new Integer(1));
        EasyMock.expect(httpMethod.getResponseBodyAsString()).andReturn("some response");
        httpMethod.releaseConnection();

        EasyMock.replay(new Object[]{httpClient, httpMethod});

        String response = serviceManager.execute("http://someserviceurl.com", params);
        assertEquals("some response", response);

        EasyMock.verify(new Object[]{httpClient, httpMethod});
    }

    public void testExecuteFailureWithInvalidUrlGivesBlogMap4JException() {

        try {
            serviceManager.execute("#&*^&@$#*^#", params);
            fail("Test should have failed at this point.");
        } catch (BlogMap4JException bme) {
            assertEquals("Unable to get xml response string due to invalid url: #&*^&@$#*^#", bme.getMessage());
        }
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
        NameValuePair[] nameValuePairs = new NameValuePair[]{};
        httpMethod.setQueryString((NameValuePair[]) EasyMock.aryEq(nameValuePairs));
        EasyMock.expect(new Integer(httpClient.executeMethod(httpMethod))).andThrow(cause);
        httpMethod.releaseConnection();

        EasyMock.replay(new Object[]{httpClient, httpMethod});

        try {
            serviceManager.execute("http://someserviceurl.com", params);
            fail("Test should have failed at this point.");
        } catch (BlogMap4JException bme) {
            assertEquals("Unable to get xml response string", bme.getMessage());
        }

        EasyMock.verify(new Object[]{httpClient, httpMethod});
    }

    public void testExecuteSuccessWithProxy() throws Exception {

        HostConfiguration hostConfiguration = (HostConfiguration) EasyMock.createStrictMock(HostConfiguration.class);

        params.put("method", "some.method");
        params.put("feed", "http://somefeedurl.com");

        EasyMock.expect(httpClient.getHostConfiguration()).andReturn(hostConfiguration);
        hostConfiguration.setProxy("some host", 80);
        httpMethod.setURI(new URI("http://someserviceurl.com", true));
        httpMethod.setFollowRedirects(true);
        NameValuePair[] nameValuePairs = new NameValuePair[]{new NameValuePair("method", "some.method"), new NameValuePair("feed", "http://somefeedurl.com")};
        httpMethod.setQueryString((NameValuePair[]) EasyMock.aryEq(nameValuePairs));
        EasyMock.expect(new Integer(httpClient.executeMethod(httpMethod))).andReturn(new Integer(1));
        EasyMock.expect(httpMethod.getResponseBodyAsString()).andReturn("some response");
        httpMethod.releaseConnection();

        EasyMock.replay(new Object[]{httpClient, httpMethod, hostConfiguration});

        serviceManager.setProxy("some host", 80);
        String response = serviceManager.execute("http://someserviceurl.com", params);
        assertEquals("some response", response);

        EasyMock.verify(new Object[]{httpClient, httpMethod, hostConfiguration});
    }

    public void testExecuteSuccessWithAuthenticatedProxy() throws Exception {

        HostConfiguration hostConfiguration = (HostConfiguration) EasyMock.createStrictMock(HostConfiguration.class);
        HttpState httpState = (HttpState) EasyMock.createStrictMock(HttpState.class);

        params.put("method", "some.method");
        params.put("feed", "http://somefeedurl.com");

        EasyMock.expect(httpClient.getHostConfiguration()).andReturn(hostConfiguration);
        hostConfiguration.setProxy("some host", 80);
        EasyMock.expect(httpClient.getState()).andReturn(httpState);
        httpState.setCredentials((AuthScope) EasyMock.isA(AuthScope.class), (UsernamePasswordCredentials) EasyMock.isA(UsernamePasswordCredentials.class));
        httpMethod.setURI(new URI("http://someserviceurl.com", true));
        httpMethod.setFollowRedirects(true);
        NameValuePair[] nameValuePairs = new NameValuePair[]{new NameValuePair("method", "some.method"), new NameValuePair("feed", "http://somefeedurl.com")};
        httpMethod.setQueryString((NameValuePair[]) EasyMock.aryEq(nameValuePairs));
        EasyMock.expect(new Integer(httpClient.executeMethod(httpMethod))).andReturn(new Integer(1));
        EasyMock.expect(httpMethod.getResponseBodyAsString()).andReturn("some response");
        httpMethod.releaseConnection();

        EasyMock.replay(new Object[]{httpClient, httpMethod, hostConfiguration});

        serviceManager.setProxy("some host", 80, "someusername", "somepassword");
        String response = serviceManager.execute("http://someserviceurl.com", params);
        assertEquals("some response", response);

        EasyMock.verify(new Object[]{httpClient, httpMethod, hostConfiguration});
    }
}
