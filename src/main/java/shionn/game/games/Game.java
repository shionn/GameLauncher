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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Game implements Comparable<Game> {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Setter
	private String letter;
	@Setter
	private String name;
	@Setter
	private List<String> instalers;
	@Setter
	private List<String> instalersImgs;
	private boolean installed;
	@Setter
	private String installedFolder;
	private String proton;
	private String runfile;
	@Setter
	private String gameId;
	@Setter
	private String store;

	private boolean gamescopeEnabled;
	private String gamescopeInResolution;
	private String gamescopeOutResolution;
	private GamescopeUpscaleFilterMode gamescopeUpscaleFilterMode;

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

	public void setGamescopeEnabled(boolean gamescopeEnabled) {
		boolean old = this.gamescopeEnabled;
		this.gamescopeEnabled = gamescopeEnabled;
		pcs.firePropertyChange("gamescopeEnabled", old, this.gamescopeEnabled);
		saveConfiguration();
	}

	public void setGamescopeInResolution(String gamescopeInResolution) {
		String old = this.gamescopeInResolution;
		this.gamescopeInResolution = gamescopeInResolution;
		pcs.firePropertyChange("gamescopeInResolution", old, this.gamescopeInResolution);
		saveConfiguration();
	}

	public void setGamescopeOutResolution(String gamescopeOutResolution) {
		String old = this.gamescopeOutResolution;
		this.gamescopeOutResolution = gamescopeOutResolution;
		pcs.firePropertyChange("gamescopeOutResolution", old, this.gamescopeOutResolution);
		saveConfiguration();
	}

	public void setGamescopeUpscaleFilterMode(GamescopeUpscaleFilterMode gamescopeUpscaleFilterMode) {
		GamescopeUpscaleFilterMode old = this.gamescopeUpscaleFilterMode;
		this.gamescopeUpscaleFilterMode = gamescopeUpscaleFilterMode;
		pcs.firePropertyChange("gamescopeUpscaleFilterMode", old, this.gamescopeUpscaleFilterMode);
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
				gamescopeEnabled = Boolean.parseBoolean(props.getProperty("gameScopeEnable", "false"));
				gamescopeInResolution = props.getProperty("gamescopeInResolution", null);
				gamescopeOutResolution = props.getProperty("gamescopeOutResolution", null);
				gamescopeUpscaleFilterMode = Optional
						.ofNullable(props.getProperty("gamescopeUpscaleFilterMode", null))
						.map(GamescopeUpscaleFilterMode::valueOf)
						.orElse(null);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
	}

	public void saveConfiguration() {
		if (isInstalled()) {
			Properties props = new Properties();
			Optional.ofNullable(proton).ifPresent(v -> props.put("proton", v));
			Optional.ofNullable(runfile).ifPresent(v -> props.put("runfile", v));
			props.put("gamescopeEnabled", Boolean.toString(gamescopeEnabled));
			Optional.ofNullable(gamescopeInResolution).ifPresent(v -> props.put("gamescopeInResolution", v));
			Optional.ofNullable(gamescopeOutResolution).ifPresent(v -> props.put("gamescopeOutResolution", v));
			Optional
					.ofNullable(gamescopeUpscaleFilterMode)
					.ifPresent(v -> props.put("gamescopeUpscaleFilterMode", v.name()));
			try {
				props.store(new FileWriter(installedFolder + "/configuration.properties"), "Save " + name);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
