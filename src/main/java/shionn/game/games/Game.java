package shionn.game.games;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Builder.Default;
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
	private List<String> archives;
	@Setter
	private List<String> instalersImgs;
	private boolean installed;
	@Setter
	private String installedFolder;
	private String proton;
	private String runfile;
	@Default
	private List<String> runArgs = new ArrayList<>();
	@Default
	private List<String> envArgs = new ArrayList<>();
	@Setter
	private String gameId;
	@Setter
	private String store;
	@Setter
	private String platform;
	private Process process;

	private boolean mangohudEnabled;
	private boolean feralGamemodeEnabled;
	private boolean gamescopeEnabled;
	private GamescopeWindowMode gamescopeWindowMode;
	private String gamescopeInResolution;
	private String gamescopeOutResolution;
	private GamescopeUpscaleFilterMode gamescopeUpscaleFilterMode;
	private GamescopeUpscaleScalerMode gamescopeUpscaleScalerMode;
	private boolean gamescopeForceGrapCursor;

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
		if (store == null) {
			return false;
		}
		return switch(store) {
		case "gog" -> proton != null && !instalers.isEmpty();
		case "steam" -> proton != null && !archives.isEmpty() && !"linux".equals(platform);
		case "abandonware" -> proton != null;
		default -> false;
		};
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

	public void setMangohudEnabled(boolean mangohudEnabled) {
		boolean old = this.mangohudEnabled;
		this.mangohudEnabled = mangohudEnabled;
		pcs.firePropertyChange("mangohudEnabled", old, this.mangohudEnabled);
		saveConfiguration();
	}

	public void setFeralGamemodeEnabled(boolean feralGamemodeEnabled) {
		boolean old = this.feralGamemodeEnabled;
		this.feralGamemodeEnabled = feralGamemodeEnabled;
		pcs.firePropertyChange("feralGamemodeEnabled", old, this.feralGamemodeEnabled);
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

	public void setGamescopeWindowMode(GamescopeWindowMode gamescopeWindowMode) {
		GamescopeWindowMode old = this.gamescopeWindowMode;
		this.gamescopeWindowMode = gamescopeWindowMode;
		pcs.firePropertyChange("gamescopeWindowMode", old, this.gamescopeWindowMode);
		saveConfiguration();
	}

	public void setGamescopeUpscaleScalerMode(GamescopeUpscaleScalerMode gamescopeUpscaleScalerMode) {
		GamescopeUpscaleScalerMode old = this.gamescopeUpscaleScalerMode;
		this.gamescopeUpscaleScalerMode = gamescopeUpscaleScalerMode;
		pcs.firePropertyChange("gamescopeUpscaleScalerMode", old, this.gamescopeUpscaleScalerMode);
		saveConfiguration();
	}

	public void setGamescopeForceGrapCursor(boolean gamescopeForceGrapCursor) {
		boolean old = this.gamescopeForceGrapCursor;
		this.gamescopeForceGrapCursor = gamescopeForceGrapCursor;
		pcs.firePropertyChange("gamescopeForceGrapCursor", old, this.gamescopeForceGrapCursor);
		saveConfiguration();
	}

	public void setRunArgs(List<String> runArgs) {
		List<String> old = this.runArgs;
		this.runArgs = runArgs;
		pcs.firePropertyChange("runArgs", old, this.runArgs);
		saveConfiguration();
	}

	public void setEnvArgs(List<String> envArgs) {
		List<String> old = this.envArgs;
		this.envArgs = envArgs;
		pcs.firePropertyChange("envArgs", old, this.envArgs);
		saveConfiguration();
	}

	public void setProcess(Process process) {
		Process old = this.process;
		this.process = process;
		pcs.firePropertyChange("process", old, this.process);
	}

	public void loadConfiguration() {
		File file = new File(installedFolder + "/configuration.properties");
		if (file.exists())
			try (FileReader reader = new FileReader(file)) {
				Properties props = new Properties();
				props.load(reader);
				proton = props.getProperty("proton", null);
				runfile = props.getProperty("runfile", null);
				Arrays
						.stream(props.getProperty("runArgs", "").split(","))
						.filter(s -> !s.isEmpty())
						.forEach(runArgs::add);
				Arrays
						.stream(props.getProperty("envArgs", "").split(","))
						.filter(s -> !s.isEmpty())
						.forEach(envArgs::add);
				mangohudEnabled = Boolean.parseBoolean(props.getProperty("mangohudEnabled", "false"));
				feralGamemodeEnabled = Boolean.parseBoolean(props.getProperty("feralGamemodeEnabled", "false"));
				gamescopeEnabled = Boolean.parseBoolean(props.getProperty("gamescopeEnabled", "false"));
				gamescopeInResolution = props.getProperty("gamescopeInResolution", null);
				gamescopeOutResolution = props.getProperty("gamescopeOutResolution", null);
				gamescopeUpscaleFilterMode = Optional
						.ofNullable(props.getProperty("gamescopeUpscaleFilterMode", null))
						.map(GamescopeUpscaleFilterMode::valueOf)
						.orElse(null);
				gamescopeWindowMode = Optional
						.ofNullable(props.getProperty("gamescopeWindowMode", null))
						.map(GamescopeWindowMode::valueOf)
						.orElse(null);
				gamescopeUpscaleScalerMode = Optional
						.ofNullable(props.getProperty("gamescopeUpscaleScalerMode", null))
						.map(GamescopeUpscaleScalerMode::valueOf)
						.orElse(null);
				gamescopeForceGrapCursor = Boolean.parseBoolean(props.getProperty("gamescopeForceGrapCursor", "false"));
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
	}

	public void saveConfiguration() {
		if (isInstalled()) {
			Properties props = new Properties();
			Optional.ofNullable(proton).ifPresent(v -> props.put("proton", v));
			Optional.ofNullable(runfile).ifPresent(v -> props.put("runfile", v));
			props.put("mangohudEnabled", Boolean.toString(mangohudEnabled));
			props.put("feralGamemodeEnabled", Boolean.toString(feralGamemodeEnabled));
			props.put("gamescopeEnabled", Boolean.toString(gamescopeEnabled));
			Optional.ofNullable(gamescopeInResolution).ifPresent(v -> props.put("gamescopeInResolution", v));
			Optional.ofNullable(gamescopeOutResolution).ifPresent(v -> props.put("gamescopeOutResolution", v));
			Optional
					.ofNullable(gamescopeUpscaleFilterMode)
					.ifPresent(v -> props.put("gamescopeUpscaleFilterMode", v.name()));
			Optional.ofNullable(gamescopeWindowMode).ifPresent(v -> props.put("gamescopeWindowMode", v.name()));
			Optional
					.ofNullable(gamescopeUpscaleScalerMode)
					.ifPresent(v -> props.put("gamescopeUpscaleScalerMode", v.name()));
			props.put("gamescopeForceGrapCursor", Boolean.toString(gamescopeForceGrapCursor));
			props.put("runArgs", runArgs.stream().collect(Collectors.joining(",")));
			props.put("envArgs", envArgs.stream().collect(Collectors.joining(",")));
			try {
				props.store(new FileWriter(installedFolder + "/configuration.properties"), "Save " + name);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
