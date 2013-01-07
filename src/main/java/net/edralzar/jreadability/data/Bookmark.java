package net.edralzar.jreadability.data;

import java.util.Date;
import java.util.List;

/**
 * java representation of a readability bookmark
 *
 * @author edralzar
 *
 */
public class Bookmark {
	private long user_id;
	private double read_percent;
	private Date date_updated;
	private boolean favorite;
	private SimpleArticle article;
	private long id;
	private Date date_archived;
	private Date date_opened;
	private Date date_added;
	private String article_href; // "/api/rest/v1/articles/86/"
	private Date date_favorited;
	private boolean archive;
	private List<Tag> tags;

	public long getUserId() {
		return user_id;
	}

	public double getReadPercent() {
		return read_percent;
	}

	public Date getDateUpdated() {
		return date_updated;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public SimpleArticle getArticle() {
		return article;
	}

	public long getId() {
		return id;
	}

	public Date getDateArchived() {
		return date_archived;
	}

	public Date getDateOpened() {
		return date_opened;
	}

	public Date getDateAdded() {
		return date_added;
	}

	public String getArticleHref() {
		return article_href;
	}

	public Date getDateFavorited() {
		return date_favorited;
	}

	public boolean isArchive() {
		return archive;
	}

	public List<Tag> getTags() {
		return tags;
	}
}
