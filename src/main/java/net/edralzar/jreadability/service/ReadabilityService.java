package net.edralzar.jreadability.service;

import net.edralzar.jreadability.oauth.store.ITokenStore;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

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

}
