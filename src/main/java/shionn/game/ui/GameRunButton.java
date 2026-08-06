package shionn.game.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import shionn.game.games.Engine;
import shionn.game.games.Game;
import shionn.game.launcher.GameRunner;

public class GameRunButton extends JButton implements PropertyChangeListener {
	private static final long serialVersionUID = 7206045278803514454L;
	private Game game;

	public GameRunButton(Engine engine, Game game) {
		super("Lancer");
		this.game = game;
		addActionListener(e -> new GameRunner(engine, game).start());
//		setFont(getFont().deriveFont(Font.BOLD, 24));
//		setBackground(Color.GREEN);
//		setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
		setVisible(game.isRunnable());
		game.getPcs().addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setVisible(game.isRunnable());
		revalidate();
	}

}
