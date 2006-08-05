package com.mbledug.blogmap4j;

import java.util.Iterator;
import java.util.List;

import com.mbledug.blogmap4j.exception.BlogMap4JException;
import com.mbledug.blogmap4j.model.Blog;
import com.mbledug.blogmap4j.model.Coordinate;
import com.mbledug.blogmap4j.model.Location;
import com.mbledug.blogmap4j.model.Point;
import com.mbledug.blogmap4j.model.Response;

import junit.framework.TestCase;

public class BlogMap4JTest extends TestCase {

    private static final String INVALID_PROXY_HOST = "http://blahblahblah";
    private static final int DUMMY_PROXY_PORT = 8080;
    private static final String DUMMY_PROXY_USERNAME = "someusername";
    private static final String DUMMY_PROXY_PASSWORD = "somepassword";

    private BlogMap4J mBlogMap4J;

    protected void setUp() {
        mBlogMap4J = new BlogMap4J();
    }

    public void testGetBlogMapSuccess() {

        try {
            Response response = mBlogMap4J.getBlogMap(
                        "http://www.csthota.com/blog/rss.aspx", new Integer(80), new Integer(80));
            assertResponse(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }

    public void testGetBlogMapFailure() {

        try {
            Response response = mBlogMap4J.getBlogMap(null, null, null);
            fail("Test with null feed should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testSearchSuccessWithPlace() {

        try {
            Response response = mBlogMap4J.search("redmond,wa", null, null, null,
                    null, null, null);
            assertResponse(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }

    public void testSearchSuccessWithLatLonCount() {

        try {
            Response response = mBlogMap4J.search(null, new Double(47),
                    new Double(-122), null, new Integer(10), null, null);
            assertResponse(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }

    public void testSearchFailure() {

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

    public void testProxyFailure() {

        try {
            mBlogMap4J.setProxy(INVALID_PROXY_HOST, DUMMY_PROXY_PORT);
            Response response = mBlogMap4J.search("melbourne, australia", null,
                    null, new Double(88), new Integer(20), new Integer(100),
                    new Integer(999999));
            fail("Test with invalid proxy should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testAuthenticatedProxyFailure() {

        try {
            mBlogMap4J.setProxy(
                    INVALID_PROXY_HOST, DUMMY_PROXY_PORT,
                    DUMMY_PROXY_USERNAME, DUMMY_PROXY_PASSWORD);
            Response response = mBlogMap4J.search("melbourne, australia", null,
                    null, new Double(88), new Integer(20), new Integer(100),
                    new Integer(999999));
            fail("Test with invalid authenticated proxy should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

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
