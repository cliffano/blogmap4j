package com.qoqoa.blogmap4j.model;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.qoqoa.blogmap4j.model.Blog;
import com.qoqoa.blogmap4j.model.Coordinate;
import com.qoqoa.blogmap4j.model.Location;
import com.qoqoa.blogmap4j.model.Point;
import com.qoqoa.blogmap4j.model.Response;
import com.qoqoa.blogmap4j.util.DataFixture;

public class ModelsTest extends TestCase {

    public void testCreateModels() {

        Response response = DataFixture.createResponse();
        ArrayList blogs = (ArrayList) response.getBlogs();
        Blog blog = (Blog) blogs.get(0);
        Location location = blog.getLocation();
        Coordinate coordinate = blog.getCoordinate();
        Point start = coordinate.getStart();
        Point end = coordinate.getEnd();

        assertEquals(DataFixture.START_X, start.getX());
        assertEquals(DataFixture.START_Y, start.getY());

        assertEquals(start, coordinate.getStart());
        assertEquals(end, coordinate.getEnd());

        assertEquals(DataFixture.LATITUDE, location.getLatitude(), 0);
        assertEquals(DataFixture.LONGITUDE, location.getLongitude(), 0);
        assertEquals(DataFixture.CITY, location.getCity());
        assertEquals(DataFixture.DIV, location.getDiv());
        assertEquals(DataFixture.REGION, location.getRegion());

        assertEquals(DataFixture.ID, blog.getId());
        assertEquals(DataFixture.NAME, blog.getName());
        assertEquals(DataFixture.XML_URL, blog.getXmlUrl());
        assertEquals(DataFixture.URL, blog.getUrl());
        assertEquals(coordinate, blog.getCoordinate());
        assertEquals(location, blog.getLocation());

        assertEquals(DataFixture.MAP_URL, response.getMapUrl());
        assertEquals(blogs, response.getBlogs());
    }
}