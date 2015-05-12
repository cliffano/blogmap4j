BlogMap4J [![http://travis-ci.org/cliffano/blogmap4j](https://img.shields.io/travis/cliffano/blogmap4j.svg)](http://travis-ci.org/cliffano/blogmap4j)
------

BlogMap4J is a Java library for [BlogMap Service APIs](http://www.feedmap.net/BlogMap/Services/). 

Usage
-----

__getBlogMap__

Get a blog from a registered feed URL, generate a 150 x 200 pixels map.

	BlogMap4J blogMap4J = new BlogMap4J();
	Response response =
	        blogMap4J.getBlogMap(
	        "http://www.csthota.com/blog/rss.aspx",
	        new Integer(150),
	        new Integer(200));
	String mapUrl = response.getMapUrl();
	Blog blog = (Blog) response.getBlogs().get(0);

__search__

Search for at most 10 blogs around redmond,wa with latitude 47 and longitude -122, within 50 kilometres from the center, and generate a 150 x 200 pixels map.

	BlogMap4J blogMap4J = new BlogMap4J();
	Response response =
	        blogMap4J.search(
	        "redmond,wa",
	        new Double(47),
	        new Double(-122),
	        new Integer(50),
	        new Integer(10),
	        new Integer(150),
	        new Integer(200));
	String mapUrl = response.getMapUrl();
	List blogs = response.getBlogs();

Colophon
--------

Follow [@cliffano](http://twitter.com/cliffano) on Twitter.
