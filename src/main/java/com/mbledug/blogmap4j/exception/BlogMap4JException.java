/**
 * Copyright (c) 2005-2006, Cliffano Subagio
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of Mbledug nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.mbledug.blogmap4j.exception;

/**
 * Exception within BlogMap4J. Used when the service returns a failure
 * response, or when there's a problem with parsing the xml response string.
 * @author Cliffano Subagio
 */
public class BlogMap4JException extends Exception {

    /**
     * Serial version UID because Exception implements Serializable.
     */
    private static final long serialVersionUID = 1;

    /**
     * Create an exception with an error message.
     * @param errorMessage the erros message of the exception
     */
    public BlogMap4JException(final String errorMessage) {
        super(errorMessage);
    }

    /**
     * Create an exception with an error message, and pass the cause.
     * @param errorMessage the error message of the exception
     * @param cause a Throwable that's causing this exception
     */
    public BlogMap4JException(
            final String errorMessage,
            final Throwable cause) {
        super(errorMessage, cause);
    }
}
