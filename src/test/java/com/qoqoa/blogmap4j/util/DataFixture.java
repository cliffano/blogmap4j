package com.qoqoa.blogmap4j.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.URIException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.qoqoa.blogmap4j.model.Blog;
import com.qoqoa.blogmap4j.model.Coordinate;
import com.qoqoa.blogmap4j.model.Location;
import com.qoqoa.blogmap4j.model.Point;
import com.qoqoa.blogmap4j.model.Response;
import com.qoqoa.blogmap4j.util.ResponseParser;
import com.qoqoa.blogmap4j.util.ServiceManager;

public class DataFixture extends MockObjectTestCase {

    public static final String VALID_SERVICE_URL = "http://www.feedmap.net/blogmap/services/rest.ashx";
    public static final String INVALID_SERVICE_URL = "http://#@(*$&(*@&$&@#$#@(&";
    public static final String FEED = "http://www.csthota.com/blog/rss.aspx";
    public static final String METHOD_GET_BLOG_MAP = "blogmap.getblogmap";
    public static final String DUMMY_PROXY_HOST = "http://@#&$(@$&@$&#@&";
    public static final int DUMMY_PROXY_PORT = 8080;
    public static final String DUMMY_PROXY_USERNAME = "Some Username";
    public static final String DUMMY_PROXY_PASSWORD = "Some Password";
    public static final String INVALID_RESPONSE_XML_STRING = "<foobar>(&#($&(*&#@($&@#</foobar>";
    public static final String FAILURE_RESPONSE_XML_STRING = "<rsp stat=\"500\"><err message=\"Input feed does not exist in blogmap service.\"/></rsp>";
    public static final int START_X = 8;
    public static final int START_Y = 9;
    public static final int END_X = 10;
    public static final int END_Y = 11;
    public static final double LATITUDE = 8.8;
    public static final double LONGITUDE = 9.9;
    public static final String CITY = "somecity";
    public static final String DIV = "somediv";
    public static final String REGION = "someregion";
    public static final int ID = 1;
    public static final String NAME = "somename";
    public static final String XML_URL = "http://someblog/somefeed";
    public static final String URL = "http://someblog";
    public static final String MAP_URL = "http://somemap";

    public static final Response createResponse() {

        Point start = new Point(DataFixture.START_X, DataFixture.START_Y);
        Point end = new Point(DataFixture.END_X, DataFixture.END_Y);

        Coordinate coordinate = new Coordinate(start, end);

        Location location = new Location(DataFixture.LATITUDE, DataFixture.LONGITUDE, DataFixture.CITY, DataFixture.DIV, DataFixture.REGION);

        Blog blog = new Blog(DataFixture.ID, DataFixture.NAME, DataFixture.XML_URL, DataFixture.URL, coordinate, location);

        List blogs = new ArrayList();
        blogs.add(blog);
        blogs.add(blog);

        return new Response(DataFixture.MAP_URL, blogs);
    }

    public static final String createBlogXmlString(final int blogId) {
        return "<blog id=\"" + blogId + "\" name=\"Chandu Thota\" xmlurl=\"http://www.csthota.com/blog/rss.aspx\" url=\"http://www.csthota.com/blog/\" coords=\"109,19,125,35\"><location><latitude>47.6785794635701</latitude><longitude>-122.13084477543</longitude><city>Redmond</city><div>Washington</div><region>United States</region></location></blog>";
    }

    public static final String createResponseXmlString(final int numOfBlogs) {
        StringBuffer responseXmlString = new StringBuffer()
                .append("<rsp stat=\"200\"><map><mapurl>http://renderv318.mappoint.net/render-30/getmap.aspx?key=4483BB03CE3FB25C7E21</mapurl></map>")
                .append("<blogs count=\"" + numOfBlogs + "\">");
        for (int i = 1; i <= numOfBlogs; i++) {
            responseXmlString.append(createBlogXmlString(i));
        }
        responseXmlString.append("</blogs></rsp>");
        return responseXmlString.toString();
    }

    public final HttpClient createMockHttpClient(final boolean withProxy, final Exception executeMethodException) {
        Mock mockHttpClient = mock(HttpClient.class);
        if (executeMethodException == null) {
            mockHttpClient.expects(once()).method("executeMethod").will(returnValue(1));
        } else {
            mockHttpClient.expects(once()).method("executeMethod").will(throwException(executeMethodException));
        }
        if (withProxy) {
            mockHttpClient.expects(once()).method("getHostConfiguration").will(returnValue(new HostConfiguration()));
            mockHttpClient.expects(once()).method("getState").will(returnValue(new HttpState()));
        }
        return (HttpClient) mockHttpClient.proxy();
    }

    public final HttpMethod createMockHttpMethod(final String url) {
        Mock mockHttpMethod = mock(HttpMethod.class);
        if (VALID_SERVICE_URL.equals(url)) {
            mockHttpMethod.expects(once()).method("getResponseBodyAsString").will(returnValue(createResponseXmlString(10)));
            mockHttpMethod.expects(once()).method("setURI");
        } else {
            mockHttpMethod.expects(once()).method("getResponseBodyAsString").will(throwException(new IOException("Some Dummy Error")));
            mockHttpMethod.expects(once()).method("setURI").will(throwException(new URIException()));
        }
        mockHttpMethod.expects(once()).method("getURI");
        mockHttpMethod.expects(once()).method("releaseConnection");
        mockHttpMethod.expects(once()).method("setFollowRedirects");
        mockHttpMethod.expects(once()).method("setQueryString");
        return (HttpMethod) mockHttpMethod.proxy();
    }

    public final ServiceManager createMockServiceManager(final boolean withProxy) {
        Mock mockServiceManager = mock(ServiceManager.class);
        mockServiceManager.expects(once()).method("execute");
        if (withProxy) {
            mockServiceManager.expects(once()).method("setProxy");
        }
        return (ServiceManager) mockServiceManager.proxy();
    }

    public final ResponseParser createMockResponseParser() {
        Mock mockResponseParser = mock(ResponseParser.class);
        mockResponseParser.expects(once()).method("parseBlogMap").will(returnValue(createResponse()));
        return (ResponseParser) mockResponseParser.proxy();
    }
}
