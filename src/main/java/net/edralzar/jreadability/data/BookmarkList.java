package net.edralzar.jreadability.data;

import java.util.List;

/**
 * A java representation of a readability bookmarks list, with the conditions
 * that where set when searching for the bookmarks, and the metadata about the
 * collection
 *
 * @author edralzar
 *
 */
public class BookmarkList {

	private Conditions conditions;
	private Meta meta;
	private List<Bookmark> bookmarks;

	public Conditions getConditions() {
		return conditions;
	}

	public Meta getMeta() {
		return meta;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

}
