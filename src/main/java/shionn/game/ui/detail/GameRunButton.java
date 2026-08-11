package shionn.game.ui.detail;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import shionn.game.games.Engine;
import shionn.game.games.Game;

public class GameRunButton extends JButton implements PropertyChangeListener {
	private static final long serialVersionUID = 7206045278803514454L;
	private Game game;

	public GameRunButton(Engine engine, Game game, String label, Runnable action) {
		super(label);
		this.game = game;
		addActionListener(e -> action.run());
		setVisible(game.isRunnable() && game.getProcess() == null);
		game.getPcs().addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setVisible(game.isRunnable() && game.getProcess() == null);
		revalidate();
	}

}
