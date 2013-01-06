package net.edralzar.jreadability.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.edralzar.jreadability.ReadabilityException;
import net.edralzar.jreadability.data.BookmarkList;
import net.edralzar.jreadability.oauth.ReadabilityConst;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;

public class BookmarkListFinder {
	private Token token;
	private OAuthService service;


	BookmarkListFinder(Token token, OAuthService service) {
		this.token = token;
		this.service = service;
	}

	private Date addedSince;
	private boolean includeArchives = false;

	public BookmarkListFinder includeArchives() {
		this.includeArchives = true;
		return this;
	}

	public BookmarkListFinder addedSince(Date utcSince) {
		this.addedSince = utcSince;
		return this;
	}

	public BookmarkList find() throws ReadabilityException {
		SimpleDateFormat sdf = new SimpleDateFormat(ReadabilityConst.DATETIME_PATTERN);

		OAuthRequest request = new OAuthRequest(Verb.GET,
				"https://www.readability.com/api/rest/v1/bookmarks/");

		if (includeArchives) {
			request.addQuerystringParameter("archive", "1");
		} else {
			request.addQuerystringParameter("archive", "0");
		}

		if (addedSince != null) {
			request.addQuerystringParameter("addedSince", sdf.format(addedSince));
		}

		service.signRequest(token, request);
		Response response = request.send();

		if (response.getCode() == 200) {
			Gson gson = new Gson();
			return gson.fromJson(response.getBody(), BookmarkList.class);
		} else
			throw new ReadabilityException(response.getCode(),
					response.getBody());
	}
}