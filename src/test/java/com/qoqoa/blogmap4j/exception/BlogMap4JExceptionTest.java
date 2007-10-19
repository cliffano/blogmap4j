package com.qoqoa.blogmap4j.exception;

import junit.framework.TestCase;

public class BlogMap4JExceptionTest extends TestCase {

	private BlogMap4JException exception;

	public void testExceptionWithErrorMessage() {

		exception = new BlogMap4JException("some error message");
		assertEquals("some error message", exception.getMessage());
	}

	public void testExceptionWithErrorMessageAndThrowableCause() {

		Throwable cause = new Throwable();
		exception = new BlogMap4JException("some error message", cause);
		assertEquals("some error message", exception.getMessage());
		assertSame(cause, exception.getCause());
		assertEquals(cause, exception.getCause());
	}
}
