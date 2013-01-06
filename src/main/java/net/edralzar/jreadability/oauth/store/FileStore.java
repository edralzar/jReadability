package net.edralzar.jreadability.oauth.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.scribe.model.Token;

/**
 * A way to store the token in a file
 *
 * @author edralzar
 *
 */
public class FileStore implements ITokenStore {

	public static final String DEFAULT_TOKENFILENAME = "token";

	private File tokenFile;

	private transient Token cachedToken;

	/**
	 * stores the token in the current directory, in a file named
	 * {@value #DEFAULT_TOKENFILENAME}
	 */
	public FileStore() {
		this(new File(DEFAULT_TOKENFILENAME));
	}

	/**
	 * stores the token in a file with the specified filename (
	 *
	 * @param fileName
	 *            either the absolute filename (directory+file) or just a name
	 *            for a file in the current directory, see {@link File})
	 */
	public FileStore(String fileName) {
		this(new File(fileName));
	}

	/**
	 * stores the token in the specified {@link File}
	 *
	 * @param tokenFile
	 */
	public FileStore(File tokenFile) {
		if (tokenFile == null || tokenFile.isDirectory())
			throw new IllegalArgumentException("A file must be provided");
		this.tokenFile = tokenFile;
	}

	@Override
	public boolean saveToken(Token token) {
		if (tokenFile.exists()) {
			boolean deleted = tokenFile.delete();
			if (!deleted)
				return false;
		}

		try (ObjectOutputStream w = new ObjectOutputStream(
				new FileOutputStream(tokenFile))) {
			w.writeObject(token);
			this.cachedToken = token;
			return true;
		} catch (IOException e) {
			e.printStackTrace(); // TODO log
			return false;
		}
	}

	@Override
	public Token loadToken() {
		if (tokenFile == null || !tokenFile.isFile())
			return null;
		try (ObjectInputStream r = new ObjectInputStream(new FileInputStream(
				tokenFile))) {
			Token token = (Token) r.readObject();
			this.cachedToken = token;
			return token;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace(); // TODO log
			return null;
		}
	}

	@Override
	public Token getToken() {
		return cachedToken;
	}

}
