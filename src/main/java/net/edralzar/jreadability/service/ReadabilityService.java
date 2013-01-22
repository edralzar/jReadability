package net.edralzar.jreadability.service;

import java.lang.reflect.Type;
import java.util.List;

import net.edralzar.jreadability.ReadabilityRestException;
import net.edralzar.jreadability.data.AppliedTag;
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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class ReadabilityService {

	private Token token;
	private OAuthService service;
	private Gson gson;

	ReadabilityService(ITokenStore tokenStore, OAuthService service, Gson gson) {
		this.token = tokenStore.getToken();
		this.service = service;
		this.gson = gson;
	}

	public long addBookmark(String url, boolean asFavorite, boolean asArchive, String... tags) {
		OAuthRequest request = new OAuthRequest(Verb.POST, ReadabilityConst.API_BOOKMARKS);
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		request.addBodyParameter("url", url);
		if (asFavorite) {
			request.addBodyParameter("favorite", "1");
		}
		if (asArchive) {
			request.addBodyParameter("archive", "1");
		}

		service.signRequest(token, request);
		Response response = request.send();
		if (response.isSuccessful() || response.getCode() == 409) {
			String loc = response.getHeader("Location");
			loc = loc.substring(loc.lastIndexOf("/") + 1);
			long id = Long.parseLong(loc);
			// the tags parameter in the bookmark post seems to be ignored for now
			// so finally, if some tags were set and the bookmark was non-existing, tag it
			if (response.isSuccessful() && tags.length > 0) {
				tagBookmark(id, tags);
			}
			return id;
		} else
			throw new ReadabilityRestException(response.getCode(),
					"Error adding bookmark");
	}

	public List<AppliedTag> tagBookmark(long id, String... tags) {
		OAuthRequest request = new OAuthRequest(Verb.POST,
				ReadabilityConst.API_BOOKMARKS + id + "/tags");
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		if (tags.length != 0) {
			StringBuilder sb = new StringBuilder();
			for (String tag : tags) {
				sb.append(tag).append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
			request.addBodyParameter("tags", sb.toString());
		}

		service.signRequest(token, request);
		Response response = request.send();
		if (response.isSuccessful()) {
			JsonObject tagList = gson.fromJson(response.getBody(),JsonObject.class);
			Type tagListType = new TypeToken<List<AppliedTag>>() {}.getType();
			return gson.fromJson(tagList.get("tags"), tagListType);
		} else {
			String detail = "Unable to add tags on bookmark id " + id;
			if (response.getCode() == 403) {
				detail = response.getBody();
			}

			throw new ReadabilityRestException(response.getCode(), detail);
		}
	}

	public Bookmark getBookmark(long id) {
		OAuthRequest request = new OAuthRequest(Verb.GET, ReadabilityConst.API_BOOKMARKS + id);
		service.signRequest(token, request);
		Response response = request.send();
		if (response.isSuccessful())
			return gson.fromJson(response.getBody(), Bookmark.class);
		else
			throw new ReadabilityRestException(response.getCode(), "Error getting bookmark " + id);
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

		return gson.fromJson(response.getBody(), Article.class);
	}

}
