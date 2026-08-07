package shionn.game.ui.detail.gamescope;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;

import shionn.game.games.Game;

public class GamescopeEnabledCheckBox extends JCheckBox implements PropertyChangeListener {

	private static final long serialVersionUID = 2546322693344931826L;

	public GamescopeEnabledCheckBox(Game game) {
		super("Gamescope");
		setSelected(game.isGamescopeEnabled());
		addActionListener(e -> game.setGamescopeEnabled(isSelected()));

		game.getPcs().addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}

}
