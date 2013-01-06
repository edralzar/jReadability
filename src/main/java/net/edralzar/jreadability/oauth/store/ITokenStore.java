package net.edralzar.jreadability.oauth.store;

import org.scribe.model.Token;

/**
 * An interface to determine how to store/retrieve the oauth token
 *
 * @author edralzar
 *
 */
public interface ITokenStore {

	/**
	 * @param token
	 *            the oauth token to save
	 */
	public boolean saveToken(Token token);

	/**
	 *
	 * @return a previously persisted oauth token, or null if none could be
	 *         found
	 */
	public Token loadToken();

	/**
	 * @return a cached copy of the last {@link #loadToken() loaded} or
	 *         {@link #saveToken(Token) saved} token
	 */
	public Token getToken();

}
