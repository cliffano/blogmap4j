package com.qoqoa.blogmap4j.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ResponseTest extends TestCase {

	public void testResponseKeepsMemberFields() {

		List blogs = new ArrayList();
		Response response = new Response("http://somemapurl.com", blogs);
		assertEquals("http://somemapurl.com", response.getMapUrl());
		assertSame(blogs, response.getBlogs());
		assertEquals(blogs, response.getBlogs());
	}
}
