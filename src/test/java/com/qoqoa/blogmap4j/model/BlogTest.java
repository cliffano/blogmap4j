package com.qoqoa.blogmap4j.model;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

public class BlogTest extends TestCase {

    public void testBlogKeepsMemberFields() {

        Coordinate coordinate = (Coordinate) EasyMock.createStrictMock(Coordinate.class);
        Location location = (Location) EasyMock.createStrictMock(Location.class);

        EasyMock.replay(new Object[]{coordinate, location});

        Blog blog = new Blog(12, "some name", "http://somefeedurl.com",
                "http://someblogurl.com", coordinate, location);
        assertEquals(12, blog.getId());
        assertEquals("some name", blog.getName());
        assertEquals("http://somefeedurl.com", blog.getXmlUrl());
        assertEquals("http://someblogurl.com", blog.getUrl());
        assertSame(coordinate, blog.getCoordinate());
        assertSame(location, blog.getLocation());
        assertEquals(coordinate, blog.getCoordinate());
        assertEquals(location, blog.getLocation());

        EasyMock.verify(new Object[]{coordinate, location});
    }
}
