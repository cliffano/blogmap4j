package com.mbledug.blogmap4j.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;

import com.mbledug.blogmap4j.exception.BlogMap4JException;

public class ServiceManagerTest extends TestCase {

    private ServiceManager mServiceManager;
    private Map mParams;

    private DataFixture mDataFixture;
    private HttpClient mMockHttpClient;
    private HttpMethod mMockHttpMethod;

    protected void setUp() {
        mDataFixture = new DataFixture();
        mParams = new HashMap();
    }

    public void testExecuteSuccessWithNonEmptyParams() {
        mParams.put("method", DataFixture.METHOD_GET_BLOG_MAP);
        mParams.put("feed", DataFixture.FEED);
        mMockHttpClient = mDataFixture.createMockHttpClient(false, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            String response = mServiceManager.execute(DataFixture.VALID_SERVICE_URL, mParams);
            assertNotNull(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }

    public void testExecuteSuccessWithEmptyParams() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            String response = mServiceManager.execute(DataFixture.VALID_SERVICE_URL, mParams);
            assertNotNull(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }

    public void testExecuteFailureWithInvalidServiceUrlGivesBlogMap4JException() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.INVALID_SERVICE_URL);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            mServiceManager.execute(DataFixture.INVALID_SERVICE_URL, mParams);
            fail("Test with invalid service url should have failed at this point.");
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testExecuteFailureWithHttpClientThrowingHttpExceptionGivesBlogMap4JException() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, new HttpException());
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            mServiceManager.execute(DataFixture.VALID_SERVICE_URL, mParams);
            fail("Test with invalid service url should have failed at this point.");
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testExecuteFailureWithHttpClientThrowingIoExceptionGivesBlogMap4JException() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, new IOException());
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            mServiceManager.execute(DataFixture.VALID_SERVICE_URL, mParams);
            fail("Test with invalid service url should have failed at this point.");
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testExecuteSuccessWithProxy() {
        mMockHttpClient = mDataFixture.createMockHttpClient(true, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);

        try {
            mServiceManager.setProxy(DataFixture.DUMMY_PROXY_HOST, DataFixture.DUMMY_PROXY_PORT, DataFixture.DUMMY_PROXY_USERNAME, DataFixture.DUMMY_PROXY_PASSWORD);
            String response = mServiceManager.execute(DataFixture.VALID_SERVICE_URL, mParams);
            assertNotNull(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }
}
