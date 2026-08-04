package shionn.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	private Properties props = new Properties();

	public Configuration() {
		try (InputStream is = Thread
				.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("configuration.properties")) {
			props.load(is);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public String instalersFolder() {
		return getFolder("folder.instalers");
	}

	public String protonsFolder() {
		return getFolder("folder.proton");
	}

	public String gamesFolder() {
		return getFolder("folder.games");
	}

	private String getFolder(String prop) {
		String folder = props.getProperty(prop);
		folder = folder.replace("~", System.getProperty("user.home"));
		return folder;
	}

}
