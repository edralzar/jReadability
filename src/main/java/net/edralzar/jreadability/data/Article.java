package net.edralzar.jreadability.data;

import java.net.URL;

/**
 * A java representation of a readability Article, complete with content
 *
 * @author edralzar
 *
 */
public class Article extends SimpleArticle {
	private String next_page_href;
	private long content_size;
	private String content; // "<div><p>[article content here]</p></div>"
	private URL short_url; // "http://rdd.me/47g6s8e7"

	public String getNextPageHref() {
		return next_page_href;
	}

	public long getContentSize() {
		return content_size;
	}

	public String getContent() {
		return content;
	}

	public URL getShortUrl() {
		return short_url;
	}
}
