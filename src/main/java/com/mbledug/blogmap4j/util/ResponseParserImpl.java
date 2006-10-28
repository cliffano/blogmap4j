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
package com.mbledug.blogmap4j.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mbledug.blogmap4j.exception.BlogMap4JException;
import com.mbledug.blogmap4j.model.Blog;
import com.mbledug.blogmap4j.model.Coordinate;
import com.mbledug.blogmap4j.model.Location;
import com.mbledug.blogmap4j.model.Point;
import com.mbledug.blogmap4j.model.Response;

/**
 * A {@link ResponseParser} implementation using Dom4J.
 * @author Cliffano Subagio
 */
public class ResponseParserImpl implements ResponseParser {

    /**
     * String indicator of the start of an xml prolog.
     */
    private static final String XML_PROLOG_STARTS_WITH = "<?";
    /**
     * Index of start point's x position within a comma separated coordinate.
     */
    private static final int START_X_INDEX = 0;
    /**
     * Index of start point's y position within a comma separated coordinate.
     */
    private static final int START_Y_INDEX = 1;
    /**
     * Index of end point's x position within a comma separated coordinate.
     */
    private static final int END_X_INDEX = 2;
    /**
     * Index of end point's y position within a comma separated coordinate.
     */
    private static final int END_Y_INDEX = 3;

    /**
     * Status code that indicates failure response.
     */
    private static final int STAT_FAILURE = 500;

    /**
     * Create a {@link ResponseParserImpl} instance.
     */
    public ResponseParserImpl() {
    }

    /**
     * Parse the xml response String using Dom4J.
     * @param xmlString the response String to parse
     * @return {@link Response} object representation of the xml response String
     * @throws BlogMap4JException when there's a failure response from BlogMap
     *             service or there's an error with parsing the xml response
     */
    public final Response parseBlogMap(final String xmlString)
            throws BlogMap4JException {

        String validXmlString = cleanXmlProlog(xmlString);

        Document doc = null;
        try {
            doc = DocumentHelper.parseText(validXmlString);
        } catch (DocumentException de) {
            throw new BlogMap4JException("Unable to parse response string:\n"
                    + xmlString, de);
        }

        Element root = doc.getRootElement();

        int stat = Integer.parseInt(root.attribute("stat").getStringValue());
        // capture error message when there's a failure
        if (stat == STAT_FAILURE) {
            String errorMessage = root.element("err").attribute("message").
                    getStringValue();
            throw new BlogMap4JException(errorMessage);
        }

        Element map = root.element("map");
        String mapUrl = map.element("mapurl").getStringValue();

        List blogsList = new ArrayList();

        Element blogs = root.element("blogs");

        for (Iterator it = blogs.elementIterator(); it.hasNext();) {

            Element blogElement = (Element) it.next();
            Blog blog = getBlogFromElement(blogElement);
            blogsList.add(blog);
        }

        Response response = new Response(mapUrl, blogsList);
        return response;
    }

    /**
     * Clean up any potential garbage characters before the start of the
     * xml prolog.
     * @param xml the xml to clean up
     * @return xml which is guaranteed to start with the prolog
     */
    private String cleanXmlProlog(final String xml) {

        String cleanXml;

        int startXmlPos = xml.indexOf(XML_PROLOG_STARTS_WITH);
        if (startXmlPos > 0) {
            cleanXml = xml.substring(startXmlPos, xml.length());
        } else {
            cleanXml = xml;
        }

        return cleanXml;
    }

    /**
     * Create {@link Blog} from blog details within blogElement.
     * @param blogElement the Element containing the blog details
     * @return {@link Blog} with the details from blogElement
     */
    private Blog getBlogFromElement(final Element blogElement) {

        int id = Integer.parseInt(blogElement.attribute("id").getStringValue());
        String name = blogElement.attribute("name").getStringValue();
        String xmlUrl = blogElement.attribute("xmlurl").getStringValue();
        String url = blogElement.attribute("url").getStringValue();
        String csvCoordinate = blogElement.attribute("coords").getStringValue();
        Coordinate coordinate = getCoordinateFromCsv(csvCoordinate);

        Element locationElement = blogElement.element("location");
        double latitude = Double.parseDouble(
                locationElement.element("latitude").getStringValue());
        double longitude = Double.parseDouble(
                locationElement.element("longitude").getStringValue());
        String city = locationElement.element("city").getStringValue();
        String div = locationElement.element("div").getStringValue();
        String region = locationElement.element("region").getStringValue();
        Location location = new Location(
                latitude, longitude, city, div, region);

        Blog blog = new Blog(id, name, xmlUrl, url, coordinate, location);
        return blog;
    }

    /**
     * Parse the comma separated coordinates into a {@link Coordinate} object.
     * @param csv comma separated coordinates in the format of x1,y1,x2,y2
     * @return {@link Coordinate} representation of the csv
     */
    private Coordinate getCoordinateFromCsv(final String csv) {

        String[] coordinateArray = csv.split(",");
        Point start = new Point(Integer.parseInt(coordinateArray[START_X_INDEX])
                , Integer.parseInt(coordinateArray[START_Y_INDEX]));
        Point end = new Point(Integer.parseInt(coordinateArray[END_X_INDEX]),
                Integer.parseInt(coordinateArray [END_Y_INDEX]));

        return new Coordinate(start, end);
    }
}
