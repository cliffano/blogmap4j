package com.qoqoa.blogmap4j.model;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

public class BlogTest extends TestCase {

	public void testBlogKeepsMemberFields() {

		IMocksControl coordinateControl = EasyMock.createStrictControl();
		IMocksControl locationControl = EasyMock.createStrictControl();

		Coordinate coordinate =
			(Coordinate) coordinateControl.createMock(Coordinate.class);
		Location location =
			(Location) locationControl.createMock(Location.class);

		coordinateControl.replay();
		locationControl.replay();

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

		coordinateControl.verify();
		locationControl.verify();
	}
}
