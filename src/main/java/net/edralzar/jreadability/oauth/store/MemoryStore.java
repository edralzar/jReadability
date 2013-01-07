package net.edralzar.jreadability.oauth.store;

import org.scribe.model.Token;

/**
 * Simply stores a token in memory.
 *
 * @author edralzar
 *
 */
public class MemoryStore implements ITokenStore {

	private Token token;

	@Override
	public boolean saveToken(Token token) {
		this.token = token;
		return true;
	}

	@Override
	public Token loadToken() {
		return getToken();
	}

	@Override
	public Token getToken() {
		return this.token;
	}
}
