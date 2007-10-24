package com.qoqoa.blogmap4j.model;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

public class CoordinateTest extends TestCase {

    public void testCoordinateKeepsMemberFields() {

        Point startPoint = (Point) EasyMock.createStrictMock(Point.class);
        Point endPoint = (Point) EasyMock.createStrictMock(Point.class);

        EasyMock.replay(new Object[]{startPoint, endPoint});

        Coordinate coordinate = new Coordinate(startPoint, endPoint);
        assertSame(startPoint, coordinate.getStart());
        assertSame(endPoint, coordinate.getEnd());
        assertEquals(startPoint, coordinate.getStart());
        assertEquals(endPoint, coordinate.getEnd());

        EasyMock.verify(new Object[]{startPoint, endPoint});
    }
}
