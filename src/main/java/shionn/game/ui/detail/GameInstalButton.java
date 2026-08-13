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
		setText(buildText(game));
		game.getPcs().addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setVisible(!game.isInstalled() && game.getProcess() == null);
		setText(buildText(game));
		setEnabled(game.isInstallable());
		revalidate();
	}


	private String buildText(Game game) {
		if (game.isInstallable()) {
			return "Installer";
		}
		if (game.isWindowsPlatform()) {
			return "Selectionner une version de proton";
		}
		return "Aucun instaler disponible";
	}

}
