package shionn.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class Configuration {
	public static final String CONF_FILE = System.getProperty("user.home") + "/.config/game-launcher.conf";

	private static final String DEFAULT_CONF = """
# Dossier contenant les installeur.
# Dois etre organiser comme suit :  [Premiere Lettre]/[Nom du jeu]/
# exemple : A/Alan Wake/
folder.instalers=/media/Games/
# Dossier d'instalation
folder.games=~/Games/
# Dossier avec les protons
folder.proton=~/Games/.proton/

gamescope.resolutions=5120x1440,3360x1440,2560x1440,\
3840x1080,2560x1080,1920x1080,\
2800x1200,1920x1200,1600x1200,\
1680x1050,\
1280x1024,\
3200x900,2100x900,1600x900,1440x900,\
1280x800,\
1152x864,\
1024x768,\
2560x720,1680x720,1280x720,\
832x624,\
800x600,\
640x480,\
720x400
""";

	private Properties props = new Properties();

	public Configuration() {
		File source = new File(CONF_FILE);
		if (!source.exists()) {
			try (FileOutputStream fos = new FileOutputStream(source); PrintWriter pw = new PrintWriter(fos)) {
				pw.print(DEFAULT_CONF);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		try (InputStream is = new FileInputStream(source)) {
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

	public String[] gamescopeResolutions() {
		return props.getProperty("gamescope.resolutions").split(",");
	}

}
