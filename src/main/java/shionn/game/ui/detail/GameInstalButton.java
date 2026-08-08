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
//		setFont(getFont().deriveFont(Font.BOLD, 24));
//		setBackground(Color.RED);
//		setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
		addActionListener(e -> new GameRunner(engine, game).startInstall());
		setVisible(game.isInstallable());
		game.getPcs().addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setVisible(game.isInstallable());
		if (game.getInstalers().isEmpty()) {
			setEnabled(false);
			setText("Aucun instaler disponible");
		}
		revalidate();
	}


}
