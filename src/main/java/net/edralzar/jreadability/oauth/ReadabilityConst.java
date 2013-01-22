package net.edralzar.jreadability.oauth;

public interface ReadabilityConst {

	public static final String PROPERTIES_FILENAME = "jreadability.properties";
	public static final String PROP_API_KEY = "api_key";
	public static final String PROP_API_SECRET = "api_secret";

	public static final String DATETIME_OUTPUT_PATTERN = "yyyyMMdd'T'hh:mm:ssZ";

	public static final String DATETIME_INPUT_PATTERN = "yyyy-MM-dd hh:mm:ss";

	public static final String API_ARTICLE = "https://www.readability.com/api/rest/v1/articles/";
	public static final String API_BOOKMARKS = "https://www.readability.com/api/rest/v1/bookmarks/";
}
