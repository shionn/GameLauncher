package shionn.game.ui.detail;

import javax.swing.JButton;

import shionn.game.games.Game;

public class GameKillButton extends JButton {
	private static final long serialVersionUID = -7689776863061708556L;

	public GameKillButton(Game game) {
		super("Stopper");
		setVisible(game.getProcess() != null);
		addActionListener(e -> {
			game.getProcess().destroy();
			game.setProcess(null);
		});
		game.getPcs().addPropertyChangeListener(evt -> setVisible(game.getProcess() != null));
	}

}
