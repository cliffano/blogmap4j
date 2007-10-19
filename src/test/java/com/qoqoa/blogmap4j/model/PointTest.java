package com.qoqoa.blogmap4j.model;

import junit.framework.TestCase;

public class PointTest extends TestCase {

	public void testPointKeepsMemberFields() {

		Point point = new Point(10, 20);
		assertEquals(10, point.getX());
		assertEquals(20, point.getY());
	}
}
