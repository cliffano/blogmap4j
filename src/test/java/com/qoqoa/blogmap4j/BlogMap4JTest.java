package com.qoqoa.blogmap4j;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

import junit.framework.TestCase;

import com.qoqoa.blogmap4j.exception.BlogMap4JException;
import com.qoqoa.blogmap4j.model.Blog;
import com.qoqoa.blogmap4j.model.Coordinate;
import com.qoqoa.blogmap4j.model.Location;
import com.qoqoa.blogmap4j.model.Point;
import com.qoqoa.blogmap4j.model.Response;
import com.qoqoa.blogmap4j.util.DataFixture;
import com.qoqoa.blogmap4j.util.ResponseParser;
import com.qoqoa.blogmap4j.util.ServiceManager;

public class BlogMap4JTest extends TestCase {

    private BlogMap4J blogMap4J;
    private IMocksControl control;
    private ServiceManager serviceManager;
    private ResponseParser responseParser;

    protected void setUp() {
        control = EasyMock.createStrictControl();
        serviceManager = (ServiceManager) control.createMock(ServiceManager.class);
        responseParser = (ResponseParser) control.createMock(ResponseParser.class);
    }

//    public void testGetBlogMapViaLiveService() {
//        blogMap4J = new BlogMap4J();
//        Response response = blogMap4J.getBlogMap(
//                    "http://www.csthota.com/blog/rss.aspx", new Integer(80), new Integer(80));
//        assertResponse(response);
//    }
//
//    public void testSearchByPlaceViaLiveService() {
//        blogMap4J = new BlogMap4J();
//        Response response = blogMap4J.search("redmond,wa", null, null, null,
//                null, null, null);
//        assertResponse(response);
//    }
//
//    public void testSearchByCoordinateViaLiveService() {
//        blogMap4J = new BlogMap4J();
//        Response response = blogMap4J.search(null, new Double(47),
//                new Double(-122), null, new Integer(10), null, null);
//        assertResponse(response);
//    }
//
//    public void testGetBlogMapGivesBlogMap4JExceptionViaLiveService() {
//        blogMap4J = new BlogMap4J();
//        try {
//            Response response = blogMap4J.getBlogMap(null, null, null);
//            fail("Test with null feed should have failed at this point. " +
//                    "Unexpected response: " + response);
//        } catch (BlogMap4JException bme) {
//            // BlogMap4JException is thrown as expected
//        }
//    }
//
//    public void testSearchGivesBlogMap4JExceptionViaLiveService() {
//        blogMap4J = new BlogMap4J();
//        try {
//            Response response = blogMap4J.search("melbourne, australia", null,
//                    null, new Double(88), new Integer(20), new Integer(100),
//                    new Integer(999999));
//            fail("Test with invalid height should have failed at this point. " +
//                    "Unexpected response: " + response);
//        } catch (BlogMap4JException bme) {
//            // BlogMap4JException is thrown as expected
//        }
//    }

    public void testGetBlogMapViaMockService() {
        blogMap4J = new BlogMap4J(serviceManager, responseParser);

        serviceManager.execute("http://somefeedurl", null);
        responseParser.parseBlogMap("some response");
        control.andReturn(new Response("blah", null));

        control.replay();

        Response response = blogMap4J.getBlogMap(
                    "http://somefeedurl", new Integer(80), new Integer(80));
        assertResponse(response);

        control.verify();
    }
//
//    public void testSearchViaMockService() {
//        mServiceManager = mDataFixture.createMockServiceManager(false);
//        mBlogMap4J = new BlogMap4J(mServiceManager, mResponseParser);
//        Response response = mBlogMap4J.search("redmond,wa", null, null, null,
//                null, null, null);
//        assertResponse(response);
//    }
//
//    public void testSearchWithProxyViaMockService() {
//        mServiceManager = mDataFixture.createMockServiceManager(true);
//        mBlogMap4J = new BlogMap4J(mServiceManager, mResponseParser);
//        mBlogMap4J.setProxy(DataFixture.DUMMY_PROXY_HOST, DataFixture.DUMMY_PROXY_PORT);
//        Response response = mBlogMap4J.search("melbourne, australia", null,
//                null, new Double(88), new Integer(20), new Integer(100),
//                new Integer(999999));
//        assertResponse(response);
//    }
//
//    public void testSearchWithAuthenticatedProxyViaMockService() {
//        mServiceManager = mDataFixture.createMockServiceManager(true);
//        mBlogMap4J = new BlogMap4J(mServiceManager, mResponseParser);
//        mBlogMap4J.setProxy(DataFixture.DUMMY_PROXY_HOST, DataFixture.DUMMY_PROXY_PORT, DataFixture.DUMMY_PROXY_USERNAME, DataFixture.DUMMY_PROXY_PASSWORD);
//        Response response = mBlogMap4J.search("melbourne, australia", null,
//                null, new Double(88), new Integer(20), new Integer(100),
//                new Integer(999999));
//        assertResponse(response);
//    }

    private void assertResponse(Response response) {

        List blogs = response.getBlogs();
        for (Iterator it = blogs.iterator(); it.hasNext();) {

            Blog blog = (Blog)it.next();
            assertNotNull(blog);

            Coordinate coordinate = blog.getCoordinate();
            assertNotNull(coordinate);

            Point start = coordinate.getStart();
            Point end = coordinate.getEnd();
            assertNotNull(start);
            assertNotNull(end);

            Location location = blog.getLocation();
            assertNotNull(location);
        }
    }
}
