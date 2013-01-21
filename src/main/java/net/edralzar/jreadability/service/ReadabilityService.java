package net.edralzar.jreadability.service;

import net.edralzar.jreadability.ReadabilityRestException;
import net.edralzar.jreadability.data.Article;
import net.edralzar.jreadability.data.Bookmark;
import net.edralzar.jreadability.oauth.ReadabilityConst;
import net.edralzar.jreadability.oauth.store.ITokenStore;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;

public class ReadabilityService {

	private Token token;
	private OAuthService service;

	ReadabilityService(ITokenStore tokenStore, OAuthService service) {
		this.token = tokenStore.getToken();
		this.service = service;
	}

	public long addBookmark(String url, boolean asFavorite, boolean asArchive, String... tags) {
		OAuthRequest request = new OAuthRequest(Verb.POST, ReadabilityConst.API_BOOKMARKS);
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		request.addBodyParameter("url", url);
		if (asFavorite)
			request.addBodyParameter("favorite", "1");
		if (asArchive)
			request.addBodyParameter("archive", "1");
		if (tags.length != 0) {
			StringBuilder sb = new StringBuilder();
			for (String tag : tags) {
				sb.append(tag).append(' ');
			}
			sb.deleteCharAt(sb.length() - 1);
			request.addBodyParameter("tags", sb.toString());
		}

		service.signRequest(token, request);
		Response response = request.send();
		if (response.isSuccessful() || response.getCode() == 409) {
			String loc = response.getHeader("Location");
			loc = loc.substring(loc.lastIndexOf("/") + 1);
			return Long.parseLong(loc);
		} else {
			throw new ReadabilityRestException(response.getCode(), "Error adding bookmark");
		}
	}

	public Bookmark getBookmark(long id) {
		OAuthRequest request = new OAuthRequest(Verb.GET, ReadabilityConst.API_BOOKMARKS + id);
		service.signRequest(token, request);
		Response response = request.send();
		if (response.isSuccessful()) {
			Gson gson = ReadabilityServiceBuilder.newGson();
			return gson.fromJson(response.getBody(), Bookmark.class);
		} else {
			throw new ReadabilityRestException(response.getCode(), "Error getting bookmark " + id);
		}
	}

	public boolean toggleBookmarkArchive(long id, boolean isArchive) {
		return toggleBookmarkField(id, "archive", isArchive);
	}

	public boolean toggleBookmarkFavorite(long id, boolean isFavorite) {
		return toggleBookmarkField(id, "favorite", isFavorite);
	}

	private boolean toggleBookmarkField(long id, String param, boolean flag) {
		OAuthRequest request = new OAuthRequest(Verb.POST, ReadabilityConst.API_BOOKMARKS + id);
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		request.addBodyParameter(param, flag ? "1" : "0");
		service.signRequest(token, request);
		Response response = request.send();
		return response.isSuccessful();
	}

	public boolean deleteBookmark(long id) {
		OAuthRequest request = new OAuthRequest(Verb.DELETE, ReadabilityConst.API_BOOKMARKS + id);
		service.signRequest(token, request);
		return request.send().isSuccessful();
	}

	public BookmarkListFinder getBookmarks() {
		return new BookmarkListFinder(this.token, this.service);
	}

	public Article getArticle(String id) {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				ReadabilityConst.API_ARTICLE + id);

		service.signRequest(token, request);
		Response response = request.send();


		Gson gson = ReadabilityServiceBuilder.newGson();
		return gson.fromJson(response.getBody(), Article.class);
	}

}
