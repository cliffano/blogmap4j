package com.qoqoa.blogmap4j.util;

import java.util.List;

import junit.framework.TestCase;

import com.qoqoa.blogmap4j.exception.BlogMap4JException;
import com.qoqoa.blogmap4j.model.Blog;
import com.qoqoa.blogmap4j.model.Response;

public class ResponseParserImplTest extends TestCase {

    private static final String SUCCESS_RESPONSE =
        "<rsp stat=\"200\"><map><mapurl>http://somemapurl.com</mapurl></map>"
        + "<blogs count=\"2\">"
        + "<blog id=\"1\" name=\"Some Name 1\" xmlurl=\"http://somefeedurl1.com\" url=\"http://someblogurl1.com\" coords=\"109,19,125,35\"><location><latitude>47.6785794635701</latitude><longitude>-122.13084477543</longitude><city>Redmond</city><div>Washington</div><region>United States</region></location></blog>"
        + "<blog id=\"2\" name=\"Some Name 2\" xmlurl=\"http://somefeedurl2.com\" url=\"http://someblogurl2.com\" coords=\"29,69,115,38\"><location><latitude>43.35701</latitude><longitude>-12.13043</longitude><city>Carnegie</city><div>Melbourne</div><region>Australia</region></location></blog>"
        + "</blogs></rsp>";

    private ResponseParser parser;

    protected void setUp() {
        parser = new ResponseParserImpl();
    }

    public void testParseInvalidResponseXmlStringGivesBlogMap4JException() {

        try {
            Response response = parser.parseBlogMap("<foobar>(&#($&(*&#@($&@#</foobar>");
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testParseFailureResponseXmlStringGivesBlogMap4JException() {

        try {
            Response response = parser.parseBlogMap("<rsp stat=\"500\"><err message=\"Input feed does not exist in blogmap service.\"/></rsp>");
            fail("Test with failure xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testParseSuccessXmlResponse() {
        Response response = parser.parseBlogMap(SUCCESS_RESPONSE);
        assertEquals("http://somemapurl.com", response.getMapUrl());
        List blogs = response.getBlogs();
        assertEquals(2, blogs.size());

        Blog blog1 = (Blog) blogs.get(0);
        assertEquals(1, blog1.getId());
        assertEquals("Some Name 1", blog1.getName());
        assertEquals("http://somefeedurl1.com", blog1.getXmlUrl());
        assertEquals("http://someblogurl1.com", blog1.getUrl());
        assertEquals(109, blog1.getCoordinate().getStart().getX());
        assertEquals(19, blog1.getCoordinate().getStart().getY());
        assertEquals(125, blog1.getCoordinate().getEnd().getX());
        assertEquals(35, blog1.getCoordinate().getEnd().getY());
        assertEquals(47.6785794635701, blog1.getLocation().getLatitude(), 0);
        assertEquals(-122.13084477543, blog1.getLocation().getLongitude(), 0);
        assertEquals("Redmond", blog1.getLocation().getCity());
        assertEquals("Washington", blog1.getLocation().getDiv());
        assertEquals("United States", blog1.getLocation().getRegion());

        Blog blog2 = (Blog) blogs.get(1);
        assertEquals(2, blog2.getId());
        assertEquals("Some Name 2", blog2.getName());
        assertEquals("http://somefeedurl2.com", blog2.getXmlUrl());
        assertEquals("http://someblogurl2.com", blog2.getUrl());
        assertEquals(29, blog2.getCoordinate().getStart().getX());
        assertEquals(69, blog2.getCoordinate().getStart().getY());
        assertEquals(115, blog2.getCoordinate().getEnd().getX());
        assertEquals(38, blog2.getCoordinate().getEnd().getY());
        assertEquals(43.35701, blog2.getLocation().getLatitude(), 0);
        assertEquals(-12.13043, blog2.getLocation().getLongitude(), 0);
        assertEquals("Carnegie", blog2.getLocation().getCity());
        assertEquals("Melbourne", blog2.getLocation().getDiv());
        assertEquals("Australia", blog2.getLocation().getRegion());
    }

    public void testParseSuccessResponseXmlStringPrependedEmptySpaces() {
        Response response = parser.parseBlogMap("   " + SUCCESS_RESPONSE);
        assertEquals(2, response.getBlogs().size());
    }

    public void testParseSuccessXmlResponseWithoutAnyBlogs() {
        Response response = parser.parseBlogMap("<rsp stat=\"200\"><map><mapurl>http://somemapurl.com</mapurl></map><blogs count=\"2\"></blogs></rsp>");
        assertEquals(0, response.getBlogs().size());
    }
}
