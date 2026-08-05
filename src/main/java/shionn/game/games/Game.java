package shionn.game.games;

import java.beans.PropertyChangeSupport;
import java.util.List;

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
	}

	public void setInstalled(boolean installed) {
		boolean old = this.installed;
		this.installed = installed;
		pcs.firePropertyChange("installed", old, this.installed);
	}

	public void setRunFile(String runfile) {
		String old = this.runfile;
		this.runfile = runfile;
		pcs.firePropertyChange("runfile", old, this.runfile);
	}

}
