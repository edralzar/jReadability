package net.edralzar.jreadability.service;

import java.util.Date;

import net.edralzar.jreadability.ReadabilityException;
import net.edralzar.jreadability.oauth.ReadabilityApi;
import net.edralzar.jreadability.oauth.ReadabilityConst;
import net.edralzar.jreadability.oauth.store.ITokenStore;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReadabilityServiceBuilder {

	public static ReadabilityService build(ITokenStore tokenStore,
			IAuthenticationDelegate delegate) {
		OAuthService service = new ServiceBuilder()
				.apiKey(ReadabilityConst.API_KEY)
				.apiSecret(ReadabilityConst.API_SECRET)
				.provider(ReadabilityApi.class).callback("oob").build();

		// try to get a saved token
		Token token = tokenStore.loadToken();
		if (token == null || token.isEmpty()) {
			// need to do the oauth dance
			Token reqToken = service.getRequestToken();
			String authUrl = service.getAuthorizationUrl(reqToken);

			// wait for the user to validate his request token (delegate to the
			// IAuthDelegate)
			try {
				String pin = delegate.onAuthenticationNeeded(authUrl);
				Verifier v = new Verifier(pin);
				token = service.getAccessToken(reqToken, v);

				// here we should have a verified access token
				if (token != null && !token.isEmpty()) {
					tokenStore.saveToken(token);
					return new ReadabilityService(tokenStore, service);
				} else
					throw new ReadabilityException("Could not access Readability API");
			} catch (Exception e) {
				throw new ReadabilityException(
						"Could not access Readability API", e);
			}
		} else
			return new ReadabilityService(tokenStore, service);
	}

	/**
	 * @return a {@link Gson} using the correct readability datetime pattern
	 */
	public static Gson newGson() {
		return new GsonBuilder()
				.registerTypeAdapter(Date.class, new ReadabilityDateAdapter())
				.create();
	}


	public static interface IAuthenticationDelegate {

		/**
		 * the token must be verified by the user by going to the webpage at
		 * <i>urlForAuthentication</u>
		 *
		 * @param urlForAuthentication
		 *            the page to visit to validate the token
		 * @return the pin displayed by the page for verification purpose, or
		 *         null if unable to get one
		 */
		public String onAuthenticationNeeded(String urlForAuthentication);
	}
}
