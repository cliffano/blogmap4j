package com.qoqoa.blogmap4j;

import java.util.ArrayList;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

import com.qoqoa.blogmap4j.model.Response;
import com.qoqoa.blogmap4j.util.ResponseParser;
import com.qoqoa.blogmap4j.util.ServiceManager;

public class BlogMap4JTest extends TestCase {

    private BlogMap4J blogMap4J;
    private ServiceManager serviceManager;
    private ResponseParser responseParser;

    protected void setUp() {
        serviceManager = (ServiceManager) EasyMock.createStrictMock(ServiceManager.class);
        responseParser = (ResponseParser) EasyMock.createStrictMock(ResponseParser.class);
    }

    public void testGetBlogMapViaMockService() {
        blogMap4J = new BlogMap4J(serviceManager, responseParser);
        ArrayList blogs = new ArrayList();

        EasyMock.expect(serviceManager.execute((String) EasyMock.eq("http://www.feedmap.net/blogmap/services/rest.ashx"), (Map) EasyMock.isA(Map.class))).andReturn("some response");
        EasyMock.expect(responseParser.parseBlogMap("some response")).andReturn(new Response("http://somemapurl.com", blogs));

        EasyMock.replay(new Object[]{serviceManager, responseParser});

        Response response = blogMap4J.getBlogMap("http://somefeedurl", new Integer(80), new Integer(80));
        assertEquals("http://somemapurl.com", response.getMapUrl());
        assertEquals(blogs, response.getBlogs());

        EasyMock.verify(new Object[]{serviceManager, responseParser});
    }

    public void testSearchViaMockService() {
        blogMap4J = new BlogMap4J(serviceManager, responseParser);
        ArrayList blogs = new ArrayList();

        EasyMock.expect(serviceManager.execute((String) EasyMock.eq("http://www.feedmap.net/blogmap/services/rest.ashx"), (Map) EasyMock.isA(Map.class))).andReturn("some response");
        EasyMock.expect(responseParser.parseBlogMap("some response")).andReturn(new Response("http://somemapurl.com", blogs));

        EasyMock.replay(new Object[]{serviceManager, responseParser});

        Response response = blogMap4J.search("redmond,wa", null, null, null, null, null, null);
        assertEquals("http://somemapurl.com", response.getMapUrl());
        assertEquals(blogs, response.getBlogs());

        EasyMock.verify(new Object[]{serviceManager, responseParser});
    }

    public void testSearchWithProxyViaMockService() {
        blogMap4J = new BlogMap4J(serviceManager, responseParser);
        ArrayList blogs = new ArrayList();

        serviceManager.setProxy("http://someproxy.com", 8080);
        EasyMock.expect(serviceManager.execute((String) EasyMock.eq("http://www.feedmap.net/blogmap/services/rest.ashx"), (Map) EasyMock.isA(Map.class))).andReturn("some response");
        EasyMock.expect(responseParser.parseBlogMap("some response")).andReturn(new Response("http://somemapurl.com", blogs));

        EasyMock.replay(new Object[]{serviceManager, responseParser});

        blogMap4J.setProxy("http://someproxy.com", 8080);
        Response response = blogMap4J.search("melbourne, australia", null,
                null, new Double(88), new Integer(20), new Integer(100),
                new Integer(999999));
        assertEquals("http://somemapurl.com", response.getMapUrl());
        assertEquals(blogs, response.getBlogs());

        EasyMock.verify(new Object[]{serviceManager, responseParser});
    }

    public void testSearchWithAuthenticatedProxyViaMockService() {
        blogMap4J = new BlogMap4J(serviceManager, responseParser);
        ArrayList blogs = new ArrayList();

        serviceManager.setProxy("http://someproxy.com", 8080, "someusername", "somepassword");
        EasyMock.expect(serviceManager.execute((String) EasyMock.eq("http://www.feedmap.net/blogmap/services/rest.ashx"), (Map) EasyMock.isA(Map.class))).andReturn("some response");
        EasyMock.expect(responseParser.parseBlogMap("some response")).andReturn(new Response("http://somemapurl.com", blogs));

        EasyMock.replay(new Object[]{serviceManager, responseParser});

        blogMap4J.setProxy("http://someproxy.com", 8080, "someusername", "somepassword");
        Response response = blogMap4J.search("melbourne, australia", null,
                null, new Double(88), new Integer(20), new Integer(100),
                new Integer(999999));
        assertEquals("http://somemapurl.com", response.getMapUrl());
        assertEquals(blogs, response.getBlogs());

        EasyMock.verify(new Object[]{serviceManager, responseParser});
    }

// Disabled live service tests (from feedmap.net) .
//
//    public void testGetBlogMapViaLiveService() {
//        blogMap4J = new BlogMap4J();
//        Response response = blogMap4J.getBlogMap(
//                "http://www.csthota.com/blog/rss.aspx", new Integer(80),
//                new Integer(80));
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
//        Response response = blogMap4J.search(null, new Double(47), new Double(
//                -122), null, new Integer(10), null, null);
//        assertResponse(response);
//    }
//
//    public void testGetBlogMapGivesBlogMap4JExceptionViaLiveService() {
//        blogMap4J = new BlogMap4J();
//        try {
//            Response response = blogMap4J.getBlogMap(null, null, null);
//            fail("Test with null feed should have failed at this point. "
//                    + "Unexpected response: " + response);
//        }
//        catch (BlogMap4JException bme) {
//            assertEquals("Invalid input feed.", bme.getMessage());
//        }
//    }
//
//    public void testSearchGivesBlogMap4JExceptionViaLiveService() {
//        blogMap4J = new BlogMap4J();
//        try {
//            Response response = blogMap4J.search("melbourne, australia", null,
//                    null, new Double(88), new Integer(20), new Integer(100),
//                    new Integer(999999));
//            fail("Test with invalid height should have failed at this point. "
//                    + "Unexpected response: " + response);
//        }
//        catch (BlogMap4JException bme) {
//            assertEquals("Input string was not in a correct format.", bme.getMessage());
//        }
//    }
//
//    private void assertResponse(Response response) {
//
//        List blogs = response.getBlogs();
//        for (Iterator it = blogs.iterator(); it.hasNext();) {
//
//            Blog blog = (Blog)it.next();
//            assertNotNull(blog);
//
//            Coordinate coordinate = blog.getCoordinate();
//            assertNotNull(coordinate);
//
//            Point start = coordinate.getStart();
//            Point end = coordinate.getEnd();
//            assertNotNull(start);
//            assertNotNull(end);
//
//            Location location = blog.getLocation();
//            assertNotNull(location);
//        }
//    }
}
