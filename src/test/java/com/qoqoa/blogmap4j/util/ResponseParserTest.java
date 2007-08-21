package com.qoqoa.blogmap4j.util;

import com.qoqoa.blogmap4j.BaseTest;
import com.qoqoa.blogmap4j.exception.BlogMap4JException;
import com.qoqoa.blogmap4j.model.Response;

public class ResponseParserTest extends BaseTest {

    private ResponseParser mParser;

    protected void setUp() {
        mParser = new ResponseParserImpl();
    }

    public void testParseInvalidResponseXmlStringGivesBlogMap4JException() {

        try {
            Response response = mParser.parseBlogMap(DataFixture.INVALID_RESPONSE_XML_STRING);
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testParseFailureResponseXmlStringGivesBlogMap4JException() {

        try {
            Response response = mParser.parseBlogMap(DataFixture.FAILURE_RESPONSE_XML_STRING);
            fail("Test with failure xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (BlogMap4JException bme) {
            // BlogMap4JException is thrown as expected
        }
    }

    public void testParseSuccessXmlResponse() {
        Response response = mParser.parseBlogMap(DataFixture.createResponseXmlString(50));
        assertResponse(response);
    }

    public void testParseSuccessResponseXmlStringPrependedEmptySpaces() {
        Response response = mParser.parseBlogMap("   " + DataFixture.createResponseXmlString(20));
        assertResponse(response);
    }

    public void testParseSuccessXmlResponseWithoutAnyBlogs() {
        Response response = mParser.parseBlogMap(DataFixture.createResponseXmlString(0));
        assertResponse(response);
    }
}
