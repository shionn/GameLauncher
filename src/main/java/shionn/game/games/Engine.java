package shionn.game.games;

import java.beans.PropertyChangeSupport;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Engine {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private List<Game> games;
	private List<Proton> protons;
	private boolean displayInstalledGameOnly;

	public Proton proton(String proton) {
		return protons.stream().filter(p -> p.getName().equals(proton)).findAny().orElse(null);
	}

	public void setDisplayInstalledGameOnly(boolean displayInstalledGameOnly) {
		boolean old = this.displayInstalledGameOnly;
		this.displayInstalledGameOnly = displayInstalledGameOnly;
		pcs.firePropertyChange("displayInstalledGameOnly", displayInstalledGameOnly, old);
	}

}
