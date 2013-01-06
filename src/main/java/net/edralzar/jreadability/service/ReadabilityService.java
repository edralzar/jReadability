package net.edralzar.jreadability.service;

import net.edralzar.jreadability.data.Article;
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
