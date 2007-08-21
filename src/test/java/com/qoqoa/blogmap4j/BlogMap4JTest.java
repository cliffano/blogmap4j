package com.qoqoa.blogmap4j;

import com.qoqoa.blogmap4j.exception.BlogMap4JException;
import com.qoqoa.blogmap4j.model.Response;
import com.qoqoa.blogmap4j.util.DataFixture;
import com.qoqoa.blogmap4j.util.ResponseParser;
import com.qoqoa.blogmap4j.util.ServiceManager;

public class BlogMap4JTest extends BaseTest {

    private BlogMap4J mBlogMap4J;
    private ServiceManager mServiceManager;
    private ResponseParser mResponseParser;
    private DataFixture mDataFixture;

    protected void setUp() {
        mDataFixture = new DataFixture();
        mResponseParser = mDataFixture.createMockResponseParser();
    }

    public void testGetBlogMapViaLiveService() {
        mBlogMap4J = new BlogMap4J();
        Response response = mBlogMap4J.getBlogMap(
                    "http://www.csthota.com/blog/rss.aspx", new Integer(80), new Integer(80));
        assertResponse(response);
    }

    public void testSearchByPlaceViaLiveService() {
        mBlogMap4J = new BlogMap4J();
        Response response = mBlogMap4J.search("redmond,wa", null, null, null,
                null, null, null);
        assertResponse(response);
    }

    public void testSearchByCoordinateViaLiveService() {
        mBlogMap4J = new BlogMap4J();
        Response response = mBlogMap4J.search(null, new Double(47),
                new Double(-122), null, new Integer(10), null, null);
        assertResponse(response);
    }

    public void testGetBlogMapGivesBlogMap4JExceptionViaLiveService() {
        mBlogMap4J = new BlogMap4J();
        try {
            Response response = mBlogMap4J.getBlogMap(null, null, null);
            fail("Test with null feed should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testSearchGivesBlogMap4JExceptionViaLiveService() {
        mBlogMap4J = new BlogMap4J();
        try {
            Response response = mBlogMap4J.search("melbourne, australia", null,
                    null, new Double(88), new Integer(20), new Integer(100),
                    new Integer(999999));
            fail("Test with invalid height should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testGetBlogMapViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(false);
        mBlogMap4J = new BlogMap4J(mServiceManager, mResponseParser);
        Response response = mBlogMap4J.getBlogMap(
                    "http://www.csthota.com/blog/rss.aspx", new Integer(80), new Integer(80));
        assertResponse(response);
    }

    public void testSearchViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(false);
        mBlogMap4J = new BlogMap4J(mServiceManager, mResponseParser);
        Response response = mBlogMap4J.search("redmond,wa", null, null, null,
                null, null, null);
        assertResponse(response);
    }

    public void testSearchWithProxyViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(true);
        mBlogMap4J = new BlogMap4J(mServiceManager, mResponseParser);
        mBlogMap4J.setProxy(DataFixture.DUMMY_PROXY_HOST, DataFixture.DUMMY_PROXY_PORT);
        Response response = mBlogMap4J.search("melbourne, australia", null,
                null, new Double(88), new Integer(20), new Integer(100),
                new Integer(999999));
        assertResponse(response);
    }

    public void testSearchWithAuthenticatedProxyViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(true);
        mBlogMap4J = new BlogMap4J(mServiceManager, mResponseParser);
        mBlogMap4J.setProxy(DataFixture.DUMMY_PROXY_HOST, DataFixture.DUMMY_PROXY_PORT, DataFixture.DUMMY_PROXY_USERNAME, DataFixture.DUMMY_PROXY_PASSWORD);
        Response response = mBlogMap4J.search("melbourne, australia", null,
                null, new Double(88), new Integer(20), new Integer(100),
                new Integer(999999));
        assertResponse(response);
    }
}
