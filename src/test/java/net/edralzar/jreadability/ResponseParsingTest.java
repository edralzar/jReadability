package net.edralzar.jreadability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import net.edralzar.jreadability.data.AppliedTag;
import net.edralzar.jreadability.data.Article;
import net.edralzar.jreadability.data.Bookmark;
import net.edralzar.jreadability.data.BookmarkList;
import net.edralzar.jreadability.data.Conditions;
import net.edralzar.jreadability.data.Meta;
import net.edralzar.jreadability.data.SimpleArticle;
import net.edralzar.jreadability.data.Tag;
import net.edralzar.jreadability.service.ReadabilityServiceBuilder;

import org.junit.Test;

import com.google.gson.Gson;

public class ResponseParsingTest {

	@Test
	public void testSimpleArticle() {
		SimpleArticle sa = testParsing("simpleArticle.json",
				SimpleArticle.class);
	}

	@Test
	public void testSimpleTag() {
		Tag st = testParsing("simpleTag.json", Tag.class);
	}

	@Test
	public void testCountedTag() {
		AppliedTag at = testParsing("countedTag.json", AppliedTag.class);
	}

	@Test
	public void testBookmark() {
		Bookmark b = testParsing("bookmark.json", Bookmark.class);
	}

	@Test
	public void testArticle() {
		Article a = testParsing("article.json", Article.class);
	}


	@Test
	public void testMeta() {
		Meta m = testParsing("meta.json", Meta.class);
	}

	@Test
	public void testConditions() {
		Conditions c = testParsing("conditions.json", Conditions.class);
	}

	@Test
	public void testBookmarkList() {
		BookmarkList bl = testParsing("bookmarkList.json", BookmarkList.class);
	}

	private <T> T testParsing(String fileName, Class<T> clazz) {
		Gson gson = ReadabilityServiceBuilder.newGson();
		try (Reader r = new InputStreamReader(
				ResponseParsingTest.class.getResourceAsStream("/" + fileName));) {
			return gson.fromJson(r, clazz);
		} catch (IOException e) {
			fail();
			return null;
		}
	}

	@Test
	public void testDateUtc() {
		// ensure that parsed date are interpreted as UTC and parsed in the
		// current timezone
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.set(2012, 11, 12, 20, 00, 00);

		Gson gson = ReadabilityServiceBuilder.newGson();
		Date fromJson = gson.fromJson("\"2012-12-12 20:00:00\"", Date.class);

		assertNotNull(fromJson);
		assertEquals(cal.getTime().getTime(), fromJson.getTime());

		cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(fromJson);
		assertEquals(20, cal.get(Calendar.HOUR_OF_DAY));
	}

}
