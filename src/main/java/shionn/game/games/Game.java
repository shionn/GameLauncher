package shionn.game.games;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Game implements Comparable<Game> {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private String letter;
	private String name;
	private List<String> instalers;
	private List<String> instalersImgs;
	private boolean installed;
	private String installedFolder;
	private String proton;
	private String runfile;
	private String gameId;
	private String store;

	@Override
	public int compareTo(Game o) {
		int c = letter.compareTo(o.letter);
		if (c == 0) {
			c = name.compareTo(o.name);
		}
		return c;
	}

	public boolean isRunnable() {
		return proton != null && isInstalled() && runfile != null;
	}

	public boolean isInstallable() {
		return proton != null && !isInstalled();
	}

	public void setProton(String proton) {
		String old = this.proton;
		this.proton = proton;
		pcs.firePropertyChange("proton", old, this.proton);
		saveConfiguration();
	}

	public void setInstalled(boolean installed) {
		boolean old = this.installed;
		this.installed = installed;
		pcs.firePropertyChange("installed", old, this.installed);
	}

	public void setRunfile(String runfile) {
		String old = this.runfile;
		this.runfile = runfile;
		pcs.firePropertyChange("runfile", old, this.runfile);
		saveConfiguration();
	}

	public void loadConfiguration() {
		File file = new File(installedFolder + "/configuration.properties");
		if (file.exists())
			try (FileReader reader = new FileReader(file)) {
				Properties props = new Properties();
				props.load(reader);
				proton = props.getProperty("proton", null);
				runfile = props.getProperty("runfile", null);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
	}

	public void saveConfiguration() {
		if (isInstalled()) {
			Properties props = new Properties();
			Optional.ofNullable(proton).ifPresent(v -> props.put("proton", v));
			Optional.ofNullable(runfile).ifPresent(v -> props.put("runfile", v));
			try {
				props.store(new FileWriter(installedFolder + "/configuration.properties"), "Save " + name);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
