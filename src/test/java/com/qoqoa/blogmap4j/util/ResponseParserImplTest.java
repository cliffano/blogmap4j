package com.qoqoa.blogmap4j.util;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.xml.sax.InputSource;

import com.qoqoa.blogmap4j.exception.BlogMap4JException;
import com.qoqoa.blogmap4j.model.Blog;
import com.qoqoa.blogmap4j.model.Response;

public class ResponseParserImplTest extends TestCase {

    private static final String SUCCESS_RESPONSE =
        "<?xml version=\"1.0\" encoding=\"utf-8\"?><rsp stat=\"200\"><map><mapurl>http://somemapurl.com</mapurl></map>"
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
            assertEquals("Unable to parse response string:\n<foobar>(&#($&(*&#@($&@#</foobar>", bme.getMessage());
        }
    }

    public void testParseFailureResponseXmlStringGivesBlogMap4JException() {

        try {
            Response response = parser.parseBlogMap("<rsp stat=\"500\"><err message=\"Input feed does not exist in blogmap service.\"/></rsp>");
            fail("Test with failure xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            assertEquals("Input feed does not exist in blogmap service.", bme.getMessage());
        }
    }

    public void testParseFailureWithDocumentBuilderFactoryThrowingParserConfigurationExceptionGivesBlogMap4JException() throws Exception {

        DocumentBuilderFactory documentBuilderFactory = (DocumentBuilderFactory) EasyMock.createStrictMock(DocumentBuilderFactory.class);
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setCoalescing(true);
        documentBuilderFactory.setNamespaceAware(false);

        EasyMock.expect(documentBuilderFactory.newDocumentBuilder()).andThrow(new ParserConfigurationException("dummy parser configuration error message"));

        EasyMock.replay(new Object[]{documentBuilderFactory});
        try {
            new ResponseParserImpl(documentBuilderFactory);
            fail("BlogMap4JException should've been thrown.");
        } catch (BlogMap4JException bme) {
            assertEquals("Unable to create DocumentBuilder.", bme.getMessage());
        }
        EasyMock.verify(new Object[]{documentBuilderFactory});
    }

    public void testParseFailureWithDocumentBuilderThrowingIOExceptionGivesBlogMap4JException() throws Exception {
        DocumentBuilder documentBuilder = (DocumentBuilder) EasyMock.createStrictMock(DocumentBuilder.class);

        DocumentBuilderFactory documentBuilderFactory = (DocumentBuilderFactory) EasyMock.createStrictMock(DocumentBuilderFactory.class);
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setCoalescing(true);
        documentBuilderFactory.setNamespaceAware(false);

        EasyMock.expect(documentBuilderFactory.newDocumentBuilder()).andReturn(documentBuilder);

        documentBuilder.parse((InputSource) EasyMock.isA(InputSource.class));
        EasyMock.expectLastCall().andThrow(new IOException("dummy IO error message"));

        EasyMock.replay(new Object[]{documentBuilderFactory, documentBuilder});
        parser = new ResponseParserImpl(documentBuilderFactory);
        try {
            parser.parseBlogMap("dummy xml string");
            fail("BlogMap4JException should've been thrown.");
        } catch (BlogMap4JException bme) {
            assertEquals("Unable to parse response string:\ndummy xml string", bme.getMessage());
        }
        EasyMock.verify(new Object[]{documentBuilderFactory, documentBuilder});
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

    public void testParseSuccessXmlResponseWithBlogNotHavingDivValue() {
        Response response = parser.parseBlogMap("<rsp stat=\"200\"><map><mapurl>http://somemapurl.com</mapurl></map><blogs count=\"2\"><blog id=\"1\" name=\"Some Name 1\" xmlurl=\"http://somefeedurl1.com\" url=\"http://someblogurl1.com\" coords=\"109,19,125,35\"><location><latitude>47.6785794635701</latitude><longitude>-122.13084477543</longitude><city>Redmond</city><div/><region>United States</region></location></blog></blogs></rsp>");
        assertEquals(1, response.getBlogs().size());
        Blog blog = (Blog) response.getBlogs().get(0);
        assertNull(blog.getLocation().getDiv());
    }
}
