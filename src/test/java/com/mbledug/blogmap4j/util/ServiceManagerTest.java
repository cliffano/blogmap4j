package com.mbledug.blogmap4j.util;

import java.util.HashMap;
import java.util.Map;

import com.mbledug.blogmap4j.exception.BlogMap4JException;
import com.mbledug.blogmap4j.util.ServiceManager;
import com.mbledug.blogmap4j.util.ServiceManagerImpl;

import junit.framework.TestCase;

public class ServiceManagerTest extends TestCase {

    private static final String FEED = "http://www.csthota.com/blog/rss.aspx";
    private static final String METHOD_GET_BLOG_MAP = "blogmap.getblogmap";
    private static final String INVALID_SERVICE_URL = "http://blahblahblah";
    private static final String VALID_SERVICE_URL = "http://www.feedmap.net/blogmap/services/rest.ashx";
    private static final String INVALID_PROXY_HOST = "http://blahblahblah";
    private static final int DUMMY_PROXY_PORT = 8080;
    private static final String DUMMY_PROXY_USERNAME = "someusername";
    private static final String DUMMY_PROXY_PASSWORD = "somepassword";

    private ServiceManager mServiceManager;
    private Map mParams;

    protected void setUp() {
        mServiceManager = new ServiceManagerImpl();

        mParams = new HashMap();
        mParams.put("method", METHOD_GET_BLOG_MAP);
        mParams.put("feed", FEED);
    }

    public void testExecuteSuccess() {
        try {
            String response = mServiceManager.execute(VALID_SERVICE_URL, mParams);
            assertNotNull(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }

    public void testExecuteFailureInvalidServiceUrl() {
        try {
            mServiceManager.execute(INVALID_SERVICE_URL, mParams);
            fail("Test with invalid service url should have failed at this point.");
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testExecuteFailureInvalidProxy() {
        try {
            mServiceManager.setProxy(INVALID_PROXY_HOST, DUMMY_PROXY_PORT);
            mServiceManager.execute(VALID_SERVICE_URL, mParams);
            fail("Test with invalid proxy should have failed at this point.");
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testExecuteFailureInvalidAuthenticatedProxy() {
        try {
            mServiceManager.setProxy(
                    INVALID_PROXY_HOST,
                    DUMMY_PROXY_PORT,
                    DUMMY_PROXY_USERNAME,
                    DUMMY_PROXY_PASSWORD);
            mServiceManager.execute(VALID_SERVICE_URL, mParams);
            fail("Test with invalid authenticated proxy should have failed at this point.");
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }
}
