package com.qoqoa.blogmap4j.model;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

public class CoordinateTest extends TestCase {

	public void testCoordinateKeepsMemberFields() {

		IMocksControl control = EasyMock.createStrictControl();
		Point startPoint = (Point) control.createMock(Point.class);
		Point endPoint = (Point) control.createMock(Point.class);

		control.replay();

		Coordinate coordinate = new Coordinate(startPoint, endPoint);
		assertSame(startPoint, coordinate.getStart());
		assertSame(endPoint, coordinate.getEnd());
		assertEquals(startPoint, coordinate.getStart());
		assertEquals(endPoint, coordinate.getEnd());

		control.verify();
	}
}
