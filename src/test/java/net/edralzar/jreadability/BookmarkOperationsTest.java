package net.edralzar.jreadability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

import javax.swing.JOptionPane;

import junit.framework.Assert;
import net.edralzar.jreadability.data.AppliedTag;
import net.edralzar.jreadability.data.Bookmark;
import net.edralzar.jreadability.data.Tag;
import net.edralzar.jreadability.oauth.store.FileStore;
import net.edralzar.jreadability.oauth.store.ITokenStore;
import net.edralzar.jreadability.service.ReadabilityService;
import net.edralzar.jreadability.service.ReadabilityServiceBuilder;
import net.edralzar.jreadability.service.ReadabilityServiceBuilder.IAuthenticationDelegate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BookmarkOperationsTest {

	private static ReadabilityService service;
	private static ITokenStore tokenStore = new FileStore("tokenTest");

	private static long testItemId = -1L;

	@BeforeClass
	public static void beforeAll() {
		service = ReadabilityServiceBuilder.build(tokenStore, new IAuthenticationDelegate() {

			@Override
			public String onAuthenticationNeeded(String urlForAuthentication) {
				try {
					Desktop.getDesktop().browse(new URI(urlForAuthentication));
				} catch (Exception e) {
					Assert.fail(e.getMessage());
				}
				return JOptionPane.showInputDialog("Please enter Readability PIN: ");
			}
		});
	}

	@AfterClass
	public static void cleanUp() {
		if (testItemId != -1L) {
			service.deleteBookmark(testItemId);
		}
	}

	@Test
	public void testAddAndGet() {
		testItemId = service.addBookmark("http://isittheweekend.com/", true,
				false, "test1", "test2");
		assertNotNull(testItemId);
		long id2 = service.addBookmark("http://isittheweekend.com/", false, false);
		assertNotNull(id2);
		assertEquals(testItemId, id2);

		Bookmark b = service.getBookmark(testItemId);
		assertTrue(b.isFavorite());
		assertFalse(b.isArchive());
		assertFalse(b.getTags().isEmpty());
		assertTrue(b.getTags().size() >= 2);
		assertNotNull(b.getArticle());
		assertEquals("http://isittheweekend.com/", b.getArticle().getUrl().toString());
	}

	@Test
	public void testDelete() {
		long id = service.addBookmark("http://isitchristmas.com/", false, false);
		Bookmark b = service.getBookmark(id);
		assertNotNull(b);
		service.deleteBookmark(id);
		try {
			service.getBookmark(id);
		} catch (ReadabilityRestException r) {
			assertEquals(404, r.getCode());
		}
	}

	@Test
	public void testAddTags() {
		if (testItemId == -1L) {
			testAddAndGet();
		}

		int tagCount = service.getBookmark(testItemId).getTags().size();

		List<AppliedTag> result = service.tagBookmark(testItemId, "test3");
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("test3", result.get(0).getText());

		List<Tag> allTags = service.getBookmark(testItemId).getTags();

		assertEquals(tagCount + 1, allTags.size());
		boolean contains3 = false;
		for (Tag t : allTags) {
			if (t.getText().equals("test3")) {
				contains3 = true;
			}
		}
		assertTrue(contains3);
	}

}
