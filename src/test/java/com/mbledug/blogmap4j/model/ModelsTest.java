package com.mbledug.blogmap4j.model;

import java.util.ArrayList;
import java.util.List;

import com.mbledug.blogmap4j.model.Blog;
import com.mbledug.blogmap4j.model.Coordinate;
import com.mbledug.blogmap4j.model.Location;
import com.mbledug.blogmap4j.model.Point;
import com.mbledug.blogmap4j.model.Response;

import junit.framework.TestCase;

public class ModelsTest extends TestCase {

    private static final int START_X = 8;
    private static final int START_Y = 9;
    private static final int END_X = 10;
    private static final int END_Y = 11;

    private static final double LATITUDE = 8.8;
    private static final double LONGITUDE = 9.9;
    private static final String CITY = "somecity";
    private static final String DIV = "somediv";
    private static final String REGION = "someregion";

    private static final int ID = 1;
    private static final String NAME = "somename";
    private static final String XML_URL = "http://someblog/somefeed";
    private static final String URL = "http://someblog";

    private static final String MAP_URL = "http://somemap";

    private Point mStart;
    private Point mEnd;
    private Coordinate mCoordinate;
    private Location mLocation;
    private Blog mBlog;
    private List mBlogs;
    private Response mResponse;

    protected void setUp() {

        mStart = new Point(START_X, START_Y);
        mEnd = new Point(END_X, END_Y);

        mCoordinate = new Coordinate(mStart, mEnd);

        mLocation = new Location(LATITUDE, LONGITUDE, CITY, DIV, REGION);

        mBlog = new Blog(ID, NAME, XML_URL, URL, mCoordinate, mLocation);

        mBlogs = new ArrayList();
        mBlogs.add(mBlog);
        mBlogs.add(mBlog);

        mResponse = new Response(MAP_URL, mBlogs);
    }

    public void testPointImmutability() {

        assertEquals(START_X, mStart.getX());
        assertEquals(START_Y, mStart.getY());
    }

    public void testCoordinateImmutability() {

        assertEquals(mStart, mCoordinate.getStart());
        assertEquals(mEnd, mCoordinate.getEnd());
    }

    public void testLocationImmutability() {

        assertEquals(LATITUDE, mLocation.getLatitude(), 0);
        assertEquals(LONGITUDE, mLocation.getLongitude(), 0);
        assertEquals(CITY, mLocation.getCity());
        assertEquals(DIV, mLocation.getDiv());
        assertEquals(REGION, mLocation.getRegion());
    }

    public void testBlogImmutability() {

        assertEquals(ID, mBlog.getId());
        assertEquals(NAME, mBlog.getName());
        assertEquals(XML_URL, mBlog.getXmlUrl());
        assertEquals(URL, mBlog.getUrl());
        assertEquals(mCoordinate, mBlog.getCoordinate());
        assertEquals(mLocation, mBlog.getLocation());
    }

    public void testResponseImmutability() {

        assertEquals(MAP_URL, mResponse.getMapUrl());
        assertEquals(mBlogs, mResponse.getBlogs());
    }
}