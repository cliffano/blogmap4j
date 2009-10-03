package com.cliffano.blogmap4j.model;

import com.cliffano.blogmap4j.model.Location;

import junit.framework.TestCase;

public class LocationTest extends TestCase {

	public void testLocationKeepsMemberFields() {

		Location location = new Location(
				-23.33, 10.28, "some city", "some div", "some region");
		assertEquals(-23.33, location.getLatitude(), 0);
		assertEquals(10.28, location.getLongitude(), 0);
		assertEquals("some city", location.getCity());
		assertEquals("some div", location.getDiv());
		assertEquals("some region", location.getRegion());
	}
}
