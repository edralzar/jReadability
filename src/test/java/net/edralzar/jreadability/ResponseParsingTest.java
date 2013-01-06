package net.edralzar.jreadability;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import net.edralzar.jreadability.data.AppliedTag;
import net.edralzar.jreadability.data.Article;
import net.edralzar.jreadability.data.Bookmark;
import net.edralzar.jreadability.data.BookmarkList;
import net.edralzar.jreadability.data.Conditions;
import net.edralzar.jreadability.data.Meta;
import net.edralzar.jreadability.data.SimpleArticle;
import net.edralzar.jreadability.data.Tag;

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
		Gson gson = new Gson();
		try (Reader r = new InputStreamReader(
				ResponseParsingTest.class.getResourceAsStream("/" + fileName));) {
			return gson.fromJson(r, clazz);
		} catch (IOException e) {
			fail();
			return null;
		}
	}

}
