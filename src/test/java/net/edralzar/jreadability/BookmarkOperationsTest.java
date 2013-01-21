package net.edralzar.jreadability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Desktop;
import java.net.URI;

import javax.swing.JOptionPane;

import junit.framework.Assert;
import net.edralzar.jreadability.data.Bookmark;
import net.edralzar.jreadability.oauth.store.FileStore;
import net.edralzar.jreadability.oauth.store.ITokenStore;
import net.edralzar.jreadability.service.ReadabilityService;
import net.edralzar.jreadability.service.ReadabilityServiceBuilder;
import net.edralzar.jreadability.service.ReadabilityServiceBuilder.IAuthenticationDelegate;

import org.junit.BeforeClass;
import org.junit.Test;

public class BookmarkOperationsTest {

	private static ReadabilityService service;
	private static ITokenStore tokenStore = new FileStore("tokenTest");

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

	@Test
	public void testAddAndGet() {
		long id = service.addBookmark("http://isittheweekend.com/", true, false, "test1", "test2");
		assertNotNull(id);
		long id2 = service.addBookmark("http://isittheweekend.com/", false, false);
		assertNotNull(id2);
		assertEquals(id, id2);

		Bookmark b = service.getBookmark(id);
		assertTrue(b.isFavorite());
		assertFalse(b.isArchive());
		assertFalse(b.getTags().isEmpty());
		assertEquals(2, b.getTags().size());
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

}
