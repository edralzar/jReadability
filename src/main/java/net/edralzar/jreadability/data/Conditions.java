package net.edralzar.jreadability.data;

public class Conditions {
	private String opened_since;
	private String added_until;
	private String opened_until;
	private String archived_until;
	private int favorite;
	private String archived_since;
	private String favorited_since;
	private String user;
	private int per_page;
	private String favorited_until;
	private int archive;
	private String added_since;
	private String order;
	private int page;
	private String updated_since;
	private String updated_until;

	public String getOpenedSince() {
		return opened_since;
	}

	public String getAddedUntil() {
		return added_until;
	}

	public String getOpenedUntil() {
		return opened_until;
	}

	public String getArchivedUntil() {
		return archived_until;
	}

	public boolean isFavorite() {
		return favorite == 1;
	}

	public String getArchivedSince() {
		return archived_since;
	}

	public String getFavoritedSince() {
		return favorited_since;
	}

	public String getUser() {
		return user;
	}

	public int getPerPage() {
		return per_page;
	}

	public String getFavoritedUntil() {
		return favorited_until;
	}

	public boolean isArchive() {
		return archive == 1;
	}

	public String getAddedSince() {
		return added_since;
	}

	public String getOrder() {
		return order;
	}

	public int getPage() {
		return page;
	}

	public String getUpdatedSince() {
		return updated_since;
	}

	public String getUpdatedUntil() {
		return updated_until;
	}

}
