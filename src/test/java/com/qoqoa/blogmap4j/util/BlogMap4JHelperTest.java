package com.qoqoa.blogmap4j.util;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.httpclient.NameValuePair;

public class BlogMap4JHelperTest extends TestCase {

	private BlogMap4JHelper helper;
	private Map params;

    protected void setUp() {
    	helper = new BlogMap4JHelper();
        params = new LinkedHashMap();
    }

	public void testConvertMapToNameValuePairArray() {

        params.put("method", "some.method");
        params.put("feed", "http://somefeedurl.com");

        NameValuePair[] nameValuePairs = helper.convertMapToNameValuePairArray(params);
        assertEquals("method", nameValuePairs[0].getName());
        assertEquals("some.method", nameValuePairs[0].getValue());
        assertEquals("feed", nameValuePairs[1].getName());
        assertEquals("http://somefeedurl.com", nameValuePairs[1].getValue());
	}

	public void testConvertMapToNameValuePairArrayWithEmptyMap() {

        NameValuePair[] nameValuePairs = helper.convertMapToNameValuePairArray(params);
        assertEquals(0, nameValuePairs.length);
	}
}
