package net.edralzar.jreadability.data;

import java.net.URL;
import java.util.Date;

/**
 * java representation of a readability bookmark's article section
 *
 * @author edralzar
 *
 */
public class SimpleArticle {
	private String domain;
	private String title;
	private URL url; //"http://www.newyorker.com/arts/critics/atlarge/2011/04/11/110411crat_atlarge_parks?currentPage=all"
    private URL lead_image_url; //"http://www.newyorker.com/images/2011/04/11/p233/110411_r20743_p233.jpg"
    private String author;
    private String excerpt;
    private String direction; //"ltr"
    private long word_count;
    private Date date_published; //original format "2011-04-11 00:00:00"
    private String dek;
    private boolean processed;
    private String id; //"47g6s8e7"

	public String getDomain() {
		return domain;
	}

	public String getTitle() {
		return title;
	}

	public URL getUrl() {
		return url;
	}

	public URL getLeadImageUrl() {
		return lead_image_url;
	}

	public String getAuthor() {
		return author;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public String getDirection() {
		return direction;
	}

	public long getWordCount() {
		return word_count;
	}

	public Date getDatePublished() {
		return date_published;
	}

	/**
	 * @return the dek of the article, aka the slug, or sub-header summary of
	 *         what the article is about (if available)
	 */
	public String getDek() {
		return dek;
	}

	public boolean isProcessed() {
		return processed;
	}

	public String getId() {
		return id;
	}

}
