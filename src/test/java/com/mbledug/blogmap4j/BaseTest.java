package com.mbledug.blogmap4j;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.mbledug.blogmap4j.model.Blog;
import com.mbledug.blogmap4j.model.Coordinate;
import com.mbledug.blogmap4j.model.Location;
import com.mbledug.blogmap4j.model.Point;
import com.mbledug.blogmap4j.model.Response;

public abstract class BaseTest extends TestCase {

    protected final void assertResponse(final Response response) {

        List blogs = response.getBlogs();
        for (Iterator it = blogs.iterator(); it.hasNext();) {

            Blog blog = (Blog)it.next();
            assertNotNull(blog);

            Coordinate coordinate = blog.getCoordinate();
            assertNotNull(coordinate);

            Point start = coordinate.getStart();
            Point end = coordinate.getEnd();
            assertNotNull(start);
            assertNotNull(end);

            Location location = blog.getLocation();
            assertNotNull(location);
        }
    }
}
