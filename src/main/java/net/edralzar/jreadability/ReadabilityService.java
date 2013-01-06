package net.edralzar.jreadability;

import net.edralzar.jreadability.oauth.store.ITokenStore;

public class ReadabilityService {

	private ITokenStore tokenStore;

	ReadabilityService(ITokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

}
