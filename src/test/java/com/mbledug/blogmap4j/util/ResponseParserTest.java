package com.mbledug.blogmap4j.util;

import com.mbledug.blogmap4j.exception.BlogMap4JException;
import com.mbledug.blogmap4j.model.Response;
import com.mbledug.blogmap4j.util.ResponseParser;
import com.mbledug.blogmap4j.util.ResponseParserImpl;

import junit.framework.TestCase;

public class ResponseParserTest extends TestCase {

    private static final String INVALID_XML = "blahblahblah";
    private static final String XML_FAILURE_RESPONSE = "<rsp stat=\"500\"><err message=\"Input feed does not exist in blogmap service.\"/></rsp>";
    private static final String XML_SUCCESS_RESPONSE = "<rsp stat=\"200\"><map><mapurl>http://renderv318.mappoint.net/render-30/getmap.aspx?key=4483BB03CE3FB25C7E21</mapurl></map>" +
            "<blogs count=\"2\"><blog id=\"1\" name=\"Chandu Thota\" xmlurl=\"http://www.csthota.com/blog/rss.aspx\" url=\"http://www.csthota.com/blog/\" coords=\"109,19,125,35\"><location><latitude>47.6785794635701</latitude><longitude>-122.13084477543</longitude><city>Redmond</city><div>Washington</div><region>United States</region></location></blog>" +
            "<blog id=\"2\" name=\"Chandu Thota\" xmlurl=\"http://www.csthota.com/blog/rss.aspx\" url=\"http://www.csthota.com/blog/\" coords=\"109,19,125,35\"><location><latitude>47.6785794635701</latitude><longitude>-122.13084477543</longitude><city>Redmond</city><div>Washington</div><region>United States</region></location></blog></blogs></rsp>";
    private static final String XML_SUCCESS_RESPONSE_WITHOUT_BLOGS = "<rsp stat=\"200\"><map><mapurl>http://renderv318.mappoint.net/render-30/getmap.aspx?key=4483BB03CE3FB25C7E21</mapurl></map><blogs count=\"0\"></blogs></rsp>";
    private static final String XML_SUCCESS_RESPONSE_STARTS_WITH_EMPTY_SPACES = "      " + XML_SUCCESS_RESPONSE;

    private ResponseParser mParser;

    protected void setUp() {
        mParser = new ResponseParserImpl();
    }

    public void testParseBlogMapFailureInvalidXml() {

        try {
            Response response = mParser.parseBlogMap(INVALID_XML);
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testParseBlogMapFailureXmlResponse() {

        try {
            Response response = mParser.parseBlogMap(XML_FAILURE_RESPONSE);
            fail("Test with failure xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testParseBlogMapSuccessXmlResponse() {

        try {
            Response response = mParser.parseBlogMap(XML_SUCCESS_RESPONSE);
            assertNotNull(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }

    public void testParseBlogMapSuccessXmlResponseStartsWithEmptySpaces() {

        try {
            Response response = mParser.parseBlogMap(XML_SUCCESS_RESPONSE_STARTS_WITH_EMPTY_SPACES);
            assertNotNull(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }

    public void testParseBlogMapSuccessXmlResponseWithoutBlogs() {

        try {
            Response response = mParser.parseBlogMap(XML_SUCCESS_RESPONSE_WITHOUT_BLOGS);
            assertNotNull(response);
        } catch (BlogMap4JException bme) {
            fail("BlogMap4JException should not occur: " + bme.getMessage());
        }
    }
}
