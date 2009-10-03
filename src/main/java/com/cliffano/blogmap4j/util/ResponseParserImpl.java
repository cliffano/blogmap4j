/**
 * Copyright (c) 2005-2007, Cliffano Subagio
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
 *   * Neither the name of Studio Cliffano nor the names of its contributors
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
package com.cliffano.blogmap4j.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.cliffano.blogmap4j.exception.BlogMap4JException;
import com.cliffano.blogmap4j.model.Blog;
import com.cliffano.blogmap4j.model.Coordinate;
import com.cliffano.blogmap4j.model.Location;
import com.cliffano.blogmap4j.model.Point;
import com.cliffano.blogmap4j.model.Response;

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
     * Document builder.
     */
    private DocumentBuilder mDocumentBuilder;

    /**
     * Create a {@link ResponseParserImpl} instance.
     */
    public ResponseParserImpl() {
        this(DocumentBuilderFactory.newInstance());

    }

    /**
     * Create a {@link ResponseParserImpl} with specified
     * document builder factory.
     * @param documentBuilderFactory document builder factory
     */
    public ResponseParserImpl(
            final DocumentBuilderFactory documentBuilderFactory) {
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setCoalescing(true);
        documentBuilderFactory.setNamespaceAware(false);
        try {
            mDocumentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new BlogMap4JException(
                    "Unable to create DocumentBuilder.", e);
        }
    }

    /**
     * Parse the xml response String using Dom4J.
     * @param xmlString the response String to parse
     * @return {@link Response} object representation of the xml response String
     */
    public final Response parseBlogMap(final String xmlString) {

        String validXmlString = cleanXmlProlog(xmlString);

        Document doc;
        try {
            doc = mDocumentBuilder.parse(new InputSource(
                    new StringReader(validXmlString)));
        } catch (IOException ioe) {
            throw new BlogMap4JException("Unable to parse response string:\n"
                    + xmlString, ioe);
        } catch (SAXException saxe) {
            throw new BlogMap4JException("Unable to parse response string:\n"
                    + xmlString, saxe);
        }

        Element root = doc.getDocumentElement();

        int stat = Integer.parseInt(root.getAttribute("stat"));
        // capture error message when there's a failure
        if (stat == STAT_FAILURE) {
            Element err = (Element) root.getElementsByTagName("err").item(0);
            String errorMessage = getAttributeValue(err, "message");
            throw new BlogMap4JException(errorMessage);
        }

        Element map = (Element) root.getElementsByTagName("map").item(0);
        String mapUrl = getChildElementValue(map, "mapurl");

        List blogsList = new ArrayList();
        NodeList blogs = ((Element) root.getElementsByTagName("blogs").item(0)).
                getElementsByTagName("blog");
        for (int i = 0; i < blogs.getLength(); i++) {
            Element blogElement = (Element) blogs.item(i);
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
     * @param blogElement the Node containing the blog details
     * @return {@link Blog} with the details from blogElement
     */
    private Blog getBlogFromElement(final Element blogElement) {

        int id = Integer.parseInt(getAttributeValue(blogElement, "id"));
        String name = getAttributeValue(blogElement, "name");
        String xmlUrl = getAttributeValue(blogElement, "xmlurl");
        String url = getAttributeValue(blogElement, "url");
        String csvCoordinate = getAttributeValue(blogElement, "coords");
        Coordinate coordinate = getCoordinateFromCsv(csvCoordinate);

        Element locationElement = (Element)
                blogElement.getElementsByTagName("location").item(0);
        double latitude = Double.parseDouble(
                getChildElementValue(locationElement, "latitude"));
        double longitude = Double.parseDouble(
                getChildElementValue(locationElement, "longitude"));
        String city = getChildElementValue(locationElement, "city");
        String div = getChildElementValue(locationElement, "div");
        String region = getChildElementValue(locationElement, "region");
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

    /**
     * Get attribute String value.
     * @param element the element
     * @param attributeName the name of element's attribute
     * @return attribute String value
     */
    private String getAttributeValue(
            final Element element, final String attributeName) {
        return element.getAttributes().getNamedItem(attributeName).
            getNodeValue();
    }

    /**
     * Get the String value of a child element.
     * @param parentElement the parent element
     * @param childElementName the name of child element
     * @return the String value of child element
     */
    private String getChildElementValue(
            final Element parentElement, final String childElementName) {
        String value = null;
        Element childElement = (Element) parentElement.getElementsByTagName(
                childElementName).item(0);
        if (childElement.getFirstChild() != null) {
            value = ((CharacterData) childElement.getFirstChild()).getData();
        }
        return value;
    }
}
