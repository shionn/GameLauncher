package shionn.game.ui.detail;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import shionn.game.games.Engine;
import shionn.game.games.Game;
import shionn.game.launcher.GameRunner;

public class GameInstalButton extends JButton implements PropertyChangeListener {

	private static final long serialVersionUID = -3644937961355365317L;
	private Game game;

	public GameInstalButton(Engine engine, Game game) {
		super("Installer");
		this.game = game;
		addActionListener(e -> new GameRunner(engine, game).startInstall());
		setVisible(!game.isInstalled() && game.getProcess() == null);
		setEnabled(game.isInstallable());
		setText(game.isInstallable() ? "Installer" : "Aucun instaler disponible");
		game.getPcs().addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setVisible(!game.isInstalled() && game.getProcess() == null);
		setText(game.isInstallable() ? "Installer" : "Aucun instaler disponible");
		setEnabled(game.isInstallable());
		revalidate();
	}


}
